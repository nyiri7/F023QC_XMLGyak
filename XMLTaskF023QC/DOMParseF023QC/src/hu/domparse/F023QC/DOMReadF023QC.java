package hu.domparse.F023QC;

import java.io.File;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class DOMReadF023QC {
	public static void main(String[] args) {
		try {
            File xmlFile = new File("XMLF023QC.xml");
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = factory.newDocumentBuilder();;
    		Document document = dBuilder.parse(xmlFile);
    		document.getDocumentElement().normalize();
    		
    		//Kiíratom a fájlt
    		printDocument(document);
    		//Új fájl létrehozása
    		writeXML(document);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	//Törlöm a felesleges text nodeokat
    private static void removeText(Node root) {
    	//Kigyűjtöm a routnak a gyerekeit, majd végigmegyek rajta
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
        	//Ha találok felesleget, akkor kitörlöm azt
            if (nodeList.item(i).getNodeType() == Node.TEXT_NODE && nodeList.item(i).getTextContent().strip().isEmpty()) {
            	nodeList.item(i).getParentNode().removeChild(nodeList.item(i));
            	//A törlés miatt 1-el előrébbb járunk, mint ahol kéne, ezért az i-t csökkentem egyel
            	i--;
            //Ha nem felesleges node, akkor ezt a node-ot is leellenőruöm és kitörlöm belőle a felesleget
            } else {
                removeText(nodeList.item(i));
            }
        }
    }
	
	private static void printDocument(Document doc) {
		//Kiíratom az xml deklarációt
		System.out.println("<?xml version=\""+doc.getXmlVersion()+"\" encoding=\""+doc.getXmlEncoding()+"\"?>");
		
		//Kiíratom a gyökérelemet
		Element rootelement =doc.getDocumentElement();
		//Első sora a gyökérelemnek
		String rootRow ="<"+rootelement.getNodeName()+" ";
		
		//Gyökérelem "attribútumainak" hozzáadása az első sorhoz
		for(int i = 0; i< rootelement.getAttributes().getLength();i++) {
			if(i==rootelement.getAttributes().getLength()-1) {
				rootRow += rootelement.getAttributes().item(i).getNodeName()+"=\""+rootelement.getAttributes().item(i).getTextContent()+"\">";
			}else {
				rootRow += rootelement.getAttributes().item(i).getNodeName()+"=\""+rootelement.getAttributes().item(i).getTextContent()+"\" ";
			}

		}
		
		//Első sor kiíratása
		System.out.println(rootRow);

		
		//A gyökérelemben lévő összes elem kiírása
		NodeList elements = rootelement.getChildNodes();
		
		//Végigmegyek az elemeken
		for(int i=0;i<elements.getLength();i++) {
			Node currentElement = elements.item(i);
			//Ha a jelenlegi elem komment, akkor kíratom kommentként
			if(currentElement.getNodeType()==Node.COMMENT_NODE) {
				System.out.println(getIndent(1)+"<!--"+currentElement.getTextContent()+"-->");
			//Ellenkező esetben kiíratom normál Node-ként
			}else if (currentElement.getNodeType()==Node.ELEMENT_NODE) {
				//Az első sora az elemnek
				String fRow ="<"+currentElement.getNodeName()+" ";
				//Hozzáadom az attribútumokat az első sorhoz
				for(int j = 0; j< currentElement.getAttributes().getLength();j++) {
					if(j==currentElement.getAttributes().getLength()-1) {
						fRow += currentElement.getAttributes().item(j).getNodeName()+"=\""+currentElement.getAttributes().item(j).getTextContent()+"\">";
					}else {
						fRow += currentElement.getAttributes().item(j).getNodeName()+"=\""+currentElement.getAttributes().item(j).getTextContent()+"\" ";
					}
				}
				//Kiíratom az első sort
				System.out.println(getIndent(1)+fRow);
				//Kigyűjtöm az elem gyerekelemeit
				NodeList childs = currentElement.getChildNodes();
				for(int j=0;j<childs.getLength();j++) {
					Node child = childs.item(j);
					//Ha a gyerekelem típusa ELEMENT
					if(child.getNodeType()==Node.ELEMENT_NODE) {
						//Ha csak sima gyerekelem, akkor kiíratom (Csak szöveg van benne)
						if(child.getChildNodes().getLength()==1) {
							System.out.println(getIndent(2)+"<"+child.getNodeName()+">"+child.getTextContent()+"</"+child.getNodeName()+">");
						//Ha nem csak szöveg van benne, akkor meg kell nézni a gyerekelem gyerekelemeit és azokat is kiíratni (cím esetében)
						}else {
							//Kiírom a gyerekelemet
							System.out.println(getIndent(2)+"<"+child.getNodeName()+">");
							//Kiírom a gyerekelem gyerekelemeit
							for(int k =0;k<child.getChildNodes().getLength();k++) {
								if(child.getChildNodes().item(k).getNodeType()==Node.ELEMENT_NODE) {
									System.out.println(getIndent(3)+"<"+child.getChildNodes().item(k).getNodeName()+">"+child.getChildNodes().item(k).getTextContent()+"</"+child.getChildNodes().item(k).getNodeName()+">");
								}
								
							}
							//Bezárom a gyerekelemet
							System.out.println(getIndent(2)+"</"+child.getNodeName()+">");
						}
					}

				}
				//Bezárom az elemet
				System.out.println(getIndent(1)+"</"+currentElement.getNodeName()+">");
			}
			
		}
		//Bezárom a gyökérelemet
		System.out.println("</"+rootelement.getNodeName()+">");
		
	}
	
	private static void writeXML(Document doc) {
        try {
        	//</text> node-októl megtisztitóm a dokumentumot, majd a transformer segítségével kiíaratom egy fájlba
            removeText(doc);
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");
            FileWriter fileWriter = new FileWriter("XMLF023QC1.xml");
            transformer.transform(new DOMSource(doc), new StreamResult(fileWriter));
            System.out.println("Sikeres kiírás fájlba!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	//Behúzások meghatározása
	private static String getIndent(int indent) {
		//A paraméternek megfelelő behúzást ad vissza, a behúzás 4 darab space
		String ret="";
		for(int i=0;i<indent;i++) {
			ret+="    ";
		}
		return ret;
	}
	
}