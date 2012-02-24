<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fn="fn" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output method="xml" indent="yes" encoding="UTF-8" />


<xsl:template match="/">
<xsl:message>context is <xsl:copy-of select="."/></xsl:message>
<Contacts>
	<xsl:message>I am here</xsl:message>
	<xsl:for-each select="root/row">
		  <Contact>
		  	  <!-- from "Card ID" filed, contact number can be optional, don't create if it is none -->
		  	  <xsl:if test="string(elem[@name='Card ID']) and not(contains(string(elem[@name='Card ID']),'None')) ">
		  	  		<ContactNumber><xsl:value-of select="elem[@name='Card ID']"/></ContactNumber>		  	  		
		  	  </xsl:if>
		  	  <!-- IF First Name ="" THEN Co._Last Name=Name ELSE =Last Name  ?? -->
		      <Name><xsl:value-of select="if (string(elem[@name='First Name'])) then concat(elem[@name='First Name'],' ', elem[@name='Co./Last Name']) else elem[@name='Co./Last Name']"/></Name>
		      <ContactStatus>ACTIVE</ContactStatus>
		      <EmailAddress><xsl:value-of select="elem[contains(@name,'Email')][1]"/></EmailAddress>
		      <!--  
		      <SkypeUserName>Skype Name/Number</SkypeUserName>
		      -->
		      <!-- from BSB -->
		      <BankAccountDetails><xsl:value-of select="elem[@name='BSB']"/></BankAccountDetails>
		      <!-- A.B.N. -->
		      <TaxNumber><xsl:value-of select="elem[contains(@name,'A.B.N.')][1]"/></TaxNumber>
		      
		     <!--  
					MYOB Tax Code	Xero TAX TYPE	RATE	Notes
					CAP	CAPEXINPUT	10	
					EXP	EXEMPTEXPORT	0	
					GNR	EXEMPTINPUT	0	
					FRE	EXEMPTINPUT	0	used for Expenses, Suppliers
					FRE	EXEMPTOUTPUT	0	used for Income, Clients
					GST	INPUT	10	GST on Expenses, Suppliers
					GST	OUTPUT	10	GST on Income, Clients
					INP	INPUTTAXED	0	
					N-T	NONE	0	
		      
		      
		 	  -->
		      <!-- this is for CUST ??  <elem name="Tax Code">GST</elem> -->
		      <xsl:variable name="taxCode" select="elem[name='Tax Code']"/>
		      <xsl:variable name="newCode">
		      	<xsl:choose>
		      		<xsl:when test="$taxCode='CAP'">CAPEXINPUT</xsl:when>
		      		<xsl:when test="$taxCode='EXP'">EXEMPTEXPORT</xsl:when>
		      		<xsl:when test="$taxCode='GNR'">EXEMPTINPUT</xsl:when>
		      		<xsl:when test="$taxCode='FRE'">EXEMPTOUTPUT</xsl:when>
		      		<xsl:when test="$taxCode='GST'">OUTPUT</xsl:when>
		      		<xsl:when test="$taxCode='INP'">INPUTTAXED</xsl:when>
		      		<xsl:when test="$taxCode='N-T'">NONE</xsl:when>
		      	</xsl:choose>
		      
		      </xsl:variable>
		      <AccountsReceivableTaxType><xsl:value-of select="$newCode"/></AccountsReceivableTaxType>
		      <!-- this is for SUPPLIER ?? 
		      <AccountsPayableTaxType>INPUT</AccountsPayableTaxType>
		      -->
		      
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
		      				<xsl:when test="(contains(lower-case($secondAdd),'po box') or contains(lower-case($secondAdd),'pobox')) and (not(contains(lower-case($firstAdd),'po box') or contains(lower-case($firstAdd),'pobox')))">
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
		          <!--  <elem name="           - Phone # 1"/> -->
		          <xsl:variable name="phone" select="elem[@name='           - Phone # 1'][1]"/>
		          <xsl:variable name="l" select="string-length($phone)"/>
		          <xsl:choose>
		          	<xsl:when test="$l &gt; 10">
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,$l)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,$l - 9,$l - 9)"/></PhoneAreaCode>
				          <PhoneCountryCode><xsl:value-of select="substring($phone,1,$l - 10)"/></PhoneCountryCode>		          	
		          	</xsl:when>
		          	<xsl:when test="$l &gt; 8">
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,$l)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,1,$l - 8)"/></PhoneAreaCode>
				          <PhoneCountryCode></PhoneCountryCode>		          	
		          	</xsl:when>	
		          	<xsl:otherwise>
				          <PhoneNumber><xsl:value-of select="$phone"/></PhoneNumber>
				          <PhoneAreaCode></PhoneAreaCode>
				          <PhoneCountryCode></PhoneCountryCode>		          	
		          	</xsl:otherwise>		          
		          </xsl:choose>
		        </Phone>
		      </Phones>
		    </Contact>
    </xsl:for-each>
</Contacts>



</xsl:template>

</xsl:stylesheet>