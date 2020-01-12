package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gameClient.Fruits;
import utils.Point3D;
import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.nodeData;
import dataStructure.node_data;

public class ashAutomatic {

	public ashAutomatic(DGraph g) {
		this._graph = g;
	}

	/**
	 * find the nearest node to a given fruit
	 * @param f
	 * @return
	 */
	public int nearestNode(Fruits f) {
		Boolean flag = false;
		ArrayList<node_data> nodes = new ArrayList<>();
		ArrayList<edge_data> edges = new ArrayList<>();
		nodes.addAll(this._graph.getV());
		nodeData src, dest;
		edgeData edge;
		for(int i = 0; i < nodes.size(); i++) {
			src = (nodeData) nodes.get(i);
			edges.addAll(this._graph.getE(i));

			for(int j = 0; j < edges.size(); j++) {
				edge = (edgeData)((edges).get(j));
				dest = edge.getNodeDest();

				if(dest.getKey() > src.getKey()) {
					flag = distance(src.getLocation(),
							dest.getLocation(),
							new Point3D(f.getPosX(), f.getPosY()));
					if(flag) {
						// check higher node key
						int higher = src.getKey() - dest.getKey();
						if(higher > 0 && f.getType() == -1) 
							return src.getKey();

						else if(higher > 0 && f.getType() == 1) 
							return dest.getKey();

						else if(f.getType() == -1) 
							return dest.getKey();

						else 
							return src.getKey();
					}
				}
			}
			edges.clear();
		}
		// not found 
		return -1;

	}

	/**
	 * check if the fruit between two nodes by checking location
	 * @param x1 - first point x
	 * @param y1 - first point y
	 * @param x2 - second point x
	 * @param y2 - second point y
	 * @param fruitX
	 * @param fruitY
	 * @return
	 */
	private boolean distance(Point3D n1, Point3D n2 , Point3D f) {
		if(n1.distance2D(f) + f.distance2D(n2) - eps <= n1.distance2D(n2)) 
			return true;
		return false;
	}







	/*** private data ****/
	private DGraph _graph;
	private final double eps = 0.00001;
}
