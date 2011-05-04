<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  version="2.0">
  <!-- XML output! -->
  <xsl:output method="xml"/> 
  <xsl:template match="/">

 			<Response>
 				<xsl:attribute name="Version">1.0</xsl:attribute>
				<xsl:copy-of select="Transaction/Action"/> 	
				
				<Offers>
        			<Offer Type="Main">
            			<Trip>
            			
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
