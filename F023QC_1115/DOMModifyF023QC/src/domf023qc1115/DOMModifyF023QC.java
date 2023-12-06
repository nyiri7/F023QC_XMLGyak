package domf023qc1115;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DOMModifyF023QC {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse("orarendF023QC.xml");

            NodeList orak = document.getElementsByTagName("ora");
            for (int i = 0; i < orak.getLength(); i++) {
                Node ora = orak.item(i);
                boolean hasOraado = false;

                NodeList children = ora.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("óraado")) {
                        hasOraado = true;
                        break;
                    }
                }

                if (!hasOraado) {
                    Element oraado = document.createElement("óraado");
                    oraado.setTextContent("Óraadó");
                    ora.appendChild(oraado);
                }
            }

            for (int i = 0; i < orak.getLength(); i++) {
                Element ora = (Element) orak.item(i);
                if (ora.getAttribute("típus").equals("gyakorlat")) {
                    ora.setAttribute("típus", "elõadás");
                }
            }

            for (int i = 0; i < orak.getLength(); i++) {
                Element ora = (Element) orak.item(i);
                NodeList idopontok = ora.getElementsByTagName("idopont");
                for (int j = 0; j < idopontok.getLength(); j++) {
                    Element idopont = (Element) idopontok.item(j);
                    NodeList napok = idopont.getElementsByTagName("nap");
                    if (napok.getLength() > 0) {
                        Element nap = (Element) napok.item(0);
                        if (nap.getTextContent().equals("Csütörtök")) {
                            nap.setTextContent("Péntek");
                        }
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(document), new StreamResult(System.out));

            transformer.transform(new DOMSource(document), new StreamResult("orarendModifyF023QC.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}