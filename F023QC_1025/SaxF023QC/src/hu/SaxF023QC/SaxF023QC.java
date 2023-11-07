package hu.SaxF023QC;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxF023QC {
    public static void main(String[] args) {
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SaxHandler handler = new SaxHandler();

			saxParser.parse(new File("F023QC_kurzusfelvetel.xml"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}   
}

class SaxHandler extends DefaultHandler {
	private int tabs = 0;

	private String formatAttributes(Attributes attributes) {
		int attributeLength = attributes.getLength();
		if (attributeLength == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(", {");
		for (int i = 0; i < attributeLength; i++) {
			sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
			if (i < attributeLength - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		tabs++;
		for (int i = 0; i < tabs; i++) {
			System.out.print(" ");
		}
		System.out.println(qName + formatAttributes(attributes) + " start");
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		for (int i = 0; i < tabs; i++) {
			System.out.print(" ");
		}
		tabs--; 
		System.out.println(qName + " end");
	}

	@Override
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch, start, length).trim();
		if (!chars.isEmpty()) {
			tabs++;
		for (int i = 0; i < tabs; i++) {
			System.out.print(" ");
		}
			tabs--;
			System.out.println(chars);
		}
	}
}
