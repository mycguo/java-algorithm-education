<?xml version="1.0" encoding="utf-8"?>
<!-- 
  This stylesheet defines try-block template.
  
  $Id: try-block.xslt 3648 2008-06-25 15:07:11Z vladimirn $
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:t="http://www.nesterovsky-bros.com/xslt/public/"
  xmlns:p="http://www.nesterovsky-bros.com/xslt/private/try-block"
  xmlns:functions="java:com.nesterovskyBros.saxon.Functions"
  exclude-result-prefixes="xs t p functions">

  <!--
    A try block.
    Executes xsl:apply-templates in mode "t:call" for $try elements.
    If error occurs then xsl:apply-templates is executed in s "t:call" 
    for $catch elements.
    
    The later is called with error info parameters:
      error as xs:QName;
      error-description as xs:string;
      error-object as item();
      error-location as (location as xs:string, context as item())*;
      
    Tunnel parameters may be used to pass data to $try and $catch handlers.
      
      $try - a try handler.
      $catch - a catch hadler.
      Returns an output produced either with $try or $catch handler.
  -->
  <xsl:template name="t:try-block">
    <xsl:param name="try" as="element()*"/>
    <xsl:param name="catch" as="element()*"/>

    <xsl:variable name="handler" as="element()">
      <p:try/>
    </xsl:variable>

    <xsl:apply-templates mode="t:call" select="$handler">
      <xsl:with-param name="try" select="$try"/>
      <xsl:with-param name="p:catch" tunnel="yes" select="$catch"/>
    </xsl:apply-templates>
  </xsl:template>

  <!--
    Formats error message.
      $error - an error name.
      $error-description - an error description.
      $error-location - an error location.
      Returns formatter message as sequence of strings.
  -->
  <xsl:function name="t:format-error-message" as="item()*">
    <xsl:param name="error" as="xs:QName"/>
    <xsl:param name="error-description" as="xs:string"/>
    <xsl:param name="error-location" as="item()*"/>

    <xsl:variable name="new-line" as="item()*">
      <xsl:text>
</xsl:text>
    </xsl:variable>

    <xsl:text>Error: </xsl:text>
    <xsl:sequence select="$error"/>
    <xsl:sequence select="$new-line"/>
    <xsl:text>Description:</xsl:text>
    <xsl:sequence select="$new-line"/>
    <xsl:sequence select="$error-description"/>
    <xsl:sequence select="$new-line"/>

    <xsl:if test="exists($error-location)">
      <xsl:sequence select="$new-line"/>
      <xsl:text>Location:</xsl:text>
      <xsl:sequence select="$new-line"/>

      <xsl:value-of separator="" select="
        for $i in (1 to count($error-location))[. mod 2 = 1],
          $message in $error-location[$i],    
          $item in $error-location[$i + 1]
        return
        (
          ($i + 1) idiv 2,
          '. ',
          $message,
          (
            if ($item instance of node()) then
              concat
              (
                '  context node:',
                $new-line,
                '    ',
                replace
                (
                  t:get-path($item), '\]/', concat(']', $new-line, '    /')
                ),
                $new-line
              )
            else if (string($item) != '') then
              concat
              (
                '  context item: ',
                $item,
                $new-line
              )
            else
              ()
          )
        )"/>

      <xsl:sequence select="$new-line"/>
    </xsl:if>
  </xsl:function>
  
  <!--
    Default error handler for the t:try-block template.
      $error - an error name.
      $error-description - an error description.
      $error-location - an error location.
  -->
  <xsl:template mode="t:call" match="t:error-handler">
    <xsl:param name="error" as="xs:QName"/>
    <xsl:param name="error-description" as="xs:string"/>
    <xsl:param name="error-location" as="item()*"/>

    <xsl:message>
      <xsl:sequence select="
        t:format-error-message($error, $error-description, $error-location)"/>
    </xsl:message>
  </xsl:template>

  <!-- 
    Builds friendly xpath to the element or attribute.
      $node - a node to build xpath for.
      Returns node's xpath.
  -->
  <xsl:function name="t:get-path" as="xs:string">
    <xsl:param name="node" as="node()"/>
    
    <xsl:value-of separator="" select="
      if ($node instance of document-node()) then
      (
        '/'
      )
      else
      (
        for $node in $node/ancestor-or-self::* return
        (
          if ($node instance of attribute()) then
          (
            '/@*[local-name() = ''',
            local-name($node),
            ''']'
          )
          else
          (        
            '/*[',
            count($node/preceding-sibling::*) + 1,
            '][local-name() = ''',
            local-name($node),
            ''']'
          ),
          (: Issue hint attributes. :)
          if (exists($node/@id)) then
          (
            '[@id = ''', 
            $node/@id, 
            ''']'
          )
          else
          (
          ),
          if (exists($node/@name)) then
          (
            '[@name = ''',
            $node/@name,
            ''']'
          )
          else if (exists($node/@type)) then
          (
            '[@type = ''', 
            $node/@type, 
            ''']'
          )
          else
          (
          )
        )
      )"/>
  </xsl:function>

  <!--
    An implementation of the template name="t:try-block".
    It's used to establish current mode, and to wrap $catch handler.

      $try - a try handler.
      Returns an output produced either with $try or $catch handler.
  -->
  <xsl:template mode="t:call" match="p:try">
    <xsl:param name="try" as="element()*"/>

    <xsl:variable name="handler" as="element()">
      <p:catch/>
    </xsl:variable>

    <xsl:sequence select="functions:try-block($try, $handler)"/>
  </xsl:template>

  <!--
    A wrapper for a $catch hander of the template name="t:try-block".
      $p:catch - a catch hadler wrapper.
      Returns an output produced with $catch handler.
  -->
  <xsl:template mode="t:call" match="p:catch">
    <xsl:param name="p:catch" tunnel="yes" as="element()*"/>

    <xsl:variable name="error-info" as="item()+"
      select="functions:current-error-info()"/>
    <xsl:variable name="error-location" as="item()*"
      select="functions:current-error-location()"/>
    <xsl:variable name="error" as="xs:QName"
      select="$error-info[1]"/>
    <xsl:variable name="error-description"  as="xs:string"
      select="$error-info[2]"/>
    <xsl:variable name="error-object" as="item()"
      select="$error-info[3]"/>

    <xsl:apply-templates mode="t:call" select="$p:catch">
      <xsl:with-param name="error" select="$error"/>
      <xsl:with-param name="error-description" select="$error-description"/>
      <xsl:with-param name="error-object" select="$error-object"/>
      <xsl:with-param name="error-location" select="$error-location"/>
    </xsl:apply-templates>
  </xsl:template>

</xsl:stylesheet>