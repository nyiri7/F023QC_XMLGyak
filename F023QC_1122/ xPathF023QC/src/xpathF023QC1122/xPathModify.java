package xpathF023QC1122;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;



public class xPathModify {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        File xmlFile = new File("orarendF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();




        NodeList nodeList = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        String xpathExpression = "//ora[@id ='2']/targy";
		String xpathExpression1 = "//ora[@id ='1']/helyszin";
		String xpathExpression2 = "//ora[@id ='2']/oktato";





        XPathExpression expr = xpath.compile(xpathExpression);
        nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        Node node = nodeList.item(0);

        node.setTextContent("alma");


		expr = xpath.compile(xpathExpression1);
        nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        node = nodeList.item(0);

        node.setTextContent("32. előadó");

		expr = xpath.compile(xpathExpression2);
        nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        node = nodeList.item(0);

        node.setTextContent("előadó");

        printXML(document, 0);

        




    }
    private static void printXML(Document document, int level) {
		try {
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer;
			transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

	        FileWriter fileWriter = new FileWriter("orarendF023QC1.xml");
	        transformer.transform(new DOMSource(document), new StreamResult(fileWriter));
	        transformer.transform(new DOMSource(document), new StreamResult(System.out));
	        fileWriter.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
