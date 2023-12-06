package hu.domparse.F023QC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMQueryF023QC {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    	String attributeName="";
    	String id = "";
    	String Tid ="";
        File xmlFile = new File("XMLF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();


        boolean exit = false;
        boolean ok = false;
        Scanner sc = new Scanner(System.in);


        while (!exit) {
            System.out.println("Válasszon!");
            System.out.println("0. Kilépés");
            System.out.println("1. Elérhető futárok");
            System.out.println("2. MasterCard kártyák adatai");
            System.out.println("3. Egy elem");
            int mode = sc.nextInt();
            sc.nextLine();
    		System.out.println();
            switch (mode){
                case 0:
                    exit=true;
                    break;
                case 1:
                	getElerheto(document);
                    break;
                case 2:
                	getMaster(document);
                    break;
                case 3:
                	ok=false;
                	//ELőször kiválaszttjuk, hogy melyik elemet akarjuk lekérdezni
                	List<String> uniqueElements = getUniqueNodes(document);
                	String selected ="";
                	while(!ok) {

                    	System.out.println("Melyiket szeretné lekérdezni?");
                    	for (String string : uniqueElements) {
    						System.out.println(string);
    					}
                    	selected = sc.nextLine();
                    	
                    	if(uniqueElements.contains(selected)) {
                    		ok=true;
                    	}else {
                    		System.out.println("Nincs ilyen elem!");
                    	}
                	}
            		System.out.println();
                	ok=false;
                	
                	//normál id (rendelés id RT estén)
                	id = "";
                	Tid =""; 
                	//Bekérjük az id-t
                	attributeName =selected.toLowerCase()+"ID";
                	if(selected.equalsIgnoreCase("Cím")) {
                		attributeName = "vevőID";
                	}
                	if(selected.equalsIgnoreCase("Bankkártya")) {
                		attributeName = "kártyaszám";
                	}

                	if(!selected.equalsIgnoreCase("RT")) {
                    	while(!ok) {
                    		System.out.println("Adja meg az id-t: ");
                        	List<String> uniqueId = getUniqueIDs(document,attributeName, document.getElementsByTagName(selected));
                        	for (String string : uniqueId) {
        						System.out.println(string);
        					}

                        	id = sc.nextLine();

                        	if(uniqueId.contains(id)) {
                        		ok=true;
                        	}else {
                        		System.out.println("Nincs ilyen ID");
                        	}
                    		System.out.println();
                        	//Kiíratjuk az elemet
                        	printNode(getNode(document,attributeName, document.getElementsByTagName(selected),id));
                    	}
                	}else {
                    	while(!ok) {
                    		System.out.println("Adja meg az rendelésID-t: ");
                        	List<String> uniqueId = getUniqueIDs(document,"rendelésID", document.getElementsByTagName(selected));
                        	for (String string : uniqueId) {
        						System.out.println(string);
        					}

                        	id = sc.nextLine();

                        	if(uniqueId.contains(id)) {
                        		ok=true;
                        	}else {
                        		System.out.println("Nincs ilyen ID");
                        	}
                    	}
                		System.out.println();
                    	ok=false;
                    	while(!ok) {
                    		System.out.println("Adja meg a termékID-t: ");
                        	List<String> uniqueId = getUniqueTIDs(document,"termékID", document.getElementsByTagName(selected),id);
                        	for (String string : uniqueId) {
        						System.out.println(string);
        					}

                        	Tid = sc.nextLine();

                        	if(uniqueId.contains(Tid)) {
                        		ok=true;
                        	}else {
                        		System.out.println("Nincs ilyen ID");
                        	}
                    	}
                		System.out.println();
                    	//Kiíratjuk az elemet RT
                    	printNode(getNodeRT(document, document.getElementsByTagName(selected),id,Tid));
                	}

                	
                	

                	
                	
                	
                    break;
            }
        }
        sc.close();
    }
    
    private static void getElerheto(Document doc) {
    	NodeList futarok = doc.getElementsByTagName("Futár");
    	System.out.println("Elérhető futárok: ");
    	
    	for(int i =0;i<futarok.getLength();i++) {
    		NodeList childs = futarok.item(i).getChildNodes();
    		
    		for(int j=0;j<childs.getLength();j++) {
    			if(childs.item(j).getNodeName().equalsIgnoreCase("elérhető") && childs.item(j).getTextContent().equalsIgnoreCase("1") ) {
    				printNode(futarok.item(i));
    				System.out.println();
    			}
    		}
    		
    		
    	}
    	
    	
    }
    private static void getMaster(Document doc) {
    	NodeList kartyak = doc.getElementsByTagName("Bankkártya");
    	System.out.println("MasterCard Kártyák: ");
    	
    	for(int i =0;i<kartyak.getLength();i++) {
    		NodeList childs = kartyak.item(i).getChildNodes();
    		
    		for(int j=0;j<childs.getLength();j++) {
    			if(childs.item(j).getNodeName().equalsIgnoreCase("típus") && childs.item(j).getTextContent().equalsIgnoreCase("Mastercard") ) {
    				printNode(kartyak.item(i));
    				System.out.println();
    			}
    		}
    		
    		
    	}
    	
    	
    }
    
  //Kiíratjuk a consolra a node értékeit
    private static void printNode(Node node) {
    	String frow = node.getNodeName()+" ";
    	
    	NodeList childs = node.getChildNodes();
    	
    	
        for (int j = 0; j < node.getAttributes().getLength(); j++) 
        {
        	Node attribute = node.getAttributes().item(j);
        	frow+=attribute.getNodeName() + ": " + attribute.getNodeValue()+" ";            
        }
    	System.out.print(frow);
    	
    	
    	for(int i = 0 ; i<childs.getLength();i++) {
    		if(childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
    			System.out.print("\n"+childs.item(i).getNodeName()+": "+childs.item(i).getTextContent());
    		}
    	}
    	
    	System.out.println();
    }
    
    
    
  //Ezzel kapjuk meg a node-ot
    private static Node getNode(Document doc,String attributeName, NodeList nodes,String Id) {
    	
    	
    	
        for(int i =0; i<nodes.getLength();i++) {
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		NamedNodeMap attributes = nodes.item(i).getAttributes();
        		if(attributes.getNamedItem(attributeName).getTextContent().equalsIgnoreCase(Id)) {
        			return nodes.item(i);
        		}
        		
        	}
        }
        
        return null;

    }
    
    
    //Ezzel kapjuk meg a node-ot az RT-hez
    private static Node getNodeRT(Document doc,NodeList nodes,String Id,String Tid) {
    	
    	
    	
        for(int i =0; i<nodes.getLength();i++) {
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		NamedNodeMap attributes = nodes.item(i).getAttributes();
        		if(attributes.getNamedItem("termékID").getTextContent().equalsIgnoreCase(Tid) && attributes.getNamedItem("rendelésID").getTextContent().equalsIgnoreCase(Id)) {
        			return nodes.item(i);
        		}
        		
        	}
        }
        
        return null;

    }
    
    
    
    //Ezzel megkapjuk az egyedi node-ok nevét
    private static List<String> getUniqueNodes(Document doc) {
    	
        List<String> uniqueElements = new ArrayList<>();
        Set<String> seenElements = new HashSet<>();
    	
        Node root = doc.getDocumentElement();
        
        
        NodeList childs = root.getChildNodes();
        
        for(int i=0 ; i<childs.getLength();i++) {
        	Node actualChild = childs.item(i);
        	
        	if(!seenElements.contains(actualChild.getNodeName()) && childs.item(i).getNodeType() == Node.ELEMENT_NODE ) {
        		seenElements.add(actualChild.getNodeName());
        		uniqueElements.add(actualChild.getNodeName());
        	}
        }
        
        return uniqueElements;

    }
    
    
    //Id-k megkeresése
    private static List<String> getUniqueIDs(Document doc,String attributeName, NodeList nodes) {
    	
        List<String> uniqueElements = new ArrayList<>();
        Set<String> seenElements = new HashSet<>();
    	
        for(int i =0; i<nodes.getLength();i++) {
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		NamedNodeMap attributes = nodes.item(i).getAttributes();
        		if(!seenElements.contains(attributes.getNamedItem(attributeName).getTextContent())) {
        			seenElements.add(attributes.getNamedItem(attributeName).getTextContent());
        			uniqueElements.add(attributes.getNamedItem(attributeName).getTextContent());
        		}
        		
        	}
        }
        
        return uniqueElements;

    }
    
    
    //Termék id-k megkeresése
    private static List<String> getUniqueTIDs(Document doc,String attributeName, NodeList nodes,String id) {
    	
        List<String> uniqueElements = new ArrayList<>();
        Set<String> seenElements = new HashSet<>();
    	
        for(int i =0; i<nodes.getLength();i++) {
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		NamedNodeMap attributes = nodes.item(i).getAttributes();
        		if(!seenElements.contains(attributes.getNamedItem(attributeName).getTextContent())&& attributes.getNamedItem("rendelésID").getTextContent().equalsIgnoreCase(id)) {
        			seenElements.add(attributes.getNamedItem(attributeName).getTextContent());
        			uniqueElements.add(attributes.getNamedItem(attributeName).getTextContent());
        		}
        		
        	}
        }
        
        return uniqueElements;

    }
    
    
    
}
