package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Server.game_service;
import utils.Range;
import utils.StdDraw;
import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.nodeData;
import dataStructure.node_data;

public class MyGameGUI {
	private game_service _game;
	private DGraph _graph;
	
	public MyGameGUI(game_service game, DGraph g){
		setGame(game);
		setGraph(g);
		init();
	}

	private void init() {
		StdDraw.setCanvasSize(1000, 800);
		draw();
		StdDraw.Visible();
	}
	
	
	/**
	 * Open windo that adjusted scale bound by the nodes
	 * drawing the Graph by draw points using iterator on each nodes location including key
	 * and draw lines to the edges including direction and weight
	 * 
	 */
	private void draw() {
		StdDraw.clear();
		Range x = this.getGraph().GraphScaleX();
		Range y = this.getGraph().GraphScaleY();
		System.out.println((x.get_max()+x.get_min())/2);
		System.out.println((y.get_max()+y.get_min())/2);
		//StdDraw.picture(0,0, "http://www.moogaz.co.il/FunnyPictures/12.jpg", 50, 50);
		StdDraw.picture((x.get_max()+x.get_min())/2,(y.get_max()+y.get_min())/2, "http://www.moogaz.co.il/FunnyPictures/12.jpg");
		System.out.println(x);
		System.out.println(y);
		double x_fracMin = x.get_min() - (int)x.get_min();
		double x_fracMax = x.get_max() - (int)x.get_max();
		double y_fracMin = y.get_min() - (int)y.get_min();
		double y_fracMax = y.get_max() - (int)y.get_max();
		System.out.println(x_fracMin);
		System.out.println(x_fracMax);
		System.out.println(y_fracMin);
		System.out.println(y_fracMax);
		StdDraw.setXscale(x.get_min() - x.get_min()*0.00001, x.get_max() + x.get_min()*0.00001);
		StdDraw.setYscale(y.get_min()  - y.get_min()*0.00001, y.get_max() + y.get_min()*0.00001);

		// directions compute;
		double directionX = 0;
		double directionY = 0;
		double middleX = 0;
		double middleY = 0;
		// draw points
		Iterator<node_data> iter = this.getGraph().getV().iterator();
		Iterator<edge_data> iter_edge;
		while (iter.hasNext()) {
			nodeData current = (nodeData) iter.next();
			iter_edge = current.get_edges().values().iterator();
			// draw edges
			while (iter_edge.hasNext()) {
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setPenRadius(0.007);
				edgeData current_edge = (edgeData) iter_edge.next();


				// calculations
				directionX = current.getLocation().x()*0.1 + current_edge.getNodeDest().getLocation().x()*0.9;
				directionY = current.getLocation().y()*0.1 + current_edge.getNodeDest().getLocation().y()*0.9;


				// draw edges
				StdDraw.line(current.getLocation().x(), current.getLocation().y(),
						current_edge.getNodeDest().getLocation().x(),
						current_edge.getNodeDest().getLocation().y());
				
				// draw direction
				StdDraw.setPenColor(Color.CYAN);
				StdDraw.setPenRadius(0.025);
				StdDraw.point(directionX, directionY);
				// edge weight 
				middleX = (current.getLocation().x() + current_edge.getNodeDest().getLocation().x()) / 2;
				middleY = (current.getLocation().y() + current_edge.getNodeDest().getLocation().y()) / 2;
				StdDraw.setPenColor(new Color(0, 153, 0));
				StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
				StdDraw.text(middleX, middleY + 0.2, String.valueOf(current_edge.getWeight()));

			}
			// draw point
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.03);
			StdDraw.point(current.getLocation().x(), current.getLocation().y());

			// node key
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
			StdDraw.text(current.getLocation().x(), current.getLocation().y() + 0.25, String.valueOf(current.getKey()));
		}
	}

	public DGraph getGraph() {
		return _graph;
	}

	public void setGraph(DGraph _graph) {
		this._graph = _graph;
	}

	public game_service getGame() {
		return _game;
	}

	public void setGame(game_service _game) {
		this._game = _game;
	}
}
