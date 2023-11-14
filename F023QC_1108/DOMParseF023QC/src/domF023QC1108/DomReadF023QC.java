package domF023QC1108;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class DomReadF023QC {



	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		File xmlFile = new File("orarendF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("ora");

		

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			System.out.println("\nCurrent Element: " + nNode.getNodeName());

			

			if (nNode.getNodeType() == Node.ELEMENT_NODE)

			{

				Element elem = (Element) nNode;
				String sid = elem.getAttribute("id");

				Node node1 = elem.getElementsByTagName("targy").item(0);
				String targy = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("idopont").item(0);
				String ido = node2.getTextContent();

				Node node4 = elem.getElementsByTagName("oktato").item(0);
				String oktato = node4.getTextContent();

				Node node3 = elem.getElementsByTagName("helyszin").item(0);
				String helyszin = node3.getTextContent();

				System.out.println("targy id: " + sid);
				System.out.println("targy: " +targy);
				System.out.println("idopont: " + ido);
				System.out.println("helyszin: " +helyszin);
				System.out.println("oktato: " +oktato);


			}

		}

	}



}