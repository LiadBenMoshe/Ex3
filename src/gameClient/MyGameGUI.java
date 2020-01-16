package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MyGameGUI implements Runnable {

	public MyGameGUI() {
		int scencario = StdDraw.dialogScenario();

		game_service game = Game_Server.getServer(scencario); // you have [0,23] games


		DGraph graph = new DGraph();
		graph.init(game.getGraph());
		Graph_Algo graphAlgo = new Graph_Algo(graph);
		setKml(new KML_Logger(scencario));
		setGraphAlgo(graphAlgo);
		setGame(game);
		init();
	}

	/**
	 * icons from https://www.flaticon.com/
	 */
	private void init() {
		StdDraw.setCanvasSize(1050, 600);
		set_x(this.getGraphAlgo().get_Dgraph().GraphScaleX());
		set_y(this.getGraphAlgo().get_Dgraph().GraphScaleY());
		Fruits();
		drawGraph();
		StdDraw.Visible();

		//setManual(new ManualPlayer(this));
		setAutoPlayer(new AutomaticPlayer(this));
		drawFruits();
		drawRobots();
		StdDraw.enableDoubleBuffering();
		StdDraw.show();

		run();
	}

	private void repaint() {
		this.drawGraph();
		this.drawRobots();
		this.drawFruits();
		StdDraw.show();
		// StdDraw.pause(50);
	}

	/**
	 * thread that start the game
	 */
	public void run() {
		this.getGame().startGame();
		music();
		while(this.getGame().isRunning()) {

			//this.getManual().moveRobotsGUI();
			this.getAutoPlayer().moveRobotsAuto();
			


			try {
				/* Thread.sleep(0); */
				repaint();
			} catch(Exception e) {
				e.printStackTrace();
			}

		}

		// game finished print results
		getKml().KMLtoFile();
		String results = this.getGame().toString();
		JOptionPane.showMessageDialog(null, "Game Over: " + results);
	}

	/**
	 * drawRobots
	 */
	private void drawRobots() {
		Iterator<Robots> r_iter = this.getRobList().iterator();
		while (r_iter.hasNext()) {
			Robots r = r_iter.next();
			if(r.getSpeed()==2) {
				StdDraw.picture(r.getPosX(), r.getPosY(), "data\\p" + (r.getId()+10) + ".png");
			}
			else if(r.getSpeed()==5) {
				StdDraw.picture(r.getPosX(), r.getPosY(), "data\\p" + (r.getId()+5) + ".png");

			}
			else
				StdDraw.picture(r.getPosX(), r.getPosY(), "data\\p" + r.getId() + ".png");

		}

		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.06);
		StdDraw.text(get_x().get_max()-0.007,get_y().get_max(),"for pickacho:0 for balbazor:1 for squirtel:2 for carmander:3 for snorlax:4");


	}


	/**
	 * drawFruits
	 */
	private void drawFruits() {
		// drawGraph Robots
		Iterator<Fruits> f_iter = this.getFruitList().iterator();
		while (f_iter.hasNext()) {
			Fruits f = f_iter.next();
			if (f.getType() == 1) {
				StdDraw.picture(f.getPosX(), f.getPosY(), "data\\apple.png");

			}
			// type -1
			else {
				StdDraw.picture(f.getPosX(), f.getPosY(), "data\\banana.png");
			}
		}

	}

	/**
	 * Open windo that adjusted scale bound by the nodes drawing the Graph by
	 * drawGraph points using iterator on each nodes location including key and
	 * drawGraph lines to the edges including direction and weight
	 * 
	 */
	public void drawGraph() {
		StdDraw.clear();
		Range x = this.get_x();
		Range y = this.get_y();

		StdDraw.setXscale(x.get_min() - x.get_min() * 0.00001, x.get_max() + x.get_min() * 0.00001);
		StdDraw.setYscale(y.get_min() - y.get_min() * 0.00001, y.get_max() + y.get_min() * 0.00001);


		StdDraw.picture((this.get_x().get_max()+this.get_x().get_min())/2, (this.get_y().get_min()+this.get_y().get_max())/2, "data\\map.png", 0.05,
				0.02);



		// directions compute;
		double directionX = 0;
		double directionY = 0;

		// for drawing edge weight
		/*
		 * double middleX = 0; double middleY = 0;
		 */

		// draw points
		Iterator<node_data> iter = this.getGraphAlgo().get_Dgraph().getV().iterator();

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
				directionX = current.getLocation().x() * 0.1 + current_edge.getNodeDest().getLocation().x() * 0.9;
				directionY = current.getLocation().y() * 0.1 + current_edge.getNodeDest().getLocation().y() * 0.9;

				// drawGraph edges
				StdDraw.line(current.getLocation().x(), current.getLocation().y(),
						current_edge.getNodeDest().getLocation().x(), current_edge.getNodeDest().getLocation().y());

				// drawGraph direction
				StdDraw.setPenColor(Color.CYAN);
				StdDraw.setPenRadius(0.015);
				StdDraw.point(directionX, directionY);
				// drawing edge weight

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
			StdDraw.text(current.getLocation().x(), current.getLocation().y() + 0.0001,
					String.valueOf(current.getKey()));

			// drawGraph timer
			StdDraw.text(this.get_x().get_max() - 0.002, this.get_y().get_min(), "Time: " + this.getGame().timeToEnd());

		}
	}



	private void Fruits() {
		// adding fruits
		this.setFruitList(new ArrayList<Fruits>());
		Iterator<String> iter = this.getGame().getFruits().iterator();
		while (iter.hasNext()) {
			this.getFruitList().add(new Fruits(iter.next()));
		}
	}
	public static void music() 
	{  
		
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;

		ContinuousAudioDataStream loop = null;

		try
		{
			InputStream test = new FileInputStream("data\\Pokemon.wav");
			BGM = new AudioStream(test);
			AudioPlayer.player.start(BGM);
			MD = BGM.getData();

			loop = new ContinuousAudioDataStream(MD);

		}
		catch(FileNotFoundException e){

		}
		catch(IOException error)
		{

		}
		MGP.start(loop);
	}

	public Graph_Algo getGraphAlgo() {
		return _graphAlgo;
	}

	public game_service getGame() {
		return _game;
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


	public AutomaticPlayer getAutoPlayer() {
		return _auto;
	}

	public ManualPlayer getManual() {
		return _manual;
	}
	public KML_Logger getKml() {
		return _kml;
	}



	/****private  data *****/
	private AutomaticPlayer _auto;
	private ManualPlayer _manual;
	private Range _x, _y;
	private game_service _game;
	private Graph_Algo _graphAlgo;
	private ArrayList<Robots> _rob_list;
	private ArrayList<Fruits> _fruit_list;
	private KML_Logger _kml;



	/******* getters/setter *****/


	private void setKml(KML_Logger _kml) {
		this._kml = _kml;
	}



	private void setManual(ManualPlayer _manual) {
		this._manual = _manual;
	}
	private void setGame(game_service _game) {
		this._game = _game;
	}

	private void setGraphAlgo(Graph_Algo _graph) {
		this._graphAlgo = _graph;
	}

	private Range get_x() {
		return _x;
	}

	private void set_x(Range _x) {
		this._x = _x;
	}

	private Range get_y() {
		return _y;
	}

	private void set_y(Range _y) {
		this._y = _y;
	}

	private void setAutoPlayer(AutomaticPlayer _auto) {
		this._auto = _auto;
	}

}
