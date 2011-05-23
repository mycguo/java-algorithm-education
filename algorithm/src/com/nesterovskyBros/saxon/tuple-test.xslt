<?xml version="1.0" encoding="utf-8"?>
<!--
  This stylesheet shows tuple and maps in action.

  $Id: tuple-test2.xslt 3341 2008-06-08 08:12:58Z vladimirn $
 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:c="java:com.nesterovskyBros.saxon.Functions"
  exclude-result-prefixes="xs f p c">

  <xsl:template match="/">
    <root>
      <xsl:variable name="tuples" as="item()*" select="
        for $i in 1 to 20 return
          c:tuple(1 to $i)"/>

      <total-items>
        <xsl:sequence select="
          sum
          (
            for $tuple in $tuples return
              count(c:tuple-items($tuple))
          )"/>
      </total-items>
 
      <tuples-size>
        <xsl:sequence select="count($tuples)"/>
      </tuples-size>

      <sums-per-tuples>
        <xsl:for-each select="$tuples">
          <xsl:variable name="index" as="xs:integer" select="position()"/>
          
          <sum 
            index="{$index}"
            value="{sum(c:tuple-items(.))}"/>
        </xsl:for-each>
      </sums-per-tuples>

      <xsl:variable name="cities" as="element()*">
        <city name="Jerusalem" country="Israel"/>
        <city name="London" country="Great Britain"/>
        <city name="Paris" country="France"/>
        <city name="New York" country="USA"/>
        <city name="Moscow" country="Russia"/>
        <city name="Tel Aviv" country="Israel"/>
        <city name="St. Petersburg" country="Russia"/>
      </xsl:variable>

      <xsl:variable name="map" as="item()" select="
        c:map
        (
          for $city in $cities return
          (
            $city/string(@country),
            $city
          )
        )"/>

      <xsl:for-each select="c:map-keys($map)">
        <xsl:variable name="key" as="xs:string" select="."/>

        <country name="{$key}">
          <xsl:sequence select="c:map-value($map, $key)"/>
        </country>
      </xsl:for-each>
     </root>
  </xsl:template>
  
</xsl:stylesheet>