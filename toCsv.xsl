<?xml version="1.0"?>


<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output omit-xml-declaration="yes" method="text"/>
<xsl:strip-space elements="*" />

<xsl:template match="//tr">
  <xsl:for-each select="td">
  <xsl:value-of select="concat(normalize-space(translate(., ',', '')),',')"/>
  </xsl:for-each>
  <xsl:value-of select="concat('&#xA;','')"/>
</xsl:template>

</xsl:stylesheet>