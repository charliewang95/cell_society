package xml;

import java.io.File;
import java.util.ResourceBundle;

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

import javafx.scene.paint.Color;
import layout.Rule;
import layout.rule.FireRule;
import layout.rule.LifeRule;
import layout.rule.SchellingRule;


/**
 * Builds an XML file based on the current state and conditions of a given simulation. 
 * 
 * @author Joy Kim
 *
 */
public class XMLWriter {
	
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element rootElement;
	
	private static final String XML_RESOURCE_PACKAGE = "xml.properties/Rule";
	private Rule myRule;
	protected String desiredRule;
	private int[][] saveGrid;
	private ResourceBundle myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE);

	
	public XMLWriter(Rule rule) {
		myRule = rule;
		desiredRule = myRule.getName();
		saveGrid = myRule.getUpdatedGrid();
		
	}
	
	/**
	 * Runs the process of writing into an XML
	 */
	public void saveXML() {
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();

			doc = docBuilder.newDocument();
			rootElement = doc.createElement(myXMLResources.getString("Root"));
			doc.appendChild(rootElement);

			Attr attrRule = doc.createAttribute("id");
			attrRule.setValue(desiredRule);
			rootElement.setAttributeNode(attrRule);

			generalElements();

			specificElements();

			addSpecificGrid(saveGrid);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data/xml/" + desiredRule + "Output" + ".xml"));

			transformer.transform(source, result);


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Controls the flow, leads to the desired rules into methods that 
	 * add into the XML files the unique elements of the particular rule.
	 */
	private void specificElements() {
		
		if (desiredRule.equals("FireRule")){
			setFire();
		}
		
		if (desiredRule.equals("LifeRule")) {
			setLife();
		}
		
		if (desiredRule.equals("SchellingRule")) {
			setSchelling();
		}
		
	}

	private void setSchelling() {
		SchellingRule mySchellingRule = (SchellingRule) myRule;
		
		Element rateA = doc.createElement(myXMLResources.getString("PercentageA"));
		rateA.appendChild(doc.createTextNode(Double.toString(mySchellingRule.getPercentageA())));
		rootElement.appendChild(rateA);
		
		Element rateEmpty = doc.createElement(myXMLResources.getString("PercentageEmpty"));
		rateEmpty.appendChild(doc.createTextNode(Double.toString(mySchellingRule.getPercentageEmpty())));
		rootElement.appendChild(rateEmpty);
		
		Element satisfied = doc.createElement(myXMLResources.getString("Satisfy"));
		satisfied.appendChild(doc.createTextNode(Double.toString(mySchellingRule.getSatisfied())));
		rootElement.appendChild(satisfied);
		
		Color[] color = mySchellingRule.getColors();
		
		Element emptyColor = doc.createElement(myXMLResources.getString("EmptyColor"));
		emptyColor.appendChild(doc.createTextNode(color[0].toString()));
		rootElement.appendChild(emptyColor);
		
		Element aColor = doc.createElement(myXMLResources.getString("AaaColor"));
		aColor.appendChild(doc.createTextNode(color[1].toString()));
		rootElement.appendChild(aColor);
		
		Element bColor = doc.createElement(myXMLResources.getString("BbbColor"));
		bColor.appendChild(doc.createTextNode(color[2].toString()));
		rootElement.appendChild(bColor);
	}

	private void setLife() {
		LifeRule myLifeRule = (LifeRule) myRule;
		
		Element lifeType = doc.createElement(myXMLResources.getString("LifeType"));
		lifeType.appendChild(doc.createTextNode((myLifeRule.getModel())));
		rootElement.appendChild(lifeType);
		
		Color[] color = myLifeRule.getColors();
		
		Element emptyColor = doc.createElement(myXMLResources.getString("EmptyColor"));
		emptyColor.appendChild(doc.createTextNode(color[0].toString()));
		rootElement.appendChild(emptyColor);
		
		Element liveColor = doc.createElement(myXMLResources.getString("LiveColor"));
		liveColor.appendChild(doc.createTextNode(color[1].toString()));
		rootElement.appendChild(liveColor);
	}

	private void setFire() {
		FireRule myFireRule = (FireRule) myRule;
		
		Element probCatch = doc.createElement(myXMLResources.getString("ProbCatch"));
		probCatch.appendChild(doc.createTextNode(Double.toString(myFireRule.getProbCatch())));
		rootElement.appendChild(probCatch);

		Color[] color = myFireRule.getColors();
		
		Element emptyColor = doc.createElement(myXMLResources.getString("EmptyColor"));
		emptyColor.appendChild(doc.createTextNode(color[0].toString()));
		rootElement.appendChild(emptyColor);

		Element treeColor = doc.createElement(myXMLResources.getString("TreeColor"));
		treeColor.appendChild(doc.createTextNode(color[1].toString()));
		rootElement.appendChild(treeColor);

		Element burnColor = doc.createElement(myXMLResources.getString("BurnColor"));
		burnColor.appendChild(doc.createTextNode(color[2].toString()));
		rootElement.appendChild(burnColor);
	}

	
	/**
	 * Adds into the XML document the general required elements in every Rule.
	 */
	private void generalElements() {
		Element ruleName = doc.createElement(myXMLResources.getString("RuleName"));
		ruleName.appendChild(doc.createTextNode(desiredRule));
		rootElement.appendChild(ruleName);

		Element title = doc.createElement(myXMLResources.getString("Title"));
		title.appendChild(doc.createTextNode(desiredRule));
		rootElement.appendChild(title);

		Element author = doc.createElement(myXMLResources.getString("Author"));
		author.appendChild(doc.createTextNode("Charlie"));
		rootElement.appendChild(author);

		Element cellLength = doc.createElement(myXMLResources.getString("CellLength"));
		cellLength.appendChild(doc.createTextNode(Double.toString(myRule.getCellLength())));
		rootElement.appendChild(cellLength);

		Element xSize = doc.createElement(myXMLResources.getString("Column"));
		xSize.appendChild(doc.createTextNode(Integer.toString(myRule.getCol())));
		rootElement.appendChild(xSize);

		Element ySize = doc.createElement(myXMLResources.getString("Row"));
		ySize.appendChild(doc.createTextNode(Integer.toString(myRule.getRow())));
		rootElement.appendChild(ySize);

		Element numNeighbor = doc.createElement(myXMLResources.getString("Neighbor"));
		numNeighbor.appendChild(doc.createTextNode(Integer.toString(myRule.getNumNeighbor())));
		rootElement.appendChild(numNeighbor);

		Element numSide = doc.createElement(myXMLResources.getString("Side"));
		numSide.appendChild(doc.createTextNode(Integer.toString(myRule.getSide())));
		rootElement.appendChild(numSide);

		Element toroidal = doc.createElement(myXMLResources.getString("Toroidal"));
		toroidal.appendChild(doc.createTextNode(Boolean.toString(myRule.getToroidal())));
		rootElement.appendChild(toroidal);
	}

	/**
	 * The method to create the XML structure of a grid within the XML file. 
	 * 
	 * @param updatedGrid
	 */
	private void addSpecificGrid(int[][] updatedGrid) {
		Element initialize = doc.createElement("init");
		initialize.appendChild(doc.createTextNode("true"));
		rootElement.appendChild(initialize);

		for (int i = 0; i < updatedGrid.length; i++) {
			Element rowState = doc.createElement("rows");
			rootElement.appendChild(rowState);

			Element indexRow = doc.createElement("index");
			indexRow.appendChild(doc.createTextNode(Integer.toString(i)));
			rowState.appendChild(indexRow);
			for (int j = 0; j < updatedGrid[i].length; j++) {
				Element columnState = doc.createElement("column");
				rowState.appendChild(columnState);

				Element indexCol = doc.createElement("index");
				indexCol.appendChild(doc.createTextNode(Integer.toString(j)));
				columnState.appendChild(indexCol);

				Element state = doc.createElement("state");
				state.appendChild(doc.createTextNode(Integer.toString(updatedGrid[i][j])));
				columnState.appendChild(state);
			}
		}

	}
}
