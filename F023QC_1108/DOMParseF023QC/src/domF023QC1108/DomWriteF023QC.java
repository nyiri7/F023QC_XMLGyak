package domF023QC1108;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class DomWriteF023QC {
    public static void main(String[] args) {
        String inputFilePath = "orarendF023QC.xml";
        String outputFilePath = "orarend1F023QC.xml";
        try {
            Document document = parseXmlFile(inputFilePath);
            writeDocumentToFile(document, outputFilePath);
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    private static Document parseXmlFile(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(filePath));
    }

    private static void writeDocumentToFile(Document document, String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        DOMSource source = new DOMSource(document);
        StreamResult outFile = new StreamResult(new File(filePath));
        transformer.transform(source, outFile);

    }
}
