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
import org.w3c.dom.Node;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DOMWriteF023QC {
	public static void main(String[] args) {
        try {
        	//Új XML fájl létrehozása
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = factory.newDocumentBuilder();;
    		Document newDocument = dBuilder.newDocument();
    		
    		//A gyökérelem létrehozása és hozzáadása a dokumentumhoz
            Element rootElement = newDocument.createElement("F023QC_rendelesek");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaF023QC.xsd");
            newDocument.appendChild(rootElement);
            
            //Rendelések hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Rendelések"));
            addRendeles(newDocument, rootElement, "1","1","A kutya harap!","12:00","15000");
            addRendeles(newDocument, rootElement, "2","1","","14:32","3000");
            addRendeles(newDocument, rootElement, "3","2","Ételalergia","14:32","3700");
            addRendeles(newDocument, rootElement, "4","3","Ételalergia","14:32","10000");
            
            //Éttermek hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Éttermek"));
            addEtterem(newDocument, rootElement, "1","3528 Miskolc Csokonai Vitéz Mihály utca 40.","Hétfő 11:00-19:00,Kedd 11:00-19:00,Szerda 11:00-19:00,Péntek 11:00-19:00","csarda@etterem.hu");
            addEtterem(newDocument, rootElement, "2","3525 Miskolc Szemere Bertalan Utca 3.","Hétfő 7:00-18:00,Kedd 7:00-18:00,Szerda 7:00-18:00,Csütörtök 7:00-18:00,Péntek 7:00-18:00,Szombat 8:00-18:00,Vasárnap 10:00-15:00","https://falankfanny.hu/");
            addEtterem(newDocument, rootElement, "3","3526 Miskolc, Petőfi Tér 1.","Hétfő 8:00-23:00,Kedd 8:00-23:00,Szerda 8:00-23:00,Csütörtök 8:00-23:00,Péntek 8:00-23:00","http://www.barkafood.hu/");
            
            //Futárok hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Futárok"));
            addFutar(newDocument, rootElement, "1","1","Nagy Milán","0630 320 4870","0","Biciklis");
            addFutar(newDocument, rootElement, "2","1","Kovács Aladár","0630 560 8893","0","Autós");
            addFutar(newDocument, rootElement, "3","1","Kiss Béla","0630 632 4856","1","Autós");
            addFutar(newDocument, rootElement, "4","2","Varga Roland","0620 569 4236","1","Rolleres");
            addFutar(newDocument, rootElement, "5","2","Kovács Péter","0646 329 4568","0","Biciklis");
            addFutar(newDocument, rootElement, "6","3","Szabó Éva","0620 563 7821","1","Autós");
            
            //Több-több kapcsolat hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Rendelés - Termék kapcsolat"));
            addRT(newDocument, rootElement, "1","1","1","Répa");
            addRT(newDocument, rootElement, "1","3","4","");
            addRT(newDocument, rootElement, "1","5","1","Porcukor");
            addRT(newDocument, rootElement, "2","1","1","Zeller");
            addRT(newDocument, rootElement, "2","5","1","Porcukor");
            addRT(newDocument, rootElement, "3","2","5","Liszt,Vanília Kivonat");
            addRT(newDocument, rootElement, "3","4","4","");
            addRT(newDocument, rootElement, "4","5","5","Só,Porcukor,Tejföl");
            
            //Termékek hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Termékek"));
            addTermek(newDocument, rootElement, "1","Húsleves","1000","30 perc","sárgarépa,fehérrépa,karalábé,zeller,vöröshagyma,paprika,petrezselyem,só,bors,tészta");
            addTermek(newDocument, rootElement, "2","Brownie","500","15 perc","liszt,só,kakaópor,étcsokoládé,vaj,nutella,tojás,cukor,vanília kivonat");
            addTermek(newDocument, rootElement, "3","Omlós libacomb","4000","130 perc","libacomb,tengeri só");
            addTermek(newDocument, rootElement, "4","Muffin","300","30 perc","tojás,cukor,vaj,tej,liszt,só,sütőpor,vaníliás cukor");
            addTermek(newDocument, rootElement, "5","Túrógombóc","2000","25 perc","túró,tojás,búzadara,só,szódabikarbóna,zsemlemorzsa,olaj,porcukor,tejföl");
            
            //Bankkártyák hozzáadása a kommenttel
            rootElement.appendChild(newDocument.createComment("Bankkártyák"));
            addKartya(newDocument, rootElement, "1174859659985563","1","Nyíri Levente","12/26","056","Mastercard");
            addKartya(newDocument, rootElement, "1174859655585888","1","Nyíri Levente","05/28","356","Visa");
            addKartya(newDocument, rootElement, "1174865851185111","1","Nyíri Levente","12/23","111","Mastercard");
            addKartya(newDocument, rootElement, "1176834769216856","2","Nagyné Kovács Rebeka","12/23","111","Visa");
            addKartya(newDocument, rootElement, "1179863452169933","2","Nagy Balázs","02/27","986","Visa");
            addKartya(newDocument, rootElement, "1175453336278779","3","Kis István","01/29","494","Mastercard");
            addKartya(newDocument, rootElement, "1171154896532478","4","Varga Iván","01/24","555","Visa");
            
            //Vevők hozzáadása a kommenttel, valamint a cím lérehozása és hozzáadása a vevőhöz
            rootElement.appendChild(newDocument.createComment("Vevők"));
            addVevo(newDocument, rootElement, "1","1","Nyíri Levente","0620 589 9938",addCim(newDocument, "3528","Csokonai Vitéz Mihály","utca","11",""));
            addVevo(newDocument, rootElement, "2","2","Nagy Balázs","0630 597 6413",addCim(newDocument, "3535","Kuruc","utca","23","5/1"));
            addVevo(newDocument, rootElement, "3","3","Kis István","0620 365 5876",addCim(newDocument, "3534","Nagy Lajos király","út","30",""));
            addVevo(newDocument, rootElement, "4","4","Varga Iván","0630 879 1298",addCim(newDocument, "3531","Aba","utca","2","3/4"));
            
            
            //Kiíratás fájlba és a konzolra
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
	
	//Függvények, amelyekkel új elemeket hozhatunk létre
	
	//Rendelés létrehozása
	private static void addRendeles(Document document, Element rootElement,String rendelesID,String etteremID,String megjegyz,String ido,String ar) {
		//Létrehozom a rendelést és beállítom az attribútumait
		Element rendeles = document.createElement("Rendelés");
		rendeles.setAttribute("rendelésID", rendelesID );
		rendeles.setAttribute("étteremID", etteremID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott rendeléshez
		
		//A rendelésnek nem feltétlenül kell tartalmazi megjegyzést, ezért ha azt nem adunk meg, akkor nem hozunk létre gyerekelemet
		if(!megjegyz.equalsIgnoreCase("")) {
			Element megjegyzes = document.createElement("megjegyzés");
			megjegyzes.setTextContent(megjegyz);
			rendeles.appendChild(megjegyzes);
		}

		
		Element ideje = document.createElement("rendelés_ideje");
		ideje.setTextContent(ido);
		rendeles.appendChild(ideje);
		
		Element teljesar = document.createElement("teljes_ár");
		teljesar.setTextContent(ar);
		rendeles.appendChild(teljesar);
		
		//Hozzáadjuk a rendelést a gyökérelemhez
		rootElement.appendChild(rendeles);
	}
	
	//Étterem létrehozása
	private static void addEtterem(Document document, Element rootElement,String etteremID,String cimin,String nyitva,String elerhet) {
		//Létrehozom az éttermet és beállítom az attribútumait
		Element etterem = document.createElement("Étterem");
		etterem.setAttribute("étteremID", etteremID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozot étteremhez
		Element cim = document.createElement("cím");
		cim.setTextContent(cimin);
		etterem.appendChild(cim);
		
		//A nyitvatartás többször is előfordulhat az étterem elemben Stringként adom meg ,-vel elválasztva, ezért először listává alakítom
		List<String> nyitvatartasok = new ArrayList<String>(Arrays.asList(nyitva.split(",")));
		
		//Végigmegyek a listán, és az értékeknek megfelelően létrehozom a nyitvatartást
		for (String string : nyitvatartasok) {
			Element nyitvat = document.createElement("nyitvatartás");
			nyitvat.setTextContent(string);
			etterem.appendChild(nyitvat);
		}
		
		Element elerhetoseg = document.createElement("elérhetőség");
		elerhetoseg.setTextContent(elerhet);
		etterem.appendChild(elerhetoseg);
		
		//Hozzáadom az éttermet a gyökérelemhez
		rootElement.appendChild(etterem);
	}
	
	//Futár létrehozása
	private static void addFutar(Document document, Element rootElement,String futarID,String etteremID,String nevin,String telefon,String elerhet,String tip) {
		//Létrehozom a futárt és beállítom az attribútumait
		Element futar = document.createElement("Futár");
		futar.setAttribute("futárID", futarID);
		futar.setAttribute("étteremID", etteremID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott futárhoz
		Element nev = document.createElement("név");
		nev.setTextContent(nevin);
		futar.appendChild(nev);
		
		Element tel = document.createElement("telefonszám");
		tel.setTextContent(telefon);
		futar.appendChild(tel);
		
		Element elerheto = document.createElement("elérhető");
		elerheto.setTextContent(elerhet);
		futar.appendChild(elerheto);
		
		Element tipus = document.createElement("típus");
		tipus.setTextContent(tip);
		futar.appendChild(tipus);
		
		//hozzáadom a futárt a gyökérelemhez
		rootElement.appendChild(futar);
	}
	
	//N:M kapcsolat létrehozása
	private static void addRT(Document document, Element rootElement,String rendelesID,String termekID,String mennyiseg,String NK) {
		//Létrehozom a kapcsolat elemét és beállítom az attribútumait ezek alapján lehet beazonosítani, hogy melyik rendelésben milyen termék szerepel
		Element rt = document.createElement("RT");
		rt.setAttribute("rendelésID", rendelesID);
		rt.setAttribute("termékID", termekID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott RT kapcsolathoz
		Element menny = document.createElement("mennyiség");
		menny.setTextContent(mennyiseg);
		rt.appendChild(menny);
		
		//A nem kívánt összetevő olyan gyerekelem, amelynek nincs megszabva, hogy hányszor fordulhat elő,(0,1,2,3...) ezért először ellenőrzöm, hogy aakarunk e hozzáadni ilyen gyerekelemet az RT-hez
		if(!NK.equalsIgnoreCase("")) {
			//Ha akarunk, akkor a ,-k mentén listává alakítom, majd végigmegyek az értékekenés létrehozom a gyerekelemeket
			List<String> nkk = new ArrayList<String>(Arrays.asList(NK.split(",")));
			
			for (String string : nkk) {
				Element nk = document.createElement("nk_összetevő");
				nk.setTextContent(string);
				rt.appendChild(nk);
			}
		}
		

		//hozzáadom a gyökérelemhez
		rootElement.appendChild(rt);
	}
	
	//Termék létrehozása
	private static void addTermek(Document document, Element rootElement,String termekID,String neve,String ara,String elkido,String ossze) {
		//Létrehozom a terméket és beállítom az attribútumait
		Element termek = document.createElement("Termék");
		termek.setAttribute("termékID", termekID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott termékhez
		Element nev = document.createElement("név");
		nev.setTextContent(neve);
		termek.appendChild(nev);
		
		Element ar = document.createElement("ár");
		ar.setTextContent(ara);
		termek.appendChild(ar);
		
		Element ido = document.createElement("elkészítési_idő");
		ido.setTextContent(elkido);
		termek.appendChild(ido);
		
		//Az összetevő többször is előfordulhat a termék elemben Stringként adom meg ,-vel elválasztva, ezért először listává alakítom
		List<String> osszetevok = new ArrayList<String>(Arrays.asList(ossze.split(",")));
		
		//Ezután az értékeknek megfelelően létrehozom az elemeket
		for (String string : osszetevok) {
			Element osszetevo = document.createElement("összetevők");
			osszetevo.setTextContent(string);
			termek.appendChild(osszetevo);
		}
		
		//Hozzáadom a gyökérelemhez
		rootElement.appendChild(termek);
	}
	
	
	//Kártya létrehozása
	private static void addKartya(Document document, Element rootElement,String szam,String vevoID,String nevin,String lejaratidatum,String biztkod,String tip) {
		//Létrehozom a kártyát és beállítom az attribútumait
		Element kartya = document.createElement("Bankkártya");
		kartya.setAttribute("kártyaszám", szam);
		kartya.setAttribute("vevőID", vevoID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott kártyához
		Element nev = document.createElement("kártyán_szereplő_név");
		nev.setTextContent(nevin);
		kartya.appendChild(nev);
		
		Element datum = document.createElement("lejárati_dátum");
		datum.setTextContent(lejaratidatum);
		kartya.appendChild(datum);
		
		Element kod = document.createElement("biztonsági_kód");
		kod.setTextContent(biztkod);
		kartya.appendChild(kod);
		
		Element tipus = document.createElement("típus");
		tipus.setTextContent(tip);
		kartya.appendChild(tipus);
		
		
		//Hozzáadom a kártyát a gyokérelemhez
		rootElement.appendChild(kartya);
	}
	
	//Cím létrehozása
	//Nem void a visszatérési érték, hanem Node, mivel ezt majd hozzá fogjuk adni a vevőhöz
	private static Node addCim(Document document,String iranyszam,String kneve,String kmegnev,String hazsz,String emelet) {
		//Létrehozom a címet, ezzel fogok visszatérni
		Element cim = document.createElement("cím");
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket
		Element irszam = document.createElement("irányítószám");
		irszam.setTextContent(iranyszam);
		cim.appendChild(irszam);
		
		Element neve = document.createElement("közterület_neve");
		neve.setTextContent(kneve);
		cim.appendChild(neve);
		
		Element megnevezes = document.createElement("közterület_megnevezése");
		megnevezes.setTextContent(kmegnev);
		cim.appendChild(megnevezes);
		
		Element hazszam = document.createElement("házszám");
		hazszam.setTextContent(hazsz);
		cim.appendChild(hazszam);
		
		//Nem biztos,hogy a címben szerepel, hogy emelet/ajtó, ezért leellenőrzöm
		if(!emelet.equalsIgnoreCase("")) {
			Element ajto = document.createElement("emelet_ajtó");
			ajto.setTextContent(emelet);
			cim.appendChild(ajto);
		}
		

		//Visszatérek a Node-al
		return cim;

		
	}
	
	//Vevő létrehozása
	//A cím itt már Node-ként jön át, így azt csak hozzá kell adnunk a vevőhöz
	private static void addVevo(Document document, Element rootElement,String vevoID,String rendelesID,String neve,String telefon,Node cimin) {
		//Létrehozom a vevőt és beállítom az attribútumait
		Element vevo = document.createElement("Vevő");
		vevo.setAttribute("vevőID", vevoID);
		vevo.setAttribute("rendelésID", rendelesID);
		
		//Létrhozom a gyerekelemeket, majd beállítom a nevüket, ezután beállítom az értéküket, majd hozzáadom az újonnan létrehozott vevőhöz
		Element nev = document.createElement("név");
		nev.setTextContent(neve);
		vevo.appendChild(nev);
		
		Element tel = document.createElement("telefonszám");
		tel.setTextContent(telefon);
		vevo.appendChild(tel);
		
		//Hozzáadom a címet a vevőhöz
		vevo.appendChild(cimin);
		
		//Hozzáadom a vevőt a gyökérelemhez
		rootElement.appendChild(vevo);
	}
	

}





