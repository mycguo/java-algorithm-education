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
		            <Prices>
		            	<xsl:attribute name="Total" select="t:Transaction/t:Trip/t:Price/@Value"/>
		            	<xsl:for-each select="t:Transaction/t:Trip/t:Price/t:Prices/t:Price">
		            		<Price>
		            			<xsl:attribute name="Quantity"><xsl:value-of select="@Quantity"/></xsl:attribute>
		            			<xsl:attribute name="UnitAmount"><xsl:value-of select="t:Amounts/t:Amount[@Target='Unit']/@Value"/></xsl:attribute>
		            			<xsl:attribute name="TotalAmount"><xsl:value-of select="t:Amounts/t:Amount[@Target='Total']/@Value"/></xsl:attribute>
		            			<xsl:variable name="content">
		            				<xsl:value-of select="."/>
		            			</xsl:variable>
		            			<xsl:value-of select="normalize-space($content)"/>
		            		</Price>
		            	</xsl:for-each>
		 			</Prices>           			
           		</Offer>
           	</Offers>	
 		
 		</Response>
 		

		
  </xsl:template>

</xsl:stylesheet>
