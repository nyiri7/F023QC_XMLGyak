package hu.domparse.F023QC;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import java.io.FileWriter;
import java.util.ArrayList;


public class DOMWriteF023QC {
	public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLF023QC.xml");
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = factory.newDocumentBuilder();;
    		Document document = dBuilder.parse(xmlFile);
    		document.getDocumentElement().normalize();
    		
    		Document newDocument = dBuilder.newDocument();
            Element rootElement = newDocument.createElement("F023QC_rendelesek");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaF023QC.xsd");
            newDocument.appendChild(rootElement);
    		
            addRendeles(newDocument,rootElement,getRendeles(document.getElementsByTagName("Rendelés")));
            addEtterem(newDocument, rootElement, getEtterem(document.getElementsByTagName("Étterem")));
            addFutar(newDocument, rootElement, getFutar(document.getElementsByTagName("Futár")));
            addRT(newDocument, rootElement, getRT(document.getElementsByTagName("RT")));
            addTermek(newDocument, rootElement, getTermek(document.getElementsByTagName("Termék")));
            addKartya(newDocument, rootElement, getKartya(document.getElementsByTagName("Bankkártya")));
            addCim(newDocument, rootElement, getCim(document.getElementsByTagName("Cím")));
            addVevo(newDocument, rootElement, getVevo(document.getElementsByTagName("Vevő")));
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");
            FileWriter fileWriter = new FileWriter("XMLF023QC1.xml");
            transformer.transform(new DOMSource(newDocument), new StreamResult(fileWriter));
	        transformer.transform(new DOMSource(newDocument), new StreamResult(System.out));

    		
        } catch (Exception e) {
            System.out.println("Hiba történt: " + e.getMessage());
        }
    }
		
	private static void addRendeles(Document document, Element rootElement,ArrayList<Rendeles> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element rendeles = document.createElement("Rendelés");
			rendeles.setAttribute("rendelésID", nodes.get(i).getRendelesID());
			rendeles.setAttribute("étteremID", nodes.get(i).getEtteremID());
			
			Element megjegyzes = document.createElement("megjegyzés");
			megjegyzes.setTextContent(nodes.get(i).getMegjegyzes());
			rendeles.appendChild(megjegyzes);
			
			Element ideje = document.createElement("rendelés_ideje");
			ideje.setTextContent(nodes.get(i).getRendelesI());
			rendeles.appendChild(ideje);
			
			Element teljesar = document.createElement("teljes_ár");
			teljesar.setTextContent(nodes.get(i).getTeljesAr());
			rendeles.appendChild(teljesar);
			
			rootElement.appendChild(rendeles);
		}
	}
	
	private static void addEtterem(Document document, Element rootElement,ArrayList<Etterem> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element etterem = document.createElement("Étterem");
			etterem.setAttribute("étteremID", nodes.get(i).getEtteremID());
			
			Element cim = document.createElement("cím");
			cim.setTextContent(nodes.get(i).getCim());
			etterem.appendChild(cim);
			for(int j = 0;j<nodes.get(i).getNyitvatartas().size();j++) {
				Element nyitva = document.createElement("nyitvatartás");
				nyitva.setTextContent(nodes.get(i).getNyitvatartas().get(j));
				etterem.appendChild(nyitva);
			}
			
			Element elerhetoseg = document.createElement("elérhetőség");
			elerhetoseg.setTextContent(nodes.get(i).getElerhetoseg());
			etterem.appendChild(elerhetoseg);
			
			
			rootElement.appendChild(etterem);
		}
	}
	
	private static void addFutar(Document document, Element rootElement,ArrayList<Futar> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element futar = document.createElement("Futár");
			futar.setAttribute("futárID", nodes.get(i).getFutarID());
			futar.setAttribute("étteremID", nodes.get(i).getEtteremID());
			
			
			Element nev = document.createElement("név");
			nev.setTextContent(nodes.get(i).getNev());
			futar.appendChild(nev);
			
			Element tel = document.createElement("telefonszám");
			tel.setTextContent(nodes.get(i).getTelefonszam());
			futar.appendChild(tel);
			
			Element elerheto = document.createElement("elérhető");
			elerheto.setTextContent(nodes.get(i).getElerheto());
			futar.appendChild(elerheto);
			
			Element tipus = document.createElement("típus");
			tipus.setTextContent(nodes.get(i).getTipus());
			futar.appendChild(tipus);
			
			rootElement.appendChild(futar);
		}
	}
	private static void addRT(Document document, Element rootElement,ArrayList<RT> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element rt = document.createElement("RT");
			rt.setAttribute("rendelésID", nodes.get(i).getRendelesID());
			rt.setAttribute("termékID", nodes.get(i).getTermekID());
			
			Element menny = document.createElement("mennyiség");
			menny.setTextContent(nodes.get(i).getMennyiseg());
			rt.appendChild(menny);
			
			for(int j = 0;j<nodes.get(i).getNk_osszetevo().size();j++) {
				Element nk = document.createElement("nk_összetevő");
				nk.setTextContent(nodes.get(i).getNk_osszetevo().get(j));
				rt.appendChild(nk);
			}
			

			
			
			rootElement.appendChild(rt);
		}
	}
	
	private static void addTermek(Document document, Element rootElement,ArrayList<Termek> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element termek = document.createElement("Termék");
			termek.setAttribute("termékID", nodes.get(i).getTermekID());
			
			Element nev = document.createElement("név");
			nev.setTextContent(nodes.get(i).getNev());
			termek.appendChild(nev);
			
			Element ar = document.createElement("ár");
			ar.setTextContent(nodes.get(i).getAr());
			termek.appendChild(ar);
			
			Element ido = document.createElement("elkészítési_idő");
			ido.setTextContent(nodes.get(i).getElkeszitesiI());
			termek.appendChild(ido);
			
			System.out.println(nodes.get(i).getOsszetevok().size());
			for(int j = 0;j<nodes.get(i).getOsszetevok().size();j++) {
				Element osszetevo = document.createElement("összetevők");
				osszetevo.setTextContent(nodes.get(i).getOsszetevok().get(j));
				termek.appendChild(osszetevo);
			}

			rootElement.appendChild(termek);
		}
	}
	
	private static void addKartya(Document document, Element rootElement,ArrayList<Bankkartya> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element kartya = document.createElement("Bankkártya");
			kartya.setAttribute("kártyaszám", nodes.get(i).getKartyaszam());
			kartya.setAttribute("vevőID", nodes.get(i).getVevoId());
			
			Element nev = document.createElement("kártyán_szereplő_név");
			nev.setTextContent(nodes.get(i).getKartyanSzereploNev());
			kartya.appendChild(nev);
			
			Element datum = document.createElement("lejárati_dátum");
			datum.setTextContent(nodes.get(i).getLejaratiDatum());
			kartya.appendChild(datum);
			
			Element kod = document.createElement("biztonsági_kód");
			kod.setTextContent(nodes.get(i).getBiztonsagiKod());
			kartya.appendChild(kod);
			
			Element tipus = document.createElement("típus");
			tipus.setTextContent(nodes.get(i).getTipus());
			kartya.appendChild(tipus);
			
			

			rootElement.appendChild(kartya);
		}
	}
	private static void addCim(Document document, Element rootElement,ArrayList<Cim> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element cim = document.createElement("Cím");
			cim.setAttribute("vevőID", nodes.get(i).getVevoId());
			
			Element irszam = document.createElement("irányítószám");
			irszam.setTextContent(nodes.get(i).getIranyitoszam());
			cim.appendChild(irszam);
			
			Element neve = document.createElement("közterület_neve");
			neve.setTextContent(nodes.get(i).getKozTNeve());
			cim.appendChild(neve);
			
			Element megnevezes = document.createElement("közterület_megnevezése");
			megnevezes.setTextContent(nodes.get(i).getKozTMegnev());
			cim.appendChild(megnevezes);
			
			Element hazszam = document.createElement("házszám");
			hazszam.setTextContent(nodes.get(i).getHazszam());
			cim.appendChild(hazszam);
			
			if(nodes.get(i).getEmeletAjto()!= null) {
				Element ajto = document.createElement("emelet_ajtó");
				ajto.setTextContent(nodes.get(i).getEmeletAjto());
				cim.appendChild(ajto);
			}
			

			
			

			rootElement.appendChild(cim);
		}
	}
	
	private static void addVevo(Document document, Element rootElement,ArrayList<Vevo> nodes) {
		for(int i =0;i<nodes.size();i++) {
			Element vevo = document.createElement("Vevő");
			vevo.setAttribute("vevőID", nodes.get(i).getVevoId());
			vevo.setAttribute("rendelésID", nodes.get(i).getRendelesId());
			
			Element nev = document.createElement("név");
			nev.setTextContent(nodes.get(i).getNev());
			vevo.appendChild(nev);
			
			Element tel = document.createElement("telefonszám");
			tel.setTextContent(nodes.get(i).getTelefonszam());
			vevo.appendChild(tel);

			rootElement.appendChild(vevo);
		}
	}
	
	private static ArrayList<Rendeles> getRendeles(NodeList nodes) {
		ArrayList<Rendeles> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Rendeles rendeles = new Rendeles();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			rendeles.setRendelesID(attributes.getNamedItem("rendelésID").getTextContent());
			rendeles.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());
			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					
					if(childs.item(j).getNodeName().equalsIgnoreCase("megjegyzés")) {
						rendeles.setMegjegyzes(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("rendelés_ideje")) {
						rendeles.setRendelesI(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("teljes_ár")) {
						rendeles.setTeljesAr(childs.item(j).getTextContent());
					}
				}
			}
			
			ret.add(rendeles);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Etterem> getEtterem(NodeList nodes) {
		ArrayList<Etterem> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Etterem etterem = new Etterem();
			ArrayList<String> nyitvatartasok = new ArrayList<String>();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			etterem.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());
			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					if(childs.item(j).getNodeName().equalsIgnoreCase("cím")) {
						etterem.setCim(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("nyitvatartás")) {
						nyitvatartasok.add(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("elérhetőség")) {
						etterem.setElerhetoseg(childs.item(j).getTextContent());
					}
				}
			}
			
			etterem.setNyitvatartas(nyitvatartasok);
			ret.add(etterem);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Futar> getFutar(NodeList nodes) {
		ArrayList<Futar> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Futar futar = new Futar();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			futar.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());
			futar.setFutarID(attributes.getNamedItem("futárID").getTextContent());
			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					
					if(childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						futar.setNev(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("telefonszám")) {
						futar.setTelefonszam(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("elérhető")) {
						futar.setElerheto(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("típus")) {
						futar.setTipus(childs.item(j).getTextContent());
					}

				}
			}
			
			ret.add(futar);
			
			
		}
		return ret;
	}
	
	private static ArrayList<RT> getRT(NodeList nodes) {
		ArrayList<RT> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			RT rt = new RT();
			ArrayList<String> nkosszetevok = new ArrayList<String>();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			rt.setRendelesID(attributes.getNamedItem("rendelésID").getTextContent());
			rt.setTermekID(attributes.getNamedItem("termékID").getTextContent());
			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					if(childs.item(j).getNodeName().equalsIgnoreCase("mennyiség")) {
						rt.setMennyiseg(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("nk_összetevő")) {
						nkosszetevok.add(childs.item(j).getTextContent());
					}

				}
			}
			
			rt.setNk_osszetevo(nkosszetevok);
			ret.add(rt);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Termek> getTermek(NodeList nodes) {
		ArrayList<Termek> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Termek termek = new Termek();
			ArrayList<String> osszetevok = new ArrayList<String>();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			termek.setTermekID(attributes.getNamedItem("termékID").getTextContent());
			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					if(childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						termek.setNev(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("ár")) {
						termek.setAr(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("elkészítési_idő")) {
						termek.setElkeszitesiI(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("összetevők")) {
						osszetevok.add(childs.item(j).getTextContent());
					}

				}
			}
			
			termek.setOsszetevok(osszetevok);
			ret.add(termek);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Bankkartya> getKartya(NodeList nodes) {
		ArrayList<Bankkartya> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Bankkartya bankkartya = new Bankkartya();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			bankkartya.setKartyaszam(attributes.getNamedItem("kártyaszám").getTextContent());
			bankkartya.setVevoId(attributes.getNamedItem("vevőID").getTextContent());

			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					
					if(childs.item(j).getNodeName().equalsIgnoreCase("kártyán_szereplő_név")) {
						bankkartya.setKartyanSzereploNev(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("lejárati_dátum")) {
						bankkartya.setLejaratiDatum(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("biztonsági_kód")) {
						bankkartya.setBiztonsagiKod(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("típus")) {
						bankkartya.setTipus(childs.item(j).getTextContent());
					}

				}
			}
			
			ret.add(bankkartya);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Cim> getCim(NodeList nodes) {
		ArrayList<Cim> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Cim cim = new Cim();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			cim.setVevoId(attributes.getNamedItem("vevőID").getTextContent());

			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					
					if(childs.item(j).getNodeName().equalsIgnoreCase("irányítószám")) {
						cim.setIranyitoszam(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("közterület_neve")) {
						cim.setKozTNeve(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("közterület_megnevezése")) {
						cim.setKozTMegnev(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("házszám")) {
						cim.setHazszam(childs.item(j).getTextContent());

					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("emelet_ajtó")) {
						cim.setEmeletAjto(childs.item(j).getTextContent());
					}

				}
			}
			
			ret.add(cim);
			
			
		}
		return ret;
	}
	
	private static ArrayList<Vevo> getVevo(NodeList nodes) {
		ArrayList<Vevo> ret = new ArrayList<>();
		for(int i =0; i<nodes.getLength();i++) {
			Vevo vevo = new Vevo();
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			vevo.setVevoId(attributes.getNamedItem("vevőID").getTextContent());
			vevo.setRendelesId(attributes.getNamedItem("rendelésID").getTextContent());

			
			
			NodeList childs = nodes.item(i).getChildNodes();
			
			for(int j =0; j<childs.getLength();j++) {
				if(childs.item(j).getNodeType()==Node.ELEMENT_NODE) {
					
					if(childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						vevo.setNev(childs.item(j).getTextContent());
					}
					if(childs.item(j).getNodeName().equalsIgnoreCase("telefonszám")) {
						vevo.setTelefonszam(childs.item(j).getTextContent());
					}

				}
			}
			
			ret.add(vevo);
			
			
		}
		return ret;
	}

}





