package f023qc;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWrite {

	public static void main(String[] args) {
		try(FileReader reader = new FileReader("orarendF023QC.json")){

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
			
			printJSON(jsonObject);
			writeJSON(jsonObject);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void printJSON(JSONObject jsonObject) {
		JSONObject root = (JSONObject) jsonObject.get("F023QC_orarend");
		JSONArray orak = (JSONArray) root.get("ora");

		for(int i=0; i<orak.size(); i++) {
			JSONObject ora = (JSONObject) orak.get(i);
			JSONObject time = (JSONObject) ora.get("idopont");
			System.out.println("Tárgy: "+ora.get("targy"));
			System.out.println("Időpont: "+time.get("nap")+" "+time.get("tol")+"-tól "+time.get("ig")+"-ig");
			System.out.println("Helyszín: "+ora.get("helyszin"));
			System.out.println("Oktató: "+ora.get("oktato"));
			System.out.println("Szak: "+ora.get("szak")+"\n");
		}
	}
	
	private static void writeJSON(JSONObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter("orarendF023QC1.json")) {
            String jsonString = jsonObject.toJSONString();
            String formattedJSONString = indentJsonString(jsonString);
            fileWriter.write(formattedJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    private static String indentJsonString(String jsonString) {
        StringBuilder indentedJson = new StringBuilder();
        int indentLevel = 0;
        for (char c : jsonString.toCharArray()) {
            if (c == '{' || c == '[') {
                indentLevel++;
                appendNewLine(indentedJson, indentLevel);
            } else if (c == '}' || c == ']') {
                indentLevel--;
                appendNewLine(indentedJson, indentLevel);
            }
            indentedJson.append(c);
            if (c == ',' || c == '{' || c == '[') {
                appendNewLine(indentedJson, indentLevel);
            }
        }
        return indentedJson.toString();
    }
    
    
    private static void appendNewLine(StringBuilder stringBuilder, int indentLevel) {
        stringBuilder.append("\n");
        for (int i = 0; i < indentLevel; i++) {
            stringBuilder.append("  ");
        }
    }
}
