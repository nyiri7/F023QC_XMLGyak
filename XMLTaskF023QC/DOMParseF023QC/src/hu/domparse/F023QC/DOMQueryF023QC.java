package hu.domparse.F023QC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMQueryF023QC {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File("XMLF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		
		//Lekérdezések
		
		//Lekérdezem az összes elérhető futárt
		System.out.println("Az elérhető futárok:");
    	getElerheto(document);
    	
    	//Lekérdezem azokat a bankkártyákat, amelyeknek a típusa mastercard
    	System.out.println();
    	System.out.println("MasterCard Bankkártyák");
    	getMaster(document);
    	
    	//Lekérdezem a rendelésben lévő termékeket
    	System.out.println();
    	System.out.println("Rendelésben lévő termékek:");
    	getTermekByRendeles(document, "1");
    	
    	//Lekérdezem az étteremhez tartozó rendeléseket
    	System.out.println();
    	System.out.println("Étteremhez tartozó rendelések:");
    	getRendelesByEtterem(document, "1");
    	
    	//Lekérdezem azokat a rendeléseket, amelyek ugyanarról a környékről származnak (irányítószám alapján)
    	System.out.println();
    	System.out.println("Rendelések egy környékről:");
    	getRendelesByIrszam(document, "3531");
    	
    }
    
    private static void getRendelesByIrszam(Document doc, String irszam) {
    	//Kigyűjtjük az összes vevőt
    	NodeList vevok = doc.getElementsByTagName("Vevő");
    	List<String> rendelesIDk = new ArrayList<String>();
    	
    	for(int i=0;i<vevok.getLength();i++) {
    		//Ez a jelenlegi vevő
    		Element vevo = (Element) vevok.item(i);
    		//A jelenlegi vevőnek a címe
    		Element cim = (Element) vevo.getElementsByTagName("cím").item(0);
    		//A jelenlegi irányítószám
    		Element iranyitoszam = (Element) cim.getElementsByTagName("irányítószám").item(0);
    		
    		//Ha a jelenlegi irányítószám megegyezik a megadottal, akkor a vevőnek a rendelés id-jét hozzáadjuk a listához
    		if(iranyitoszam.getTextContent().equalsIgnoreCase(irszam)) {
    			rendelesIDk.add(vevo.getAttribute("rendelésID"));
    		}
    	}
    	
    	//A dokumentumból kiszedem a rendeléseket
    	NodeList rendelesek = doc.getElementsByTagName("Rendelés");
    	
    	//végigmegyek az összes rendelésen
    	for(int i =0;i<rendelesek.getLength();i++) {
    		//Ha a rendelés id-je benne van az irányítószámhoz tartozó rendelésid-k listájában, akkor kiíratom
    		if(rendelesIDk.contains(rendelesek.item(i).getAttributes().getNamedItem("rendelésID").getTextContent())) {
    			printNode(rendelesek.item(i));
    		}
    	}
    	
    	
    }
    
    private static void getRendelesByEtterem(Document doc, String etteremID) {
    	//A dokumentumból kiszedem az összes rendelést
    	NodeList Rendelesek = doc.getElementsByTagName("Rendelés");
    	
    	//Végigmegyek a rendeléseken és ha egyezik a megadott étteremid-vel az attribute értéke, akkor kiíratom
    	for(int i=0;i<Rendelesek.getLength();i++) {
    		if(Rendelesek.item(i).getAttributes().getNamedItem("étteremID").getTextContent().equalsIgnoreCase(etteremID)) {
    			printNode(Rendelesek.item(i));
    		}
    	}
    }
    
    
    private static void getTermekByRendeles(Document doc,String rendelesID) {
    	//Lekérem az összes RT kapcsolatot
    	NodeList RT = doc.getElementsByTagName("RT");
    	//Ebben fpgom tárolni a termékek id-it
    	List<String> termekIDk = new ArrayList<String>();
    	
    	
    	//végigmegyek a kapcsolat elemein és megnézem a rendeléID-ket, ha egyezik akkor a termék id-t hozzáadom a listához
    	for(int i=0;i < RT.getLength();i++) {
    		if(RT.item(i).getAttributes().getNamedItem("rendelésID").getTextContent().equalsIgnoreCase(rendelesID)) {
    			termekIDk.add(RT.item(i).getAttributes().getNamedItem("termékID").getTextContent());
    		}
    	}
    	
    	//Kigyűjtöm az összes terméket a dokumentumból
    	NodeList termekek = doc.getElementsByTagName("Termék");
    	
    	//Végigmegyek a termékeken és ha egyezést találok, akkor kiíratom a terméket
    	for(int i =0;i<termekek.getLength();i++) {
    		//Ha benne a van a listában a termék id-je, akkor benne van a rendelésben
    		if(termekIDk.contains(termekek.item(i).getAttributes().getNamedItem("termékID").getTextContent())) {
    			printNode(termekek.item(i));
    		}
    	}
    	
    	
    	
    	
    }
    
    private static void getElerheto(Document doc) {
    	//Kigyűjtöm a futárokat a dokumentumból
    	NodeList futarok = doc.getElementsByTagName("Futár");
    	
    	//Végigmegyek a futárokon
    	for(int i =0;i<futarok.getLength();i++) {
    		//Kigyűjtöm az aktuális futár gyerekelemeit
    		NodeList childs = futarok.item(i).getChildNodes();
    		//Végigmegyek a gyerekelemeken és keresem az elérhető nevű gyerekelemet
    		for(int j=0;j<childs.getLength();j++) {
    			//Ha a futár elérhető, akkor kiíratom
    			if(childs.item(j).getNodeName().equalsIgnoreCase("elérhető") && childs.item(j).getTextContent().equalsIgnoreCase("1") ) {
    				printNode(futarok.item(i));
    				System.out.println();
    			}
    		}
    		
    		
    	}
    	
    	
    }
    private static void getMaster(Document doc) {
    	//Kigyűjtöm a kártyákat a dokumentumból
    	NodeList kartyak = doc.getElementsByTagName("Bankkártya");

    	//Végigmegyek a kártyákon
    	for(int i =0;i<kartyak.getLength();i++) {
    		//Az aktuális kártya gyerekelemei
    		NodeList childs = kartyak.item(i).getChildNodes();
    		
    		//Megkeresem a típus nevű gyerekelemet
    		for(int j=0;j<childs.getLength();j++) {
    			//Ha az értéke Mastercard, akkor kiíratom a kártyát
    			if(childs.item(j).getNodeName().equalsIgnoreCase("típus") && childs.item(j).getTextContent().equalsIgnoreCase("Mastercard") ) {
    				printNode(kartyak.item(i));
    				System.out.println();
    			}
    		}
    		
    		
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
