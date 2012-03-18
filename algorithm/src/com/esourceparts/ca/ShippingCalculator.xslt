<?xml version="1.0" encoding="UTF-8" ?>
    <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
      <xsl:output method="html" version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="yes" />
      <xsl:template match="/OnlineStore">
        <html>
          <head>
            <title>
              <xsl:value-of select="@pageName" />
            </title>
            <link rel="stylesheet" type="text/css" href="/Design/Templates/18/css/error.css" />
            <link rel="stylesheet" type="text/css" href="/Design/Templates/18/css/css.css" />
            <link rel="stylesheet" type="text/css" href="/Design/Templates/18/css/checkout.css" />
          </head>
          <body  leftmargin="15" topmargin="15" marginwidth="15" marginheight="15"  bgcolor="#ECEAE1" onload="window.focus()">
            <xsl:apply-templates select="ShippingLocation" />
            <xsl:apply-templates select="ShipmentProviderList" />
            <xsl:apply-templates select="FixCost" />
          </body>
        </html>
      </xsl:template>
      <xsl:template match="ShippingLocation">
        <table width="100%"  border="0" cellspacing="1" cellpadding="5" bgcolor="#A9A090">
          <tr>
            <td bgcolor="D4CFBB">
              <!-- Choose location -->
              <span class="window_header">
                Choose location
              </span>
              <!-- /Choose location-->
            </td>
          </tr>
          <tr>
            <td bgcolor="#ECEAE1" valign="top"  align="center" style="padding: 12px 20px 20px 20px">
              <!--inside-->
              <table>
                <form method="post" action="ShippingCalculator.aspx">
                  <input type="hidden" name="id" value="{/OnlineStore/ProductDetailsId}"/>
                  <tr>
                    <td>
                      <span class="checkout_item">
                        Country :
                      </span>
                    </td>
                    <td></td>
                    <td>
                      <select name="countryId" class="select" style="width:200px">
                        <option value="">
                          Select Country
                        </option>
                        <xsl:apply-templates select="CountryList" />
                      </select>
                    </td>
                  </tr>
                  <xsl:if test="@type = 'calculator'">
                    <tr>
                      <td>
                        <span class="checkout_item">
                          Zip/Postal Code :
                        </span>
                      </td>
                      <td></td>
                      <td>
                        <input type="text" maxlength="10" onkeyup="this.value=this.value.toUpperCase();" name="zipCode"
											value="" size="15" class="checkout_input" />
                        <span class="checkout_text">
                          (##### for US, A#A#A# for Canada)
                        </span>
                      </td>
                    </tr>
                  </xsl:if>
                  <tr>
                    <td colspan="3" align="center">
                      <!--<input type="submit" value="" title="View Shipping Costs" class="view_costs"   />-->
                      <input type="submit" class="button" value="View Shipping Costs" />
                    </td>
                  </tr>
                </form>
              </table>
              <!--inside-->
            </td>
          </tr>
        </table>
      </xsl:template>
      <xsl:template match="CountryList">
        <xsl:for-each select="Country">
          <option value="{@id}">
            <xsl:value-of select="current()" />
          </option>
        </xsl:for-each>
      </xsl:template>
      <!-- ShipmentProviderList   -->
      <xsl:template match="ShipmentProviderList">
        <!--1-->
       <table width="100%"  border="0" cellspacing="1" cellpadding="5" bgcolor="#A9A090">
          <tr>
            <td bgcolor="D4CFBB">
              <!-- Choose location -->
              <span class="window_header">
                Calculated Shipping Costs
              </span>
              <!-- /Choose location-->
            </td>
          </tr>
          <tr>
            <td bgcolor="#ECEAE1" valign="top"  align="center" style="padding: 12px 20px 20px 20px">
              <!--/1-->
              <!-- for-each -->
              <table cellpadding="3" cellspacing="0" border="0">
                <xsl:for-each select="ShipmentProvider">
                  <tr>
                    <td valign="top" class="checkout_price">
                      <span class="checkout_price">
                        <nobr>
                          $
                          <xsl:value-of select="@finalRate" />
                        </nobr>
                      </span>
                    </td>
                    <td valign="top">
                      <span class="checkout_item">
                        <nobr>
                          <xsl:value-of select="@CarrierName" />
                        </nobr>
                      </span>
                    </td>
                    <td width="99%">
                      <span class="checkout_item">
                        <xsl:value-of select="@name" />
                      </span>
                      <br />
                      <span class="checkout_information" style="font-size: 9px">
                        Time in transit:
                        <xsl:value-of select="@timeInTransit" />
                      </span>
                    </td>
                  </tr>
                </xsl:for-each>
              </table>
              <!--/for-each  -->
			  <!--2-->
            </td>
          </tr>
        </table>
        <!--/2-->
      </xsl:template>
      <!-- FixCost -->
      <xsl:template match="FixCost">
        <table width="100%"  border="0" cellspacing="1" cellpadding="5" bgcolor="#A9A090">
          <tr>
            <td bgcolor="D4CFBB">
              <!-- window_header -->
              <span class="window_header">
                Fix Shipping Cost
              </span>
              <!-- /window_header-->
            </td>
          </tr>
          <tr>
            <td bgcolor="#ECEAE1" valign="top"  align="center" style="padding: 12px 20px 20px 20px">
              <!--/1-->
              <!--  -->
              <table>
                <tr>
                  <td>
                    <span class="checkout_item">
                      Shipping Cost:
                    </span>
                    <span class="checkout_form_price">
                      <xsl:value-of select="Cost/Money/Formated" disable-output-escaping="yes"/>
                    </span>
                  </td>
                </tr>
                <tr>
                  <td valign="bottom" align="center">
                    <span class="checkout_item">
                      Shipping Insurance :
                    </span>
                    <span class="checkout_form_price">
                      <xsl:value-of select="InshuranceCost/Money/Formated" disable-output-escaping="yes"/>
                    </span>
                  </td>
                </tr>
              </table>
              <!--2-->
            </td>
          </tr>
        </table>
        <!--/2-->
      </xsl:template>
    </xsl:stylesheet>
Thread was being aborted.