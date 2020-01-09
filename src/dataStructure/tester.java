package dataStructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import algorithms.Graph_Algo;
import gui.Graph_GUI;
import utils.Point3D;

public class tester {

	public static void main(String[] args) {

		/*
		 * initialize new graph
		 */
		DGraph dg = new DGraph(); 

		/*
		 * creating new points x,y using Point3D for utils 
		 */
		Point3D p1 = new Point3D(-4.0, 4.0); 
		Point3D p2 = new Point3D(3, 8);
		Point3D p3 = new Point3D(8, 5);
		Point3D p4 = new Point3D(12, 5);

		/*
		 * initialize new nodes getting points as parameter
		 */
		nodeData nd1 = new nodeData(p1); 
		nodeData nd2 = new nodeData(p2);
		nodeData nd3 = new nodeData(p3);
		nodeData nd4 = new nodeData(p4);

		/*
		 * adding those nodes to the graph
		 */
		dg.addNode(nd1); 
		dg.addNode(nd2);
		dg.addNode(nd3);
		dg.addNode(nd4);

		/*
		 * creating new edges between those nodes
		 * getting srcNode.key,destNode.key,weight(for edge) as parameter
		 */
		dg.connect(1, 2, 1);
		dg.connect(2, 3, 2);
		dg.connect(1, 3, 0.2);
		dg.connect(3, 4, 0.5);

		/*
		 * initialize new Graph_Algo getting Dgraph - graph as parameter
		 */
		//Graph_Algo ga = new Graph_Algo(dg);
		/*
		 * you now able to do algorithms on the graph, for example
		 */
		//ga.isConnected();

		/*
		 * initialize new GUI getting Graph_Algo as paramter
		 * this line will open the gui windo 
		 */
		Graph_GUI gugu = new Graph_GUI(new Graph_Algo(dg));



		
		for(int i = 0; i < 30;i++) {
			try {
				TimeUnit.SECONDS.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//ga.get_graphAlgo().addNode(new nodeData(new Point3D(3,i)));
		}
	
	}

}
