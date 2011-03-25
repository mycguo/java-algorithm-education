/* -*- mode: Java; c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
/*
 * Copyright (c) 2002 Extreme! Lab, Indiana University. All rights reserved.
 *
 * This software is open source. See the bottom of this file for the licence.
 *
 * $Id: AllTests.java,v 1.7 2003/04/06 00:04:02 aslom Exp $
 */

import junit.framework.Test;
import junit.framework.TestSuite;

import org.gjt.xpp.XmlPullParserException;
import org.gjt.xpp.XmlPullParserFactory;

/**
 * TestSuite that runs all the sample tests
 *
 * @version $Revision: 1.7 $
 * @author Aleksander A. Slominski mailto:aslom@extreme.indiana.edu
 */

public class AllTests {

    public static void main (String[] args) {
        String[] factories = new String[] {
            null,
                "org.gjt.xpp.impl.PullParserFactoryFullImpl",
                "org.gjt.xpp.x2impl.X2PullParserFactoryImpl",
        };
        for(int i = 0; i < factories.length; ++i) {
            String fctry = factories[i];
            String systemDefFctry = System.getProperty(
                org.gjt.xpp.XmlPullParserFactory.DEFAULT_PROPERTY_NAME);
            Class fctryClass = null;
            if(fctry != null) {
                System.setProperty(
                    org.gjt.xpp.XmlPullParserFactory.DEFAULT_PROPERTY_NAME,
                    fctry);
            }
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.DEFAULT_PROPERTY_NAME) );
                fctryClass = factory.getClass();
            } catch(XmlPullParserException ex) {
                if(fctry != null) {
                    System.out.println("cannot run test for XPP2 factory: "+fctry);
                } else {
                    System.out.println("cannot test for default XPP2 factory");
                }
                ex.printStackTrace(System.out);
                continue;
            }
            if(fctry != null) {
                System.out.println("running test for factory: "+fctry+"\nfound "+fctryClass);
            } else {
                System.out.println("running test for default factory\nfound "+fctryClass);
            }
            junit.textui.TestRunner.run (suite());
            if(fctry != null) {
                if(systemDefFctry != null) {
                    System.setProperty(
                        org.gjt.xpp.XmlPullParserFactory.DEFAULT_PROPERTY_NAME,
                        systemDefFctry);
                } else {
                    // null can not be put on Hashtable or NullPointerException
                    // --- dirty hack to restore previous value...
                    System.getProperties().remove(
                        org.gjt.xpp.XmlPullParserFactory.DEFAULT_PROPERTY_NAME);
                }
            }
        }
        System.exit(0);
    }

    public static Test suite ( ) {
        TestSuite suite= new TestSuite("All JUnit Tests for XPP2");
        suite.addTest(parser.ParserTest.suite());
        suite.addTest(format.FormatTest.suite());
        suite.addTest(node.NodeTest.suite());
        suite.addTest(lowlevel.LowLevelTest.suite());
        /*
         //*/
        return suite;
    }
}


/*
 * Indiana University Extreme! Lab Software License, Version 1.2
 *
 * Copyright (C) 2002 The Trustees of Indiana University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1) All redistributions of source code must retain the above
 *    copyright notice, the list of authors in the original source
 *    code, this list of conditions and the disclaimer listed in this
 *    license;
 *
 * 2) All redistributions in binary form must reproduce the above
 *    copyright notice, this list of conditions and the disclaimer
 *    listed in this license in the documentation and/or other
 *    materials provided with the distribution;
 *
 * 3) Any documentation included with all redistributions must include
 *    the following acknowledgement:
 *
 *      "This product includes software developed by the Indiana
 *      University Extreme! Lab.  For further information please visit
 *      http://www.extreme.indiana.edu/"
 *
 *    Alternatively, this acknowledgment may appear in the software
 *    itself, and wherever such third-party acknowledgments normally
 *    appear.
 *
 * 4) The name "Indiana University" or "Indiana University
 *    Extreme! Lab" shall not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission from Indiana University.  For written permission,
 *    please contact http://www.extreme.indiana.edu/.
 *
 * 5) Products derived from this software may not use "Indiana
 *    University" name nor may "Indiana University" appear in their name,
 *    without prior written permission of the Indiana University.
 *
 * Indiana University provides no reassurances that the source code
 * provided does not infringe the patent or any other intellectual
 * property rights of any other entity.  Indiana University disclaims any
 * liability to any recipient for claims brought by any other entity
 * based on infringement of intellectual property rights or otherwise.
 *
 * LICENSEE UNDERSTANDS THAT SOFTWARE IS PROVIDED "AS IS" FOR WHICH
 * NO WARRANTIES AS TO CAPABILITIES OR ACCURACY ARE MADE. INDIANA
 * UNIVERSITY GIVES NO WARRANTIES AND MAKES NO REPRESENTATION THAT
 * SOFTWARE IS FREE OF INFRINGEMENT OF THIRD PARTY PATENT, COPYRIGHT, OR
 * OTHER PROPRIETARY RIGHTS.  INDIANA UNIVERSITY MAKES NO WARRANTIES THAT
 * SOFTWARE IS FREE FROM "BUGS", "VIRUSES", "TROJAN HORSES", "TRAP
 * DOORS", "WORMS", OR OTHER HARMFUL CODE.  LICENSEE ASSUMES THE ENTIRE
 * RISK AS TO THE PERFORMANCE OF SOFTWARE AND/OR ASSOCIATED MATERIALS,
 * AND TO THE PERFORMANCE AND VALIDITY OF INFORMATION GENERATED USING
 * SOFTWARE.
 */

