<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes"/>
	<xsl:include href="HeaderFooter.xslt"/>
	<xsl:template match="/OnlineStore">
		<xsl:apply-templates select="Header"/>
	</xsl:template>
	<xsl:template name="Body">
		<xsl:call-template name="ShoppingCartCustom"/>
	</xsl:template>
	<xsl:template name="ShoppingCartCustom">
		<xsl:if test="/OnlineStore/BuySafeRollover">
			<xsl:value-of select="/OnlineStore/BuySafeRollover/RolloverScript" disable-output-escaping="yes"/>
		</xsl:if>
		<table width="100%" cellpadding="5" cellspacing="0" border="0">
			<form action="Cart.aspx" method="get">
				<tr>
					<td>
						<p class="information_text">
							<xsl:value-of select="/OnlineStore/Page/CustomContent" disable-output-escaping="yes"/>
						</p>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="99%" align="right">
									<span class="cart_subtotal_text">
                        Subtotal:&#160;
                      </span>
								</td>
								<td class="cart_subtotal_price" valign="center">
									<xsl:choose>
										<xsl:when test="number(/OnlineStore/Cart/Subtotal/Money/Amount) = 0">0</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="/OnlineStore/Cart/Subtotal/Money/Formated" disable-output-escaping="yes"/>
										</xsl:otherwise>
									</xsl:choose>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td class="update_btn">
												<input type="submit" value="Update Cart" title="Update Cart" class="button"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" valign="top">
						<table width="100%" cellpadding="0" cellspacing="0" border="0" class="cart">
							<xsl:choose>
								<xsl:when test="count(/OnlineStore/Cart/Product) != 0">
									<tr>
										<td class="cart_header_1">Photo</td>
										<td width="55%" class="cart_header_2">
                          Shopping Cart Items
&#160;
                          -
&#160;
                          To Buy Now
                        </td>
										<td width="10%" class="cart_header_3">
                          Cost
                        </td>
										<td width="10%" class="cart_header_4">
                          Quantity
                        </td>
										<td width="10%" class="cart_header_5">
                          Price
                        </td>
										<td width="15%" class="cart_header_6">
                          Actions
                        </td>
									</tr>
								</xsl:when>
								<xsl:otherwise>
									<tr>
										<td align="center">
											<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td class="div" align="center" valign="middle">
														<br/>
														<span class="checkout_information">
                                  No Items Found in Your Shopping Cart
                                </span>
														<br/>
														<br/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:for-each select="/OnlineStore/Cart/Product">
								<tr>
									<td style="padding:5px;" class="cart_1">
										<xsl:call-template name="ProductImageLink">
											<xsl:with-param name="image" select="ProductPictureID"/>
											<xsl:with-param name="alt" select="Title"/>
											<xsl:with-param name="w">50</xsl:with-param>
											<xsl:with-param name="h">50</xsl:with-param>
											<xsl:with-param name="class">image</xsl:with-param>
										</xsl:call-template>
										<!--
                    	<img src="/Picture.aspx?width=50&amp;height=50&amp;id={ProductPictureID}" hspace="5" vspace="5" alt="{Title}" onclick="document.location.href='/ProductInfo.aspx?id={@id}'" class="image" width="50" height="50" /> -->
									</td>
									<td class="cart_2">
										<xsl:call-template name="ProductTextLink">
											<xsl:with-param name="text" select="Title"/>
											<xsl:with-param name="class">cart_item_title</xsl:with-param>
										</xsl:call-template>
										<!--                         <xsl:if test="count(VariationOrderValueList/VariationValue)!=0">
<p style="font-size:11px;margin-top:0"><xsl:for-each select="VariationOrderValueList/VariationValue">
<xsl:value-of select="Name"/><xsl:if test="position()!=last()">;</xsl:if>
</xsl:for-each></p></xsl:if> -->
									</td>
									<td class="cart_3">
										<span class="cart_cost">
											<xsl:value-of select="EComPrice/Money/Formated" disable-output-escaping="yes"/>
										</span>
									</td>
									<td class="cart_4">
										<span class="cart_quantity">
											<xsl:call-template name="BuildQuantityInputField"/>
										</span>
									</td>
									<td class="cart_5">
										<nobr>
											<span class="cart_price">
												<xsl:value-of select="TotalItemPrice/Money/Amount"/>
												<xsl:text>
							&#160;
                          </xsl:text>
												<xsl:value-of select="EComPrice/Money/Currency/@code"/>
											</span>
										</nobr>
									</td>
									<td class="cart_6">
										<xsl:call-template name="RemoveButtonGlobal"/>
									</td>
								</tr>
								<xsl:if test="position()!=last()">
									<tr>
										<td colspan="5" class="cart_spacer">
											<img src="spacer.gif" class="cart_spacer_size"/>
										</td>
									</tr>
								</xsl:if>
							</xsl:for-each>
							<xsl:call-template name="BuySafeShoppingCartGlobal"/>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" title="Continue Shopping" value="Continue Shopping" class="button" onclick="window.location.replace('/Catalog.aspx')" id="btn_shoppingcart_continueshopping"/>
						<xsl:if test="count(/OnlineStore/Cart/Product) != 0">
							<input type="button" title="Checkout" class="button" value="Checkout" onclick="window.location.replace('/Checkout.aspx')"/>
						</xsl:if>
					</td>
				</tr>
				<xsl:call-template name="GoogleCheckoutSectionGlobal"/>
			</form>
		</table>
	</xsl:template>
</xsl:stylesheet>
