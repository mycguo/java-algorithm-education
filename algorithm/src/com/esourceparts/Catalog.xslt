<?xml version="1.0" encoding="UTF-8" ?>
    <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
      <xsl:output method="html" version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes" />
      <xsl:include href="HeaderFooter.xslt" />
      <!-- OnlineStore -->
      <xsl:template match="/OnlineStore">
        <xsl:apply-templates select="Header" />
      </xsl:template>
      <!-- Body -->
      <xsl:template name="Body">
        <xsl:apply-templates select="/OnlineStore/Catalog" />
      </xsl:template>
      <!-- Catalog -->
      <xsl:template match="Catalog">
      <xsl:call-template name="DisplayCategoryDescription"/>
        <!--header-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/12.gif">
          <tr>
            <td width="100%">
              <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/spacer.gif"  width="1" height="15" align="absmiddle"/>
            </td>
            <td>
              <!--top Scroll-->
              <xsl:if test="count(ProductGroup)!=0">
                <xsl:apply-templates select="PageScroll" />
              </xsl:if>
              <!--/top Scroll-->
            </td>
          </tr>
        </table>
        <!--/header-->
        <!--main-->
        <xsl:choose>
          <xsl:when test="count(ProductGroup)!=0">
            <xsl:apply-templates select="ProductGroup" />
          </xsl:when>
          <xsl:otherwise>
            <p class="text" align="center">
              Products are not found
            </p>
          </xsl:otherwise>
        </xsl:choose>
        <!--main-->
        <!--bottom scroll-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" background="/Design/Templates/{/OnlineStore/@designDirectory}/images/22.gif">
          <tr>
            <td align="right" style="padding-left: 15px;">
              <xsl:if test="count(ProductGroup)!=0">
                <xsl:apply-templates select="PageScroll" />
              </xsl:if>
            </td>
          </tr>
        </table>
        <!--/bottom scroll-->
      </xsl:template>
      <!-- ProductGroup -->
      <xsl:template match="ProductGroup">
        <xsl:apply-templates select="Product" />
      </xsl:template>
      <!-- Product -->
      <xsl:template match="Product" name="Product">
		<!--header-->
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%"  background="/Design/Templates/{/OnlineStore/@designDirectory}/images/22.gif" colspan="2">
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <img src="/Design/Templates/{/OnlineStore/@designDirectory}/images/18-verh-1psd_43.gif" width="42" height="38" alt="" />
                  </td>
                  <td width="100%" class="special_text pad3">
				  <nobr>
                    <!--title-->
<xsl:call-template name="ProductTextLink">
	<xsl:with-param name="text" select="Title"/>
	<xsl:with-param name="class">product_info_item_title</xsl:with-param>
</xsl:call-template>                  
                    
                
                    <!--title-->
					</nobr>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
		<!--header-->
		<!-- table -->
        
              <!-- main info  -->
              <table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 20px 10px 15px 7px">
                <tr>
                  <td height="100%" valign="top" style="padding-left: 10px; padding-right: 10px">
                    <table height="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td align="center" valign="middle" height="99%">
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td style="padding-left: 21px">
                              
<xsl:call-template name="ProductImageLink">
	<xsl:with-param name="image" select="ProductPictureID"/>
	<xsl:with-param name="alt" select="Title"/>
	<xsl:with-param name="w">150</xsl:with-param>
	<xsl:with-param name="h">150</xsl:with-param>
	<xsl:with-param name="class">image</xsl:with-param>
</xsl:call-template>                            
                              
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td heignht="99%" align="center" valign="top" style="padding-top: 5px; padding-bottom: 5px ">
                          <table height="100%" cellpadding="2" cellspacing="0" border="0">
                            <tr>
                              <td align="center" class="small_text">
                                Price:
                              </td>
                            </tr>
                            <tr>
                              <td align="center" class="item_details_price">
                                <strong>
                                  <!--price-->
                                  <xsl:value-of select="EComPrice/Money/Formated" disable-output-escaping="yes" />
                                  <!--price-->
                                </strong>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="99%" height="100%" valign="top">
                    <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td>
                          <span class="text">
                            <div style="height:157px; overflow: hidden"><br />
 <!--                             <xsl:value-of select="Description" disable-output-escaping="yes" />
                              <br />
                              <xsl:value-of select="AdditionalDescription" disable-output-escaping="yes" />
                              <br /> -->
                            </div>
                          </span>
                        </td>
                      </tr>
                      <tr>
                        <td valign="middle" align="left" height="99%" style="padding: 5px 0px 0px 0px">
                          <!--buttons-->
                          
	<xsl:call-template name="ProductButtonLink">
		<xsl:with-param name="text">More</xsl:with-param>
		<xsl:with-param name="class">button</xsl:with-param>                   
    </xsl:call-template>
                          
<!--                          <input type="button" class="button" value="More" style="width:80px" onClick="document.location.href='/ProductInfo.aspx?id={@id}'"
															title="See more information" /> -->
															
															<xsl:choose>
                                          <xsl:when test="Quantity != 0">
                          <input type="button" class="button" value="Add To Cart" style="width:80px" onClick="document.location.href='/Cart.aspx?addProductId={@id}'" />
                                              </xsl:when>
                                          <xsl:otherwise>

                                            <span class="text">&#160;not in stock&#160;</span>

                                          </xsl:otherwise>
                                          </xsl:choose>
															

                          
                        </td>
                      </tr>
                    </table>
              <!-- /main info  -->
            </td>
          </tr>
        </table>
        <!-- /table -->
      </xsl:template>
      <!-- PageScroll -->
      
    </xsl:stylesheet>
Thread was being aborted.