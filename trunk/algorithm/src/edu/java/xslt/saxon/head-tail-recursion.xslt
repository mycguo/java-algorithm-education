<!-- Example from Saxon book Page 994 -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:f="local-functions.uri" exclude-result-prefixes="xs f" version="2.0">
	<xsl:function name="f:total-numbers" as="xs:string*">
		<xsl:param name="input" as="xs:string*"/>
		<xsl:param name="total" as="xs:string"/>
		<xsl:if test="exists($input)">
			<xsl:variable name="x" as="xs:string" select="xs:string(xs:decimal($input[1]) + xs:decimal($total))"/>
			<xsl:sequence select="$x"/>
			<xsl:sequence select="f:total-numbers(subsequence($input,2),$x)"/>
		</xsl:if>
	</xsl:function>
	
	<!-- tail recursion -->
	<xsl:function name="f:reverse" as="xs:string*">
		<xsl:param name="input" as="xs:string*"/>
		<xsl:if test="exists($input)">
			<xsl:sequence select="f:reverse(remove($input,1)),$input[1]"/>
		</xsl:if>
	</xsl:function>
	<xsl:template match="/">
		<total values="{f:total-numbers(numbers/n,xs:string(0))}"/>
		<xsl:sequence select="f:reverse(numbers/n)"/>
	</xsl:template>
</xsl:stylesheet>