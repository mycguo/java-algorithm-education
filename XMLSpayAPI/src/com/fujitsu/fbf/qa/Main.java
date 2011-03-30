/**
 * Copyright ... ..., All Rights Reserved.
 */

package com.fujitsu.fbf.qa;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import XMLSpyInterface.SPYViewModes;
import XMLSpyInterface.SpyApplication;
import XMLSpyInterface.SpyDoc;

/**
 * http://manual.altova.com/XMLSpy/spyprofessional/index.html?xmlspyoverview.htm
 * http://manual.altova.com/AuthenticDesktop/authenticdesktopcommunity/index.html?javaprogreference.htm
 * @author 
 * @version 
 */

public class Main {

	private static void innerListFiles(Collection<File> files, File directory,
			IOFileFilter filter) {
		File[] found = directory.listFiles((FileFilter) filter);
		if (found != null) {
			for (int i = 0; i < found.length; i++) {
				if (found[i].isDirectory()) {
					innerListFiles(files, found[i], filter);
				} else {
					files.add(found[i]);
				}
			}
		}
	}

	private static StringBuffer validate(SpyApplication app, String filePath) {
		SpyDoc oDoc = null;
		StringBuffer msg = new StringBuffer();

		try {
			oDoc = app.GetDocuments().OpenFile(filePath, true);

			if (oDoc != null) {
				oDoc.SwitchViewMode(SPYViewModes.spyViewAuthentic);

				if (oDoc.IsValid() == false) {
					msg.append("Not valid: "
							+ filePath
							+ oDoc.GetErrorString()
							+ "\n");
				} 
				else {
					msg.append("Valid: " + filePath + "\n");
				}
			}
			
			oDoc.Close(false);

		} finally {
			if (oDoc != null)
				oDoc.ReleaseInstance();
		}
		return msg;
	}

	private static String validate(Collection<File> files) {
		SpyApplication app = null;
		StringBuffer buffer = new StringBuffer();

		try {
			app = new SpyApplication();
			app.ShowApplication(true);
			
			for (Iterator<File> i = files.iterator(); i.hasNext();) {
				buffer.append( validate(app, i.next().toString()) );
			}

			app.Quit();
		} finally {
			if (app != null)
				app.ReleaseInstance();
		}

		return buffer.toString();
	}

	public static void main(String[] args) {
		Collection<File> files = new java.util.LinkedList<File>();
		innerListFiles(
				files, 
				new File("C:\\CVS\\FBF_GIGTEST\\content\\gigv4\\dev"), 
				FileFilterUtils.orFileFilter(FileFilterUtils.directoryFileFilter(), FileFilterUtils.suffixFileFilter("xmla"))
		);
		System.out.println(validate(files));
	}

}
