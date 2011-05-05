<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:t="http://www.exchangefortravel.org" version="2.0" exclude-result-prefixes="t">
  <!-- XML output! -->
  <xsl:output method="xml" indent="yes"  omit-xml-declaration = "yes" encoding="UTF-8"/> 
  <xsl:template match="/">
 		<Response>
 			<xsl:attribute name="Version">1.0</xsl:attribute>
 				
			<xsl:copy-of select="t:Transaction/t:Action"/> 	
				
			<Offers>
        		<Offer Type="Main">
           			<Trip>
            			<Product>
            				<xsl:attribute name="Code"><xsl:value-of select="t:Transaction/t:Trip/t:Segment/t:Code/@Value"/></xsl:attribute>
            				<!-- process each segements -->
            				<xsl:for-each select="t:Transaction/t:Trip/t:Segment/t:Segments/t:Segments">
            				    <Departure>
				                    <Date>
				                    	<xsl:attribute name="Value"><xsl:value-of select="t:Segment/t:Begin/@Value"/></xsl:attribute>
				                    </Date>	
				                    <City>
				                    	<xsl:attribute name="Code"><xsl:value-of select="t:Segment/t:City/@Code"/></xsl:attribute>
				                    </City>
				                </Departure>
				                <Duration Value="11" Unit="Night">
				                	<xsl:attribute name="Value"><xsl:value-of select="t:Segment/t:Duration/@Value"/></xsl:attribute>
				                	<xsl:attribute name="Unit"><xsl:value-of select="t:Segment/t:Duration/@Unit"/></xsl:attribute>
				                </Duration>
				                <Passangers>
				                    <Passanger type="Adult" Quantity="1"/>                    
				                </Passangers>
				               
				                <Rooms>
				                	<xsl:copy-of select="t:Segment/t:At/t:Room"/>
				                </Rooms>
				                <MealPlan><xsl:value-of select="t:Segment/t:MealPlan/@Code"/></MealPlan>
				             </xsl:for-each>
            			</Product>
           			</Trip>
           		</Offer>
           	</Offers>	
 		
 		</Response>
 		

		
  </xsl:template>
 
  <!-- Lower level titles strip out the element tag -->

  <!-- Top-level title -->
  <xsl:template match="/Article/ArtHeader/Title">
    <TITLE> <xsl:apply-templates/> </TITLE>
  </xsl:template>

  <xsl:template match="//Sect1">
      <SECT><xsl:apply-templates/></SECT>
  </xsl:template>

  <!-- Case-change -->
  <xsl:template match="Para">
      <PARA><xsl:apply-templates/></PARA>
  </xsl:template>

</xsl:stylesheet>
