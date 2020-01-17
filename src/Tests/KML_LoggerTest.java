package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.nodeData;
import gameClient.KML_Logger;
import utils.Point3D;

class KML_LoggerTest {
	static DGraph dg =new DGraph();
	static nodeData dn=new nodeData(new Point3D(2,4));
	static KML_Logger  kml = new KML_Logger(1,dg);
	
	@BeforeEach
	static void param() {
		dg.addNode(dn);
		
	}
	@Test
	void testBaseKML() {
		kml.baseKML(1);
		
	}
	@Test
	void testKMLtoFile() {
		kml.KMLtoFile();
	}

}
