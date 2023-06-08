<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"    
    xmlns:date="http://exslt.org/dates-and-times" 
	>
	<xsl:template match="/">
	<!-- a doctype is not allowed in content <!DOCTYPE html> -->
	<!-- Merke: Wir machen hier nix mit PDF, also raus
	     xmlns:fo="http://www.w3.org/1999/XSL/Format" -->
	<html>
	<head>
	    <!-- ./ ist das gleiche Verzeichnis. Aber / ist das Root Verzeichnis des Servers -->
		<link rel="stylesheet" type="text/css" href="./styles.css" />
	</head>
	<body>
	
		<h1 style="font-size:20pt;color:#FF0000">IP-Adressen der Server</h1>
		
		<!-- das klappt einfach mit der Namespace angabe von exslt, ohne etwas zu importieren -->
		<xsl:variable name="now" select="date:date-time()" />
		<xsl:variable name="timeNow">
			<xsl:value-of select="date:format-date($now,'HH:mm:ss')" />
		</xsl:variable>
		<h2 style="font-size:20pt;color:#FF0000">Erstellt am: <xsl:value-of select="date:format-date($now,'EEEE, dd. MMMMMMMMMMM yyyy HH:mm:ss')" /></h2>
		<table id="tableWithIpNr">
			<!-- Header, Merke: Das label-xml-tag ist über alle Reihen gleich, darum das 1. nehmen -->			
			<xsl:for-each select="/tabledata/row">			
			<xsl:if test="position()=1"><!-- Das soll nur 1x laufen fuer die 1. Zeile -->
			<tr>
				<xsl:for-each select="column">
					<xsl:variable name="headId">
						<xsl:value-of select="headId"/>
					</xsl:variable>
				
					<!-- Hole ueber das Template fuer diese Spalte die Tabellenueberschrift -->
					<xsl:variable name="current_label">
        				<xsl:call-template name="mapHeaderIdToLabel">
            				<xsl:with-param name="current_headerId" select="$headId"/>
        				</xsl:call-template>
    				</xsl:variable>
					<xsl:message><xsl:value-of select="concat(string('theHeaderLabel='),string($current_label))"/></xsl:message>
					<th>					
						<xsl:value-of select="$current_label"/>
					</th>
				</xsl:for-each>
			</tr>	
			</xsl:if>			
			</xsl:for-each>				
			
			<!-- Daten -->
	        <xsl:for-each select="tabledata/row">
	            <tr>
	            	<xsl:for-each select="column">	
	            		
	            		<td> 
	            		<xsl:choose>							 								
								<xsl:when test="@name='ipnr'">	<!-- DIESER ALIASWERT IST NOCH NICHT IM XML VORHANDEN -->
									<!-- Die normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
									<xsl:attribute name = "headers">
									    <xsl:value-of select="headId"/>
									</xsl:attribute>
															
									<!-- Mache einen Link -->
				            		<a>             
				            			<xsl:attribute name = "href"><!-- create href attribute -->	            			
				                    		<xsl:value-of select="value"/>
				                    	</xsl:attribute>
				                    	<xsl:value-of select="value"/>
				                    </a>								
								</xsl:when>	
								<xsl:when test="headId='IPNr'"> <!-- hier müsste eigentlich auf einen unique Aliasname sein und nicht ein flexibles Label -->
									<!-- Die normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
									<xsl:attribute name = "headers">
									    <xsl:value-of select="headId"/>
									</xsl:attribute>
																	
									<!-- Mache einen Link für die IP-Adresse -->
				            		<a>             
				            			<xsl:attribute name = "href"><!-- create href attribute -->	            			
				                    		<xsl:value-of select="value"/>
				                    	</xsl:attribute>
				                    	<xsl:value-of select="value"/>
				                    </a>								
								</xsl:when>
													
								<xsl:otherwise>
									<!-- Nur normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
									<xsl:attribute name = "headers">
									    <xsl:value-of select="headId"/>
									</xsl:attribute>
									<xsl:value-of select="value"/>								
								</xsl:otherwise>-->												
						</xsl:choose>  	            	
	                    </td>	                    
	                </xsl:for-each>
	            </tr>
	        </xsl:for-each>
		</table>
	
<!-- 		<h1 style="font-size:20pt;color:#FF0000">Hello World DOM + XML + XSLT</h1> -->
<!--         <xsl:for-each select="company/staff"> -->
<!--             <ul> -->
<!--                 <li> -->
<!--                     <xsl:value-of select="@id"/> - -->
<!--                     <xsl:value-of select="name"/> - -->
<!--                     <xsl:value-of select="role"/> -->
<!--                 </li> -->
<!--             </ul> -->
<!--         </xsl:for-each> -->
	</body>
	</html>
	</xsl:template>
	
	<!-- Template: Hole anhand der HeaderId (Kommt aus dem XML) die passende Tabellenueberschrift. Hier ist also die Stelle an der die "Labels" definiert werden -->
	<!-- TODOGOON: Besser waere es noch man wuerde oben in der XSLT-Seite eine Map definieren und hier nur auf den Map-Inhalt abpruefen!!! -->
	<xsl:template name="mapHeaderIdToLabel">
    <xsl:param name="current_headerId"/>

	<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - Input current_headerId='),string($current_headerId))"/></xsl:message>    
	<xsl:choose>
	<xsl:when test="$current_headerId='IPNr'">
		<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - match for: '),string($current_headerId))"/></xsl:message>
		<xsl:value-of select="'IP Adresse'" />
	</xsl:when>
	<xsl:otherwise>
		<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - no match for: '),string($current_headerId))"/></xsl:message>
		<!-- falls nicht gemappt, gib die eingegebene Id zurueck -->
		<xsl:value-of select="$current_headerId" />
	</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>
</xsl:stylesheet>