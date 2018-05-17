<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">
<xsl:for-each select="//Row/Cell">
<xsl:if test="count(preceding-sibling::Cell) = 0">
<xsl:text>NEXT</xsl:text>
 </xsl:if>
<xsl:value-of select="concat(Data,';')"/>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>