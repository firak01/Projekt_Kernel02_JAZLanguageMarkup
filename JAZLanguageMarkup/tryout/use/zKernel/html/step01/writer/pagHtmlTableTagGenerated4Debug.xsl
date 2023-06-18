<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"    
    xmlns:date="http://exslt.org/dates-and-times" 
    xmlns:xalan="http://xml.apache.org/xalan"
    xmlns:java="http://xml.apache.org/xalan/java"
    xmlns:map="xalan://java.util.Map"   
    xmlns:mapindexed="xalan://basic.zBasic.util.abstractList.HashMapIndexedZZZ"
    xmlns:head="xalan://use.zKernel.html.step01.writer.TableHeadZZZ" 
    xmlns:counter="use.zKernel.html.step01.writer.MyCounter"
    extension-element-prefixes="map mapindexed head counter">
   
    <!-- a doctype is not allowed in content <!DOCTYPE html> -->
	<!-- Merke: Wir machen hier nix mit PDF, also raus... xmlns:fo="http://www.w3.org/1999/XSL/Format" -->
	<!-- das braucht man... xmlns:xalan="http://xml.apache.org/xalan" -->
	<!-- darunter dann etwas in der Form... 	xmlns:prefix=URI -->
	<!-- In a literal result element or extension element include the xsl prefix:
			xsl:extension-element-prefixes="prefix1 prefix2 ..." -->
    <!-- Was das soll weiss ich nicht...  exclude-result-prefixes="java"> -->

	<xsl:output method="html" />
	
	<!-- Diese Parameter werden im Java Code an das Transformer - Objekt uebergeben -->
	<!-- <xsl:param name="mapTableHeadLabel" select="java:java.util.HashMap.new" /> -->
	<xsl:param name="mapTableHeadLabelIndexed" select="java:jbasic.zBasic.util.abstractList.HashMapIndexedZZZ.new" />

	<!-- Merke: Objekte koennen auch uebergeben werden. D.h. hier angekommen, haben im Java gesetzten Werte
	            hier: mapTableHeadLabelIndexe -->
	<!--        Daher funktioniert es die HashMapIndexed schon vorher zu erstellen, mit:
			<xsl:stylesheet ...
	            xmlns:mapindexed="xalan://basic.zBasic.util.abstractList.HashMapIndexedZZZ"
	            ...
    		<xsl:param name="mapTableHeadLabelIndexed" select="java:jbasic.zBasic.util.abstractList.HashMapIndexedZZZ.new" />
  	  -->
  
  	<!-- Merke: Damit auch auf andere Objekte, die in den uebergebenen Objekten enthalten sind zugegriffen werden kann,
  	            mussen diese Klassen auch definiert worden sein -->
  	<!--        Daher ist notwendig:
  	 			<xsl:stylesheet ...
  	 			   xmlns:head="xalan://basic.zKernel.html.writer.TableHeadZZZ"-->
  
 	<!-- Davon zu unterscheiden sind Komponenten, die als Xalan-Objekte hier vorort erstellt werden -->
  	<!-- Merke: Damit auch diese bekannt sind, muss defiert werden:
  				<xsl:stylesheet ...
  				   xmlns:counter="basic.zKernel.html.writer.MyCounter"
   -->
  <xalan:component prefix="counter"
                   elements="init incr" functions="read">
    <xalan:script lang="javaclass" src="xalan://use.zKernel.html.step01.writer.MyCounter"/>
  </xalan:component>
  
  <!-- Fuer weitere Beispiele siehe auch: https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwje0cvi07j_AhXbgv0HHd8oCWY4ChAWegQIGRAB&url=https%3A%2F%2Fwi.wu.ac.at%2Frgf%2Fdiplomarbeiten%2FBakkStuff%2F2007%2F200706_Pezerovic%2FPezerovic_XSLT_Using_External_Functions_20070608.pdf&usg=AOvVaw3en0M7Y-DfW7UuWJpXuCeg -->
  <!-- obiger link liefert ein PDF, das ich mir in mein google docs Verzeichnis java / java_xslt gespeichert habe -->
  
	<xsl:template match="/">

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
		
		<!-- <xsl:variable name="current_labelFromMap" select="$mapTableHeadLabel"/>--><!-- funktioniert, string mit Gleichheitszeichen -->
		<!-- <xsl:message><xsl:value-of select="concat(string('theHeaderLabel from map as total ='),string($current_labelFromMap))"/></xsl:message> -->
		<!--  <xsl:message><xsl:value-of select="concat(string('theHeaderLabel as map to string='),string($mapTableHeadLabel))"/></xsl:message> -->
		<!-- Der einzelne Wert kann dann später so abgefragt werden -->
		<!-- <xsl:variable name="current_labelFromMap" select="map:get($mapTableHeadLabel,string($current_headerId))"/> -->
								
		<xsl:variable name="current_indexedLabelFromMap" select="$mapTableHeadLabelIndexed"/>
		<!-- TODOGOON DAS ZIEL: <xsl:variable name="current_indexedLabelFromMap" select="mapindexed:get($mapTableHeadLabel,string( ...  irgendein counter ...))"/> --> 
		<xsl:message><xsl:value-of select="concat(string('theHeader INDEXED Label from map as total ='),string($current_indexedLabelFromMap))"/></xsl:message>
		<xsl:message><xsl:value-of select="concat(string('theHeader INDEXED Label as map to string='),string($mapTableHeadLabelIndexed))"/></xsl:message>
		
		<!-- Das liefert das abgespeicherte Java-Objekt zurueck, vom Typ: tryout\basic\zKernel\html\writer\TableHeadZZZ.java -->		
		<xsl:variable name="current_indexedLableHeadObject" select="mapindexed:getValue($mapTableHeadLabelIndexed,1)"/>
		<xsl:message><xsl:value-of select="concat(string('current_indexedLabelHeadObject='),string($current_indexedLableHeadObject))"/></xsl:message>
		
		<!-- Nun aus dem abgespeicherten Java-Objekt die Details holen -->
		<xsl:variable name="current_HeadObjectLabel" select="head:getHeadLabel($current_indexedLableHeadObject)"/>
		<xsl:message><xsl:value-of select="concat(string('current_HeadObjectLabel='),string($current_HeadObjectLabel))"/></xsl:message>

				
		<xsl:variable name="current_dummyFromMap" select="mapindexed:getDummy()"/>
		<xsl:message><xsl:value-of select="concat(string('theHeader dummy from indexed hashmap ='),string($current_dummyFromMap))"/></xsl:message>		
		<!-- TODOGOON 20230610: Es sieht so aus, als koennt man auf keine Eigenschaften eigener Objekte zugreifen. -->

		
		<xsl:variable name="current_indexedLabelMap" select="mapindexed:getHashMap()"/>
		<xsl:message><xsl:value-of select="concat(string('theHeader INDEXED map as total ='),string($current_indexedLabelMap))"/></xsl:message>
		
		<!-- <xsl:variable name="current_labelFromMap" select="map:get($mapTableHeadLabelIndexed,string($current_headerId))"/> -->
		
						
		<h2 style="font-size:20pt;color:#FF0000">Erstellt am: <xsl:value-of select="date:format-date($now,'EEEE, dd. MMMMMMMMMMM yyyy HH:mm:ss')" /></h2>
		<table id="tableWithIpNr">
			
			<!-- Header, Merke: Das label-xml-tag ist über alle Reihen gleich, darum das 1. nehmen -->			
			<xsl:for-each select="/tabledata/row">			
			<xsl:if test="position()=1"><!-- Das soll nur 1x laufen fuer die 1. Zeile -->
			<tr>
				<counter:init name="index" value="0"/>		
				<xsl:for-each select="column">
					<xsl:variable name="index">
						 <xsl:value-of select="counter:read('index')"/>
					</xsl:variable>					
					<xsl:message><xsl:value-of select="concat(string('index from java counter='),string($index))"/></xsl:message>
				
					<!-- ###################################### -->
					<!-- Der Weg ohne eine Sortierung per Index -->
<!-- 					<xsl:variable name="headId"> -->
<!-- 						<xsl:value-of select="headId"/> -->
<!-- 					</xsl:variable> -->
<!-- 					<xsl:message><xsl:value-of select="concat(string('in for-each: headId='),string($headId))"/></xsl:message> -->
						
<!-- 					Hole ueber das Template fuer diese Spalte die Tabellenueberschrift -->
<!-- 					<xsl:variable name="current_label"> -->
<!--         				<xsl:call-template name="mapHeaderIdToLabel"> -->
<!--             				<xsl:with-param name="current_headerId" select="$headId"/> -->
<!--         				</xsl:call-template> -->
<!--     				</xsl:variable> -->
<!-- 					<xsl:message><xsl:value-of select="concat(string('theHeaderLabel='),string($current_label))"/></xsl:message> -->
<!-- 					<th>					 -->
<!-- 						<xsl:value-of select="$current_label"/> -->
<!-- 					</th> -->
					<!-- ###################################### -->
					
					
					<!-- Der Weg ueber eine Sortierung per Index -->
					<xsl:variable name="headId_byIndex">
						<xsl:call-template name="mapHeaderIdByIndex">
            				<xsl:with-param name="current_index" select="$index"/>
        				</xsl:call-template>
					</xsl:variable>
					<xsl:message><xsl:value-of select="concat(string('theHeaderId by Index='),string($headId_byIndex))"/></xsl:message>
					
					<xsl:variable name="headLabel_byIndex">
						<xsl:call-template name="mapHeaderLabelByIndex">
            				<xsl:with-param name="current_index" select="$index"/>
        				</xsl:call-template>
					</xsl:variable>
					<xsl:message><xsl:value-of select="concat(string('theHeaderLabel by Index='),string($headLabel_byIndex))"/></xsl:message>
					
					<th>					
						<xsl:value-of select="$headLabel_byIndex"/>
					</th>
					<!-- ########################################### -->
					
					<counter:incr name="index"/>
				</xsl:for-each>
			</tr>	
			</xsl:if>			
			</xsl:for-each>				
			
			<!-- Daten -->
	        <xsl:for-each select="tabledata/row">
	            <counter:init name="index" value="0"/><!-- den counter fuer jede Zeile neu setzen -->
	            <tr>	            	
	            	<xsl:for-each select="column">	
	            		<xsl:if test="position()=1">
	            				            	
	            		</xsl:if><!-- Das soll nur 1x laufen fuer die 1. Spalte. Merke: in der tr- -->
	            		<xsl:variable name="index">
						 	<xsl:value-of select="counter:read('index')"/>
						</xsl:variable>
	            		
<!-- 	            		<td>  -->	            		
<!-- 	            		<xsl:choose>	 -->
<!-- Allererste Loesung zu Domino Zeiten "VIA" Projekt. Wenn ipnr als Name eines Inputfelds existiert haette -->	            								 								
<!-- 								<xsl:when test="@name='ipnr'">	DIESER ALIASWERT IST NOCH NICHT IM XML VORHANDEN -->
<!-- 									Die normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
<!-- 									<xsl:attribute name = "headers"> -->
<!-- 									    <xsl:value-of select="headId"/> -->
<!-- 									</xsl:attribute> -->
															
<!-- 									Mache einen Link -->
<!-- 				            		<a>              -->
<!-- 				            			<xsl:attribute name = "href">create href attribute	            			 -->
<!-- 				                    		<xsl:value-of select="value"/> -->
<!-- 				                    	</xsl:attribute> -->
<!-- 				                    	<xsl:value-of select="value"/> -->
<!-- 				                    </a>								 -->
<!-- 								</xsl:when>	 -->

<!-- Loesung ueber eine einfache HashMap ohne Indizierung. Die Reihenfolge der Spalten entspricht der Reihenfolge im XML -->
<!-- 								<xsl:when test="headId='IPNr'"> hier müsste eigentlich auf einen unique Aliasname sein und nicht ein flexibles Label -->								
<!-- 									Die normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
<!-- 									<xsl:attribute name = "headers"> -->
<!-- 									    <xsl:value-of select="headId"/> -->
<!-- 									</xsl:attribute> -->
																	
<!-- 									Mache einen Link für die IP-Adresse -->
<!-- 				            		<a>              -->
<!-- 				            			<xsl:attribute name = "href">create href attribute	            			 -->
<!-- 				                    		<xsl:value-of select="value"/> -->
<!-- 				                    	</xsl:attribute> -->
<!-- 				                    	<xsl:value-of select="value"/> -->
<!-- 				                    </a>								 -->
<!-- 								</xsl:when> -->

<!-- Loesung ueber eine HashMap mit Indizierung. Der Index gibt die Reihenfolge der Spalten an. -->
						<td>
						
						<!-- Der Weg ueber eine Sortierung per Index -->
						<xsl:variable name="headId_byIndex">
							<xsl:call-template name="mapHeaderIdByIndex">
	            				<xsl:with-param name="current_index" select="$index"/>
	        				</xsl:call-template>
						</xsl:variable>
						<xsl:message><xsl:value-of select="concat(string('the HeadId by Index='),string($headId_byIndex))"/></xsl:message>
						
						<!-- Der richtige Knoten ist derjenige mit dem Wert des per Index ermittelten schluessels -->
						<!-- Zugriff auf ein Element, dass auf der gleichen Ebene liegt, wie das Element, welches den Wert 17 hat -->
						<!--  <xsl:variable name="checkStudium" select="Root/Antrag/applicationContent/applicationContentId[.='17']/../applicationContentFieldInput/SelectionField/defaulttext" /> -->
						<xsl:variable name="value_byHeadId" select="..//headId[.=$headId_byIndex]/../value" />
						<xsl:message><xsl:value-of select="concat(string('the Value by HeadId byIndex='),string($value_byHeadId))"/></xsl:message>
						
						<xsl:choose>
								<xsl:when test="$headId_byIndex='IPNr'"> <!-- hier müsste eigentlich auf einen unique Aliasname sein und nicht ein flexibles Label -->
									<!-- Die normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
									<xsl:attribute name = "headers">
									    <xsl:value-of select="$headId_byIndex"/>
									</xsl:attribute>
																	
									<!-- Mache einen Link für die IP-Adresse -->								
				            		<a>             
				            			<xsl:attribute name = "href"><!-- create href attribute -->	            			
				                    		<xsl:value-of select="$value_byHeadId"/>
				                    	</xsl:attribute>
				                    	<xsl:value-of select="$value_byHeadId"/>
				                    </a>								
								</xsl:when>													
								<xsl:otherwise>
									<!-- Nur normale Ausgabe des Stringwerts mit Bezug auf die Headerzeile -->
									<xsl:attribute name = "headers">
									    <xsl:value-of select="$headId_byIndex"/>
									</xsl:attribute>
									<xsl:value-of select="$value_byHeadId"/>								
								</xsl:otherwise>											
						</xsl:choose>  	            	
	                    </td>	   
	                     <counter:incr name="index"/>                 
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
	
	<!-- VERALTETETES TEMPLATE -->
	<!-- Template: Hole anhand der HeaderId (Kommt aus dem XML) die passende Tabellenueberschrift. Hier ist also die Stelle an der die "Labels" definiert werden -->	
<!-- 	<xsl:template name="mapHeaderIdToLabel"> -->
<!--     <xsl:param name="current_headerId"/> -->
<!-- 	<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - Input current_headerId='),string($current_headerId))"/></xsl:message> -->
						
<!-- Die Loesung hier die Ueberschriften ueber eine Fallunterschreibung zu verwalten ist nicht optimal -->				    
<!-- 	<xsl:choose> -->
<!-- 	<xsl:when test="$current_headerId='IPNr'"> -->
<!-- 	<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - match for: '),string($current_headerId))"/></xsl:message> -->
<!-- 		<xsl:value-of select="'IP Adresse'" /> -->

<!-- Verbesserte Loesung: -->
<!-- Hole aus einer Java-Map das Label. Merke: Java Map wird dem Transformer-Objekt uebergeben. -->
<!-- 	<xsl:variable name="current_labelFromMap" select="map:get($mapTableHeadLabel,string($current_headerId))"/> -->		
<!-- 	<xsl:message><xsl:value-of select="concat(string('theHeaderLabel from map in template with variable key='),string($current_labelFromMap))"/></xsl:message> -->
	
<!-- 		<xsl:choose>	 -->
<!-- 		<xsl:when test="$current_labelFromMap!=''"> -->
<!-- 			<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - match for: '),string($current_headerId))"/></xsl:message> -->
<!-- 			<xsl:value-of select="$current_labelFromMap" /> -->
<!-- 		</xsl:when> -->
<!-- 		<xsl:otherwise> -->
<!-- 			<xsl:message><xsl:value-of select="concat(string('mapHeaderIdToLabel - no match for: '),string($current_headerId))"/></xsl:message> -->
				<!-- falls nicht gemappt, gib die eingegebene Id zurueck -->
<!-- 			<xsl:value-of select="$current_headerId" /> -->
<!-- 		</xsl:otherwise> -->
<!-- 		</xsl:choose>		 -->
<!-- 	</xsl:template> -->
	
	<!-- Template: Hole anhand eines Zähler-Index (Kommt aus einer hier lokal als Xalan-Komponente definierten Java-Klasse) den passende Eintrag aus einer an das Script uebergebenen Java HashMap mit Index -->
	<!-- Das ist noch eine bessere Lösung, da nun die Reihenfolge der Spalten so definiert werden kann, unabhängig von der Reihenfolge im XML -->
	<xsl:template name="mapHeaderIdByIndex">
    <xsl:param name="current_index"/>
 		<xsl:message><xsl:value-of select="concat(string('mapTableHeadObjectByIndex - Input current_index='),string($current_index))"/></xsl:message>
		 	
 		<!-- Verbesserte Loesung: -->
		<!-- Hole aus einer indizierten Java-Map das TableHead-Objekt. Merke: Das HashMapsIndexed Java Objekt wird dem Transformer-Objekt uebergeben. -->
		<!-- Das liefert das abgespeicherte Java-Objekt zurueck, vom Typ: tryout\basic\zKernel\html\writer\TableHeadZZZ.java -->
		<xsl:variable name="current_objectFromMap" select="mapindexed:getValue($mapTableHeadLabelIndexed,$current_index)"/>		
	 	<xsl:message><xsl:value-of select="concat(string('current_objectFromMap: '),string($current_objectFromMap))"/></xsl:message>
	 	
	 	<!-- Nun aus dem abgespeicherten Java-Objekt die Details holen -->
		<xsl:variable name="current_headId" select="head:getHeadId($current_objectFromMap)"/>
		<xsl:message><xsl:value-of select="concat(string('current_headId='),string($current_headId))"/></xsl:message>
	 	
	 	<xsl:value-of select="$current_headId"/>
    </xsl:template>
    
    <!-- Template: Hole anhand eines Zähler-Index (Kommt aus einer hier lokal als Xalan-Komponente definierten Java-Klasse) den passende Eintrag aus einer an das Script uebergebenen Java HashMap mit Index -->
	<!-- Das ist noch eine bessere Lösung, da nun die Reihenfolge der Spalten so definiert werden kann, unabhängig von der Reihenfolge im XML -->
	<xsl:template name="mapHeaderLabelByIndex">
    <xsl:param name="current_index"/>
 		<xsl:message><xsl:value-of select="concat(string('mapTableHeadObjectByIndex - Input current_index='),string($current_index))"/></xsl:message>
		 	
 		<!-- Verbesserte Loesung: -->
		<!-- Hole aus einer indizierten Java-Map das TableHead-Objekt. Merke: Das HashMapsIndexed Java Objekt wird dem Transformer-Objekt uebergeben. -->
		<!-- Das liefert das abgespeicherte Java-Objekt zurueck, vom Typ: tryout\basic\zKernel\html\writer\TableHeadZZZ.java -->
		<xsl:variable name="current_objectFromMap" select="mapindexed:getValue($mapTableHeadLabelIndexed,$current_index)"/>		
	 	<xsl:message><xsl:value-of select="concat(string('current_objectFromMap: '),string($current_objectFromMap))"/></xsl:message>
	 	
	 	<!-- Nun aus dem abgespeicherten Java-Objekt die Details holen -->
		<xsl:variable name="current_headLabel" select="head:getHeadLabel($current_objectFromMap)"/>
		<xsl:message><xsl:value-of select="concat(string('current_headLabel='),string($current_headLabel))"/></xsl:message>
	 	
	 	<xsl:value-of select="$current_headLabel"/>
    </xsl:template>
</xsl:stylesheet>