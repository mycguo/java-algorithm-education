<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fn="fn" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output indent="yes" encoding="UTF-8" />


<xsl:template match="/">
<Contacts>
	<xsl:for-each select="//row">
		  <Contact>
		  	  <!-- from "Card ID" filed, contact number can be optional, don't create if it is none -->
		  	  <xsl:if test="string(elem[@name='Card ID']) and not(contains(string(elem[@name='Card ID']),'None')) ">
		  	  		<ContactNumber><xsl:value-of select="elem[@name='Card ID']"/></ContactNumber>		  	  		
		  	  </xsl:if>
		  	  <!-- IF First Name ="" THEN Co._Last Name=Name ELSE =Last Name  ?? -->
		      <Name><xsl:value-of select="if (string(elem[@name='First Name'])) then concat(elem[@name='First Name'],' ', elem[@name='Co./Last Name']) else elem[@name='First Name']"/></Name>
		      <ContactStatus>ACTIVE</ContactStatus>
		      <EmailAddress><xsl:value-of select="elem[contains(@name,'Email')][1]"/></EmailAddress>
		      <!--  
		      <SkypeUserName>Skype Name/Number</SkypeUserName>
		      -->
		      <!-- from BSB -->
		      <BankAccountDetails><xsl:value-of select="elem[@name='BSB']"/></BankAccountDetails>
		      <!-- A.B.N. -->
		      <TaxNumber><xsl:value-of select="elem[contains(@name,'A.B.N.')][1]"/></TaxNumber>
		      <!-- this is for CUST ?? -->
		      <AccountsReceivableTaxType>OUTPUT</AccountsReceivableTaxType>
		      <!-- this is for SUPPLIER ?? -->
		      <AccountsPayableTaxType>INPUT</AccountsPayableTaxType>
		      
		      <FirstName><xsl:value-of select="elem[@name='First Name']"/></FirstName>
		      <LastName><xsl:value-of select="elem[@name='Co./Last Name']"/></LastName>
		      <DefaultCurrency>AUD</DefaultCurrency>
		      <Addresses>
		      	<!-- check for second address -->
		      	<xsl:variable name="second" select="string(elem[@name='Addr 2 - Line 1']) or string(elem[@name='           - Line 2'][2]) or string(elem[@name='           - Line 3'][2]) or string(elem[@name='           - Line 4'][2])"/>
		      	<xsl:variable name="firstAdd"  select="concat(string(elem[@name='Addr 1 - Line 1']) , string(elem[@name='           - Line 2'][1]) , string(elem[@name='           - Line 3'][1]) , string(elem[@name='           - Line 4'][1]))"/>
		      	<xsl:variable name="secondAdd" select="concat(string(elem[@name='Addr 2 - Line 1']) , string(elem[@name='           - Line 2'][2]) , string(elem[@name='           - Line 3'][2]) , string(elem[@name='           - Line 4'][2]))"/>
		      	<xsl:choose>
		      		<!-- has seoncd address,  -->
		      		<xsl:when test="$second"> 
		      			<!-- if seoncd has POBOX and first dont' have pobox, use it as the first, otherwise, use the first as pobox address -->
		      			<xsl:choose>
		      				<xsl:when test="(contains(lowercase($secondAdd),'po box') or contains(lowercase($secondAdd),'pobox')) and (not(contains(lowercase($firstAdd),'po box') or contains(lowercase($firstAdd),'pobox')))">
						        <Address>
						          <AddressType>POBOX</AddressType>
						          <!-- 
						          <AttentionTo>Simon G.</AttentionTo>
						           -->
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 2 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][2]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 3'][2]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 4'][2]"/></AddressLine2>
						          <City><xsl:value-of         select="elem[@name='           - City'][2]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][2]"/></Region>
						          <PostalCode><xsl:value-of   select="elem[@name='           - Postcode'][2]"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][2]"/></Country>
						        </Address>
						        <Address>
						          <AddressType>STREET</AddressType>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine2>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="elem[@name='           - Postcode'][1]"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>			      				
		      				</xsl:when>
		      				<xsl:otherwise>
						        <Address>
						          <AddressType>POBOX</AddressType>
						          <!-- 
						          <AttentionTo>Simon G.</AttentionTo>
						           -->
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine2>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="elem[@name='           - Postcode'][1]"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>
						        <Address>
						          <AddressType>STREET</AddressType>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 2 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][2]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 3'][2]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 4'][2]"/></AddressLine2>
						          <City><xsl:value-of         select="elem[@name='           - City'][2]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][2]"/></Region>
						          <PostalCode><xsl:value-of   select="elem[@name='           - Postcode'][2]"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][2]"/></Country>
						        </Address>			      				
		      				</xsl:otherwise>
		      			</xsl:choose>	      		
		      		</xsl:when>
		      		<xsl:otherwise>
						        <Address>
						          <AddressType>POBOX</AddressType>
						          <!-- 
						          <AttentionTo>Simon G.</AttentionTo>
						           -->
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine2>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine2>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="elem[@name='           - Postcode'][1]"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>		      		
		      		</xsl:otherwise>
		      	</xsl:choose>
		      </Addresses>
		      <Phones>
		      	<!-- how many phones ?? -->
		        <Phone>
		          <PhoneType>DEFAULT</PhoneType>
		          <PhoneNumber>5996999</PhoneNumber>
		          <PhoneAreaCode>877</PhoneAreaCode>
		          <PhoneCountryCode>0001</PhoneCountryCode>
		        </Phone>
		        <Phone>
		          <PhoneType>DDI</PhoneType>
		          <PhoneNumber>1234567</PhoneNumber>
		          <PhoneAreaCode>877</PhoneAreaCode>
		          <PhoneCountryCode>0001</PhoneCountryCode>
		        </Phone>
		        <Phone>
		          <PhoneType>FAX</PhoneType>
		          <PhoneNumber>7654321</PhoneNumber>
		          <PhoneAreaCode>877</PhoneAreaCode>
		          <PhoneCountryCode>0001</PhoneCountryCode>
		        </Phone>
		        <Phone>
		          <PhoneType>MOBILE</PhoneType>
		          <PhoneNumber>5555555</PhoneNumber>
		          <PhoneAreaCode>877</PhoneAreaCode>
		          <PhoneCountryCode>0001</PhoneCountryCode>
		        </Phone>
		      </Phones>
		    </Contact>
    </xsl:for-each>
</Contacts>



</xsl:template>

</xsl:stylesheet>