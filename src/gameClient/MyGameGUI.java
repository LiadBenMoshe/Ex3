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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;
import dataStructure.DGraph;

import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;

public class MyGameGUI implements Runnable {


	public MyGameGUI(){
		game_service game = Game_Server.getServer(StdDraw.dialogScenario()); // you have [0,23] games

		DGraph graph = new DGraph();
		graph.init(game.getGraph());
		Graph_Algo graphAlgo = new Graph_Algo(graph);
		setGraph(graphAlgo);
		setGame(game);
		setAuto(new ashAutomatic(graphAlgo));
		init();
	}

	/**
	 * icons from https://www.flaticon.com/
	 */
	private void init() {
		StdDraw.setCanvasSize(1050, 600);
		set_x(this.getGraph().get_graphAlgo().GraphScaleX());
		set_y(this.getGraph().get_graphAlgo().GraphScaleY());
		Fruits();
		drawGraph();
		StdDraw.Visible();
		StdDraw.enableDoubleBuffering();
		//RobotsStartPosition();
		RobotsAutoPosition();
		drawFruits();

		drawRobots();
		StdDraw.show();

		run();
	}

	private void repaint(){
		this.drawGraph();
		this.drawRobots();
		this.drawFruits();
		StdDraw.show();
		//StdDraw.pause(50);
	}

	private void moveRobots() {
		//update fruit
		List<String> fruits = this.getGame().getFruits();
		for(int i = 0; i < fruits.size(); i++) {
			this.getFruitList().get(i).init(fruits.get(i));
		}

		List<String> log = this.getGame().move();
		if (log != null) {
			for (int i = 0; i < log.size(); i++) {
				Robots r = this.getRobList().get(i);
				r.init(log.get(i));
				System.out.println(r.getValue());

				if (r.getDest() == -1) {

					this.getGame().chooseNextEdge(i, nextNode(r.getSrc()));

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
	private int nextNode(int src) {
		int ans = -1;
		Collection<edge_data> ee = this.getGraph().get_graphAlgo().getE(src);
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
	 * Moves each of the robots along the edge, in case the robot is on a node the
	 * next destination (next edge) is chosen (randomly).
	 * 
	 * @param game
	 * @param gg
	 * @param log
	 */
	private void moveRobotsGUI() {
		char c='0';
		//update fruit
		List<String> fruits = this.getGame().getFruits();
		for(int i = 0; i < fruits.size(); i++) {
			this.getFruitList().get(i).init(fruits.get(i));
		}


		List<String> log = this.getGame().move();
		if (log != null) {
			for (int i = 0; i < log.size(); i++) {
				Robots r = this.getRobList().get(i);
				r.init(log.get(i));
				//System.out.println(log.get(i));

				for(int j=0;j<this.getRobList().size();j++) {
					c=(char) (j+'0');
					if(StdDraw.isKeyPressed(c))
						StdDraw.setPlayer(j);
				}


				if (r.getDest() == -1) {
					this.getGame().chooseNextEdge(StdDraw.getPlayer(), nextNodeGUI(r.getSrc()));


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
	private int nextNodeGUI(int src) {
		int nextDest = -1;
		double x = 0, y = 0;



		if(StdDraw.isMousePressed()) {
			x = StdDraw.mouseX();
			y = StdDraw.mouseY();
		}
		Point3D p = new Point3D(x, y);
		Iterator<edge_data> iter = this.getGraph().get_graphAlgo().getE(src).iterator();
		edgeData edge;
		while(iter.hasNext()) {
			edge = (edgeData) iter.next();
			double check = p.distance2D(edge.getNodeDest().getLocation());
			if(check <= 0.0005) {
				return edge.getDest();
			}

		}
		return nextDest;
	}



	/**
	 * thread that start the game
	 */
	public void run() {
		this.getGame().startGame(); // should be a Thread!!! moveRobots(game, gg);

		while(this.getGame().isRunning()) {
			moveRobotsGUI();
			try {
				/* Thread.sleep(0); */
				repaint();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		// game finished print results
		String results = this.getGame().toString(); 
		JOptionPane.showMessageDialog(null, "Game Over: "+results);
	}
	/**
	 * drawRobots
	 */
	public void drawRobots() {

		//drawGraph Robots
		Iterator<Robots> r_iter = this.getRobList().iterator();
		while(r_iter.hasNext()) {
			Robots r = r_iter.next();
			StdDraw.picture(r.getPosX(),r.getPosY(), "data\\p"+r.getId()+".png");

		}

		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.06);
		StdDraw.text(get_x().get_min()+0.007,get_y().get_max(),"for pickacho:0 for balbazor:1 for squirtel:2 for carmander:3 for snorlax:4");

	}

	/**
	 * drawFruits
	 */
	public void drawFruits() {
		//drawGraph Robots
		Iterator<Fruits> f_iter = this.getFruitList().iterator();
		while(f_iter.hasNext()) {
			Fruits f = f_iter.next();
			if(f.getType() == 1) {
				StdDraw.picture(f.getPosX(),f.getPosY(), "data\\apple.png");

			}
			// type -1
			else {
				StdDraw.picture(f.getPosX(),f.getPosY(), "data\\banana.png");
			}
		}

	}


	/**
	 * Open windo that adjusted scale bound by the nodes
	 * drawing the Graph by drawGraph points using iterator on each nodes location including key
	 * and drawGraph lines to the edges including direction and weight
	 * 
	 */
	public void drawGraph() {
		StdDraw.clear();
		Range x = this.get_x();
		Range y = this.get_y();


		StdDraw.setXscale(x.get_min() - x.get_min()*0.00001, x.get_max() + x.get_min()*0.00001);
		StdDraw.setYscale(y.get_min() - y.get_min()*0.00001, y.get_max() + y.get_min()*0.00001);
		// background
		String back = this.getGame().toString();
		JSONObject obj;
		try {
			obj = new JSONObject(back);

			String backpicture = obj.getJSONObject("GameServer").getString("graph");
			double AverageX = ((this.get_x().get_max() + this.get_x().get_min())/2)*0.00001;
			double AverageY = ((this.get_y().get_max() + this.get_y().get_min())/2)*0.00001;



			StdDraw.picture(this.get_x().get_max() - 0.0083, this.get_y().get_min()+0.0031, "data\\map.png",0.05,0.01);
		} catch (JSONException e) {

			e.printStackTrace();
		}

		// directions compute;
		double directionX = 0;
		double directionY = 0;
		double middleX = 0;
		double middleY = 0;

		// draw points
		Iterator<node_data> iter = this.getGraph().get_graphAlgo().getV().iterator();

		Iterator<edge_data> iter_edge;
		while (iter.hasNext()) {
			nodeData current = (nodeData) iter.next();
			iter_edge = current.get_edges().values().iterator();
			// drawGraph edges
			while (iter_edge.hasNext()) {
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setPenRadius(0.005);
				edgeData current_edge = (edgeData) iter_edge.next();


				// calculations
				directionX = current.getLocation().x()*0.1 + current_edge.getNodeDest().getLocation().x()*0.9;
				directionY = current.getLocation().y()*0.1 + current_edge.getNodeDest().getLocation().y()*0.9;


				// drawGraph edges
				StdDraw.line(current.getLocation().x(), current.getLocation().y(),
						current_edge.getNodeDest().getLocation().x(),
						current_edge.getNodeDest().getLocation().y());

				// drawGraph direction
				StdDraw.setPenColor(Color.CYAN);
				StdDraw.setPenRadius(0.015);
				StdDraw.point(directionX, directionY);
				// edge weight 
				/*
				 * middleX = (current.getLocation().x() +
				 * current_edge.getNodeDest().getLocation().x()) / 2; middleY =
				 * (current.getLocation().y() + current_edge.getNodeDest().getLocation().y()) /
				 * 2; StdDraw.setPenColor(new Color(0, 153, 0)); StdDraw.setFont(new
				 * Font("Arial", Font.PLAIN, 20)); StdDraw.text(middleX, middleY + 0.0001,
				 * String.valueOf(current_edge.getWeight()));
				 */

			}
			// drawGraph point
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.02);
			StdDraw.point(current.getLocation().x(), current.getLocation().y());

			// node key
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
			StdDraw.text(current.getLocation().x(), current.getLocation().y() + 0.0001, String.valueOf(current.getKey()));

			// drawGraph timer
			StdDraw.text(this.get_x().get_max() - 0.002, this.get_y().get_min(), "Time: "+this.getGame().timeToEnd());

		}
	}

	/**
	 * get number of robots
	 */
	private void RobotsStartPosition() {
		JSONObject GameJson;
		try {
			GameJson = new JSONObject(this.getGame().toString()).getJSONObject("GameServer");
			int Robot_num = GameJson.getInt("robots"); 
			setRobList(new ArrayList<Robots>(Robot_num));			
			for (int a = 0; a < Robot_num; a++) {
				this.getGame().addRobot(StdDraw.dialogRobots(a, this.getGraph().get_graphAlgo().nodeSize()));
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

	private void RobotsAutoPosition() {
		JSONObject GameJson;
		try {
			GameJson = new JSONObject(this.getGame().toString()).getJSONObject("GameServer");
			int Robot_num = GameJson.getInt("robots"); 
			int nextNode;
			// if less fruit then robots
			setRobList(new ArrayList<Robots>(Robot_num));			
			for (int i = 0; i < Robot_num; i++) {
				nextNode = this.getAuto().nearestNode(this.getFruitList().get(i));
				if(nextNode == -1) {
					this.getGame().addRobot((i+6)%this.getGraph().get_graphAlgo().nodeSize());
				}
				else {
					this.getGame().addRobot(nextNode);
					this.getGame().chooseNextEdge(i, nextNode);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// adding robots
		Iterator<String> iter = this.getGame().getRobots().iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			System.out.println(s);
			this.getRobList().add(new Robots(s));
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

	public Graph_Algo getGraph() {
		return _graph;
	}

	public void setGraph(Graph_Algo _graph) {
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

	public ashAutomatic getAuto() {
		return _driver;
	}

	public void setAuto(ashAutomatic _auto) {
		this._driver = _auto;
	}
	/**** data *****/

	private ashAutomatic _driver;

	private Range _x, _y;
	private game_service _game;
	private Graph_Algo _graph;
	private ArrayList<Robots> _rob_list;
	private ArrayList<Fruits> _fruit_list;
}
