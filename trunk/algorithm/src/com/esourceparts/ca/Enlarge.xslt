<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes" />

<xsl:include href="HeaderFooter.xslt" />

	<xsl:template match="/OnlineStore">
		<xsl:call-template name="PictureViewer"/>
	</xsl:template>
</xsl:stylesheet>