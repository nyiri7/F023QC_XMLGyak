<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >

	<xsl:template match="/">
		<html>
			<body>

				<table style="border-collapse: collapse;">
					<tr style="background-color: greenyellow;">
						<th>ID</th>
						<th>Tipus</th>
						<th>Targy</th>
						<th>Idopont</th>
						<th>Helyszin</th>
						<th>Oktato</th>
						<th>Szak</th>
					</tr>
					
					<xsl:for-each select="F023QC_kurzusfelvetel/kurzusok/kurzus">
						<tr>

							<td><xsl:value-of select = "@id"/></td>
							<td><xsl:value-of select = "@jovahagyas"/></td>
							<td><xsl:value-of select = "kredit"/></td>

							<td><xsl:value-of select = "hely"/></td>
							<td><xsl:value-of select = "idopont"/></td>
                            <td><xsl:value-of select = "oktato"/></td>

							<td><xsl:value-of select = "oraado"/></td>


						</tr>
					</xsl:for-each>
					
				</table>
			</body>
		</html>
		
	</xsl:template>	
	<xsl:output method="html" version="5.0" encoding="utf-8" indent="yes"></xsl:output>

</xsl:stylesheet>