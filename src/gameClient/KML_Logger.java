package gameClient;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KML_Logger {
	public static final String xmlFilePath = "hh.xml";
	private Element _game;
	private Document _document;

	public KML_Logger(int Scenario) {
		baseKML(Scenario);
	}


	public void baseKML(int Scenario){
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder;
			documentBuilder = documentFactory.newDocumentBuilder();

			setDocument(documentBuilder.newDocument());

			// root element
			Element root = getDocument().createElement("kml");
			Attr attr_kml = getDocument().createAttribute("xmlns");
			attr_kml.setValue("http://earth.google.com/kml/2.2");
			root.setAttributeNode(attr_kml);
			getDocument().appendChild(root);

			setGame(getDocument().createElement("game"));
			root.appendChild(this.getGame());

			Element GameName = getDocument().createElement("name");
			GameName.appendChild(getDocument().createTextNode("Game Scenario "+Scenario+""));
			this.getGame().appendChild(GameName);

		} catch (ParserConfigurationException e) {
		
			e.printStackTrace();
		}

	}


	public static void main(String argv[]) {
		KML_Logger k = new KML_Logger(3);
		k.icon(1);
		k.icon(2);
		k.Placemark(3, 12, 11, 789456);
		k.Placemark(7, 23, 11, 789456);
		k.Placemark(3, 11, 11, 789456);
		k.KMLtoFile();
		
	}
	public void icon(int robot_id) {
		Element Style = getDocument().createElement("Style");
        Attr attr = getDocument().createAttribute("id");
        attr.setValue("#Robot-"+robot_id);
        Style.setAttributeNode(attr);
		getGame().appendChild(Style);
		Element IconStyle = getDocument().createElement("IconStyle");
		Style.appendChild(IconStyle);
		Element Icon= getDocument().createElement("Icon");
		Element href = getDocument().createElement("href");
		href.appendChild(getDocument().createTextNode("<div>Icons made by <a href=\"https://www.flaticon.com/authors/roundicons-freebies\" title=\"Roundicons Freebies\">Roundicons Freebies</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a></div>"));
		Icon.appendChild(href);
		IconStyle.appendChild(Icon);
		Element hotSpot= getDocument().createElement("hotSpot");
		Attr yunits = getDocument().createAttribute("yunits");
		yunits.setValue("fraction");
		
		Attr xunits = getDocument().createAttribute("xunits");
		xunits.setValue("fraction");

		Attr y = getDocument().createAttribute("y");
		y.setValue(".5");
		
		Attr x = getDocument().createAttribute("x");
		x.setValue("0");
		
		hotSpot.setAttributeNode(yunits);
		hotSpot.setAttributeNode(xunits);
		hotSpot.setAttributeNode(y);
		hotSpot.setAttributeNode(x);
		IconStyle.appendChild(hotSpot);
	}
	
	

	public void Placemark(int rob_id, double posX, double posY, long time){
		Element Placemark = getDocument().createElement("Placemark");
		getGame().appendChild(Placemark);
		Element TimeRemaining= getDocument().createElement("TimeStamp");
		Placemark.appendChild(TimeRemaining);
		Element when = getDocument().createElement("when");
		when.appendChild(getDocument().createTextNode(""+time));
		TimeRemaining.appendChild(when);
		Element robot = getDocument().createElement("styleUrl");
		robot.appendChild(getDocument().createTextNode("#Robot-"+rob_id));
		Placemark.appendChild(robot);
		Element point = getDocument().createElement("Point");
		Placemark.appendChild(point);
		Element coordinates = getDocument().createElement("coordinates");
		coordinates.appendChild(getDocument().createTextNode(""+posX+","+posY+",0"));
		Placemark.appendChild(coordinates);
	}
	/**
	 * create the xml file
	 * transform the DOM Object to an XML File
	 */
	public void KMLtoFile() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();

			DOMSource domSource = new DOMSource(getDocument());
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
		
			transformer.transform(domSource, streamResult);
				
			System.out.println("workds");
		} catch (TransformerConfigurationException e) {
		
			e.printStackTrace();

		} catch(TransformerException e) {
		
			e.printStackTrace();
		}
	}


	public Element getGame() {
		return _game;
	}

	public void setGame(Element _game) {
		this._game = _game;
	}


	public Document getDocument() {
		return _document;
	}


	public void setDocument(Document _document) {
		this._document = _document;
	}


}
