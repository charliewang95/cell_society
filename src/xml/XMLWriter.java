package xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {
	public static void main(String argv[]) {

		try {
			String rule = "FireRule";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Rule");
			doc.appendChild(rootElement);
			
			// set attribute to root element
			Attr attrRule = doc.createAttribute("id");
			attrRule.setValue(rule);
			rootElement.setAttributeNode(attrRule);
			
			//rootElement.setAttribute("id", "FireRule");

			// child elements
			Element ruleName = doc.createElement("nameRule");
			ruleName.appendChild(doc.createTextNode(rule));
			rootElement.appendChild(ruleName);

			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode("Forest Fire"));
			rootElement.appendChild(title);
			
			Element author = doc.createElement("author");
			author.appendChild(doc.createTextNode("Charlie"));
			rootElement.appendChild(author);
			
			Element cellLength = doc.createElement("cellLength");
			cellLength.appendChild(doc.createTextNode("20"));
			rootElement.appendChild(cellLength);
			
			Element xSize = doc.createElement("xSize");
			xSize.appendChild(doc.createTextNode("30"));
			rootElement.appendChild(xSize);
			
			Element ySize = doc.createElement("ySize");
			ySize.appendChild(doc.createTextNode("30"));
			rootElement.appendChild(ySize);
			
			Element numNeighbor = doc.createElement("numNeighbor");
			numNeighbor.appendChild(doc.createTextNode("4"));
			rootElement.appendChild(numNeighbor);
			
			Element numSide = doc.createElement("numSide");
			numSide.appendChild(doc.createTextNode("4"));
			rootElement.appendChild(numSide);
			
			Element toroidal = doc.createElement("toroidal");
			toroidal.appendChild(doc.createTextNode("true"));
			rootElement.appendChild(toroidal);
			
			Element probCatch = doc.createElement("probcatch");
			probCatch.appendChild(doc.createTextNode("0.5"));
			rootElement.appendChild(probCatch);
			
			Element emptyColor = doc.createElement("emptyColor");
			emptyColor.appendChild(doc.createTextNode("YELLOW"));
			rootElement.appendChild(emptyColor);
			
			Element treeColor = doc.createElement("treeColor");
			treeColor.appendChild(doc.createTextNode("green"));
			rootElement.appendChild(treeColor);
			
			Element burnColor = doc.createElement("burnColor");
			burnColor.appendChild(doc.createTextNode("blue"));
			rootElement.appendChild(burnColor);
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data/xml/" + rule + "xmlwriter" + ".xml"));

			// Output to console for testing
			//StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
