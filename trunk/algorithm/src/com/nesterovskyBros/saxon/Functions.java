package com.nesterovskyBros.saxon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.transform.SourceLocator;

import net.sf.saxon.Controller;

import net.sf.saxon.event.SequenceOutputter;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.XPathContextMajor;
import net.sf.saxon.expr.XPathContextMinor;

import net.sf.saxon.instruct.ApplyTemplates;
import net.sf.saxon.instruct.ParameterSet;
import net.sf.saxon.instruct.TailCall;
import net.sf.saxon.instruct.Template;

import net.sf.saxon.om.Item;
import net.sf.saxon.om.ListIterator;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StandardNames;
import net.sf.saxon.om.StructuredQName;

import net.sf.saxon.trace.InstructionInfo;

import net.sf.saxon.trans.Mode;
import net.sf.saxon.trans.Rule;
import net.sf.saxon.trans.XPathException;

import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.EmptySequence;
import net.sf.saxon.value.ObjectValue;
import net.sf.saxon.value.QNameValue;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.value.Value;

/**
 * Interface for the tuple and map classes, apporximating
 * http://www.w3.org/Bugs/Public/show_bug.cgi?id=5630 proposal, and
 * try-block api. 
 */
public class Functions
{
  /**
   * Creates a tuple for a value.
   * @param value - a value to wrap in tuple.
   * @return new tuple.
   */
  public static TupleValue tuple(Value value)
    throws XPathException
  {
    return new TupleValue(value);
  }
  
  /**
   * Returns items contained in a tuple.
   * @param tuple - a tuple to get items for.
   * @return items contained in a tuple.
   */
  public static Value tupleItems(TupleValue tuple)
    throws XPathException
  {
    return tuple.getValue();
  }
    
  /**
   * Creates a map for a sequence of pairs (key, value).
   * @param iterator - an input sequence of pairs (key, value).
   * @return new map.
   */
  public static MapValue map(SequenceIterator iterator)
    throws XPathException
  {
    return new MapValue(iterator, EmptySequence.getInstance());
  }
  
  /**
   * Creates an ordered map, with a specified ordering direction, 
   * for a sequence of pairs (key, value).
   * @param iterator - an input sequence of pairs (key, value).
   * @param order - a sort order.
   * @return new map.
   */
  public static MapValue map(SequenceIterator iterator, Value order)
    throws XPathException
  {
    return new MapValue(iterator, order);
  }

  /**
   * Returns map items.
   * Items is a sequence of (key, value) pairs.
   * @param map - a map to get items for.
   * @return a sequence of (key, value) pairs.
   */
  public static SequenceIterator mapItems(MapValue map)
    throws XPathException
  {
    return map.getItems();
  }
  
  /**
   * Returns a sequence of keys contained in the map.
   * @param map - a map to get keys for.
   * @return a sequence of keys contained in the map.
   */
  public static SequenceIterator mapKeys(MapValue map)
    throws XPathException
  {
    return map.getKeys();
  }

  /**
   * Returns a sequence of values contained in the map.
   * @param map - a map to get keys for.
   * @return a sequence of values contained in the map.
   */
  public static SequenceIterator mapValues(MapValue map)
    throws XPathException
  {
    return map.getValues();
  }

  /**
   * Returns a values for a specified key contained in the map.
   * @param map - a map to get values for.
   * @param key - a key in the map.
   * @return a values for a specified key contained in the map.
   */
  public static SequenceIterator mapValue(MapValue map, AtomicValue key)
    throws XPathException
  {
    return map.getValue(key).iterate();
  }

  /**
   * Executes xsl:apply-templates for the try items.
   * If operation fails, then executes xsl:apply-templates for the chatch items,
   * xsl:apply-templates is called in current mode.
   * 
   * Current exception info is available during execution of exception handler
   * through current-error-info() function returning a sequence:
   *   error as xs:QName;
   *   error-description as xs:string;
   *   error-object as item();
   *   
   * and through current-location-info() function returning a sequence of pairs:
   *   (
   *     location as xs:string, 
   *     context as item()
   *   )*;
   * 
   * @param context - a dynamic context.
   * @param tryItems - items to execute.
   * @param catchItems - items to execute in case of error.
   */
  public static void tryBlock(
    XPathContext context,
    SequenceIterator tryItems,
    SequenceIterator catchItems)
    throws Exception
  {
    Controller controller = context.getController();
    XPathContextMinor minorContext = (XPathContextMinor)context;
    ParameterSet tunnelParameters = context.getTunnelParameters();
    Mode mode = context.getCurrentMode();
    SequenceOutputter sequenceOutputter = new SequenceOutputter(); 
    
    sequenceOutputter.setPipelineConfiguration(
      controller.makePipelineConfiguration());

    try
    {
      XPathContextMajor majorContext = 
        XPathContextMajor.newContext(minorContext);
        
      majorContext.setReceiver(sequenceOutputter);
      
      TailCall tailCall = ApplyTemplates.applyTemplates(
        tryItems, 
        mode, 
        ParameterSet.EMPTY_PARAMETER_SET,
        tunnelParameters,
        majorContext,
        false, 
        0);
      
      while(tailCall != null)
      {
        tailCall = tailCall.processLeavingTail();
      }
    }
    catch(Exception e)
    {
      // Release dirty data.
      sequenceOutputter = null;

      Object data = controller.getUserData(currentErrorKey, currentErrorKey);
      
      try
      {
        controller.setUserData(currentErrorKey, currentErrorKey, e);
        
        XPathContextMajor majorContext = 
          XPathContextMajor.newContext(minorContext);
        
        TailCall tailCall = ApplyTemplates.applyTemplates(
          catchItems, 
          mode, 
          ParameterSet.EMPTY_PARAMETER_SET,
          tunnelParameters,
          majorContext,
          false, 
          0);
        
        while(tailCall != null)
        {
          tailCall = tailCall.processLeavingTail();
        }
      }
      finally
      {
        controller.setUserData(currentErrorKey, currentErrorKey, data);
      }
      
      return;
    }
    
    SequenceIterator iterator = sequenceOutputter.iterate();
    
    while(true)
    {
      Item item = iterator.next();
      
      if (item == null)
      {
        break;
      }
      
      context.getReceiver().append(item, 0, NodeInfo.ALL_NAMESPACES);
    }
  }

  /**
   * Returns current error info in the format:
   *   error as xs:QName;
   *   error-description as xs:string;
   *   error-object as item();
   *
   * @param context - a dynamic context.
   * @return a error info.
   */
  public static SequenceIterator currentErrorInfo(XPathContext context)
    throws Exception
  {
    Controller controller = context.getController();
    
    Object data = controller.getUserData(currentErrorKey, currentErrorKey);
    
    if ((data == null) || !(data instanceof Exception))
    {
      throw new XPathException(
        "Cannot call current-error-info() in this context.",
        "ERR001");
    }
    
    Exception exception = (Exception)data;
    XPathException xpathException = null;
    QNameValue error;
    
    if (exception instanceof XPathException)
    {
      xpathException = (XPathException)exception;
      error = new QNameValue(
        "", 
        xpathException.getErrorCodeNamespace(), 
        xpathException.getErrorCodeLocalPart());
    }
    else
    {
      error = new QNameValue("", "", "ERROR");
    }
    
    ArrayList<Item> items = new ArrayList<Item>();
    
    items.add(error);
    items.add(new StringValue(exception.getMessage()));
    items.add(new ObjectValue(exception));
    
    return new ListIterator(items);
  }
   
  /**
   * Returns current error info in the format:
   *   (
   *     location as xs:string, 
   *     context as item()
   *   )*;
   *
   * @param context - a dynamic context.
   * @return a error info.
   */
  public static SequenceIterator currentErrorLocation(XPathContext context)
    throws Exception
  {
    Controller controller = context.getController();
    
    Object data = controller.getUserData(currentErrorKey, currentErrorKey);
    
    if ((data == null) || !(data instanceof Exception))
    {
      throw new XPathException(
        "Cannot call current-error-location() in this context.",
        "ERR002");
    }
    
    Exception exception = (Exception)data;
    ArrayList<Item> items = new ArrayList<Item>();
    XPathException xpathException = null;
    
    if (exception instanceof XPathException)
    {
      xpathException = (XPathException)exception;
      
      XPathContext errorContext = xpathException.getXPathContext();

      populateLocation(context, xpathException.getLocator(), items);
      
      while(errorContext != null)
      {
      
        errorContext = errorContext.getCaller();
      }
    }
    else
    {
      StringWriter writer = new StringWriter();
      
      exception.printStackTrace(new PrintWriter(writer));
      items.add(new StringValue(writer.toString()));
      items.add(StringValue.EMPTY_STRING);
    }
    
    return new ListIterator(items);
  }

  /**
   * Populates location information for a specified context.
   * @param context - a dynamic context.
   * @param locator - a locator instance.
   * @param items - a location items.
   */
  private static void populateLocation(
    XPathContext context,
    SourceLocator locator,
    ArrayList<Item> items)
    throws Exception
  {
    if (locator == null)
    {
      return;
    }
   
    StringBuilder message = new StringBuilder();
    InstructionInfo info = null;
    boolean issueContextItem = false;
   
    if (locator instanceof InstructionInfo)
    {
      info = (InstructionInfo)locator;
    }
    // No more cases.
 
    if (info != null)
    {
      int construct = info.getConstructType();
      StructuredQName qName = info.getObjectName();

      switch(construct)
      {
        case StandardNames.XSL_FOR_EACH:
        {
          message.append("for-each");
          message.append("\n");
          issueContextItem = true;
          
          break;
        }
        case StandardNames.XSL_FOR_EACH_GROUP:
        {
          message.append("for-each-group");
          message.append("\n");
          issueContextItem = true;
          
          break;
        }
        case StandardNames.XSL_FUNCTION:
        {
          issueContextItem = true;
          message.append("function ");
          message.append(qName);
          message.append("\n");
   
          break;
        }
        case StandardNames.XSL_TEMPLATE:
        {
          if (qName != null)
          {
            issueContextItem = true;
            message.append("template name=\"");
            message.append(qName);
            message.append("\n");
          }
          else
          {
            Mode mode = context.getCurrentMode();
            Rule rule = context.getCurrentTemplateRule();
            
            if (rule != null)
            {
              issueContextItem = true;
              message.append("template");
       
              if (mode != null)
              {
                qName = mode.getModeName();
                
                if (qName != null)
                {
                  message.append(" mode=\"");
                  message.append(qName);
                  message.append("\"");
                }
              }
               
              message.append("\n");
              message.append("  match=\"");
              message.append(rule.getPattern());
              message.append("\"");
              message.append("\n");
            }
          }
          
          break;
        }
        case StandardNames.XSL_CALL_TEMPLATE:
        {
          Object templateObject = info.getProperty("template");
          
          if ((templateObject != null) && (templateObject instanceof Template))
          {
            Template template = (Template)templateObject;
       
            message.append("call-template name=\"");
            message.append(template.getTemplateName());
            message.append("\"");
            message.append("\n");
          }
         
          break;
        }
        default:
        {
          if (qName != null)
          {
            if (info.getProperty("expression") != null)
            {
              message.append("expression");
            }
            else
            {
              message.append("instruction");
            }
      
            if (construct < 1024)
            {
              message.append(" ");
              message.append(StandardNames.getDisplayName(construct));
            }
      
            message.append(" name=\"");
            message.append(qName);
            message.append("\"");
            message.append("\n");
          }
             
          break;
        }
      }
    }

    String systemId = locator.getSystemId();
 
    if (systemId != null)
    {
      int line = locator.getLineNumber();
     
      // NOTE: this magic is here as Saxon internally uses 20 bits only 
      // to represent a line number, and sometimes returns 0xfffff (a.k.a. -1),
      // as a marker of absense of the line info.
      if (line > 0)
      {
        line &= 0xfffff;
   
        if (line == 0xfffff)
        {
          line = -1;
        }
      }
 
      if (message.length() > 0)
      {
        message.append("  ");
      }

      message.append("systemID: ");
      message.append("\"");
      message.append(systemId);
      message.append("\"");
   
      if (line != -1)
      {
        message.append(", line: ");
        message.append(line);
     
        int column = locator.getColumnNumber();
       
        if (column != -1)
        {
          message.append(", column: ");
          message.append(column);
        }
      }
    
      message.append("\n");
    }
    
    if (message.length() == 0)
    {
      return;
    }
    
    items.add(new StringValue(message.toString()));
    
    Item item = context.getContextItem();

    if (issueContextItem && (item != null))
    {
      items.add(item);
    }
    else
    {
      items.add(StringValue.EMPTY_STRING);
    }
  }
  
  /**
   * User data key for a current error.
   */
  private static final String currentErrorKey = 
    Functions.class.getName() + ".current-error"; 
}
