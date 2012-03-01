<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fn="fn" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output method="xhtml" indent="yes" encoding="UTF-8" />


<xsl:template match="/">

<Contacts  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="Contacts.xsd">
	<xsl:message>I am here</xsl:message>
	<xsl:for-each select="root/row">
		  <Contact>
		  	  <!-- from "Card ID" filed,  -->
		  	  <xsl:variable name="theNumber" select="if (contains(string(elem[@name='Card ID']),'None')) then 0 else replace(string(elem[@name='Card ID']), '[^0-9]', '') "/>
		  	  <ContactNumber><xsl:value-of select="if (string($theNumber)) then $theNumber else 0"/></ContactNumber>		  	  		
		  	  <!-- IF First Name ="" THEN Co._Last Name=Name ELSE =Last Name  ?? -->
		      <Name><xsl:value-of select="if (string(elem[@name='First Name'])) then concat(elem[@name='First Name'],' ', elem[@name='Co./Last Name']) else elem[@name='Co./Last Name']"/></Name>
		      <ContactStatus>ACTIVE</ContactStatus>
		      <EmailAddress><xsl:value-of select="elem[contains(@name,'Email')][1]"/></EmailAddress>
				<!-- always empty -->
		      <SkypeUserName></SkypeUserName>

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
		      <xsl:variable name="taxCode" select="elem[@name='Tax Code']"/>
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

		      <AccountsPayableTaxType></AccountsPayableTaxType>
		      
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
						          <AttentionTo></AttentionTo>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 2 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][2]"/></AddressLine2>
						          <AddressLine3><xsl:value-of select="elem[@name='           - Line 3'][2]"/></AddressLine3>
						          <AddressLine4><xsl:value-of select="elem[@name='           - Line 4'][2]"/></AddressLine4>
						          <City><xsl:value-of         select="elem[@name='           - City'][2]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][2]"/></Region>
						          <PostalCode><xsl:value-of   select="if (string(elem[@name='           - Postcode'][2])) then elem[@name='           - Postcode'][2] else 0"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][2]"/></Country>
						        </Address>
						        <Address>
						          <AddressType>STREET</AddressType>
						          <AttentionTo></AttentionTo>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine3><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine3>
						          <AddressLine4><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine4>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="if  (string(elem[@name='           - Postcode'][1])) then elem[@name='           - Postcode'][1] else 0"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>			      				
		      				</xsl:when>
		      				<xsl:otherwise>
						        <Address>
						          <AddressType>POBOX</AddressType>
						          <AttentionTo></AttentionTo>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine3><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine3>
						          <AddressLine4><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine4>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="if (string(elem[@name='           - Postcode'][1])) then elem[@name='           - Postcode'][1] else 0"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>
						        <Address>
						          <AddressType>STREET</AddressType>
						          <AttentionTo></AttentionTo>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 2 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][2]"/></AddressLine2>
						          <AddressLine3><xsl:value-of select="elem[@name='           - Line 3'][2]"/></AddressLine3>
						          <AddressLine4><xsl:value-of select="elem[@name='           - Line 4'][2]"/></AddressLine4>
						          <City><xsl:value-of         select="elem[@name='           - City'][2]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][2]"/></Region>
						          <PostalCode><xsl:value-of   select="if (string(elem[@name='           - Postcode'][2])) then elem[@name='           - Postcode'][2] else 0"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][2]"/></Country>
						        </Address>			      				
		      				</xsl:otherwise>
		      			</xsl:choose>	      		
		      		</xsl:when>
		      		<xsl:otherwise>
						        <Address>
						          <AddressType>POBOX</AddressType>
						          <AttentionTo></AttentionTo>
						          <AddressLine1><xsl:value-of select="elem[@name='Addr 1 - Line 1']"/></AddressLine1>
						          <AddressLine2><xsl:value-of select="elem[@name='           - Line 2'][1]"/></AddressLine2>
						          <AddressLine3><xsl:value-of select="elem[@name='           - Line 3'][1]"/></AddressLine3>
						          <AddressLine4><xsl:value-of select="elem[@name='           - Line 4'][1]"/></AddressLine4>
						          <City><xsl:value-of         select="elem[@name='           - City'][1]"/></City>
						          <Region><xsl:value-of       select="elem[@name='           - State'][1]"/></Region>
						          <PostalCode><xsl:value-of   select="if (string(elem[@name='           - Postcode'][1])) then elem[@name='           - Postcode'][1] else 0"/></PostalCode>
						          <Country><xsl:value-of      select="elem[@name='           - Country'][1]"/></Country>
						        </Address>		      		
		      		</xsl:otherwise>
		      	</xsl:choose>
		      </Addresses>
		      <Phones>
		      	<!-- how many phones =3 -->
		        <Phone>
		                   
		          <xsl:variable name="phoneNumber" select="elem[@name='           - Phone # 1'][1]"/>
		          <!-- remove all strings, other than + or space -->
		          <xsl:variable name="phone" select="replace($phoneNumber, '[^0-9]', '')"/>
		          <xsl:message>phonenumer=<xsl:copy-of select="$phoneNumber"/> phone=<xsl:copy-of select="$phone"/></xsl:message>
		          <xsl:variable name="l" select="string-length($phone)"/>
		          <xsl:choose>
		          	<xsl:when test="$l &gt; 10">
		          		  <xsl:variable name="areaCode" select="substring($phone,$l - 9,2)"/>
		          		  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,$l - 9,2)"/></PhoneAreaCode>
				          <PhoneCountryCode><xsl:value-of select="substring($phone,1,$l - 10)"/></PhoneCountryCode>		          	
		          	</xsl:when>
		          	<xsl:when test="$l &gt; 8">
		          	 	  <xsl:variable name="areaCode" select="substring($phone,1,2)"/>
		          	 	  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,1,2)"/></PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:when>	
		          	<xsl:otherwise>
		          		  <PhoneType>DEFAULT</PhoneType>
				          <PhoneNumber><xsl:value-of select="if (string($phone)) then $phone else 0"/></PhoneNumber>
				          <PhoneAreaCode>0</PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:otherwise>		          
		          </xsl:choose>
		        </Phone>
		        <!-- seocnd phone -->
		         <xsl:variable name="phoneNumber2" select="elem[@name='           - Phone # 2'][1]"/>
		         <xsl:if test="string($phoneNumber2)">
		        <Phone>
		          <!-- remove all strings, other than + or space -->
		          <xsl:variable name="phone" select="replace($phoneNumber2, '[^0-9]', '')"/>
		          <xsl:message>phonenumer2=<xsl:copy-of select="$phoneNumber2"/> phone=<xsl:copy-of select="$phone"/></xsl:message>
		          <xsl:variable name="l" select="string-length($phone)"/>
		          <xsl:choose>
		          	<xsl:when test="$l &gt; 10">
		          		  <xsl:variable name="areaCode" select="substring($phone,$l - 9,2)"/>
		          		  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,$l - 9,2)"/></PhoneAreaCode>
				          <PhoneCountryCode><xsl:value-of select="substring($phone,1,$l - 10)"/></PhoneCountryCode>		          	
		          	</xsl:when>
		          	<xsl:when test="$l &gt; 8">
		          	 	  <xsl:variable name="areaCode" select="substring($phone,1,2)"/>
		          	 	  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,1,2)"/></PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:when>	
		          	<xsl:otherwise>
		          		  <PhoneType>DEFAULT</PhoneType>
				          <PhoneNumber><xsl:value-of select="if (string($phone)) then $phone else 0"/></PhoneNumber>
				          <PhoneAreaCode>0</PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:otherwise>		          
		          </xsl:choose>
		        </Phone>
		        </xsl:if>

		        <!-- third phone -->
		         <xsl:variable name="phoneNumber3" select="elem[@name='           - Phone # 2'][1]"/>
		         <xsl:if test="string($phoneNumber3)">
		        <Phone>
		          <!-- remove all strings, other than + or space -->
		          <xsl:variable name="phone" select="replace($phoneNumber3, '[^0-9]', '')"/>
		          <xsl:message>phonenumer2=<xsl:copy-of select="$phoneNumber3"/> phone=<xsl:copy-of select="$phone"/></xsl:message>
		          <xsl:variable name="l" select="string-length($phone)"/>
		          <xsl:choose>
		          	<xsl:when test="$l &gt; 10">
		          		  <xsl:variable name="areaCode" select="substring($phone,$l - 9,2)"/>
		          		  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,$l - 9,2)"/></PhoneAreaCode>
				          <PhoneCountryCode><xsl:value-of select="substring($phone,1,$l - 10)"/></PhoneCountryCode>		          	
		          	</xsl:when>
		          	<xsl:when test="$l &gt; 8">
		          	 	  <xsl:variable name="areaCode" select="substring($phone,1,2)"/>
		          	 	  <PhoneType><xsl:value-of select="if (contains($areaCode,'4')) then 'MOBILE' else 'DEFAULT'"/></PhoneType> 
				          <PhoneNumber><xsl:value-of select="substring($phone,$l - 7,8)"/></PhoneNumber>
				          <PhoneAreaCode><xsl:value-of select="substring($phone,1,2)"/></PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:when>	
		          	<xsl:otherwise>
		          		  <PhoneType>DDI</PhoneType>
				          <PhoneNumber><xsl:value-of select="if (string($phone)) then $phone else 0"/></PhoneNumber>
				          <PhoneAreaCode>0</PhoneAreaCode>
				          <PhoneCountryCode>0</PhoneCountryCode>		          	
		          	</xsl:otherwise>		          
		          </xsl:choose>
		        </Phone>
		        </xsl:if>		        		        
		      </Phones>
		    </Contact>
    </xsl:for-each>
</Contacts>



</xsl:template>

</xsl:stylesheet>