<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes"/>
	<xsl:include href="HeaderFooter.xslt"/>
	<xsl:template match="/OnlineStore">
		<xsl:apply-templates select="Header"/>
	</xsl:template>
	<xsl:template name="Body">
		<xsl:apply-templates select="/OnlineStore/Catalog"/>
	</xsl:template>
	<xsl:template match="Catalog">
		<xsl:apply-templates select="ProductGroup"/>
	</xsl:template>
	<xsl:template match="ProductGroup">
		<xsl:apply-templates select="Product"/>
	</xsl:template>
	<xsl:template match="Product">
		<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding-top: 5px">
			<tr>
				<td height="100%" valign="top" style="padding-left: 5px; padding-right: 5px">
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td align="center" valign="top">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											<img src="/Picture.aspx?width=190&amp;height=190&amp;id={ProductPictureID}" class="image" vspace="0" alt="{Title}" onclick="window.open('/Enlarge.aspx?id={@id}')" style="cursor:hand" width="190" height="190"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td heignht="99%" align="center" valign="top" style="padding-top: 5px; padding-bottom: 5px ">
								<xsl:choose>
									<xsl:when test="ShippingPrice/@type = 'pickup'">
										<table height="100%" cellpadding="2" cellspacing="0" border="0">
											<tr>
												<td align="center" class="small_text">Price:&#160;</td>
											</tr>
											<tr>
												<td align="center" class="item_details_price">
													<strong>
														<xsl:value-of select="EComPrice/Money/Formated" disable-output-escaping="yes"/>
													</strong>
												</td>
											</tr>
										</table>
									</xsl:when>
									<xsl:otherwise>
										<table height="100%" cellpadding="2" cellspacing="0" border="0">
											<tr>
												<td align="center" class="small_text">Price:&#160;</td>
											</tr>
											<tr>
												<td align="center" class="item_details_price">
													<strong>
														<xsl:value-of select="EComPrice/Money/Formated" disable-output-escaping="yes"/>
													</strong>
												</td>
											</tr>
										</table>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</table>
				</td>
				<td width="99%" height="100%" valign="top" style="padding-left: 10px">
					<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td align="left" height="40" valign="top">
								<xsl:call-template name="ProductTextLink">
									<xsl:with-param name="text" select="Title"/>
									<xsl:with-param name="class">product_info_item_title</xsl:with-param>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td>
								<span class="text">
									<div id="productinfo_div">
										<xsl:value-of select="Description" disable-output-escaping="yes"/>
										<br/>
										<xsl:value-of select="AdditionalDescription" disable-output-escaping="yes"/>
										<br/>
									</div>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" cellpadding="3" cellspacing="0" border="0">
									<xsl:if test="count(Classification) &gt; 0">
										<tr>
											<td>
												<table cellpadding="0" cellspacing="0" class="table_1">
													<xsl:for-each select="Classification">
														<tr>
															<td class="td_1_1">
																<xsl:value-of select="@name"/>
															</td>
															<td class="td_1_2">
																<xsl:value-of select="@value"/>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
										</tr>
									</xsl:if>
									<tr>
										<td class="text">
											<xsl:apply-templates select="PartList"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<br/>
							</td>
						</tr>
						<tr>
							<td valign="middle" align="left" height="99%">
								<input type="button" class="button" value="Enlarge" style="width:80px" onClick="window.open('/Enlarge.aspx?id={@id}', 'ShippingCalculator','menubar=no, scrollbars=yes, toolbar=no, location=no, directories=no, resizable=no, width=700, height=500');" title="Enlarge"/>
								<input type="button" class="button" value="Shipping Price" style="width:80px" onClick="window.open('/ShippingCalculator.aspx?id={@id}', 'ShippingCalculator','menubar=no, scrollbars=yes, toolbar=no, location=no, directories=no, resizable=no, width=700, height=500');" title="View Shipping Price"/>
								<xsl:if test="Quantity != 0">
									<input type="button" class="button" value="Add To Cart" style="width:80px" onClick="document.location.href='/Cart.aspx?addProductId={@id}'"/>
								</xsl:if>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template match="PartList">
		<table width="100%" cellspacing="0" cellpadding="5" border="0">
			<xsl:if test="count(Part[string(@type) = 'include']) &gt; 0">
				<tr>
					<td width="49%" valign="top">
						<p class="header_include">This Item Includes:</p>
						<span class="text">
							<xsl:for-each select="Part[string(@type) = 'include']">
								<xsl:value-of select="current()"/>
								<xsl:if test="position() != last()">
									<xsl:text>,&#160;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</span>
					</td>
				</tr>
			</xsl:if>
			<xsl:if test="count(Part[string(@type) = 'miss']) &gt; 0">
				<tr>
					<td width="49%" valign="top">
						<p class="header_missing">This Item Does Not Include:</p>
						<span class="text">
							<xsl:for-each select="Part[string(@type) = 'miss']">
								<xsl:value-of select="current()"/>
								<xsl:if test="position() != last()">
									<xsl:text>,&#160;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</span>
					</td>
				</tr>
			</xsl:if>
		</table>
	</xsl:template>
</xsl:stylesheet>
