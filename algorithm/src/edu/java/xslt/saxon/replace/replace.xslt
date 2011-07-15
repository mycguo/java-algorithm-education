<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:my="http://my.com" exclude-result-prefixes="xsl my">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>
 <xsl:strip-space elements="*"/>

 <my:params xml:space="preserve">
  <pattern>
   <old>&#xA;</old>
   <new><br /></new>
  </pattern>
  <pattern>
   <old>quick</old>
   <new>slow</new>
  </pattern>
  <pattern>
   <old>fox</old>
   <new>elephant</new>
  </pattern>
  <pattern>
   <old>brown</old>
   <new>white</new>
  </pattern>
 </my:params>

 <xsl:variable name="vPats"
      select="document('')/*/my:params/*"/>

 <xsl:template match="text()" name="multiReplace">
  <xsl:param name="pText" select="."/>
  <xsl:param name="pPatterns" select="$vPats"/>

  <xsl:if test=
   "string-length($pText) >0">

    <xsl:variable name="vPat" select=
     "$vPats[starts-with($pText, old)][1]"/>
    <xsl:choose>
     <xsl:when test="not($vPat)">
       <xsl:copy-of select="substring($pText,1,1)"/>
     </xsl:when>
     <xsl:otherwise>
       <xsl:copy-of select="$vPat/new/node()"/>
     </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="multiReplace">
      <xsl:with-param name="pText" select=
       "substring($pText, 1 + not($vPat) + string-length($vPat/old/node()))"/>
    </xsl:call-template>
  </xsl:if>
 </xsl:template>
</xsl:stylesheet>
