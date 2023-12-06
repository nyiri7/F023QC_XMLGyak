package hu.domparse.F023QC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMReadF023QC {

	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {

		File xmlFile = new File("XMLF023QC.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();

		System.out.println("Gyökér elem: " + document.getDocumentElement().getNodeName());

		NodeList RendelesekList = document.getElementsByTagName("Rendelés");
		System.out.println("\n\t ----Rendelések----\n");
		ArrayList<Rendeles> rendelesek = getRendeles(RendelesekList);
		for(int i = 0;i<rendelesek.size();i++) {
			rendelesek.get(i).toPrint();
		}

		NodeList EtteremList = document.getElementsByTagName("Étterem");
		System.out.println("\n\t ----Éttermek----\n");
		ArrayList<Etterem> ettermek = getEtterem(EtteremList);

		for(int i = 0;i<ettermek.size();i++) {
			ettermek.get(i).toPrint();
		}
		
		NodeList FutarList = document.getElementsByTagName("Futár");
		System.out.println("\n\t ----Futárok----\n");
		ArrayList<Futar> futarok = getFutar(FutarList);
		
		for(int i = 0;i<futarok.size();i++) {
			futarok.get(i).toPrint();
		}
		
		
		NodeList RTList = document.getElementsByTagName("RT");
		System.out.println("----Rendelés - Termék kapcsolatok----\n");
		ArrayList<RT> rt = getRT(RTList);
		
		for(int i = 0;i<rt.size();i++) {
			rt.get(i).toPrint();
		}
		
		
		NodeList TermekList = document.getElementsByTagName("Termék");
		System.out.println("\n\t ----Termékek----\n");
		ArrayList<Termek> termekek = getTermek(TermekList);

		for(int i = 0;i<termekek.size();i++) {
			termekek.get(i).toPrint();
		}
		
		NodeList BKList = document.getElementsByTagName("Bankkártya");
		System.out.println("\n\t ----Bankkártyák----\n");
		ArrayList<Bankkartya> kartyak = getKartya(BKList);

		for(int i = 0;i<kartyak.size();i++) {
			kartyak.get(i).toPrint();
		}
		
		NodeList CimList = document.getElementsByTagName("Cím");
		System.out.println("\n\t ----Címek----\n");
		ArrayList<Cim> cimek = getCim(CimList);

		for(int i = 0;i<cimek.size();i++) {
			cimek.get(i).toPrint();
		}
		
		NodeList VevoList = document.getElementsByTagName("Vevő");
		System.out.println("\n\t ----Vevők----\n");
		ArrayList<Vevo> vevok = getVevo(VevoList);
		
		for(int i = 0;i<vevok.size();i++) {
			vevok.get(i).toPrint();
		}
	}



	//Getterek az egyes osztályokhoz
	private static ArrayList<Rendeles> getRendeles(NodeList nodes) {
		ArrayList<Rendeles> ret = new ArrayList<>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Rendeles rendeles = new Rendeles();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			rendeles.setRendelesID(attributes.getNamedItem("rendelésID").getTextContent());
			rendeles.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {

					if (childs.item(j).getNodeName().equalsIgnoreCase("megjegyzés")) {
						rendeles.setMegjegyzes(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("rendelés_ideje")) {
						rendeles.setRendelesI(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("teljes_ár")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Etterem etterem = new Etterem();
			ArrayList<String> nyitvatartasok = new ArrayList<String>();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			etterem.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childs.item(j).getNodeName().equalsIgnoreCase("cím")) {
						etterem.setCim(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("nyitvatartás")) {
						nyitvatartasok.add(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("elérhetőség")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Futar futar = new Futar();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			futar.setEtteremID(attributes.getNamedItem("étteremID").getTextContent());
			futar.setFutarID(attributes.getNamedItem("futárID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {

					if (childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						futar.setNev(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("telefonszám")) {
						futar.setTelefonszam(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("elérhető")) {
						futar.setElerheto(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("típus")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			RT rt = new RT();
			ArrayList<String> nkosszetevok = new ArrayList<String>();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			rt.setRendelesID(attributes.getNamedItem("rendelésID").getTextContent());
			rt.setTermekID(attributes.getNamedItem("termékID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childs.item(j).getNodeName().equalsIgnoreCase("mennyiség")) {
						rt.setMennyiseg(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("nk_összetevő")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Termek termek = new Termek();
			ArrayList<String> osszetevok = new ArrayList<String>();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			termek.setTermekID(attributes.getNamedItem("termékID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						termek.setNev(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("ár")) {
						termek.setAr(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("elkészítési_idő")) {
						termek.setElkeszitesiI(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("összetevők")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Bankkartya bankkartya = new Bankkartya();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			bankkartya.setKartyaszam(attributes.getNamedItem("kártyaszám").getTextContent());
			bankkartya.setVevoId(attributes.getNamedItem("vevőID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {

					if (childs.item(j).getNodeName().equalsIgnoreCase("kártyán_szereplő_név")) {
						bankkartya.setKartyanSzereploNev(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("lejárati_dátum")) {
						bankkartya.setLejaratiDatum(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("biztonsági_kód")) {
						bankkartya.setBiztonsagiKod(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("típus")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Cim cim = new Cim();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			cim.setVevoId(attributes.getNamedItem("vevőID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {

					if (childs.item(j).getNodeName().equalsIgnoreCase("irányítószám")) {
						cim.setIranyitoszam(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("közterület_neve")) {
						cim.setKozTNeve(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("közterület_megnevezése")) {
						cim.setKozTMegnev(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("házszám")) {
						cim.setHazszam(childs.item(j).getTextContent());

					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("emelet_ajtó")) {
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
		for (int i = 0; i < nodes.getLength(); i++) {
			Vevo vevo = new Vevo();

			NamedNodeMap attributes = nodes.item(i).getAttributes();
			vevo.setVevoId(attributes.getNamedItem("vevőID").getTextContent());
			vevo.setRendelesId(attributes.getNamedItem("rendelésID").getTextContent());

			NodeList childs = nodes.item(i).getChildNodes();

			for (int j = 0; j < childs.getLength(); j++) {
				if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {

					if (childs.item(j).getNodeName().equalsIgnoreCase("név")) {
						vevo.setNev(childs.item(j).getTextContent());
					}
					if (childs.item(j).getNodeName().equalsIgnoreCase("telefonszám")) {
						vevo.setTelefonszam(childs.item(j).getTextContent());
					}

				}
			}

			ret.add(vevo);

		}
		return ret;
	}

}

class Rendeles{
	private String megjegyzes;
	private String rendelesI;
	private String teljesAr;
	
	private String rendelesID;
	
	public void toPrint() {
		System.out.println("\t ---Rendelés---");
		System.out.println("\t Rendelés id-je: "+this.rendelesID);
		System.out.println("\t Étterem id-je: "+this.etteremID);
		System.out.println("\t Megjegyzes: "+this.megjegyzes);
		System.out.println("\t Rendelés ideje: "+this.rendelesI);
		System.out.println("\t Teljes ár: "+this.teljesAr);
		System.out.println();
		

	}
	
	public String getMegjegyzes() {
		return megjegyzes;
	}
	public void setMegjegyzes(String megjegyzes) {
		this.megjegyzes = megjegyzes;
	}
	public String getRendelesI() {
		return rendelesI;
	}
	public void setRendelesI(String rendelesI) {
		this.rendelesI = rendelesI;
	}
	public String getTeljesAr() {
		return teljesAr;
	}
	public void setTeljesAr(String teljesAr) {
		this.teljesAr = teljesAr;
	}
	public String getRendelesID() {
		return rendelesID;
	}
	public void setRendelesID(String rendelesID) {
		this.rendelesID = rendelesID;
	}
	public String getEtteremID() {
		return etteremID;
	}
	public void setEtteremID(String etteremID) {
		this.etteremID = etteremID;
	}
	private String etteremID;


}

class Etterem{
	private String cim;
	private ArrayList<String> nyitvatartas;
	private String elerhetoseg;

	private String etteremID;

	
	public void toPrint() {
		System.out.println("\t ---Étterem---");
		System.out.println("\t Étterem id-je: "+this.etteremID);
		System.out.println("\t Cím: "+this.cim);
		System.out.println("\t Elérhetőség: "+this.elerhetoseg);
		System.out.println("\t Nyitvatartás:");
		for (String string : this.nyitvatartas) {
			System.out.println("\t\t"+string);
		}
		System.out.println();
	}
	
	public String getCim() {
		return cim;
	}

	public void setCim(String cim) {
		this.cim = cim;
	}

	public ArrayList<String> getNyitvatartas() {
		return nyitvatartas;
	}

	public void setNyitvatartas(ArrayList<String> nyitvatartas) {
		this.nyitvatartas = nyitvatartas;
	}

	public String getElerhetoseg() {
		return elerhetoseg;
	}

	public void setElerhetoseg(String elerhetoseg) {
		this.elerhetoseg = elerhetoseg;
	}

	public String getEtteremID() {
		return etteremID;
	}

	public void setEtteremID(String etteremID) {
		this.etteremID = etteremID;
	}

}



class Futar{
	
	private String nev;
	private String telefonszam;
	private String elerheto;
	
	private String futarID;
	private String etteremID;
	private String tipus;
	
	public void toPrint() {
		System.out.println("\t ---Futár---");
		System.out.println("\t Futár id-je: "+this.futarID);
		System.out.println("\t Étterem id-je: "+this.etteremID);
		System.out.println("\t Név: "+this.nev);
		System.out.println("\t Telefonszám: "+this.telefonszam);
		System.out.println("\t Elérhető: "+this.elerheto);
		System.out.println("\t Típus: "+this.tipus);
		System.out.println();

	}
	
	public String getNev() {
		return nev;
	}
	public void setNev(String nev) {
		this.nev = nev;
	}
	public String getTelefonszam() {
		return telefonszam;
	}
	public void setTelefonszam(String telefonszam) {
		this.telefonszam = telefonszam;
	}
	public String getElerheto() {
		return elerheto;
	}
	public void setElerheto(String elerheto) {
		this.elerheto = elerheto;
	}
	public String getFutarID() {
		return futarID;
	}
	public void setFutarID(String futarID) {
		this.futarID = futarID;
	}
	public String getEtteremID() {
		return etteremID;
	}
	public void setEtteremID(String etteremID) {
		this.etteremID = etteremID;
	}

	public String getTipus() {
		return tipus;
	}
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}


}



class RT{
	
	
	private String mennyiseg;
	private ArrayList<String> nk_osszetevo;
	
	private String rendelesID;
	private String termekID;
	
	public void toPrint() {
		System.out.println("---Rendelés Termék kapcsolat---");
		System.out.println("\t Rendelés id-je: "+this.rendelesID);
		System.out.println("\t Termék id-je: "+this.termekID);
		System.out.println("\t Mennyiség: "+this.mennyiseg);
		System.out.print("\t Nem kívánt összetevők: ");
		for (String string : this.nk_osszetevo) {
			System.out.print(string+", ");
		}
		System.out.println("\n");

	}
	
	public String getMennyiseg() {
		return mennyiseg;
	}
	public void setMennyiseg(String mennyiseg) {
		this.mennyiseg = mennyiseg;
	}
	public ArrayList<String> getNk_osszetevo() {
		return nk_osszetevo;
	}
	public void setNk_osszetevo(ArrayList<String> nk_osszetevo) {
		this.nk_osszetevo = nk_osszetevo;
	}
	public String getRendelesID() {
		return rendelesID;
	}
	public void setRendelesID(String rendelesID) {
		this.rendelesID = rendelesID;
	}
	public String getTermekID() {
		return termekID;
	}
	public void setTermekID(String termekID) {
		this.termekID = termekID;
	}


}


class Termek{
	private String nev;
	private String ar;
	private String elkeszitesiI;
	private ArrayList<String> osszetevok;

	private String termekID;
	
	public void toPrint() {
		System.out.println("\t ---Termék---");
		System.out.println("\t Név: "+this.nev);
		System.out.println("\t Ár: "+this.ar);
		System.out.println("\t Elkészítési idő: "+this.elkeszitesiI);
		System.out.print("\t Összetevők: ");
		for (String string : this.osszetevok) {
			System.out.print(string+", ");
		}
		System.out.println("\n");

	}
	
	public String getNev() {
		return nev;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getElkeszitesiI() {
		return elkeszitesiI;
	}

	public void setElkeszitesiI(String elkeszitesiI) {
		this.elkeszitesiI = elkeszitesiI;
	}

	public ArrayList<String> getOsszetevok() {
		return osszetevok;
	}

	public void setOsszetevok(ArrayList<String> osszetevok) {
		this.osszetevok = osszetevok;
	}

	public String getTermekID() {
		return termekID;
	}

	public void setTermekID(String termekID) {
		this.termekID = termekID;
	}


}



class Bankkartya{
	
	private String kartyanSzereploNev;
	private String lejaratiDatum;
	private String biztonsagiKod;
	private String tipus;
	
	private String kartyaszam;
	private String vevoId;
	
	public void toPrint() {
		System.out.println("\t ---Bankkártya---");
		System.out.println("\t Vevő id-je: "+this.vevoId);
		System.out.println("\t Kártyaszám: "+this.kartyaszam);
		System.out.println("\t Kártyán szereplő név: "+this.kartyanSzereploNev);
		System.out.println("\t Lejárati dátum: "+this.lejaratiDatum);
		System.out.println("\t Biztonsági kód: "+this.biztonsagiKod);
		System.out.println("\t Típus: "+this.tipus);
		System.out.println();
	}
	
	public String getKartyanSzereploNev() {
		return kartyanSzereploNev;
	}
	public void setKartyanSzereploNev(String kartyanSzereploNev) {
		this.kartyanSzereploNev = kartyanSzereploNev;
	}
	public String getLejaratiDatum() {
		return lejaratiDatum;
	}
	public void setLejaratiDatum(String lejaratiDatum) {
		this.lejaratiDatum = lejaratiDatum;
	}
	public String getBiztonsagiKod() {
		return biztonsagiKod;
	}
	public void setBiztonsagiKod(String biztonsagiKod) {
		this.biztonsagiKod = biztonsagiKod;
	}
	public String getTipus() {
		return tipus;
	}
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}
	public String getKartyaszam() {
		return kartyaszam;
	}
	public void setKartyaszam(String kartyaszam) {
		this.kartyaszam = kartyaszam;
	}
	public String getVevoId() {
		return vevoId;
	}
	public void setVevoId(String vevoId) {
		this.vevoId = vevoId;
	}
	
}

class Cim{
	
	private String iranyitoszam;
	private String kozTNeve;
	private String kozTMegnev;
	private String hazszam;
	private String emeletAjto;
	
	private String vevoId;
	
	
	public void toPrint() {
		System.out.println("\t ---Cím---");
		System.out.println("\t Vevő id-je: "+this.vevoId);
		if(this.emeletAjto == null) {
			System.out.println("\t Cím: "+this.iranyitoszam+", "+this.kozTNeve+" "+this.kozTMegnev+" "+this.hazszam+".");
		}else {
			System.out.println("\t Cím: "+this.iranyitoszam+", "+this.kozTNeve+" "+this.kozTMegnev+" "+this.hazszam+". "+this.emeletAjto);
		}
		System.out.println();
	}

	public String getIranyitoszam() {
		return iranyitoszam;
	}

	public void setIranyitoszam(String iranyitoszam) {
		this.iranyitoszam = iranyitoszam;
	}

	public String getKozTNeve() {
		return kozTNeve;
	}

	public void setKozTNeve(String kozTNeve) {
		this.kozTNeve = kozTNeve;
	}

	public String getKozTMegnev() {
		return kozTMegnev;
	}

	public void setKozTMegnev(String kozTMegnev) {
		this.kozTMegnev = kozTMegnev;
	}

	public String getHazszam() {
		return hazszam;
	}

	public void setHazszam(String hazszam) {
		this.hazszam = hazszam;
	}

	public String getEmeletAjto() {
		return emeletAjto;
	}

	public void setEmeletAjto(String emeletAjto) {
		this.emeletAjto = emeletAjto;
	}

	public String getVevoId() {
		return vevoId;
	}

	public void setVevoId(String vevoId) {
		this.vevoId = vevoId;
	}
	
}


class Vevo{
	private String nev;
	private String telefonszam;
	
	private String vevoId;
	private String rendelesId;
	
	
	public void toPrint() {

		System.out.println("\t ---Vevő---");
		System.out.println("\t Vevő id-je: "+this.vevoId);
		System.out.println("\t Rendelés id-je: "+this.rendelesId);
		System.out.println("\t Név: "+this.telefonszam);
		System.out.println("\t Telefonszám: "+this.telefonszam);
		System.out.println();

	}
	
	public String getRendelesId() {
		return rendelesId;
	}

	public void setRendelesId(String rendelesId) {
		this.rendelesId = rendelesId;
	}

	public String getNev() {
		return nev;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}

	public String getTelefonszam() {
		return telefonszam;
	}

	public void setTelefonszam(String telefonszam) {
		this.telefonszam = telefonszam;
	}

	public String getVevoId() {
		return vevoId;
	}

	public void setVevoId(String vevoId) {
		this.vevoId = vevoId;
	}
}