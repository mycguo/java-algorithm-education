package com.nesterovskyBros.saxon;

import net.sf.saxon.expr.XPathContext;
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
import net.sf.saxon.value.Value;

/**
 * This class defines a tuple, apporximating
 * http://www.w3.org/Bugs/Public/show_bug.cgi?id=5630 proposal.
 */
@SuppressWarnings("serial")
public class TupleValue extends AtomicValue implements Comparable
{
  /**
   * Constructs a TupleValue instance.
   * @param value - a value contained in a tuple.
   * @param typeLabel - a type label.
   */
  public TupleValue(Value value, AtomicType typeLabel)
  {
    this.value = value;
    this.typeLabel = typeLabel;
  }

  /**
   * Constructs a TupleValue instance.
   * @param value - a value contained in a tuple.
   */
  public TupleValue(Value value)
    throws XPathException
  {
    this.value = value;
    this.typeLabel = BuiltInAtomicType.ANY_ATOMIC;
  }
  
  /**
   * Gets a value contained in the tuple.
   * @return a Value instance.
   */
  public Value getValue()
    throws XPathException
  {
    return value;
  }

  /**
   * Converts tuple's value to a build in atomic type.
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
        "Tuple cannot be converted to " + 
        requiredType.getDescription() + 
        ".");
      
      failure.setErrorCode("TUPLE0001");

      return failure;
    }    
  }

  /**
   * Create a copy of this atomic value, with a different type label.
   * @param typeLabel - the type label of the new copy.
   * @return the copied value.
   */
  @Override
  public AtomicValue copyAsSubType(AtomicType typeLabel)
  {
    return new TupleValue(value, typeLabel);
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
      return value.getStringValue();
    }
    catch(XPathException e)
    {
      throw new AssertionError("Cannot convert tuple into string.");
    }
  }
  
  /**
   * Get the effective boolean value of the value.
   * @return tuple values's effictive boolean value.
   */
  @Override
  public boolean effectiveBooleanValue() throws XPathException
  {
    return value.effectiveBooleanValue();
  }

  /**
   * Tests whether a tuple value is equal to other value.
   * @param other - an object to compare with.
   * Returns if objects are equal, and false otherwise.
   */
  @Override
  public boolean equals(Object other)
  {
    return compareTo(other) == 0;
  }
  
  /**
   * Returns a hash code value for the tuple.
   */
  @Override
  public int hashCode()
  {
    return hashCode(value);
  }
  
  /**
   * Compares this tuple with the specified object for order.  Returns a
   * negative integer, zero, or a positive integer as this object is less
   * than, equal to, or greater than the specified object.
   * 
   * In the case of data types that are partially ordered, the returned 
   * Comparable extends the standard semantics of the compareTo() 
   * method by returning the value {@link #INDETERMINATE_ORDERING} 
   * when there is no defined order relationship between two given values.
   *
   * @param other - the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object
   *    is less than, equal to, greater than the specified object, or
   *    {@link #INDETERMINATE_ORDERING} value.
   */
  @SuppressWarnings("unchecked")
  public int compareTo(Object other) 
  {
    if (other instanceof TupleValue)
    {
      TupleValue otherTuple = (TupleValue)other;
      
      try
      {
        return compare(value, otherTuple.value);
      } 
      catch(XPathException e)
      {
        throw new AssertionError(
          "Failure comparing tuple values: " + 
          e.getMessage());
      }
    }
    else
    {
      return INDETERMINATE_ORDERING;
    }
  }  

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
    return this;
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
    return this;
  }
  
  /**
   * Returns a hash code for a value.
   * @param value - a value to calculate hash code for.
   */
  public static int hashCode(Value value)
  {
    return value.getSchemaComparable().hashCode();
  }

  /**
   * Compares first value with the specified second value for order. Returns a
   * negative integer, zero, or a positive integer as the first value is less
   * than, equal to, or greater than the specified second value.
   * 
   * In the case of data types that are partially ordered, the returned 
   * Comparable extends the standard semantics of the compareTo() 
   * method by returning the value {@link #INDETERMINATE_ORDERING} 
   * when there is no defined order relationship between two given values.
   *
   * @param first - a first value to compare.
   * @param second - a second value to compare.
   * @return a negative integer, zero, or a positive integer as the first value
   *    is less than, equal to, greater than the specified second value, or
   *    {@link #INDETERMINATE_ORDERING}.
   */
  @SuppressWarnings("unchecked")
  public static int compare(Value first, Value second)
    throws XPathException 
  {
    if (first == second)
    {
      return 0;  
    }
    else
    {
      return compare(first.iterate(), second.iterate());
    }
  }  

  /**
   * Compares first sequence with the specified second sequence for order. 
   * Returns a negative integer, zero, or a positive integer as the 
   * first sequence is less than, equal to, or greater than the specified 
   * second sequence.
   * 
   * In the case of data types that are partially ordered, the returned 
   * Comparable extends the standard semantics of the compareTo() 
   * method by returning the value {@link #INDETERMINATE_ORDERING} 
   * when there is no defined order relationship between two given values.
   * 
   * Sequences are compared in order of items:
   *   each item is atomized before comparision;
   *   if two items are equal then comparision is continued for next items;
   *   if two items are not equal then comparision result for these items 
   *   is returned;
   *   if there is an item from first sequence and there is no item from second 
   *   one then result is +1;
   *   if there is no item from first sequence and there is an item from second 
   *   one then result is -1;
   *   
   * @param first - a first sequence to compare.
   * @param second - a second sequence to compare.
   * @return a negative integer, zero, or a positive integer as the first 
   *   sequence is less than, equal to, greater than the specified 
   *   second sequence, or {@link #INDETERMINATE_ORDERING}.
   */
  @SuppressWarnings("unchecked")
  public static int compare(SequenceIterator first, SequenceIterator second) 
    throws XPathException
  {
    while(true) 
    {
      Item firstItem = first.next();
      Item secondItem = second.next();
      
      if (firstItem == null)
      {
        if (secondItem == null)
        {
          return 0;
        }
        else
        {
          return -1;
        }
      }
      else 
      {
        if (secondItem == null)
        {
          return +1;
        }
      }
      
      Value firstValue;
      
      if (firstItem instanceof NodeInfo)
      {
        NodeInfo node = (NodeInfo)firstItem;
        
        firstValue = node.atomize();
      }
      else
      {
        firstValue = (Value)firstItem;
      }
      
      Value secondValue;
      
      if (secondItem instanceof NodeInfo)
      {
        NodeInfo node = (NodeInfo)secondItem;
        
        secondValue = node.atomize();
      }
      else
      {
        secondValue = (Value)secondItem;
      }

      int c = firstValue.getSchemaComparable().
        compareTo(secondValue.getSchemaComparable());
      
      if (c != 0)
      {
        return c;
      }
    }
  }  

  // Contained value.
  private final Value value;
}
