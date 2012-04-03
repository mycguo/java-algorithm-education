<?xml version="1.0" encoding="UTF-8"?>
<!--
	This sample XSLT file demonstrates how data and styles can be directly extracted	out of Microsoft Word 2007 DOCX files.
	The result of this sample transformation is a HTML file which resembles Word paragraphs and some styles.
-->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:docx="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
	xmlns:docxpkgrels="http://schemas.openxmlformats.org/package/2006/relationships">
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="yes"/>
	<xsl:variable name="inputFile" select="'file:///gig4/JavaProject/src/com/altova/office/XMLSpy2007.docx'"/>
	/*
	http://news.oreilly.com/2008/08/the-challenge-of-validating-xm.html
The first problem we had was the issue of access to parts in the document. Sun defined a jar:and zip: URL scheme for accessing parts of a JAR archive (a Jar archive is a ZIP file) which is available as a built-in part of the Java API. This uses a bang ! to separate the ZIP part locator from the ZIP filename. For example jar:http://www.foo.com/bar/jar.jar!/baz/entry.txt.
	
	*/
	<xsl:template match="/">
		<html>
			<head>
				<title />
			</head>
			<body>
				<!-- extract the file extension from the input document -->
				<xsl:variable name="document-uri-ext" select="'docx'" />
				<xsl:message>document extension=<xsl:copy-of select="$document-uri-ext"/></xsl:message>
				<xsl:choose>
					<xsl:when test="$document-uri-ext = 'docx'">
						<xsl:variable name="rels-uri" select="concat('jar:', $inputFile, '!/_rels/.rels')" />
						<xsl:variable name="rDoc" select="document($rels-uri)"/>
						<xsl:message>doc=<xsl:copy-of select="$rDoc"/></xsl:message>
						<xsl:variable name="doc-uri" select="concat('jar:' , $inputFile, '!/', document($rels-uri)//docxpkgrels:Relationship[@Type = 'http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument']/@Target)"/>
						<xsl:for-each select="document($doc-uri)/docx:document/docx:body/docx:p">
							<p>
								<xsl:for-each select="docx:r">
									<span>
										<xsl:attribute name="style">
											<xsl:apply-templates select="docx:rPr" />
										</xsl:attribute>
										<xsl:value-of select="docx:t" />
									</span>
								</xsl:for-each>
							</p>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<p><span style="font-weight:bold;">This transformation can only be applied to Microsoft Word 2007 DOCX files!</span></p>
					</xsl:otherwise>
				</xsl:choose>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="docx:u">text-decoration:underline;</xsl:template>
	<xsl:template match="docx:b">font-weight:bold;</xsl:template>
	<xsl:template match="docx:i">font-style:italic;</xsl:template>
</xsl:stylesheet>
