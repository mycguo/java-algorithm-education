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
            			</Product>
            				<!-- process each segements -->
            				<xsl:for-each select="t:Transaction/t:Trip/t:Segment/t:Segments/t:Segments/t:Segment">
            				    <Departure>
				                    <Date>
				                    	<xsl:attribute name="Value"><xsl:value-of select="t:Begin/@Value"/></xsl:attribute>
				                    </Date>	
				                    <City>
				                    	<xsl:attribute name="Code"><xsl:value-of select="t:City/@Code"/></xsl:attribute>
				                    </City>
				                </Departure>
				                <Duration Value="11" Unit="Night">
				                	<xsl:attribute name="Value"><xsl:value-of select="t:Duration/@Value"/></xsl:attribute>
				                	<xsl:attribute name="Unit"><xsl:value-of select="t:Duration/@Unit"/></xsl:attribute>
				                </Duration>
				                <Passangers>
				                    <Passanger type="Adult" Quantity="1"/>                    
				                </Passangers>
				               
				                <Rooms>
				                	<xsl:copy-of select="t:At/t:Room"/>
				                </Rooms>
				                <MealPlan><xsl:value-of select="t:MealPlan/@Code"/></MealPlan>
				             </xsl:for-each>
            			
           			</Trip>
		            <Prices Total="299660">
		                <Price Quantity="1" UnitAmount="5525" TotalAmount="552500"><![CDATA[CROISIERE VISTAMAR   11NTS]]>
		                </Price>
		                <Price Quantity="1" UnitAmount="1100" TotalAmount="1100"><![CDATA[SURCHARGE CARBURANT                       (C)]]></Price>
		                <Price Quantity="1" UnitAmount="6500" TotalAmount="6500"><![CDATA[TAXES SURCHARGES AERIENNES ET TAXE SOLIDARITE]]></Price>
		 			</Prices>           			
           		</Offer>
           	</Offers>	
 		
 		</Response>
 		

		
  </xsl:template>

</xsl:stylesheet>
