package xpathnF023QC;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class xPathF023QC {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        File xmlFile = new File("studentF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();


        NodeList nodeList = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();



        //String xpathExpression = "/class/student";
        //String xpathExpression = "/class/student[@id ='2']";
        //String xpathExpression = "//student";
        //String xpathExpression = "/class//student[2]";
        //String xpathExpression = "/class/student[last()]";
        //String xpathExpression = "/class/student[last()-1]";
        //String xpathExpression = "/class/student[position() <= 2]";
        //String xpathExpression = "/class/*";
        //String xpathExpression = "//student[@*]";
        //String xpathExpression = "//*";
        //String xpathExpression = "/class/student[kor > 20]";
        String xpathExpression = "//keresztnev | //vezeteknev";




        XPathExpression expr = xpath.compile(xpathExpression);
        nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        for(int i= 0; i<nodeList.getLength();i++){
            Node actualNode = nodeList.item(i);
            System.out.println(actualNode.getNodeName()+": "+actualNode.getTextContent());
        }


        //printElements(nodeList);

    }


    private static void printElements(NodeList NodeList){
    	if(NodeList.getLength()>0) {
            for(int i = 0; i<NodeList.getLength();i++){
                String fRow = "\n";
                Node actualNode = NodeList.item(i);
                fRow+="<"+actualNode.getNodeName()+" ";

                Element actualElement = (Element) actualNode;

    	        for (int j = 0; j < actualElement.getAttributes().getLength(); j++) 
    	        {
    	        	Node attribute = actualElement.getAttributes().item(j);
                    if(j==actualElement.getAttributes().getLength()-1){
                        fRow+=attribute.getNodeName() + "=\"" + attribute.getNodeValue()+"\">";
                    }else{
                        fRow+=attribute.getNodeName() + "=\"" + attribute.getNodeValue()+"\" ";
                    }
                    
    	        }
                System.out.println(fRow);

                NodeList actualChilds = actualElement.getChildNodes();
                if(actualChilds.getLength()>1){
                    for (int j = 0; j < actualChilds.getLength(); j++)
                    {
                        Node actualChild = actualChilds.item(j);
                        if (actualChild.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            
                            System.out.println("\t<"+actualChild.getNodeName() + ">" + actualChild.getTextContent()+"</"+actualChild.getNodeName() + ">");
                                
                        }
                        
                    }
                }else{
                    System.out.println(actualNode.getTextContent());
                }

                System.out.println("</"+actualNode.getNodeName()+">");
            }
    	}else {
    		System.out.println("Nincs ilyen elem!");
    	}

    }
}
