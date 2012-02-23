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
		      <ContactNumber>0006841301</ContactNumber>
		      <Name>Ariki Properties</Name>
		      <ContactStatus>ACTIVE</ContactStatus>
		      <EmailAddress>emailaddress@yourdomain.com</EmailAddress>
		      <SkypeUserName>Skype Name/Number</SkypeUserName>
		      <BankAccountDetails>Bank Account Details</BankAccountDetails>
		      <TaxNumber>Tax ID Number</TaxNumber>
		      <AccountsReceivableTaxType>OUTPUT</AccountsReceivableTaxType>
		      <AccountsPayableTaxType>INPUT</AccountsPayableTaxType>
		      <FirstName>Simon</FirstName>
		      <LastName>Greenville</LastName>
		      <DefaultCurrency>USD</DefaultCurrency>
		      <Addresses>
		        <Address>
		          <AddressType>STREET</AddressType>
		          <AttentionTo>Simon G.</AttentionTo>
		          <AddressLine1>Level 71</AddressLine1>
		          <AddressLine2>30 Rockefeller plaza</AddressLine2>
		          <AddressLine3></AddressLine3>
		          <AddressLine4></AddressLine4>
		          <City>New York</City>
		          <Region>New York State</Region>
		          <PostalCode>10112</PostalCode>
		          <Country>USA</Country>
		        </Address>
		        <Address>
		          <AddressType>POBOX</AddressType>
		          <AttentionTo>Simon G.</AttentionTo>
		          <AddressLine1>PO Box 10112</AddressLine1>
		          <AddressLine2></AddressLine2>
		          <AddressLine3></AddressLine3>
		          <AddressLine4></AddressLine4>
		          <City>New York</City>
		          <Region>New York State</Region>
		          <PostalCode>10112</PostalCode>
		          <Country>USA</Country>
		        </Address>
		      </Addresses>
		      <Phones>
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