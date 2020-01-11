package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import utils.Range;
import utils.StdDraw;
import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;

public class MyGameGUI implements Runnable {


	public MyGameGUI(game_service game, DGraph g){
		setGame(game);
		setGraph(g);
		init();
	}

	private void init() {
		StdDraw.setCanvasSize(1000, 800);
		StdDraw.enableDoubleBuffering();
		set_x(this.getGraph().GraphScaleX());
		set_y(this.getGraph().GraphScaleY());
		Robots();
		Fruits();
		draw();
		drawFruits();
		drawRobots();
		//StdDraw.background((int)(this.get_x().get_max()+this.get_x().get_min())/2,(int) (this.get_y().get_max()+this.get_y().get_min())/2,"data\\A0.png");
		StdDraw.show();
		StdDraw.Visible();
		run();
	}


	/**
	 * Moves each of the robots along the edge, in case the robot is on a node the
	 * next destination (next edge) is chosen (randomly).
	 * 
	 * @param game
	 * @param gg
	 * @param log
	 */
	private void moveRobots() {
		// update fruits
		List<String> fruits = this.getGame().getFruits();
		for(int i = 0; i < fruits.size(); i++) {
			this.getFruitList().get(i).init(fruits.get(i));
		}
		
		List<String> log = this.getGame().move();
		if (log != null) {
			long t = this.getGame().timeToEnd();
			for (int i = 0; i < log.size(); i++) {
				Robots r = this.getRobList().get(i);
				r.init(log.get(i));
				
				this.draw();
				this.drawRobots();
				this.drawFruits();
				StdDraw.show();
				//StdDraw.pause(50);
				



				if (r.getDest() == -1) {
					r.setDest(nextNode(this.getGraph(), r.getSrc()));
					this.getGame().chooseNextEdge(r.getId(), r.getDest());
				}
			}
		}
	}

	/**
	 * a very simple random walk implementation!
	 * 
	 * @param g
	 * @param src
	 * @return
	 */
	private int nextNode(DGraph g, int src) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int) (Math.random() * s);
		int i = 0;
		while (i < r) {
			itr.next();
			i++;
		}
		ans = itr.next().getDest();
		return ans;
	}



	/**
	 * thread that start the game
	 */
	public void run() {
		this.getGame().startGame(); // should be a Thread!!! moveRobots(game, gg);

		while(this.getGame().isRunning()) {
			moveRobots(); 
		}




	}
	/**
	 * drawRobots
	 */
	public void drawRobots() {
		//draw Robots
		Iterator<Robots> r_iter = this.getRobList().iterator();
		while(r_iter.hasNext()) {
			Robots r = r_iter.next();
			StdDraw.picture(r.getPosX(),r.getPosY(), "data\\p"+r.getId()+".png");
			
		}

	}

	/**
	 * drawFruits
	 */
	public void drawFruits() {
		//draw Robots
		Iterator<Fruits> f_iter = this.getFruitList().iterator();
		while(f_iter.hasNext()) {
			Fruits f = f_iter.next();
			if(f.getType() == 1) {
				StdDraw.picture(f.getPosX(),f.getPosY(), "data\\apple.png");
				
			}
			else {
				StdDraw.picture(f.getPosX(),f.getPosY(), "data\\banana.png");
			

			}
		}

	}


	/**
	 * Open windo that adjusted scale bound by the nodes
	 * drawing the Graph by draw points using iterator on each nodes location including key
	 * and draw lines to the edges including direction and weight
	 * 
	 */
	public void draw() {
		StdDraw.clear();
		Range x = this.get_x();
		Range y = this.get_y();


		StdDraw.setXscale(x.get_min() - x.get_min()*0.00001, x.get_max() + x.get_min()*0.00001);
		StdDraw.setYscale(y.get_min() - y.get_min()*0.00001, y.get_max() + y.get_min()*0.00001);

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
			StdDraw.text(current.getLocation().x(), current.getLocation().y() + 0.0001, String.valueOf(current.getKey()));
			
			// draw timer
			StdDraw.text(this.get_x().get_max() - 0.002, this.get_y().get_min(), "Time: "+this.getGame().timeToEnd());
		}
	}

	/**
	 * get number of robots
	 */
	private void Robots() {
		JSONObject GameJson;
		try {
			GameJson = new JSONObject(this.getGame().toString()).getJSONObject("GameServer");
			int Robot_num = GameJson.getInt("robots"); 
			setRobList(new ArrayList<Robots>(Robot_num));			
			int src_node = 0; // arbitrary node, you should start at one of the fruits
			for (int a = 0; a < Robot_num; a++) {
				this.getGame().addRobot(src_node + a);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// adding robots
		Iterator<String> iter = this.getGame().getRobots().iterator();
		while (iter.hasNext()) {
			this.getRobList().add(new Robots(iter.next()));
		}
	}

	private void Fruits() {
		// adding fruits
		setFruitList(new ArrayList<Fruits>());
		Iterator<String> iter = this.getGame().getFruits().iterator();
		while (iter.hasNext()) {
			this.getFruitList().add(new Fruits(iter.next()));
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

	public ArrayList<Robots> getRobList() {
		return _rob_list;
	}

	public void setRobList(ArrayList<Robots> rob_list) {
		this._rob_list = rob_list;
	}

	public ArrayList<Fruits> getFruitList() {
		return _fruit_list;
	}

	public void setFruitList(ArrayList<Fruits> fruit_list) {
		this._fruit_list = fruit_list;
	}
	public Range get_x() {
		return _x;
	}

	public void set_x(Range _x) {
		this._x = _x;
	}
	public Range get_y() {
		return _y;
	}

	public void set_y(Range _y) {
		this._y = _y;
	}
	/**** data *****/
	private Range _x, _y;
	private game_service _game;
	private DGraph _graph;
	private ArrayList<Robots> _rob_list;
	private ArrayList<Fruits> _fruit_list;
}
