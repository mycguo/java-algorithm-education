<?xml version="1.0" encoding="UTF-8"?>
<!--
	Purpose:
					Transformer for MS Office Excel XML file.
	Usage:
					This must be part of transformer.xslt.
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">
	<xsl:strip-space elements="*"/>
	<xsl:variable name="allStyles" select="//ss:Styles/ss:Style"/>
	<xsl:variable name="boldStyles" select="//ss:Styles/ss:Style[child::*/@ss:Bold]/@ss:ID"/>
	<xsl:variable name="italicStyles" select="//ss:Styles/ss:Style[child::*/@ss:Italic]/@ss:ID"/>
	<xsl:variable name="centeredStyles" select="//ss:Styles/ss:Style[child::*/@ss:Horizontal[.='Center']]/@ss:ID"/>
	<!--
		ss:Workbook transformer.
	-->
	<xsl:template match="/ss:Workbook" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">
		<xsl:element name="fbf_workbook">
			<xsl:attribute name="xsi:noNamespaceSchemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">http://gnd.fai.fujitsu.com/2003/xsd/FBFWorkbook.xsd</xsl:attribute>
			<xsl:copy-of select="@*[name() != 'xsi:noNamespaceSchemaLocation']"/>
			<xsl:apply-templates mode="ss_Workbook"/>
		</xsl:element>
	</xsl:template>
	<!-- mel -->
	<xsl:template match="*" mode="ss_Workbook"/>
	<xsl:template match="o:CustomDocumentProperties" mode="ss_Workbook" xmlns:o="urn:schemas-microsoft-com:office:office">
		<xsl:element name="metadata">
			<xsl:apply-templates mode="ss_Workbook.metadata"/>
			<xsl:element name="properties">
				<xsl:apply-templates mode="ss_Workbook.metadata.properties"/>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="*" mode="ss_Workbook.metadata" priority="-1"/>
	<xsl:template match="*" mode="ss_Workbook.metadata.properties" xmlns:o="urn:schemas-microsoft-com:office:office">
		<xsl:element name="property">
			<xsl:attribute name="id"><xsl:value-of select="name()"/></xsl:attribute>
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="o:Destination" mode="ss_Workbook.metadata" xmlns:o="urn:schemas-microsoft-com:office:office">
		<!-- tokenize is not supported by xalan processor, this is used in siteconfig state and it is using xalan for now -->
		<xsl:element name="regions">
			<xsl:call-template name="transformer.util.tokenize">
				<xsl:with-param name="delimiter" select="','"/>
				<xsl:with-param name="elmName" select="'region'"/>
				<xsl:with-param name="value" select="text()"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template match="ss:Worksheet" mode="ss_Workbook">
		<xsl:if test="ss:Table">
			<xsl:element name="sheet">
				<xsl:variable name="id">
					<xsl:choose>
						<xsl:when test="@ss:Name">
							<xsl:value-of select="@ss:Name"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="position()"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
				<xsl:apply-templates mode="ss_Workbook"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match="ss:Table" mode="ss_Workbook">
		<xsl:variable name="rowIds" select="ss:Row[1]"/>
		<xsl:element name="table">
			<xsl:apply-templates select="ss:Row[ ss:Cell/ss:Data and position() != 1]" mode="ss_Workbook">
				<xsl:with-param name="rowId" select="$rowIds"/>
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>
	<xsl:template match="ss:Row" mode="ss_Workbook">
		<xsl:param name="rowId"/>
		<xsl:element name="row">
			<xsl:for-each select="ss:Cell">
				<xsl:variable name="cellPosition">
					<xsl:choose>
						<xsl:when test="@ss:Index">
							<xsl:value-of select="current()/@ss:Index"/>
						</xsl:when>
						<xsl:when test="preceding-sibling::ss:Cell[@ss:Index]">
							<xsl:variable name="cellsWithIndex" select="preceding-sibling::ss:Cell[@ss:Index]"/>
							<xsl:variable name="maxIndex" select="$cellsWithIndex[count($cellsWithIndex)]"/>
							<xsl:variable name="currentId" select="generate-id(.)"/>
							<xsl:value-of select="number($maxIndex/@ss:Index) + count($maxIndex/following-sibling::ss:Cell[generate-id() = $currentId or following-sibling::ss:Cell[generate-id() = $currentId]])"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="position()"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:element name="cell">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($rowId/ss:Cell[position() = $cellPosition])"/></xsl:attribute>
					<xsl:if test="@ss:MergeDown">
						<xsl:attribute name="rowspan"><xsl:value-of select="@ss:MergeDown+1"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="@ss:MergeAcross">
						<xsl:attribute name="colspan"><xsl:value-of select="@ss:MergeAcross+1"/></xsl:attribute>
					</xsl:if>
					<!-- alignments -->
					<xsl:variable name="myStyle" select="$allStyles[@ss:ID =current()/@ss:StyleID]"/>
					<xsl:if test="$myStyle/ss:Alignment/@ss:Horizontal">
						<xsl:attribute name="align"><xsl:value-of select="translate($myStyle/ss:Alignment/@ss:Horizontal,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="$myStyle/ss:Alignment/@ss:Vertical">
						<xsl:variable name="valign">
							<xsl:value-of select="translate($myStyle/ss:Alignment/@ss:Vertical,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
						</xsl:variable>
						<xsl:choose>
							<xsl:when test="$valign='center'">
								<xsl:attribute name="valign">middle</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="valign"><xsl:value-of select="$valign"/></xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<xsl:apply-templates select="ss:Data" mode="ss_Workbook">
						<xsl:with-param name="cellStyle" select="@ss:StyleID"/>
						<xsl:with-param name="isLink" select="@ss:HRef"/>
					</xsl:apply-templates>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template match="ss:Data[@ss:Type='DateTime']" mode="ss_Workbook">
		<xsl:param name="cellStyle"/>
		<xsl:param name="isLink"/>
		<!-- this variable realIsoDate will remove the .000 from the Microsoft Iso date -->
		<xsl:variable name="realIsoDate" select="substring-before(.,'.')"/>
		<xsl:variable name="adjustedIsoDate">
			<xsl:call-template name="fbf.utils.adjustDateForTimezone">
				<xsl:with-param name="timezone" select="substring($realIsoDate,20)"/>
				<xsl:with-param name="isoDate" select="substring($realIsoDate,1, 19)"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:attribute name="month_num" select="(number(substring($adjustedIsoDate,1,4)) *12) +(number(substring($adjustedIsoDate,6,2))-1)"/>
		<xsl:attribute name="number"><xsl:value-of select="translate($adjustedIsoDate, '-T:', '')"/></xsl:attribute>
		<xsl:attribute name="date_format"><xsl:choose><xsl:when test="$allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format = '[ENG]mmm\ dd' or $allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format = 'mmm\ dd'">short</xsl:when><xsl:when test="$allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format ='[ENG]mmmm\ dd' or $allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format ='mmmm\ dd'">long</xsl:when><xsl:when test="$allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format  ='[ENG]mmmm\ dd\,\ yyyy' or $allStyles[@ss:ID =$cellStyle]/ss:NumberFormat/@ss:Format  ='mmmm\ dd\,\ yyyy'">long-with-year</xsl:when><xsl:otherwise>default</xsl:otherwise></xsl:choose></xsl:attribute>
		<xsl:apply-templates select="." mode="ss_Workbook.styles">
			<xsl:with-param name="cellStyle" select="$cellStyle"/>
			<xsl:with-param name="isLink" select="$isLink"/>
			<xsl:with-param name="content" select="$adjustedIsoDate"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="ss:Data" mode="ss_Workbook">
		<xsl:param name="cellStyle"/>
		<xsl:param name="isLink"/>
		<xsl:apply-templates select="." mode="ss_Workbook.styles">
			<xsl:with-param name="cellStyle" select="$cellStyle"/>
			<xsl:with-param name="isLink" select="$isLink"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="ss:Data" mode="ss_Workbook.styles">
		<xsl:param name="cellStyle"/>
		<xsl:param name="isLink"/>
		<xsl:param name="content" select="text()"/>
		<xsl:choose>
			<xsl:when test="string($isLink)">
				<xsl:element name="link">
					<xsl:choose>
						<xsl:when test="contains($isLink,'asset://')">
							<xsl:attribute name="assetref"><xsl:value-of select="substring-after($isLink, 'asset://')"/></xsl:attribute>
						</xsl:when>
						<xsl:when test="contains($isLink, 'category://')">
							<xsl:attribute name="categoryref"><xsl:value-of select="substring-after($isLink, 'category://')"/></xsl:attribute>
						</xsl:when>
						<xsl:when test="contains($isLink, 'binary://')">
							<xsl:attribute name="binaryref"><xsl:value-of select="substring-after($isLink, 'binary://')"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="href"><xsl:value-of select="$isLink"/></xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:apply-templates select="." mode="ss_Workbook.styles.bold">
						<xsl:with-param name="cellStyle" select="$cellStyle"/>
						<xsl:with-param name="content" select="$content"/>
						<xsl:with-param name="isLink" select="$isLink"/>
					</xsl:apply-templates>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="ss_Workbook.styles.bold">
					<xsl:with-param name="cellStyle" select="$cellStyle"/>
					<xsl:with-param name="content" select="$content"/>
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="ss:Data" mode="ss_Workbook.styles.bold">
		<xsl:param name="cellStyle"/>
		<xsl:param name="content"/>
		<xsl:choose>
			<xsl:when test="string($cellStyle) = $boldStyles">
				<xsl:element name="strong">
					<xsl:apply-templates select="." mode="ss_Workbook.styles.italic">
						<xsl:with-param name="cellStyle" select="$cellStyle"/>
						<xsl:with-param name="content" select="$content"/>
					</xsl:apply-templates>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="ss_Workbook.styles.italic">
					<xsl:with-param name="cellStyle" select="$cellStyle"/>
					<xsl:with-param name="content" select="$content"/>
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="ss:Data" mode="ss_Workbook.styles.italic">
		<xsl:param name="cellStyle"/>
		<xsl:param name="content"/>
		<xsl:choose>
			<xsl:when test="string($cellStyle) = $italicStyles">
				<xsl:element name="em">
					<xsl:call-template name="ss_Workbook.findTextBreaks">
						<xsl:with-param name="text" select="$content"/>
					</xsl:call-template>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="ss_Workbook.findTextBreaks">
					<xsl:with-param name="text" select="$content"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="ss_Workbook.findTextBreaks">
		<xsl:param name="text"/>	
		<xsl:message>text [<xsl:copy-of select="$text"/>]</xsl:message>	
		<xsl:choose>
			<!-- to prvent this from happening <Data ss:Type="String">电源适配器<break/> (车用) </Data></Cell> -->
			<xsl:when test="contains(string-join($text,''), '&#10;')">
				<xsl:value-of select="substring-before(string-join($text,''), '&#10;')"/>
				<xsl:element name="break"/>
				<xsl:variable name="after" select="substring-after(string-join($text,''), '&#10;')"/>
				<xsl:call-template name="ss_Workbook.findTextBreaks">
					<xsl:with-param name="text" select="$after"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--
		Copied from fbf-utils.  Since this is the only template used from there, this decouples this file from fbf-utils.
		TODO: use java extension to do these types of things...
	-->
	<xsl:template name="fbf.utils.adjustDateForTimezone">
		<xsl:param name="timezone"/>
		<xsl:param name="isoDate"/>
		<xsl:choose>
			<xsl:when test="string($timezone)">
				<xsl:variable name="date" select="substring-before($isoDate,'T')"/>
				<xsl:variable name="isoTime" select="substring-after($isoDate,'T')"/>
				<xsl:variable name="tzMinutes">
					<xsl:choose>
						<!-- Xalan doesn't like "+XX" numbers.. number() returns NaN-->
						<xsl:when test="starts-with($timezone,'+')">
							<xsl:value-of select="number(substring($timezone,2,2)) * 60 + number(substring-after($timezone,':'))"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="number(substring-before($timezone,':')) * 60 - number(substring-after($timezone,':'))"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="isoMinutes" select="number(substring($isoTime,1,2)) * 60 + number(substring($isoTime,4,2))"/>
				<xsl:variable name="fixedMinutes" select="$isoMinutes - $tzMinutes"/>
				<xsl:variable name="absMinutes">
					<xsl:choose>
						<xsl:when test="$fixedMinutes &lt; 0">
							<xsl:value-of select="$fixedMinutes + 1440"/>
						</xsl:when>
						<xsl:when test="$fixedMinutes &gt;= 1440">
							<xsl:value-of select="$fixedMinutes mod 1440"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$fixedMinutes"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<!-- Date -->
				<xsl:choose>
					<xsl:when test="$fixedMinutes &lt; 0">
						<xsl:call-template name="fbf.utils.addDay">
							<xsl:with-param name="date" select="$date"/>
							<xsl:with-param name="add" select="-1"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$fixedMinutes &gt;= 1440">
						<xsl:call-template name="fbf.utils.addDay">
							<xsl:with-param name="date" select="$date"/>
							<xsl:with-param name="add" select="1"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$date"/>
					</xsl:otherwise>
				</xsl:choose>
				<!-- Time -->
				<xsl:value-of select="concat('T',format-number(floor($absMinutes div 60), '00'),':',format-number($absMinutes mod 60, '00'),':',substring(substring-after($isoDate,'T'),7,2))"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$isoDate"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="fbf.utils.addDay">
		<xsl:param name="date"/>
		<xsl:param name="add"/>
		<xsl:variable name="year" select="number(substring($date,1,4))"/>
		<xsl:variable name="maxdays">
			<xsl:choose>
				<xsl:when test="$year mod 4">
					<xsl:value-of select="'312831303130313130313031'"/>
				</xsl:when>
				<!-- leap year -->
				<xsl:otherwise>
					<xsl:value-of select="'312931303130313130313031'"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="month" select="number(substring($date,6,2))"/>
		<xsl:variable name="maxday" select="number(substring($maxdays,$month*2 -1,2))"/>
		<xsl:variable name="day" select="number(substring($date,9,2))"/>
		<xsl:choose>
			<xsl:when test="$day + $add = 0">
				<xsl:choose>
					<xsl:when test="$month = 1">
						<xsl:value-of select="concat($year -1,'-12-31')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat($year,'-',format-number($month - 1,'00'),'-',format-number(number(substring($maxdays,$month*2 -3,2)),'00'))"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$day + $add &gt; $maxday">
				<xsl:choose>
					<xsl:when test="$month = 12">
						<xsl:value-of select="concat($year+1,'-01-01')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat($year,'-',format-number($month + 1,'00'),'-01')"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat($year,'-',format-number($month,'00'),'-',format-number($day + $add,'00'))"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--
		Utility template
	-->
	<xsl:template name="transformer.util.tokenize">
		<xsl:param name="delimiter" select="' '"/>
		<xsl:param name="elmName"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($value, $delimiter)">
				<xsl:variable name="pre" select="substring-before($value, $delimiter)"/>
				<xsl:if test="normalize-space(string($pre))">
					<xsl:element name="{$elmName}">
						<xsl:value-of select="normalize-space(string($pre))"/>
					</xsl:element>
				</xsl:if>
				<xsl:call-template name="transformer.util.tokenize">
					<xsl:with-param name="delimiter" select="$delimiter"/>
					<xsl:with-param name="elmName" select="$elmName"/>
					<xsl:with-param name="value" select="substring-after($value, $delimiter)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="normalize-space(string($value))">
					<xsl:element name="{$elmName}">
						<xsl:value-of select="normalize-space(string($value))"/>
					</xsl:element>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
</xsl:stylesheet>
