<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes" />
	<xsl:include href="HeaderFooter.xslt" />
	<xsl:template match="/OnlineStore">
		<xsl:apply-templates select="Header" />
	</xsl:template>
	<xsl:template name="Body">
		<xsl:apply-templates select="/OnlineStore/Default" />
	</xsl:template>
	<xsl:template match="Default">
		<table width="100%" cellpadding="0" cellspacing="0" border="0" style="border-bottom: 1px solid #0C69B7; padding-bottom: 5px; padding-top: 5px;">
			<tr>
				<td>
					<p class="information_text">
						<xsl:value-of select="/OnlineStore/Page/CustomContent" disable-output-escaping="yes"/>
					</p>
				</td>
			</tr>
		</table>
		
	</xsl:template>
</xsl:stylesheet>
Thread was being aborted.