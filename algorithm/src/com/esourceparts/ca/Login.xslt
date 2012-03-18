<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="Information">
		<tr>
			<td class="checkout_information" valign="top">
				<i>NOTE:</i> You must provide the same e-mail address that is currently registered for your online marketplace account (eBay, Amazon.Com, Yahoo!, etc.) with which you have won this auction.
<br/>
				<br/>
Please make sure to enter valid eBay Item Number. Valid eBay item number is always 12 digits long.

		</td>
			<td rowspan="2" align="center">
				<img vspace="1" src="Design/Templates/General/icon_visa.gif"/>
				<br/>
				<img vspace="1" src="Design/Templates/General/icon_mastercard.gif"/>
				<br/>
				<img vspace="1" src="Design/Templates/General/icon_discover.gif"/>
				<br/>
				<img vspace="1" src="Design/Templates/General/icon_echeck.gif"/>
				<br/>
				<img vspace="1" src="Design/Templates/General/icon_amex.gif"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="https://www.paypal.com/en_US/i/logo/PayPal_Certified_Int_100x42.gif"/>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="PaymentProcessInformation">
		<tr>
			<td class="checkout_information">
		The Kyozou Interactive Checkout uses an electronic merchant to submit online payment transactions in a secure, timely, and efficient manner. After you click the "Continue" button on the Kyozou Checkout Order Confirmation Screen, you will be re-directed to a electronic merchant website (PayPal, AuctionPayments, etc.) to complete your order and finalize the transaction. An official Kyozou receipt from the seller will be issued to you upon the completion of the transaction.<br/>
				<br/>
				<i>NOTE:</i> All transactions are processed via third-party electronic payment processors. The Kyozou checkout will never prompt you to enter any passwords or electronic merchant account information details.
		</td>
		</tr>
		<tr>
			<td align="center">
				<a href="../rma/rmalogin.aspx" title="RMA">
					<img src="/Design/Templates/10/vision/rma.gif" width="110" height="35" border="0"/>
				</a>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="LoginTitle">
		CheckOut Auction Management System.
	</xsl:template>
	<xsl:template name="RegisterNowInformation">
	</xsl:template>
	<xsl:template name="LoginInformation">
	</xsl:template>
	<xsl:template name="EcommerceLoginTitle">
		Ecommerce Login
	</xsl:template>
	<xsl:template name="CustomerRegistrationButton">
		<img src="/Design/Templates/4/vision/register.gif" width="200" height="80" border="0" hspace="2"/>
	</xsl:template>
	<xsl:template name="ShoppingCartTitle">
		Shopping Cart
	</xsl:template>
</xsl:stylesheet>
