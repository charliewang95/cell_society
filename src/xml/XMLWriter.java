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
import layout.rule.SugarRule;


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

			// root elements
			doc = docBuilder.newDocument();
			rootElement = doc.createElement(myXMLResources.getString("Root"));
			doc.appendChild(rootElement);

			// set attribute to root element
			Attr attrRule = doc.createAttribute("id");
			attrRule.setValue(desiredRule);
			rootElement.setAttributeNode(attrRule);

			// rootElement.setAttribute("id", "FireRule");

			// child elements
			generalElements();

			// unique to FireRule...
			specificElements();

			addSpecificGrid(saveGrid);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data/xml/" + desiredRule + "Output" + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

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
		
		if (desiredRule.equals("SugarRule")) {
			SugarRule mySugarRule = (SugarRule) myRule;
			
			Element percent0 = doc.createElement(myXMLResources.getString("Percent0"));
			percent0.appendChild(doc.createTextNode(Double.toString(mySugarRule.getPercent0())));
			rootElement.appendChild(percent0);
			Element percent1 = doc.createElement(myXMLResources.getString("Percent1"));
			percent1.appendChild(doc.createTextNode(Double.toString(mySugarRule.getPercent1())));
			rootElement.appendChild(percent1);
			Element percent2 = doc.createElement(myXMLResources.getString("Percent2"));
			percent2.appendChild(doc.createTextNode(Double.toString(mySugarRule.getPercent2())));
			rootElement.appendChild(percent2);
			Element percent3 = doc.createElement(myXMLResources.getString("Percent3"));
			percent3.appendChild(doc.createTextNode(Double.toString(mySugarRule.getPercent3())));
			rootElement.appendChild(percent3);
			Element percentA = doc.createElement(myXMLResources.getString("PercentA"));
			percentA.appendChild(doc.createTextNode(Double.toString(mySugarRule.getPercentAgent())));
			rootElement.appendChild(percentA);
			
			Color[] color = mySugarRule.getColors();
			
			Element color0 = doc.createElement(myXMLResources.getString("Color0"));
			color0.appendChild(doc.createTextNode(color[0].toString()));
			rootElement.appendChild(color0);
			
			Element color1 = doc.createElement(myXMLResources.getString("Color1"));
			color1.appendChild(doc.createTextNode(color[1].toString()));
			rootElement.appendChild(color1);
			
			Element color2 = doc.createElement(myXMLResources.getString("Color2"));
			color2.appendChild(doc.createTextNode(color[2].toString()));
			rootElement.appendChild(color2);
			
			Element color3 = doc.createElement(myXMLResources.getString("Color3"));
			color3.appendChild(doc.createTextNode(color[3].toString()));
			rootElement.appendChild(color3);
			
			Element color4 = doc.createElement(myXMLResources.getString("Color4"));
			color4.appendChild(doc.createTextNode(color[4].toString()));
			rootElement.appendChild(color4);
			
			Element vision = doc.createElement(myXMLResources.getString("Vision"));
			vision.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getVision())));
			rootElement.appendChild(vision);
			
			Element metabolism = doc.createElement(myXMLResources.getString("Metabolism"));
			metabolism.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getMetabolism())));
			rootElement.appendChild(metabolism);
			
			Element minSugar = doc.createElement(myXMLResources.getString("MinSugar"));
			minSugar.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getMinSugar())));
			rootElement.appendChild(minSugar);
			
			Element maxSugar = doc.createElement(myXMLResources.getString("MaxSugar"));
			maxSugar.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getMaxSugar())));
			rootElement.appendChild(maxSugar);
			
			Element sugarGrow = doc.createElement(myXMLResources.getString("SugarGrow"));
			sugarGrow.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getSugarGrow())));
			rootElement.appendChild(sugarGrow);
			
			Element preset = doc.createElement(myXMLResources.getString("Preset"));
			preset.appendChild(doc.createTextNode(Integer.toString(mySugarRule.getPreset())));
			rootElement.appendChild(preset);
			
			
			
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
		//Element ruleName = doc.createElement("nameRule");
		ruleName.appendChild(doc.createTextNode(desiredRule));
		rootElement.appendChild(ruleName);

		Element title = doc.createElement(myXMLResources.getString("Title"));
		//Element title = doc.createElement("title");
		title.appendChild(doc.createTextNode(desiredRule));
		rootElement.appendChild(title);

		Element author = doc.createElement(myXMLResources.getString("Author"));
		//Element author = doc.createElement("author");
		author.appendChild(doc.createTextNode("Charlie"));
		rootElement.appendChild(author);

		Element cellLength = doc.createElement(myXMLResources.getString("CellLength"));
		//Element cellLength = doc.createElement("cellLength");
		cellLength.appendChild(doc.createTextNode(Double.toString(myRule.getCellLength())));
		rootElement.appendChild(cellLength);

		Element xSize = doc.createElement(myXMLResources.getString("Column"));
		//Element xSize = doc.createElement("xSize");
		xSize.appendChild(doc.createTextNode(Integer.toString(myRule.getCol())));
		rootElement.appendChild(xSize);

		Element ySize = doc.createElement(myXMLResources.getString("Row"));
		//Element ySize = doc.createElement("ySize");
		ySize.appendChild(doc.createTextNode(Integer.toString(myRule.getRow())));
		rootElement.appendChild(ySize);

		Element numNeighbor = doc.createElement(myXMLResources.getString("Neighbor"));
		//Element numNeighbor = doc.createElement("numNeighbor");
		numNeighbor.appendChild(doc.createTextNode(Integer.toString(myRule.getNumNeighbor())));
		rootElement.appendChild(numNeighbor);

		Element numSide = doc.createElement(myXMLResources.getString("Side"));
		//Element numSide = doc.createElement("numSide");
		numSide.appendChild(doc.createTextNode(Integer.toString(myRule.getSide())));
		rootElement.appendChild(numSide);

		Element toroidal = doc.createElement(myXMLResources.getString("Toroidal"));
		//Element toroidal = doc.createElement("toroidal");
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
