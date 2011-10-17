<?xml version="1.0" encoding="utf-8"?>
<!--
  This stylesheet how to use t:try-block template.

  $Id: tuple-test.xslt 3341 2008-06-08 08:12:58Z vladimirn $
  
  To Run:

	Must Set additional classpath to refer to the project in Run Configuration
	Optionally, set the two parameters below to see debug log
	  
  14:11:02,684 INFO  [main] JAXPSAXProcessorInvoker  - Setting attribute 'http://saxon.sf.net/feature/trace-external-functions' to value true
  14:11:02,684 INFO  [main] JAXPSAXProcessorInvoker  - Setting attribute 'http://saxon.sf.net/feature/allow-external-functions' to value true

 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:t="http://www.nesterovsky-bros.com/xslt/public/"
  exclude-result-prefixes="xs t">

  <xsl:include href="try-block.xslt"/>

  <xsl:template match="/">
    <result>
      <xsl:for-each select="1 to 10">
        <xsl:call-template name="t:try-block">
          <xsl:with-param name="value" tunnel="yes" select=". - 5"/>
          <xsl:with-param name="try" as="element()">
            <try/>
          </xsl:with-param>
          <xsl:with-param name="catch" as="element()">
            <t:error-handler/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:for-each>
    </result>
  </xsl:template>

  <xsl:template mode="t:call" match="try">
    <xsl:param name="value" tunnel="yes" as="xs:decimal"/>

    <value>
      <xsl:sequence select="1 div $value"/>
    </value>
  </xsl:template>

</xsl:stylesheet>