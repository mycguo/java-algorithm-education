<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="pageLayout" select="'ContactsFormOnTop'"/>
	<xsl:param name="pictureWidth" select="'80'"/>
	<xsl:param name="pictureHeight" select="'80'"/>
	<xsl:include href="../../AllFunctions.xslt"/>
	<xsl:include href="../../AllLayouts.xslt"/>
	<xsl:include href="./seo.xslt"/>
	<xsl:output encoding="utf-8"/>
	<xsl:template match="Header">
		<html>
			<head>
				<title>
					<xsl:value-of select="/OnlineStore/Page/PageTitle"/>
				</title>
				<meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
				<META NAME="Description">
					<xsl:attribute name="CONTENT"><xsl:value-of select="/OnlineStore/Page/MetaDescription"/></xsl:attribute>
				</META>
				<META NAME="keywords">
					<xsl:attribute name="CONTENT"><xsl:value-of select="/OnlineStore/Page/MetaKeywords"/></xsl:attribute>
				</META>
				<meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
				<script type="text/javascript" language="javascript" src="/Design/Templates/14/js/rollovers.js"/>
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/css.css"/>
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/checkout.css"/>
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/error.css"/>
				<meta name='google-site-verification' content='Cfd3vuJoZn9HBXrxmfe6S6KErljI3WCEur-8lkokToo'/>
				<xsl:value-of select="/OnlineStore/Header/HeaderCodeElement" disable-output-escaping="yes"/>
			</head>
			<body bgcolor="#D4CFBB" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" class="text">
				<br/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="6%">
&#160;
                </td>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="100%" valign="top" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/59.gif" style="background-position:bottom ">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0" cellpadding="0" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/52.gif">
														<tr>
															<td>
																<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/51.gif" width="42" height="38" alt=""/>
															</td>
															<td width="50%" class="special_text pad3">
																<!-- korzina -->
																<xsl:call-template name="Header_Footer_Cart">
																	<xsl:with-param name="Cart" select="/OnlineStore/Cart"/>
																</xsl:call-template>
																<!--/korzina -->
															</td>
															<td>
																<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/53.gif" width="42" height="38" alt=""/>
															</td>
															<!-- search form -->
															<form action="/Catalog.aspx" method="get">
																<td width="50%" class="special_text pad3">
&#160;
                                      search:
&#160;
                                      <input type="Text" name="srchSearchCriteria" class="inp"/>
&#160;
                                      <input type="submit" value="" title="Search" class="button_go"/>
																</td>
															</form>
															<!--/search form -->
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td>
																<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/58.gif" width="9" height="88" alt=""/>
															</td>
															<td width="100%" align="left" valign="middle" style="color:#4C4C4C; font-size: 16px; font-weight: bold; font-family: Arial;">
																<table cellpadding="0" cellspacing="0" align="left" width="100%">
																	<tr>
																		<td>
																			<!-- logo -->
																			<xsl:if test="string(Logo/@type) = 'image'">
																				<a href="/">
																					<img src="{Logo}" alt="Logo" border="0"/>
																				</a>
																			</xsl:if>
																			<xsl:if test="string(Logo/@type) = 'text'">
																				<img src="/Design/Templates/13/vision/spacer.gif" width="222" height="1" align="absmiddle"/>
																				<br/>
																				<nobr>
																					<a href="/">
																						<xsl:value-of select="Logo"/>
																					</a>
																				</nobr>
																			</xsl:if>
																			<!--/logo -->
																		</td>
																		<td valign="middle" align="middle" width="95%" style="font-family: Arial, Helvetica, sans-serif; font-size: 36px; font-weight: bold; font-style:italic">“Your source for electronics and parts!”</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/59.gif" style="background-position:bottom ">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:url(/Design/Templates/{/OnlineStore/@designDirectory}/images/70.gif) bottom right no-repeat">
											<tr>
												<td>
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/91.gif" width="42" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/56.gif" align="right">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/56.gif" width="112" height="16" border="0" hspace="0" vspace="0" alt=""/>
													<!--                              <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="112" height="1" /> -->
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/57.gif" width="11" height="16" border="0" hspace="0" vspace="0" alt=""/>
												</td>
											</tr>
											<tr>
												<td>
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/90.gif" width="42" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/60.gif">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/catalog.aspx">Catalog</a>
												</td>
											</tr>
											<tr>
												<td valign="top">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/72.gif" width="12" height="6" alt=""/>
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/61.gif" alt="" width="30" height="16" align="top"/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/62.gif" class="special_text">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/auctions.aspx">Auctions</a>
												</td>
											</tr>
											<tr>
												<td align="right">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/63.gif" width="30" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/64.gif" class="special_text">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/contacts.aspx">Contact Us</a>
												</td>
											</tr>
											<tr>
												<td align="right">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/63.gif" width="30" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/64.gif" class="special_text">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/aboutus.aspx">Shipping &amp; Policies</a>
												</td>
											</tr>
											<tr>
												<td align="right">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/65.gif" width="30" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/67.gif" class="special_text">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/cart.aspx">Cart</a>
												</td>
											</tr>
											<tr>
												<td align="right">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/68.gif" width="30" height="16" alt=""/>
												</td>
												<td background="/Design/Templates/{/OnlineStore/@designDirectory}/images/69.gif" class="special_text">
													<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker-2.gif" width="3" height="3" hspace="5" align="absmiddle"/>
													<a class="menu" href="/checkout.aspx">Checkout</a>
												</td>
											</tr>
											<tr>
												<td>
													<img src="/Design/Templates/common/images/i.gif" width="1" height="1" border="0" alt=""/>
												</td>
												<td style="height: 14px;background-color:transparent">
													<img src="/Design/Templates/common/images/i.gif" width="1" height="1" border="0" alt=""/>
													<!--                              <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/70.gif" width="9" height="30" alt="" /> -->
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="1" height="8"/>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td valign="top" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/17.gif" style="background-position:left; background-repeat:repeat-y" bgcolor="#ECEAE1">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td>
																<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_37.gif" width="42" height="38" alt=""/>
															</td>
															<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/12.gif" class="special_text pad3" onClick="document.location.href='/catalog.aspx'" style="cursor: hand">
                                    Categories
                                    <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="150" height="1" align="absmiddle"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td valign="top">
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td>
																<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="11" height="1"/>
															</td>
															<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/18.gif" style="background-repeat:repeat-x; padding: 10px 10px 10px ">
																<!--left navigation-->
																<xsl:apply-templates select="/OnlineStore/CategoryList"/>
																<!--/left navigation-->
															</td>
														</tr>
														<!--Featured Product-->
														<xsl:call-template name="MainPromoProduct">
															<xsl:with-param name="productGroup" select="/OnlineStore/PromoProductGroup"/>
														</xsl:call-template>
														<!--/Featured Product-->
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="100%" valign="top" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/19.gif" style="background-position:left; background-repeat:repeat-y" bgcolor="#ECEAE1">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td valign="top">
													<!--main-->
													<xsl:choose>
														<xsl:when test="@type = 'checkout'">
															<!-- header -->
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td width="100%">
																		<table width="100%" border="0" cellspacing="0" cellpadding="0">
																			<tr>
																				<td>
																					<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_39.gif" width="42" height="38" alt=""/>
																				</td>
																				<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/12.gif" class="special_text pad3">
																					<!--Page Name-->
																					<span class="window_header">
																						<xsl:value-of select="@pageName"/>
																					</span>
																					<!--/Page Name-->
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- /header -->
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td>
																		<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="11" height="1"/>
																	</td>
																	<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/18.gif" style="background-repeat:repeat-x " class="pad">
																		<xsl:call-template name="Body"/>
																	</td>
																</tr>
															</table>
														</xsl:when>
														<xsl:when test="@type = 'rma'">
															<xsl:call-template name="Body"/>
														</xsl:when>
														<xsl:when test="@pageName = 'Catalog'">
															<xsl:call-template name="Body"/>
														</xsl:when>
														<xsl:when test="@pageName = 'SearchResults'">
															<xsl:call-template name="Body"/>
														</xsl:when>
														<xsl:otherwise>
															<!--main table-->
															<!-- header -->
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td width="100%">
																		<table width="100%" border="0" cellspacing="0" cellpadding="0">
																			<tr>
																				<td>
																					<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_39.gif" width="42" height="38" alt=""/>
																				</td>
																				<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/12.gif" class="special_text pad3">
																					<!--Page Name-->
																					<span class="window_header">
																						<xsl:value-of select="@pageName"/>
																					</span>
																					<!--/Page Name-->
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- /header -->
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td>
																		<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="11" height="1"/>
																	</td>
																	<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/18.gif" style="background-repeat:repeat-x " class="pad">
																		<!-- call body -->
																		<xsl:call-template name="Body"/>
																		<!-- /call body -->
																	</td>
																</tr>
															</table>
															<!-- /main table-->
														</xsl:otherwise>
													</xsl:choose>
													<!-- /main -->
													<!--PromoProductGroup-->
													<xsl:apply-templates select="/OnlineStore/PromoProductGroup"/>
													<!--/PromoProductGroup-->
												</td>
											</tr>
										</table>
									</td>
									<td valign="top" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/20.gif" style="background-position:left; background-repeat:repeat-y">
										<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/16.gif" width="11" height="32" alt=""/>
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/25.gif">
								<tr>
									<td>
										<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/24.gif" width="42" height="38" alt=""/>
									</td>
									<td width="100%" class="pad3" align="center">
										<span class="special_text">&#169; Kyozou.com
		<xsl:value-of select="substring(/OnlineStore/@currentDate, 0, 5)" disable-output-escaping="yes"/>
		. All Rights Reserved.</span>
									</td>
									<td>
										<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/26.gif" width="11" height="38" alt=""/>
									</td>
								</tr>
							</table>
						</td>
						<td width="6%">
&#160;
                </td>
					</tr>
				</table>
				<br/>
				<div align="center">
					<span id="siteseal">
						<script type="text/javascript" src="https://seal.godaddy.com/getSeal?sealID=VdjeMcXs2gfVvqJGbMYi63vk6BwU0acLEuSUECLYBJJSFGZAOOt13Is"/>
						<br/>
						<a href='https://www.godaddy.com/gdshop/ssl/ssl.asp'>SSL Certificates</a>
					</span>
				</div>
				<xsl:if test="not(contains(/OnlineStore/@currentUrl, 'https://'))">
					<xsl:value-of select="/OnlineStore/Header/AnalyticsCodeElement" disable-output-escaping="yes"/>
				</xsl:if>
			</body>
		</html>
	</xsl:template>
	<!-- templates -->
	<xsl:template name="Header_Footer_Cart">
		<xsl:param name="Cart"/>
		<nobr>
          Shopping-cart:
          <strong>
				<xsl:value-of select="sum($Cart/Product/CartQuantity)"/>
			</strong>
          item
          <strong>
				<xsl:value-of select="$Cart/Subtotal/Money/Formated" disable-output-escaping="yes"/>
			</strong>
          cost
        </nobr>
	</xsl:template>
	<!--navigation-->
	<xsl:template match="PromoProductGroup">
		<xsl:if test="count(Product) &gt; 1">
			<!-- In The Spotlight header-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/22.gif" colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_43.gif" width="42" height="38" alt=""/>
								</td>
								<td width="100%" class="special_text pad3">
                      In The Spotlight
                    </td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- /In The Spotlight header-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img src="/Design/Templates/15/vision/spacer.gif" alt="" width="1" height="47"/>
					</td>
					<xsl:choose>
						<xsl:when test="count(Product) &lt; 4">
							<xsl:call-template name="TitleLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="count(Product)"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="TitleLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="4"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
					<!--spacer 1 left
              <td>
                <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" alt="" width="1" height="47" />
              </td>-->
				</tr>
				<tr>
					<td>
						<img src="/Design/Templates/15/vision/spacer.gif" width="11" height="1"/>
					</td>
					<xsl:choose>
						<xsl:when test="count(Product) &lt; 4">
							<xsl:call-template name="ProductLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="count(Product)"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="ProductLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="4"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
					<!--spacer 1 right
              <td>
                <img src="/Design/Templates/15/vision/spacer.gif" width="1" height="1" />
              </td>-->
				</tr>
				<tr>
					<td>
						<img src="/Design/Templates/15/vision/spacer.gif" alt="" width="1" height="47"/>
					</td>
					<xsl:choose>
						<xsl:when test="count(Product) &lt; 4">
							<xsl:call-template name="AddToCartLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="count(Product)"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="AddToCartLoop">
								<xsl:with-param name="productGroup" select="current()"/>
								<xsl:with-param name="i" select="2"/>
								<xsl:with-param name="to" select="4"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
					<!--spacer 1 left
              <td>
                <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" alt="" width="1" height="47" />
              </td>-->
				</tr>
			</table>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Product" mode="Promo">
		<td width="34%" valign="top" align="middle" style="padding: 12px 12px 10px 12px" class="text">
			<xsl:call-template name="ProductImageLink">
				<xsl:with-param name="image" select="ProductPictureID"/>
				<xsl:with-param name="alt" select="Title"/>
				<xsl:with-param name="w">150</xsl:with-param>
				<xsl:with-param name="h">150</xsl:with-param>
				<xsl:with-param name="class">image</xsl:with-param>
			</xsl:call-template>
			<br/>
			<br/>
			<strong>
				<xsl:value-of select="EComPrice/Money/Formated" disable-output-escaping="yes"/>
			</strong>
			<br/>
			<br/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="text">
				<tr>
					<td align="left" valign="middle">
						<xsl:value-of select="substring(Description, 0, 100)" disable-output-escaping="yes"/>
                ...
<xsl:call-template name="ProductTextLink">
							<xsl:with-param name="text">read more</xsl:with-param>
							<xsl:with-param name="class">style3</xsl:with-param>
						</xsl:call-template>
					</td>
				</tr>
			</table>
		</td>
	</xsl:template>
	<xsl:template name="TitleBlock">
		<xsl:param name="product"/>
		<td valign="middle" align="center" style="padding: 12px 10px 0px 10px">
			<xsl:call-template name="ProductTextLinkByNode">
				<xsl:with-param name="node" select="$product"/>
				<xsl:with-param name="text" select="$product/Title"/>
				<xsl:with-param name="class">style1</xsl:with-param>
			</xsl:call-template>
			<br/>
		</td>
	</xsl:template>
	<xsl:template name="AddToCartBlock">
		<xsl:param name="product"/>
		<td valign="middle" align="center">
			<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker.gif" width="10" height="9" hspace="7" vspace="3" align="absmiddle"/>
			<a href="/Cart.aspx?addProductId={$product/@id}" class="style3">Add to cart</a>
		</td>
	</xsl:template>
	<xsl:template name="TitleLoop">
		<xsl:param name="productGroup"/>
		<xsl:param name="i"/>
		<xsl:param name="to"/>
		<xsl:if test="$i &lt;= $to">
			<xsl:call-template name="TitleBlock">
				<xsl:with-param name="product" select="$productGroup/Product[$i]"/>
			</xsl:call-template>
			<xsl:if test="$i != $to">
				<!--borer line part1-->
				<td bgcolor="#783835">
					<img src="/Design/Templates/15/vision/spacer.gif" width="1" height="9"/>
				</td>
				<!--/borer line part1-->
			</xsl:if>
			<xsl:call-template name="TitleLoop">
				<xsl:with-param name="productGroup" select="$productGroup"/>
				<xsl:with-param name="i" select="$i + 1"/>
				<xsl:with-param name="to" select="$to"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="AddToCartLoop">
		<xsl:param name="productGroup"/>
		<xsl:param name="i"/>
		<xsl:param name="to"/>
		<xsl:if test="$i &lt;= $to">
			<xsl:call-template name="AddToCartBlock">
				<xsl:with-param name="product" select="$productGroup/Product[$i]"/>
			</xsl:call-template>
			<xsl:if test="$i != $to">
				<!--border line part1-->
				<td bgcolor="#783835">
					<img src="/Design/Templates/15/vision/spacer.gif" width="1" height="9"/>
				</td>
				<!--/border line part1-->
			</xsl:if>
			<xsl:call-template name="AddToCartLoop">
				<xsl:with-param name="productGroup" select="$productGroup"/>
				<xsl:with-param name="i" select="$i + 1"/>
				<xsl:with-param name="to" select="$to"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="ProductLoop">
		<xsl:param name="productGroup"/>
		<xsl:param name="i"/>
		<xsl:param name="to"/>
		<xsl:if test="$i &lt;= $to">
			<xsl:apply-templates select="$productGroup/Product[$i]" mode="Promo"/>
			<xsl:if test="$i != $to">
				<!--border line part2-->
				<td bgcolor="#783835" valign="top">
					<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="1" height="9"/>
				</td>
				<!--border line part2-->
			</xsl:if>
			<xsl:call-template name="ProductLoop">
				<xsl:with-param name="productGroup" select="$productGroup"/>
				<xsl:with-param name="i" select="$i + 1"/>
				<xsl:with-param name="to" select="$to"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="MainPromoProduct">
		<xsl:param name="productGroup"/>
		<xsl:if test="count($productGroup/Product) &gt; 0">
			<tr>
				<td width="100%" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/22.gif" colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_43.gif" width="42" height="38" alt=""/>
							</td>
							<td width="100%" class="special_text pad3">
                    Featured Product
                  </td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif" width="11" height="1"/>
				</td>
				<td width="100%">
					<table width="100%" border="0" cellspacing="9" cellpadding="0">
						<tr>
							<td colspan="2" align="center">
								<xsl:call-template name="ProductTextLinkByNode">
									<xsl:with-param name="node" select="$productGroup/Product[1]"/>
									<xsl:with-param name="text" select="$productGroup/Product[1]/Title"/>
									<xsl:with-param name="class">style1</xsl:with-param>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td>
								<!-- sm photo -->
								<xsl:call-template name="ProductImageLinkByNode">
									<xsl:with-param name="node" select="$productGroup/Product[1]"/>
									<xsl:with-param name="alt" select="$productGroup/Product[1]/Title"/>
									<xsl:with-param name="class">image</xsl:with-param>
									<xsl:with-param name="w">80</xsl:with-param>
									<xsl:with-param name="h">80</xsl:with-param>
									<xsl:with-param name="image" select="concat('/Picture.aspx?width=80&amp;height=80&amp;id=', $productGroup/Product[1]/ProductPictureID)"/>
								</xsl:call-template>
							</td>
							<td width="100%" valign="bottom">
								<div align="center" class="Price_Promo_Main">
									<xsl:value-of select="$productGroup/Product[1]/EComPrice/Money/Formated" disable-output-escaping="yes"/>
								</div>
								<br/>
								<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker.gif" width="10" height="9" hspace="7" vspace="3" align="absmiddle"/>
								<xsl:call-template name="ProductTextLinkByNode">
									<xsl:with-param name="node" select="$productGroup/Product[1]"/>
									<xsl:with-param name="text">More info</xsl:with-param>
									<xsl:with-param name="class">style3</xsl:with-param>
								</xsl:call-template>
								<br/>
								<img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/marker.gif" width="10" height="9" hspace="7" vspace="3" align="absmiddle"/>
								<a href="/Cart.aspx?addProductId={$productGroup/Product[1]/@id}" class="style3">Add to cart</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
