package edu.java.xml.xpath;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.sf.saxon.om.NodeInfo;

import org.xml.sax.InputSource;

/**
 * Implement this:
 * http://www.onjava.com/pub/a/onjava/excerpt/java_xslt_ch5/index.html?page=1
 * http://www.onjava.com/pub/a/onjava/2005/01/12/xpath.html
 * @author CGuo
 *
 */
public class CatalogXPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CatalogXPath p = new CatalogXPath();
		try {
			p.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doIt() throws Exception {
		
		//1.0 create an empty document
		//DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//Document document = builder.parse(CatalogXPath.class.getResourceAsStream("/edu/java/xml/xpath/catelog.xml"));

		InputSource input =new InputSource(CatalogXPath.class.getResourceAsStream("/edu/java/xml/xpath/catelog.xml"));
		
		XPathFactory factory = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI);;
		
		System.out.println("Factory: " + factory);
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext( new NamespaceContext() {

			public String getNamespaceURI(String prefix) {
				if (prefix.equals("journal"))
					return "http://www.journal.org/";
				else
					return null;
			}

			public String getPrefix(String namespaceURI) {
				if (namespaceURI.equals("http://www.journal.org/"))
					return "journal";
				else
					return null;
			}

			public Iterator getPrefixes(String namespaceURI) {
				return null;
			}
			
		});
		System.out.println("XPath: " + xpath);
		NodeInfo node = (NodeInfo) xpath.evaluate("/catalog/journal:journal[@title='XML']", input, XPathConstants.NODE);
		
		System.out.println("Node: " + node + " value: " + node.getStringValue());
		


	}


}
