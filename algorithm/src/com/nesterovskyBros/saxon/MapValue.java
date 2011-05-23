package com.nesterovskyBros.saxon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.EmptyIterator;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.sort.StringCollator;
import net.sf.saxon.trans.NoDynamicContextException;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.type.AtomicType;
import net.sf.saxon.type.BuiltInAtomicType;
import net.sf.saxon.type.ConversionResult;
import net.sf.saxon.type.ValidationFailure;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.ShareableSequence;
import net.sf.saxon.value.Value;

/**
 * This class defines a map, apporximating
 * http://www.w3.org/Bugs/Public/show_bug.cgi?id=5630 proposal.
 */
@SuppressWarnings("serial")
public class MapValue extends AtomicValue
{
  /**
   * Creates a MapValue instance, using existing map.
   * @param map - a map instance.
   * @param typeLabel - type label.
   */
  public MapValue(Map<AtomicValue, Value> map, AtomicType typeLabel)
  {
    this.typeLabel = typeLabel;
    this.map = map;    
  }
  
  /**
   * Constructs a MapValue instance, based on sorted map.
   * This constructor permits multiple values for a key.
   * @param iterator - a value (a sequence of key, value pairs) 
   *   contained in a map.
   * @param orders - a value (possibly a sequence) defining an order of 
   *   keys (ascending, descending) in map.
   */
  public MapValue(SequenceIterator iterator, Value order)
    throws XPathException
  {
    this.typeLabel = BuiltInAtomicType.ANY_ATOMIC;
    
    final boolean orders[] = new boolean[order.getLength()];
    
    for(int i = 0; i < orders.length; i++)
    {
      orders[i] = Value.asValue(order.itemAt(i)).effectiveBooleanValue();
    }
    
    this.map = new TreeMap<AtomicValue, Value>(
      new Comparator<AtomicValue>()
      {
        @SuppressWarnings("unchecked")
        public int compare(AtomicValue first, AtomicValue second)
        {
          try
          {
            if (first instanceof TupleValue)
            {
              TupleValue firstTuple = (TupleValue)first;
              
              if (second instanceof TupleValue)
              {
                TupleValue secondTuple = (TupleValue)second;
                
                SequenceIterator firstIterator = 
                  firstTuple.getValue().iterate();
                SequenceIterator secondIterator = 
                  secondTuple.getValue().iterate();
                int index = 0;
                
                while(true) 
                {
                  boolean order = (orders.length <= index) || orders[index]; 
                  Item firstItem = firstIterator.next();
                  Item secondItem = secondIterator.next();
                  
                  if (firstItem == null)
                  {
                    if (secondItem == null)
                    {
                      return 0;
                    }
                    else
                    {
                      return order ? -1 : 1;
                    }
                  }
                  else 
                  {
                    if (secondItem == null)
                    {
                      return order ? +1 : -1;
                    }
                  }
                  
                  Value firstValue;
                  
                  // Note: we atomize NodeInfo.
                  if (firstItem instanceof NodeInfo)
                  {
                    NodeInfo node = (NodeInfo)firstItem;
                    
                    firstValue = node.atomize();
                  }
                  else
                  {
                    firstValue = Value.asValue(firstItem);
                  }
                  
                  Value secondValue;
                  
                  // Note: we atomize NodeInfo.
                  if (secondItem instanceof NodeInfo)
                  {
                    NodeInfo node = (NodeInfo)secondItem;
                    
                    secondValue = node.atomize();
                  }
                  else
                  {
                    secondValue = Value.asValue(secondItem);
                  }
  
                  int c = firstValue.getSchemaComparable().
                    compareTo(secondValue.getSchemaComparable());
                  
                  if (c != 0)
                  {
                    if (c == INDETERMINATE_ORDERING)
                    {
                      return INDETERMINATE_ORDERING;
                    }
                    else 
                    {
                      return order ? c : -c;
                    }
                  }
                }
              }
            }
          
            int c = first.getSchemaComparable().
              compareTo(second.getSchemaComparable());
            
            if (c == INDETERMINATE_ORDERING)
            {
              return INDETERMINATE_ORDERING;
            }
            else 
            {
              boolean order = (orders.length == 0) || orders[0]; 
  
              return order ? c : -c;
            }
          }
          catch(XPathException e)
          {
            return INDETERMINATE_ORDERING;
          }
        }
      });
    
    populate(map, iterator, false);
  }
  
  /**
   * Gets a map contained in the MapValue.
   * @return a Map instance.
   */
  public Map<AtomicValue, Value> getMap()
    throws XPathException
  {
    return map;
  }
  
  /**
   * Gets key, value pairs for the map.
   * @return key, value pairs for the map.
   */
  public SequenceIterator getItems()
    throws XPathException
  {
    return new MapIterator(map, MapIterator.Items);
  }

  /**
   * Gets map keys.
   * @return map keys.
   */
  public SequenceIterator getKeys()
    throws XPathException
  {
    return new MapIterator(map, MapIterator.Keys);
  }

  /**
   * Gets map values.
   * @return map values.
   */
  public SequenceIterator getValues()
    throws XPathException
  {
    return new MapIterator(map, MapIterator.Values);
  }
  
  /**
   * Gets map values for a specified key.
   * @param key - a key to get values for.
   * @return map values for a specified key.
   */
  public Value getValue(AtomicValue key)
    throws XPathException
  {
    return map.get(key);
  }

  /**
   * Converts map to a build in atomic type.
   * Note: map can be converted to {@link BuiltInAtomicType#ANY_ATOMIC} only.
   * @param requiredType - the required atomic type.
   * @param validate - true if validation is required.
   * @param context - the conversion context to be used.
   * @return the result of the conversion, if successful. 
   *   If unsuccessful, the value returned will be a ValidationFailure. 
   */
  @Override
  protected ConversionResult convertPrimitive(
    BuiltInAtomicType requiredType, 
    boolean validate, 
    XPathContext context)
  {
    if (requiredType == BuiltInAtomicType.ANY_ATOMIC)
    {
      return this;
    }
    else
    {
      ValidationFailure failure = new ValidationFailure(
        "Map cannot be converted to " + requiredType.getDescription());
      
      failure.setErrorCode("MAP0001");
  
      return failure;
    }
  }

  /**
   * Create a copy of this atomic value, with a different type label.
   * Note: map cannot be converted to other type.
   * @param typeLabel - the type label of the new copy.
   * @return the copied value.
   */
  @Override
  public AtomicValue copyAsSubType(AtomicType typeLabel)
  {
    return new MapValue(map, typeLabel);
  }

  /**
   * Determine the primitive type of the value.
   * @return the primitive type
   */
  @Override
  public BuiltInAtomicType getPrimitiveType()
  {
    return BuiltInAtomicType.ANY_ATOMIC;
  }

  /**
   * Convert the value to a string, using the serialization rules.
   */
  @Override
  public String getStringValue()
  {
    // NOTE: the following try block is due to the fact that
    //  AtomicValue.getStringValue() have no XPathException in throws clause.
    try
    {
      SequenceExtent sequence = new SequenceExtent(getItems());
      
      return sequence.getStringValue();
    }
    catch(XPathException e)
    {
      throw new AssertionError("Cannot convert map into string.");
    }
  }
  
  /**
   * Get the effective boolean value of the value.
   * @return true, if map is not empty, and false otherwise.
   */
  @Override
  public boolean effectiveBooleanValue() throws XPathException
  {
    return !map.isEmpty();
  }

  /**
   * Tests whether a tuple value is equal to other value.
   * @param other - an object to compare with.
   * Returns if objects are equal, and false otherwise.
   */
  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    
    if (!(other instanceof MapValue))
    {
      return false;
    }
    
    MapValue otherMap = (MapValue)other;
    
    if (map.size() != otherMap.map.size())
    {
      return false;
    }
    
    try
    {
      for(Map.Entry<AtomicValue, Value> entry: map.entrySet())
      {
        Value otherValue = otherMap.map.get(entry.getKey());
        
        if ((otherValue == null) || 
          (TupleValue.compare(entry.getValue(), otherValue) != 0))
        {
          return false;
        }
      }
    }
    catch(XPathException e)
    {
      return false;
    }
    
    return false;
  }
  
  /**
   * Returns a hash code value for the map.
   */
  @Override
  public int hashCode()
  {
    int hash = 0x06639662;  // arbitrary seed
    
    for(Map.Entry<AtomicValue, Value> entry: map.entrySet())
    {
      hash ^= TupleValue.hashCode(entry.getKey()) ^ 
        TupleValue.hashCode(entry.getValue());
    }
    
    return hash;
  }
  
  // NOTE: we do not implement Comparable as there is no single way 
  //   to order maps. 
  /**
   * Get a Comparable value that implements the XML Schema ordering 
   * comparison semantics for this value.
   *
   * In the case of data types that are partially ordered, 
   * the returned Comparable extends the standard semantics 
   * of the compareTo() method by returning the value 
   * {@link #INDETERMINATE_ORDERING} when there is no defined 
   * order relationship between two given values. This value is 
   * also returned when two values of different types are compared.
   *
   * @return a Comparable that follows XML Schema comparison rules.
   */
  @Override
  public Comparable getSchemaComparable()
  {
    return new Comparable()
    {
      public int compareTo(Object other) 
      {
        return equals(other) ? 0 : INDETERMINATE_ORDERING;
      }

      public boolean equals(Object other) 
      {
        return MapValue.this.equals(other);
      }

      public int hashCode() 
      {
        return MapValue.this.hashCode();
      }
    };
  }

  /**
   * Get an object value that implements the XPath equality and 
   * ordering comparison semantics for this value.
   * 
   * @param ordered - true if an ordered comparison is required.
   * @param collator - the collation to be used when comparing strings.
   * @param context - the XPath dynamic evaluation context, used in cases 
   *   where the comparison is context sensitive.
   * @return Comparable object.
   */
  @Override
  public Object getXPathComparable(
    boolean ordered, 
    StringCollator collator, 
    XPathContext context) 
    throws NoDynamicContextException
  {
    return ordered ? null : this;
  }

  /**
   * Populates a map with values from sequence.
   * @param map - a destination map.
   * @param iterator - input sequence.
   * @param unique - true to allow unique key only, and 
   *   false to allow multiple values for a same key.
   */
  @SuppressWarnings("unchecked")
  private static void populate(
    Map<AtomicValue, Value> map, 
    SequenceIterator iterator,
    boolean unique)
    throws XPathException
  {
    while(true)
    {
      Item item = iterator.next();
      
      if (item == null)
      {
        break;
      }
      
      AtomicValue key = (AtomicValue)item;
      
      item = iterator.next();
      
      if (item == null)
      {
        throw new XPathException(
          "Invalid input sequence for map. Expected even number of elements", 
          "MAP0001");
      }
      
      Value value = Value.asValue(item);
      Value mapValue = map.put(key, value);
      
      if (mapValue != null)
      {
        if (unique)
        {
          throw new XPathException(
            "Duplicate key in input sequence for a map.", 
            "MAP0002");
        }
        
        List<Item> list;
        
        if (mapValue instanceof ShareableSequence)
        {
          ShareableSequence values = (ShareableSequence)mapValue;
          
          list = values.getList();
        }
        else
        {
          list = new ArrayList<Item>();
          list.add(Value.asItem(mapValue));
        }

        list.add(Value.asItem(value));
        map.put(key, new ShareableSequence(list));
      }
    }    
  }

  /**
   * Iterates over keys, values, or items of the map.
   */
  private static class MapIterator implements SequenceIterator
  {
    /**
     * Specifies iteration over map keys.
     */
    public static final int Keys = 0;
    
    /**
     * Specifies iteration over map values.
     */
    public static final int Values = 1;
    
    /**
     * Specifies iteration over key, value pairs.
     */
    public static final int Items = 2;
    
    /**
     * Creates instance of MapIterator.
     * @param map - a source map.
     * @param options - iteration options.
     */
    public MapIterator(Map<AtomicValue, Value> map, int options)
    {
      this.map = map;
      this.options = options;
    }
    
    /**
     * Get the current value in the sequence.
     * @return the current item.
     */
    public Item current()
    {
      if (position <= 0)
      {
        return null;
      }
      
      if (currentIsKey)
      {
        return key;
      }
      else
      {
        return valueIterator.current();
      }
    }

    /**
     * Get another SequenceIterator that iterates over the same items as 
     * the original, but which is repositioned at the start of the sequence.
     * @return a SequenceIterator that iterates over the same items,
     *   positioned before the first item
     */
    public SequenceIterator getAnother()
      throws XPathException
    {
      return new MapIterator(map, options);
    }

    /**
     * Get properties of this iterator, as a bit-significant integer.
     * Note: no special properties are supported.
     * @return the properties of this iterator.
     */
    public int getProperties()
    {
      return 0;
    }

    public Item next()
      throws XPathException
    {
      if (position < 0)
      {
        return null;
      }
      
      if (position == 0)
      {
        mapIterator = map.entrySet().iterator();
      }
      
      switch(options)
      {
        case Keys:
        {
          if (!mapIterator.hasNext())
          {
            position = -1;
            
            return null;
          }

          key = mapIterator.next().getKey();
          ++position;
          currentIsKey = true;
          
          return key;
        }
        case Values:
        {
          Item item = valueIterator.next();
          
          if (item == null)
          {
            if (!mapIterator.hasNext())
            {
              position = -1;
              
              return null;
            }
            
            valueIterator = mapIterator.next().getValue().iterate();
            item = valueIterator.next();
            currentIsKey = false;
            
            assert item != null;
          }
          
          ++position;
          
          return item;
        }
        case Items:
        {
          if (currentIsKey)
          {
            Item item = valueIterator.current();
            
            assert item != null;
            
            valueIterator.next();
            ++position;
            currentIsKey = false;
            
            return item;
          }
          else
          {
            if ((key == null) || (valueIterator.current() == null))
            {
              if (!mapIterator.hasNext())
              {
                position = -1;
                
                return null;
              }
              
              Map.Entry<AtomicValue, Value> entry = mapIterator.next();
              
              key = entry.getKey();
              valueIterator = entry.getValue().iterate();
            }

            ++position;
            currentIsKey = true;
            
            return key;
          }
        }
        default:
        {
          throw new IllegalStateException();
        }
      }
    }

    /**
     * Get the current position.
     * @return the current position.
     */
    public int position()
    {
      return position;
    }
    
    // Iterator options.
    int options;
    
    // Original map.
    Map<AtomicValue, Value> map;

    // Map entry iterator.
    Iterator<Map.Entry<AtomicValue, Value>> mapIterator;

    // Current key.
    AtomicValue key;
    
    // Current value iterator.
    SequenceIterator valueIterator = EmptyIterator.getInstance();

    // Current position.
    int position;
    
    // true if current item is a key, and false if current item is a value.
    boolean currentIsKey;

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
  }

  // Contained map.
  private final Map<AtomicValue, Value> map;
}
