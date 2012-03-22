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
				<link rel="stylesheet" type="text/css" href="http://hosting.kyozou.com/esource/css/stylesheet.css"/>
				<link rel="stylesheet" type="text/css" href="http://hosting.kyozou.com/esource/css/slideshow.css"/>
				
				<!--  
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/css.css"/>
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/checkout.css"/>
				<link rel="stylesheet" type="text/css" href="/Design/Templates/{/OnlineStore/@designDirectory}/css/error.css"/>
				-->
				<meta name='google-site-verification' content='Cfd3vuJoZn9HBXrxmfe6S6KErljI3WCEur-8lkokToo'/>
				<xsl:value-of select="/OnlineStore/Header/HeaderCodeElement" disable-output-escaping="yes"/>
			</head>
			
			<body class="common-home">

    			<div class="content-padd">

    				<div class="content-bg">

						<p id="back-top"> <a href="#top"><span></span></a> </p>
			
						<div class="row-1">
		
							<div id="header">
		
								<div id="logo">
									<a
										href="http://www.esourceparts.ca/">
										<img
											src="http://www.esourceparts.ca/pictures/_11/10107/10106246.jpg"
											title="Electronics Store" alt="Electronics Store" />
									</a>
								</div>

		                        <!--  
								<form
									action="http://livedemo00.template-help.com/opencart_38241/index.php?route=common/home"
									method="post" enctype="multipart/form-data">
		
									<div id="currency">
		
										<a title="Euro"
											onclick="$('input[name=\'currency_code\']').attr('value', 'EUR').submit(); $(this).parent().parent().submit();">
											<span>€</span>
										</a>
		
										<a title="Pound Sterling"
											onclick="$('input[name=\'currency_code\']').attr('value', 'GBP').submit(); $(this).parent().parent().submit();">
											<span>£</span>
										</a>
		
										<a title="US Dollar">
											<span class="act">$</span>
										</a>
		
										<input type="hidden" name="currency_code" value="" />
		
										<input type="hidden" name="redirect"
											value="http://livedemo00.template-help.com/opencart_38241/index.php?route=common/home" />
		
									</div>
		
								</form>
		                         -->
								<div id="search">
		
									<div class="button-search"></div>
		
									<span class="search-bg">
											<!-- search form -->
											<form action="/Catalog.aspx" method="get">
												<div class="special_text pad3">&#160;search:&#160;
		                                      		<input type="Text" name="srchSearchCriteria" class="inp"/>&#160;
		                                      		<input type="submit" value="Enter search keywords here" onclick="this.value = '';" onkeydown="this.style.color = '#b3b3b3';" title="Search" class="button_go"/></div>
											</form>
											<!--/search form -->
									</span>
		
									<!--  
									<a class="adv-search" href="index.php?route=product/search">Advanced
										Search</a>
									-->
		
								</div>
		
								<div class="right-header">
		
									<div class="wrapper">
		
										<ul class="links">
		
											<li class="m1">
												<a href="/catalog.aspx">Catalog<strong></strong></a>
											</li>
		
											<li class="m2">
												<a href="/auctions.aspx">Auctions<strong></strong></a>
											</li>
		
											<li class="m3">
												<a href="/contacts.aspx">Contact Us<strong></strong></a>
											</li>
		
											<li class="m4">
												<a href="/aboutus.aspx">Shipping &amp; Policies<strong></strong></a>
											</li>
		
											<li class="m5">
												<a href="/cart.aspx">Cart<strong></strong></a>
											</li>

											<li class="m5">
												<a ref="/checkout.aspx">Checkout<strong></strong></a>
											</li>											
		
										</ul>
		
										<div id="cart">
		
											<div class="cart_inner">
		
												<div class="heading">
		
													<h4>Shopping Cart:</h4>
													<!-- korzina -->
													<xsl:call-template name="Header_Footer_Cart">
															<xsl:with-param name="Cart" select="/OnlineStore/Cart"/>
													</xsl:call-template>
													<!--/korzina -->													
													
													<!--  
													<a
														href="http://livedemo00.template-help.com/opencart_38241/index.php?route=checkout/cart"
														id="cart_total">
														<strong>0 item(s)</strong>
														- $0.00
													</a>
		
													<span class="sc-button"></span>
													-->
		
												</div>
		
											</div>
		
											<div class="content"></div>
		
										</div>
		
									</div>
		
		
		
								</div>
		
								<div id="welcome">
		
									Welcome visitor you can
									<a
										href="https://www.esourceparts.ca/Login.aspx">login</a>
									or
									<a
										href="https://www.esourceparts.ca/ShipmentInfo.aspx?quick=true">create an account</a>
									.
								</div>
		
		
		
							</div>
		
						</div>
						
						<div id="container">

 							<div id="notification"> </div>

							<div class="wrap-content">
								<div id="column-left">

    <script type="text/javascript">

	$(document).ready(function(){

	   $(".act-cat").toggle( 

				function() { 

					 $(this).next('.level-2-cat').slideToggle("slow"),{

						duration: 'slow',

						easing: 'easeOutBounce'

					};

				}, 

				function() { 

					 $(this).next('.level-2-cat').slideToggle("slow"),{

						duration: 'slow',

						easing: 'easeOutBounce'

					};

				} 

			);

		$('.box-category > ul > li').last().addClass('last');

		$('.level-2-cat > li').last().addClass('last');

		

	});

	

</script>



								<div class="box category">

  									<div class="box-content">

    									<div class="box-category">

      										<div class="box-category">
												<!--left navigation-->
												<xsl:apply-templates select="/OnlineStore/CategoryList"/>
												<!--/left navigation-->
												</div>
											</div>
										</div>
									</div> 

									
								</div><!-- left column -->
								
																	
								<div id="content"> 
									<div class="slideshow">
										 <div id="slideshow0" class="nivoSlider" style="width: 630px; height: 380px;">
																<!--Featured Product-->
																<xsl:call-template name="MainPromoProduct">
																	<xsl:with-param name="productGroup" select="/OnlineStore/PromoProductGroup"/>
																</xsl:call-template>
																<!--/Featured Product-->								
										</div>		
									</div>
									

									<div id="banner0" class="banner">
									
									      <div><a href="index.php?route=product/product&amp;path=69&amp;product_id=43"><img src="http://livedemo00.template-help.com/opencart_38241/image/cache/data/baner-column-150x266.jpg" alt="baner" title="baner" /></a></div>
									
									</div>
									
									<script type="text/javascript"><!--
									
									var banner = function() {
									
										$('#banner0').cycle({
									
											before: function(current, next) {
									
												$(next).parent().height($(next).outerHeight());
									
											}
									
										});
									
									}
									
									
									
									setTimeout(banner, 2000);
									
									//--></script>
									<script type="text/javascript">
									
										$(function(){
									
											$('.info-list li').last().addClass('last');
									
										});
									
									</script>

									
									<div class="info-box">
									
									  <div class="info-box-title">Follow <strong>us:</strong></div>
									
									  <div class="info-box-content">
									
									  <ul class="list-services">
									
									        <li class="second"><a class="tooltips" title="facebook" href="facebook.com"></a></li>
									
											<li class="first"><a class="tooltips" title="twitter" href="twitter.com"></a></li>
									
									        <li class="five"><a class="tooltips" title="youtube" href="youtube.com"></a></li>
									
									  </ul>
									
									  </div>
									
									</div> <!-- info-box -->
									<!-- 
									<script type="text/javascript">
									
											(function($){$.fn.equalHeights=function(minHeight,maxHeight){tallest=(minHeight)?minHeight:0;this.each(function(){if($(this).height()>tallest){tallest=$(this).height()}});if((maxHeight)&&tallest>maxHeight)tallest=maxHeight;return this.each(function(){$(this).height(tallest)})}})(jQuery)
									
										$(window).load(function(){
									
											if($(".maxheight").length){
									
											$(".maxheight").equalHeights()}
									
										})
									
									</script>  -->
									
									<div class="box new-products">
									
									  <div class="box-heading">Featured products</div>
									
									  <div class="box-content">
									
									    <div class="box-product">

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
																	<!--Page Name-->
																	<span class="window_header">
																			<xsl:value-of select="@pageName"/>
																	</span>
																	<!--/Page Name-->
				
																	<!-- /header -->
																	<!-- call body -->
																	<xsl:call-template name="Body"/>
																	<!-- /call body -->

																</xsl:otherwise>
															</xsl:choose>
															<!-- /main -->
															<!--PromoProductGroup-->
															<xsl:apply-templates select="/OnlineStore/PromoProductGroup"/>
															<!--/PromoProductGroup-->



															<div class="special_text" align="center">&#169; Kyozou.com
																	<xsl:value-of select="substring(/OnlineStore/@currentDate, 0, 5)" disable-output-escaping="yes"/>
																	. All Rights Reserved.
															</div>
																			<!--  
																			<div align="center">
																				<span id="siteseal">
																					<script type="text/javascript" src="https://seal.godaddy.com/getSeal?sealID=VdjeMcXs2gfVvqJGbMYi63vk6BwU0acLEuSUECLYBJJSFGZAOOt13Is"/>
																					<br/>
																					<a href='https://www.godaddy.com/gdshop/ssl/ssl.asp'>SSL Certificates</a>
																				</span>
																			</div>
																			-->
															<xsl:if test="not(contains(/OnlineStore/@currentUrl, 'https://'))">
																<xsl:value-of select="/OnlineStore/Header/AnalyticsCodeElement" disable-output-escaping="yes"/>
															</xsl:if>


									    
									    </div>
									   </div>
									 </div>  <!-- box new-products -->
									
									
								</div> <!-- content -->
							</div> <!-- wrap-content -->
						</div> <!-- container -->
					</div> <!-- content-bg -->
				</div> <!-- content-padd -->
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
			<div class="box-heading">In The Spotlight</div>

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
			<div class="box-heading">								
				<xsl:call-template name="ProductTextLinkByNode">  <!-- Feature Products -->
							<xsl:with-param name="node" select="$productGroup/Product[1]"/>
							<xsl:with-param name="text">More info</xsl:with-param>
							<xsl:with-param name="class">style3</xsl:with-param>
				</xsl:call-template>
			</div>

			<a href="/Cart.aspx?addProductId={$productGroup/Product[1]/@id}" class="style3">								<!-- sm photo -->
				<xsl:call-template name="ProductImageLinkByNode">
					<xsl:with-param name="node" select="$productGroup/Product[1]"/>
					<xsl:with-param name="alt" select="$productGroup/Product[1]/Title"/>
					<xsl:with-param name="class">image</xsl:with-param>
					<xsl:with-param name="w">630</xsl:with-param>
					<xsl:with-param name="h">380</xsl:with-param>
					<xsl:with-param name="image" select="concat('/Picture.aspx?width=80&amp;height=80&amp;id=', $productGroup/Product[1]/ProductPictureID)"/>
				</xsl:call-template>
			</a>

		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
