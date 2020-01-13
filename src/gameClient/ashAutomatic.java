package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import Server.game_service;
import algorithms.Graph_Algo;
import gameClient.Fruits;
import utils.Point3D;
import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.nodeData;
import dataStructure.node_data;

public class ashAutomatic {

	public ashAutomatic(Graph_Algo g) {
		this._graph = g;
	}

	/**
	 * find the nearest node to a given fruit
	 * 
	 * @param f
	 * @return
	 */
	public int[] nearestNode(Fruits f) {
		Boolean flag = false;
		ArrayList<node_data> nodes = new ArrayList<>();
		ArrayList<edge_data> edges = new ArrayList<>();
		nodes.addAll(this._graph.get_graphAlgo().getV());
		nodeData src, dest;
		edgeData edge;
		int[] ans = new int[2];
		for (int i = 0; i < nodes.size(); i++) {
			src = (nodeData) nodes.get(i);
			edges.addAll(this._graph.get_graphAlgo().getE(i));

			for (int j = 0; j < edges.size(); j++) {
				edge = (edgeData) ((edges).get(j));
				dest = edge.getNodeDest();

				if (dest.getKey() > src.getKey()) {
					flag = distance(src.getLocation(), dest.getLocation(), new Point3D(f.getPosX(), f.getPosY()));
					if (flag) {
						// check higher node key
						int higher = src.getKey() - dest.getKey();
						if (higher > 0 && f.getType() == -1) {
							ans[0] = src.getKey();
							ans[1] = dest.getKey();
							return ans;
						}

						else if (higher > 0 && f.getType() == 1) {
							ans[0] = dest.getKey();
							ans[1] = src.getKey();
							return ans;
						}

						else if (f.getType() == -1) {
							ans[0] = dest.getKey();
							ans[1] = src.getKey();
							return ans;
						}

						else {
							ans[0] = src.getKey();
							ans[1] = dest.getKey();
							return ans;
						}
					}
				}
			}
			edges.clear();
		}
		// not found

		ans[0] = -1;
		ans[1] = -1;
		return ans;
	}

	/**
	 * check if the fruit between two nodes by checking location
	 * 
	 * @param x1     - first point x
	 * @param y1     - first point y
	 * @param x2     - second point x
	 * @param y2     - second point y
	 * @param fruitX
	 * @param fruitY
	 * @return
	 */
	private boolean distance(Point3D n1, Point3D n2, Point3D f) {
		if (n1.distance2D(f) + f.distance2D(n2) - eps <= n1.distance2D(n2))
			return true;
		return false;
	}

	/*
	 * private void moveRobots() { //update fruit List<String> fruits =
	 * this.getGame().getFruits(); for(int i = 0; i < fruits.size(); i++) {
	 * this.getFruitList().get(i).init(fruits.get(i)); }
	 * 
	 * List<String> log = this.getGame().move(); if (log != null) { for (int i = 0;
	 * i < log.size(); i++) { Robots r = this.getRobList().get(i);
	 * r.init(log.get(i)); System.out.println(r.getValue());
	 * 
	 * if (r.getDest() == -1) {
	 * 
	 * this._game.chooseNextEdge(i, nextNode(r.getSrc()));
	 * 
	 * } } } }
	 * 
	 *//**
		 * a very simple random walk implementation!
		 * 
		 * @param g
		 * @param src
		 * @return
		 *//*
			 * private int nextNode(int src) { int ans = -1; Collection<edge_data> ee =
			 * this._graph.get_graphAlgo().getE(src); Iterator<edge_data> itr =
			 * ee.iterator(); int s = ee.size(); int r = (int) (Math.random() * s); int i =
			 * 0; while (i < r) { itr.next(); i++; } ans = itr.next().getDest(); return ans;
			 * }
			 */

	/*** private data ****/
	private game_service _game;
	private Graph_Algo _graph;
	private final double eps = 0.000001;
}
