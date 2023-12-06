package hu.domparse.F023QC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMModifyF023QC {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String attributeName = "";
		String id = "";
		String Tid = "";
		int index;
		File xmlFile = new File("XMLF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		
		
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
		transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        FileWriter fileWriter = new FileWriter("XMLF023QC1.xml");
		
		
		boolean exit = false;
		boolean ok = false;
		Scanner sc = new Scanner(System.in);

		while (!exit) {
			ok = false;
			System.out.println("Válasszon!");

			// ELőször kiválaszttjuk, hogy melyik elemet akarjuk lekérdezni
			List<String> uniqueElements = getUniqueNodes(document.getDocumentElement());
			uniqueElements.add("exit");
			String selected = "";
			while (!ok) {

				System.out.println("Melyiket szeretné lekérdezni?");
				for (String string : uniqueElements) {
					System.out.println(string);
				}

				selected = sc.nextLine();

				if (uniqueElements.contains(selected)) {
					ok = true;
				} else {
					System.out.println("Nincs ilyen elem!");
				}
			}

			if (selected.equalsIgnoreCase("exit")) {
				break;
			}
			System.out.println();
			ok = false;

			// normál id (rendelés id RT estén)
			id = "";
			Tid = "";
			// Bekérjük az id-t
			attributeName = selected.toLowerCase() + "ID";
			if (selected.equalsIgnoreCase("Cím")) {
				attributeName = "vevőID";
			}
			if (selected.equalsIgnoreCase("Bankkártya")) {
				attributeName = "kártyaszám";
			}
			Node result = null;
			if (!selected.equalsIgnoreCase("RT")) {
				while (!ok) {
					System.out.println("Adja meg az id-t: ");
					List<String> uniqueId = getUniqueIDs(document, attributeName,
							document.getElementsByTagName(selected));
					for (String string : uniqueId) {
						System.out.println(string);
					}

					id = sc.nextLine();

					if (uniqueId.contains(id)) {
						ok = true;
					} else {
						System.out.println("Nincs ilyen ID");
					}
					System.out.println();
					// Kiíratjuk az elemet
					result = getNode(document, attributeName, document.getElementsByTagName(selected), id);
				}
			} else {
				while (!ok) {
					System.out.println("Adja meg az rendelésID-t: ");
					List<String> uniqueId = getUniqueIDs(document, "rendelésID",
							document.getElementsByTagName(selected));
					for (String string : uniqueId) {
						System.out.println(string);
					}

					id = sc.nextLine();

					if (uniqueId.contains(id)) {
						ok = true;
					} else {
						System.out.println("Nincs ilyen ID");
					}
				}
				System.out.println();
				ok = false;
				while (!ok) {
					System.out.println("Adja meg a termékID-t: ");
					List<String> uniqueId = getUniqueTIDs(document, "termékID", document.getElementsByTagName(selected),
							id);
					for (String string : uniqueId) {
						System.out.println(string);
					}

					Tid = sc.nextLine();

					if (uniqueId.contains(Tid)) {
						ok = true;
					} else {
						System.out.println("Nincs ilyen ID");
					}
				}
				System.out.println();

				result = getNodeRT(document, document.getElementsByTagName(selected), id, Tid);
			}

			List<Node> modifyNodes = getNodesToModify(result);
			ok = false;
			Node nodeToModify = null;
			while (!ok) {
				System.out.println("Melyiket szeretnéd módosítani?");

				for (int i = 0; i < modifyNodes.size(); i++) {
					System.out.println(
							i + ". " + modifyNodes.get(i).getNodeName() + ": " + modifyNodes.get(i).getTextContent());
				}
				index = sc.nextInt();
				sc.nextLine();

				if (index > 0 && index < modifyNodes.size()) {
					nodeToModify = modifyNodes.get(index);
					ok = true;
				} else {
					System.out.println("Helytelen szám");
				}
			}
			
			System.out.println("Adja meg, hogy mire szeretné módosítani:");
			String to = sc.nextLine();
			
			nodeToModify.setTextContent(to);
	        transformer.transform(new DOMSource(document), new StreamResult(fileWriter));
	        transformer.transform(new DOMSource(document), new StreamResult(System.out));
	        fileWriter.close();
			
		}
		sc.close();
	}


	// Ezzel kapjuk meg a node-ot
	private static Node getNode(Document doc, String attributeName, NodeList nodes, String Id) {

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				if (attributes.getNamedItem(attributeName).getTextContent().equalsIgnoreCase(Id)) {
					return nodes.item(i);
				}

			}
		}

		return null;

	}

	// Ezzel kapjuk meg a node-ot az RT-hez
	private static Node getNodeRT(Document doc, NodeList nodes, String Id, String Tid) {

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				if (attributes.getNamedItem("termékID").getTextContent().equalsIgnoreCase(Tid)
						&& attributes.getNamedItem("rendelésID").getTextContent().equalsIgnoreCase(Id)) {
					return nodes.item(i);
				}

			}
		}

		return null;

	}

	private static List<Node> getNodesToModify(Node node) {
		List<Node> ret = new ArrayList<>();
		NodeList childs = node.getChildNodes();

		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				ret.add(childs.item(i));
			}
		}
		return ret;
	}

	// Ezzel megkapjuk az egyedi node-ok nevét
	private static List<String> getUniqueNodes(Node node) {

		List<String> uniqueElements = new ArrayList<>();
		Set<String> seenElements = new HashSet<>();

		NodeList childs = node.getChildNodes();

		for (int i = 0; i < childs.getLength(); i++) {
			Node actualChild = childs.item(i);

			if (!seenElements.contains(actualChild.getNodeName())
					&& childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				seenElements.add(actualChild.getNodeName());
				uniqueElements.add(actualChild.getNodeName());
			}
		}

		return uniqueElements;

	}

	// Id-k megkeresése
	private static List<String> getUniqueIDs(Document doc, String attributeName, NodeList nodes) {

		List<String> uniqueElements = new ArrayList<>();
		Set<String> seenElements = new HashSet<>();

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				if (!seenElements.contains(attributes.getNamedItem(attributeName).getTextContent())) {
					seenElements.add(attributes.getNamedItem(attributeName).getTextContent());
					uniqueElements.add(attributes.getNamedItem(attributeName).getTextContent());
				}

			}
		}

		return uniqueElements;

	}

	// Termék id-k megkeresése
	private static List<String> getUniqueTIDs(Document doc, String attributeName, NodeList nodes, String id) {

		List<String> uniqueElements = new ArrayList<>();
		Set<String> seenElements = new HashSet<>();

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				if (!seenElements.contains(attributes.getNamedItem(attributeName).getTextContent())
						&& attributes.getNamedItem("rendelésID").getTextContent().equalsIgnoreCase(id)) {
					seenElements.add(attributes.getNamedItem(attributeName).getTextContent());
					uniqueElements.add(attributes.getNamedItem(attributeName).getTextContent());
				}

			}
		}

		return uniqueElements;

	}

}
