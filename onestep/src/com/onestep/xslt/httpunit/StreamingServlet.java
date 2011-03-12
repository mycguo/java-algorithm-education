package com.onestep.xslt.httpunit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.onestep.xslt.saxon.RunSaxon;

public class StreamingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		Templates template = null;

		//String xmlSource = "com/onestep/xslt/saxon/profile-report.xml";
		//String[] xsltFiles = { "com/onestep/xslt/saxon/profile.xslt" };
		

		String xmlSource = "com/onestep/xslt/saxon/indexe.xmle";
        String[] xsltFiles = { "com/onestep/xslt/saxon/xf-ss_workbook.xslt" };

		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunSaxon.class.getClassLoader());

		InputStream stream = RunSaxon.class.getClassLoader().getResourceAsStream(xsltFiles[0]);

		try {
			template = factory.newTemplates(new StreamSource(stream));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		StreamResult result = null;
		try {
			result = new StreamResult(response.getOutputStream());

			Transformer transformer = template.newTransformer();

			transformer.setParameter("SV_BaseOutputFileName", "dummy");

			transformer.transform(new StreamSource(RunSaxon.class.getClassLoader().getResourceAsStream(xmlSource)), result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
