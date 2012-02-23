<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fn="fn" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output indent="yes" encoding="UTF-8" />

<xsl:param name="pathToCSV" select="'file:///C:/Users/cg/workspace/JavaProject/src/com/jetbookkeeping/CUST.TXT'" />


<xsl:template match="/" name="main">
    <xsl:choose>
    <xsl:when test="unparsed-text-available($pathToCSV)">
        <xsl:variable name="csv" select="unparsed-text($pathToCSV)" />
        <xsl:variable name="lines" select="tokenize($csv, '&#10;')" as="xs:string+" />
        <xsl:variable name="elemNames" select="tokenize($lines[1], '&#9;')" as="xs:string+" />
        <root>
        <xsl:for-each select="$lines[position() &gt; 1]">
        	<xsl:if test="string-length(string(.)) > 0">
	            <row>
	            <xsl:variable name="lineItems" select="tokenize(., '&#9;')" as="xs:string+" />
	
	            <xsl:for-each select="$elemNames">
	                <xsl:variable name="pos" select="position()" />
	                <elem name="{.}">
	                <xsl:value-of select="$lineItems[$pos]" />
	                </elem>
	            </xsl:for-each>
	            </row>
            </xsl:if>
        </xsl:for-each>
        </root>
    </xsl:when>
    <xsl:otherwise>
        <xsl:text>Cannot locate : </xsl:text><xsl:value-of select="$pathToCSV" />
    </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>