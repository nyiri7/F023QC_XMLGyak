package hu.domparse.F023QC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		File xmlFile = new File("XMLF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		
		
		//Étterem címének a módosítása
		System.out.println("Második Étterem címének a módosítása");
		setEtterem(document, "2", "3525 Miskolc Szemere Bertalan Utca 12.");
		
		//Futár elérhetőségének a módosítása
		System.out.println("Első Futár elérhetőségének a módosítása");
		changeElerheto(document, "1");
		
		//Mennyiség módosítása
		System.out.println("Az első rendelésbe tartozó 5-ös id-vel rendelkező termék mennyiségének a módosítása");
		setMennyiseg(document, "1","5", "2");
		
		//Házszám módosítása
		System.out.println("Az első vevő címében lévő házszám módosítása");
		setHazszam(document, "1", "22");
		
		//A vevő összes kártyatípusának a módosítása
		System.out.println("Az első vevő bankkártyáinak beállítása MasterCard típusúra");
		setMasterCardforVevo(document, "1");
		
		//Dokumentum mentése és kiíratása
		writeXML(document);
	}

	
	
	//Étterem címének a módosítása
	private static void setEtterem(Document doc,String id,String cim) {
		//Kiszedem a dokumentumból az éttermeket
		NodeList ettermek = doc.getElementsByTagName("Étterem");
		
		//Végigmegyek az éttermeken és ha egyezik a rendelésid a megadott id-vel, akkor az étterem címét átírom
		for(int i =0; i<ettermek.getLength();i++) {
			if(ettermek.item(i).getAttributes().getNamedItem("étteremID").getTextContent().equalsIgnoreCase(id)) {
				//Aktuális étterem
				Element etterem = (Element) ettermek.item(i);
				//Aktuális étterem előtte 
				System.out.println("Előtte:\n");
				printNode(etterem);
				//Cím kiválasztása és módosítása
				Node cimNode = etterem.getElementsByTagName("cím").item(0);
				cimNode.setTextContent(cim);
				//Aktuális étterem utána
				System.out.println("Utána:\n");
				printNode(etterem);
				
			}
		}
		
	}
	
	//Futár elérhetőségének a módosítása
	private static void changeElerheto(Document doc,String futarID) {
		//A dokumentumból kiszedem az összes futárt, majd végigmegyek rajtuk egy for ciklussal
		NodeList futarok = doc.getElementsByTagName("Futár");
		
		for(int i=0;i<futarok.getLength();i++) {
			//Aktuális futár
			Element futar = (Element) futarok.item(i);
			//Ha az aktuális futár id-je megegyezik a megadott id-vel
			if(futar.getAttribute("futárID").equalsIgnoreCase(futarID)) {
				System.out.println("Előtte:\n");
				printNode(futar);
				//Eléhető nevezésű elem kiválasztása az aktuális futárból
				Element elerheto = (Element) futar.getElementsByTagName("elérhető").item(0);
				//Ha elérhető, akkor legyen nem elérhető
				if(elerheto.getTextContent().equalsIgnoreCase("1")) {
					elerheto.setTextContent("0");
				//Ha nem elérhető, akkor legyen elérhető
				}else {
					elerheto.setTextContent("1");
				}
				System.out.println("Utána:\n");
				printNode(futar);
			}
			
		}
		
	}
	
	
	
	//Mennyiség beállítása
	private static void setMennyiseg(Document doc,String rendelesID,String termekID,String mennyiseg) {
		//Kiszedem a dokumentumból az összes RT-t
		NodeList RT = doc.getElementsByTagName("RT");
		
		//For ciklussal végigmegyek az összes RT-n
		for(int i =0;i<RT.getLength();i++) {
			//Az aktuális RT kapcsolat elem
			Element rt = (Element) RT.item(i);
			//RendelésId és termék id-k ellenőrzése
			if(rt.getAttribute("rendelésID").equalsIgnoreCase(rendelesID) && rt.getAttribute("termékID").equalsIgnoreCase(termekID)) {
				System.out.println("Előtte:\n");
				printNode(rt);
				//Mennyiség elem kiválasztása és módosítása
				Element menny = (Element) rt.getElementsByTagName("mennyiség").item(0);
				menny.setTextContent(mennyiseg);
				System.out.println("Utána:\n");
				printNode(rt);
			}
		}
		
	}
	
	//Cím beállítása
	private static void setHazszam(Document doc,String vevoID,String hazszam) {
		//Vevők kigyűjtése
		NodeList Vevok = doc.getElementsByTagName("Vevő");
		
		for(int i =0; i<Vevok.getLength();i++) {
			//Aktuális vevő
			Element vevo = (Element) Vevok.item(i);
			//Vevőid ellenőrzése
			if(vevo.getAttribute("vevőID").equalsIgnoreCase(vevoID)) {
				System.out.println("Előtte:\n");
				printNode(vevo);
				//Cím elem kiválastása
				Element cim = (Element) vevo.getElementsByTagName("cím").item(0);
				//Házszám kiválasztása és beállítása
				Element hazsz = (Element) cim.getElementsByTagName("házszám").item(0);
				hazsz.setTextContent(hazszam);
				System.out.println("Utána:\n");
				printNode(vevo);
			}
		}
	}
	
	//Bankkártyák beállítása
	private static void setMasterCardforVevo(Document doc, String vevoID) {
		//Bankkártyák kigyűjtése
		NodeList kartyak = doc.getElementsByTagName("Bankkártya");
		
		for(int i =0; i<kartyak.getLength();i++) {
			//Aktuális bankkártya
			Element kartya = (Element) kartyak.item(i);
			
			//VevőID ellenőrzése
			if(kartya.getAttribute("vevőID").equalsIgnoreCase(vevoID)) {
				System.out.println("Előtte:\n");
				printNode(kartya);
				//típus kiválasztása és módosítása
				Element tipus = (Element) kartya.getElementsByTagName("típus").item(0);
				tipus.setTextContent("MasterCard");
				System.out.println("Utána:\n");
				printNode(kartya);
			}
		}
	}
	
	
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
            transformer.transform(new DOMSource(doc), new StreamResult(System.out));
            System.out.println("Sikeres kiírás fájlba!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	  //Kiíratjuk a consolra a node értékeit
    private static void printNode(Node currentElement) {
    	//Csak akkor fusson le, ha a típusa ELEMENT
    	if (currentElement.getNodeType()==Node.ELEMENT_NODE) {
    		//Első sor az xml szintaktikája + a node neve
			String fRow ="<"+currentElement.getNodeName()+" ";
			
			//Az első sorhoz hozzáadom az attribútumokat
			for(int j = 0; j< currentElement.getAttributes().getLength();j++) {
				if(j==currentElement.getAttributes().getLength()-1) {
					fRow += currentElement.getAttributes().item(j).getNodeName()+"=\""+currentElement.getAttributes().item(j).getTextContent()+"\">";
				}else {
					fRow += currentElement.getAttributes().item(j).getNodeName()+"=\""+currentElement.getAttributes().item(j).getTextContent()+"\" ";
				}
			}
			
			
			//Kiíratom az első sort
			System.out.println(getIndent(1)+fRow);
			
			//Végigmegyek a Node gyerekelemein
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
			System.out.println(getIndent(1)+"</"+currentElement.getNodeName()+">\n");
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
