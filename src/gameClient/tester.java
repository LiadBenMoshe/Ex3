package gameClient;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import dataStructure.DGraph;
import dataStructure.nodeData;
import utils.Point3D;

public class tester {

	public static String print(Element elem) {
		String output = "";
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(elem);
			transformer.transform(source, result);

			output = result.getWriter().toString();
			
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void main(String[] args) {

	

		MyGameGUI game = new MyGameGUI();

	}

}
