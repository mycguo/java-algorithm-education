<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tour="http://wrox.com/5067/tour" 
exclude-result-prefixes="xs tour"
version="2.0">

<xsl:output method="html" indent="yes"/>
<xsl:param name="start" select="'a1'" as="xs:string"/>

<xsl:variable name="start-column" as="xs:integer" select="xs:integer(number(translate(substring($start,1,1),'abcdefgh','01234567')))"/>
<xsl:variable name="start-row" as="xs:integer" select="xs:integer(8-number(substring($start,2,1)))"/>

	<xsl:template name="main" match="/">
		<xsl:if test="not(matches($start,'^[a-h][0-7]$'))">
			<xsl:message terminate="yes">wrong input</xsl:message>
		</xsl:if>
		
		<!-- setup the empty board -->
		<xsl:variable name="empty-board" as="xs:integer*" select="for $var in (1 to 64) return 0"/>
		
		<!-- place knight in the starting position -->
		<xsl:variable name="initial-board" as="xs:integer*" select="tour:place-knight(1,$empty-board,
					$start-row * 8 + $start-column)"/>
					
		<!-- evaluate the tour -->
		<xsl:variable name="final-board" as="xs:integer*" select="tour:make-moves(2,$initial-board,
					$start-row * 8 + $start-column)"/>
					
		<xsl:call-template name="print-board">
			<xsl:with-param name="board" select="$final-board"/>
		</xsl:call-template>
	</xsl:template>
						
				
	<xsl:function name="tour:make-moves" as="xs:integer*">
		<xsl:param name="move" as="xs:integer"/>
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="square" as="xs:integer"/>
		<xsl:variable name="possible-move-list" as="xs:integer*"
			select="tour:list-possible-moves($board,$square)"/>
		<xsl:message>move is <xsl:value-of select="$move"/> at square <xsl:value-of select="$square"/> possible move: <xsl:copy-of select="$possible-move-list"/></xsl:message>
		<xsl:sequence select="tour:try-possible-moves($move,$board,$square,$possible-move-list)"/>
	</xsl:function>
	
	<xsl:function name="tour:list-possible-moves" as="xs:integer*">
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="square" as="xs:integer"/>
		<xsl:variable name="row" as="xs:integer" select="$square idiv 8"/>
		<xsl:variable name="column" as="xs:integer" select="$square mod 8"/>
		
		<xsl:sequence select=
		" for $r in (-2, -1,+1,+2),$c in (-(3-abs($r)), +(3-abs($r))) return
			(($row+$r)*8 + ($column+$c) )
			[($row+$r) = 0 to 7 and ($column+$c) = 0 to 7]
			[$board[($row+$r)*8 + ($column+$c) + 1] eq 0]
		"/>
					
	</xsl:function>
				
	<xsl:function name="tour:try-possible-moves" as="xs:integer*">
		<xsl:param name="move" as="xs:integer"/>
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="square" as="xs:integer"/>
		<xsl:param name="possible-moves" as="xs:integer*"/>
		<xsl:sequence select="if (exists($possible-moves)) then 
					tour:make-best-move($move,$board,$square,$possible-moves) 
					else ()"/>		
	</xsl:function>
	
	<xsl:function name="tour:make-best-move" as="xs:integer*">
		<xsl:param name="move" as="xs:integer"/>
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="square" as="xs:integer"/>
		<xsl:param name="possible-moves" as="xs:integer*"/>
		<xsl:variable name="best-move" select="tour:find-best-move($board,$possible-moves,9,999)"></xsl:variable>
		<xsl:variable name="other-possible-moves" as="xs:integer*" select="$possible-moves[. != $best-move]"/>
		<xsl:variable name="next-board" as="xs:integer*" select="tour:place-knight($move,$board,$best-move)"/>
		<xsl:variable name="final-board" as="xs:integer*" 
			select="if (exists($next-board[. =0])) then tour:make-moves($move+1,$next-board,$best-move)
			else $next-board"/>
		<xsl:sequence select="if (empty($final-board)) then tour:try-possible-moves($move,$board,$square,$other-possible-moves) else $final-board"/>	
	</xsl:function>
	
	<xsl:function name="tour:find-best-move" as="xs:integer*">
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="possible-moves" as="xs:integer*"/>
		<xsl:param name="fewest-exits" as="xs:integer"/>
		<xsl:param name="best-so-far" as="xs:integer"/>
		<xsl:variable name="trial-move" as="xs:integer" select="$possible-moves[1]"/>
		<xsl:variable name="other-possible-moves" as="xs:integer*" select="$possible-moves[position() gt 1]"/>
		<xsl:variable name="trial-board" as="xs:integer*" select="tour:place-knight(99,$board,$trial-move)"/>
		<xsl:variable name="trial-move-exit-list" as="xs:integer*" select="tour:list-possible-moves($trial-board,$trial-move)"/>
		<xsl:variable name="number-of-exits" as="xs:integer" select="count($trial-move-exit-list)"/>
		<xsl:variable name="mininum-exits" as="xs:integer" select="min(($number-of-exits,$fewest-exits))"/>
		<xsl:variable name="new-best-so-far" as="xs:integer" select="if ($number-of-exits lt $fewest-exits) then $trial-move else $best-so-far"/>
		<xsl:sequence select="if (exists($other-possible-moves)) then tour:find-best-move($board,$other-possible-moves,$mininum-exits,$new-best-so-far) else $new-best-so-far"/>
	</xsl:function>
	<!-- place the knight -->
	<xsl:function name="tour:place-knight" as="xs:integer*">
		<xsl:param name="move" as="xs:integer"/>
		<xsl:param name="board" as="xs:integer*"/>
		<xsl:param name="square" as="xs:integer"/>
		<xsl:sequence select="for $i in (1 to 64) return if ($i = $square + 1) then $move else $board[$i]"/>
	</xsl:function>
				

	
	<xsl:template name="print-board">
		<xsl:param name="board" as="xs:integer*"/>
		<html>
			<head>
				<title>Knight's Tour</title>
			</head>
			<body>
				<div align="center">
					<h1>Knight's Tour starting at <xsl:value-of select="$start"/></h1>
					<table border="1" cellpadding="4" size="{count($board)}">
						<xsl:for-each select="0 to 7">
							<xsl:variable name="row" select="."/>
							<tr>
								<xsl:for-each select="0 to 7">
									<xsl:variable name="column" select="."/>
									<xsl:variable name="color" select="if (($row+$column) mod 2 =1) then 'xffff44' else 'white'"/>
									<td align="center" bgcolor="{$color}">
										<xsl:value-of select="$board[$row * 8 + $column + 1]"/>
									</td>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</table>
				</div>
			</body>
		</html>
	</xsl:template>
	



</xsl:transform>