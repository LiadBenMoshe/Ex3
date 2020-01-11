package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;


import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Range;
import utils.StdDraw;

/**
 * this class if built for drawing the Graph using StdDraw
 * used only in the Graph_GUI class 
 * @author Ben itzhak
 * @author Liad ben moshe
 * 
 */
public class Draw {
	/**
	 * constructor that get collection of functions
	 * 
	 * @param g - graph 
	 */
	protected Draw(graph g) {
		setGraph(g);
		StdDraw.setCanvasSize(this.get_Width(), this.get_Height()); // GUI windo witdh and height
	}
	
	/**
	 * Open windo with defualt settings 
	 * when graph is empty or when create a new object of Graph_GUI
	 * 
	 */
	protected void drawEmptyGraph() {
		StdDraw.clear();
		StdDraw.setScale(-5,5);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
		StdDraw.text(0, 4 , "Empty Graph");
	}
	

	/**
	 * Open windo that adjusted scale bound by the nodes
	 * drawing the Graph by draw points using iterator on each nodes location including key
	 * and draw lines to the edges including direction and weight
	 * 
	 * @param type - 1 if there is an Additions drawing for Algoritem (shortestPath/TSP)
	 * @param src_dest is an Arraylist - the list of nodes to draw the result of the algoritem
	 */
	protected void draw_graph(int type, ArrayList<node_data> src_dest) {
		StdDraw.clear();
		setRangeX(this.getGraphDraw().GraphScaleX());
		setRangeY(this.getGraphDraw().GraphScaleY());
		StdDraw.setXscale(this.get_RangeX().get_min() - 1, this.get_RangeX().get_max() + 1);
		StdDraw.setYscale(this.get_RangeY().get_min() - 1, this.get_RangeY().get_max() + 1);

		// directions compute;
		double directionX = 0;
		double directionY = 0;
		double middleX = 0;
		double middleY = 0;
		// draw points
		Iterator<node_data> iter = this.getGraphDraw().getV().iterator();
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
				
				if(type == 1) {
					StdDraw.setPenColor(new Color(242, 189, 16));
					StdDraw.setPenRadius(0.007);
					Iterator<node_data> path = src_dest.iterator();
					nodeData path_src = null;
					nodeData path_dest;
					if(path.hasNext())
						path_src=(nodeData) path.next();
					while(path.hasNext()) {
						path_dest = (nodeData) path.next();

						StdDraw.line(path_src.getLocation().x(), path_src.getLocation().y(),
								path_dest.getLocation().x(),
								path_dest.getLocation().y());
						path_src = path_dest;
					}
				}

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

	protected DGraph getGraphDraw() {
		return _graphDraw;
	}

	protected int get_Width() {
		return _Width;
	}

	protected int get_Height() {
		return _Height;
	}


	protected Range get_RangeY() {
		return _RangeY;
	}

	protected Range get_RangeX() {
		return _RangeX;
	}
	protected void setRangeX(Range r) {
		this._RangeX = r;
	}
	protected void setRangeY(Range r) {
		this._RangeY = r;
	}

	/**** Private methods and data ******/

	private void setGraph(graph _graph) {
		this._graphDraw = (DGraph) _graph;
	}

	/** private data ***/
	private DGraph _graphDraw;
	private int _Width = 900, _Height = 650;
	private Range _RangeX, _RangeY;

}
