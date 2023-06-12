1. Aus der XML Datei die XSD erstellen lassen
https://stackoverflow.com/questions/31403920/how-to-create-an-xsd-file-using-xml-data-in-java

Aber der Rechtsclick auf die Datei bietet keinen Wizard an dafür eine XSD Datei zu erstellen.
Erstellt wird eine XSD Datei hierüber:
https://www.freeformatter.com/xsd-generator.html#before-output

Danach per Rechtsclick:
"generate JAXB Classes"

Diese in einem neuen Package ablegen:
z.B. ObjectFactory.java
hat nun die Methoden
.createTabledata()
.createTabledataRow()
.createTabeldataRowColumn()


