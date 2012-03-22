<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes" />
	<xsl:include href="HeaderFooter.xslt" />
	<xsl:template match="/OnlineStore">
		<xsl:apply-templates select="Header" />
	</xsl:template>
	<xsl:template name="Body">
		<xsl:apply-templates select="/OnlineStore/Default" />
	</xsl:template>
	<xsl:template match="Default">

		<xsl:value-of select="/OnlineStore/Page/CustomContent" disable-output-escaping="yes"/>

	</xsl:template>
</xsl:stylesheet>
