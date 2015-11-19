import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class GATE2PubAnnotaiton {
	public static LinkedList<GATEAnnotation> gateAnnotations = new LinkedList<GATEAnnotation>();
	public static String text;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<1)
		{
			System.out.println("You need to specify filepath of the GATE Annotated document");
			System.out.println("Example of command: java -jar GATE2PubAnnotation.jar /example/example.xml");
			return;
		}
		String pathOfDataSet = args[0];
		System.out.println("Processing: "+pathOfDataSet);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					pathOfDataSet));
			String line = null;
			String xml = "";
			while ((line = reader.readLine()) != null) {
				xml += line + '\n';
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// System.out.println(xml);
			factory.setNamespaceAware(true);
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document parse = builder.parse(is);
			NodeList nodes = parse.getElementsByTagName("TextWithNodes");
			for (int j = 0; j < nodes.getLength(); j++) {
				Element element = (Element) nodes.item(j);
				text = element.getTextContent();
			}
			NodeList nodes2 = parse.getElementsByTagName("AnnotationSet");
			for (int j = 0; j < nodes2.getLength(); j++) {
				NodeList tags = nodes2.item(j).getChildNodes();

				for (int k = 0; k < tags.getLength(); k++) {
					String tagName = tags.item(k).getNodeName();
					if (tagName.equals("Annotation")) {
						int start = Integer.parseInt(tags.item(k)
								.getAttributes().getNamedItem("StartNode")
								.getNodeValue());
						int end = Integer.parseInt(tags.item(k).getAttributes()
								.getNamedItem("EndNode").getNodeValue());
						String type = tags.item(k).getAttributes()
								.getNamedItem("Type").getNodeValue();
						String id = tags.item(k).getAttributes()
								.getNamedItem("Id").getNodeValue();
						GATEAnnotation ga = new GATEAnnotation();
						ga.setStart(start);
						ga.setEnd(end);
						ga.setAnnotationName(type);
						ga.setId(id);
						gateAnnotations.add(ga);
					}
				}
			}
			String[] splits = pathOfDataSet.split("/");
			toPubAnnotation(splits[splits.length-1]);
		} catch (Exception ex) {
			System.out.println("Wups, something went wrong! Please check whether document you provided is the right GATE annotated file");
			ex.printStackTrace();
		}
		
		System.out.println("Processing done! You can find .json files in output folder. Thank you for using GATE2PubAnnotation!");

	}

	public static void toPubAnnotation(String fileName) {
		File theDir = new File("output");

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + "output");
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		JSONObject js = new JSONObject();
		js.put("text", text);
		JSONArray denotations = new JSONArray();

		// JSONArray modifications = new JSONArray();
		// js.put("modifications", modifications);

		for (int i = 0; i < gateAnnotations.size(); i++) {
			GATEAnnotation annot = gateAnnotations.get(i);

			JSONObject denotation = new JSONObject();
			denotation.put("id", annot.getId());
			JSONObject span = new JSONObject();
			span.put("begin", annot.getStart());
			span.put("end", annot.getEnd());
			denotation.put("span", span);
			denotation.put("obj", annot.getAnnotationName());
			denotations.add(denotation);
		}
		js.put("denotations", denotations);

		// try{
		// PrintWriter writer = new
		// PrintWriter("output/"+this.documentName+".json", "UTF-8");
		// writer.println(js.toJSONString());
		// writer.close();

		try (FileWriter file = new FileWriter("output/" + fileName + ".json")) {
			file.write(js.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			// System.out.println("\nJSON Object: " + js);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
