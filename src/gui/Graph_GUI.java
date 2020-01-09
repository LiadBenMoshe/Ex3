package gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JMenuBar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import algorithms.Graph_Algo;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;


public class Graph_GUI extends JFrame implements ActionListener ,Runnable  {



	/**
	 * initialize new GUI
	 * creating empty graph
	 */
	public Graph_GUI() {
		set_graphGui(new Graph_Algo());
		initGUI();
	}

	/**
	 * initialize new gui
	 * @param ga - graph algo 
	 */
	public Graph_GUI(Graph_Algo ga) {
		set_graphGui(ga);
		initGUI();
	}

	/**
	 * initialize GUI by setting size, menu, panels and layout as null
	 * using swing.timer to listen for changes on the graph
	 * sorce code https://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html
	 */
	private void initGUI() {
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu1();
		panel1();
		panel3();
		panel2();
		set_changes(this.get_graphGui().get_graphAlgo().getMC());
		this.get_console().setText("Click Show Graph to see the graph");
		if (this != null) this.setVisible(true);
		set_draw(new Draw(this.get_graphGui().get_graphAlgo()));
		
		// thread that listen changes in the graph
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				run();
			}
		};
		javax.swing.Timer t = new javax.swing.Timer(1000, taskPerformer);
		t.setRepeats(true);
		t.start();
	}

	@Override
	public void run() {
		int mc = this.get_graphGui().get_graphAlgo().getMC();
		if(this.get_changes() != mc) {
			set_changes(mc);
			this.get_draw().draw_graph(0, new ArrayList<node_data>());
			StdDraw.Visible();
		}
	}

	/**
	 * initialize panel1 bounds,background color
	 * adding label 'Algorithms'
	 * and create four new object of our project class panel1_button and jframe
	 * that are used for button for each algoritem and add an Action listener
	 * to each one
	 */
	private void panel1() {
		set_panel1(new JPanel());  
		this.get_panel1().setLayout(null);
		this.get_panel1().setBounds(0,0,300,this.getHeight()); 
		Color dark = new Color(44, 45, 50);
		this.get_panel1().setBackground(dark);  


		//labels
		JLabel label=new JLabel("Algorithms"); 
		label.setForeground(Color.WHITE);
		label.setFont((new Font("Arial", Font.PLAIN, 30)));
		label.setBounds(75, 20, 200, 100);

		// buttons
		panel1_Button b1 = new panel1_Button("is Connected");
		panel1_Button b2 = new panel1_Button("Shortest Path Dist");
		panel1_Button b3 = new panel1_Button("Shortest Path");
		panel1_Button b4 = new panel1_Button("TSP");
		b1.getbutton().addActionListener(this);
		b2.getbutton().addActionListener(this);
		b3.getbutton().addActionListener(this);
		b4.getbutton().addActionListener(this);

		this.get_panel1().add(label);
		this.get_panel1().add(b1.getbutton());
		this.get_panel1().add(b2.getbutton());
		this.get_panel1().add(b3.getbutton());
		this.get_panel1().add(b4.getbutton());
		this.add(this.get_panel1());  
	}

	/**
	 * initialize panel2 bounds,background color
	 * create button that will open a Graph draw with Action listener
	 * and uneditable text field that will use for print messges
	 */
	private void panel2() {
		set_panel2(new JPanel());  
		this.get_panel2().setLayout(null);
		this.get_panel2().setBounds(300,0,this.getWidth()-300,this.getHeight()); 
		Color green = new Color(16, 152, 150);
		this.get_panel2().setBackground(green);  

		// text field
		set_console(new JTextField());  
		this.get_console().setBounds(70,420,550,50);  
		this.get_console().setBackground(new Color(242, 242, 244));
		this.get_console().setEditable(false);
		this.get_console().setFont((new Font("Arial", Font.PLAIN, 20)));
		this.get_console().setHorizontalAlignment(SwingConstants.CENTER);

		//button
		set_show_graph(new JButton("Show Graph"));
		this.get_show_graph().setBounds(270, 490, 150, 60);
		get_show_graph().setForeground(Color.WHITE);
		get_show_graph().setFont((new Font("Arial", Font.PLAIN, 15)));
		get_show_graph().setBackground(new Color(44, 45, 50));
		get_show_graph().setBorderPainted(false);
		get_show_graph().addActionListener(this);


		this.get_panel2().add(get_show_graph());
		this.get_panel2().add(this.get_console());
		this.add(this.get_panel2());  

	}

	/**
	 * initialize panel3 bounds,background color
	 * creating new Label 'Graph GUI'
	 */
	private void panel3() {
		set_panel3(new JPanel());  
		this.get_panel3().setLayout(null);
		this.get_panel3().setBounds(300,60,this.getWidth()-300,200); 
		Color dark_green = new Color(3, 81, 81);

		// label 

		JLabel label2=new JLabel("Graph GUI"); 
		label2.setForeground(Color.WHITE);
		label2.setFont((new Font("Arial", Font.PLAIN, 50)));
		label2.setBounds(220,50, 500, 100);
		this.get_panel3().setBackground(dark_green);  
		this.get_panel3().add(label2);
		this.add(this.get_panel3());  
	}
	/**
	 * initialize new MenuBar setting name and adding items
	 * using our project menu class and Jframe
	 */
	private void menu1() {
		set_mb(new JMenuBar());
		// file menu
		Menu menu1 = new Menu("File");
		menu1.addItem("Save Graph");
		menu1.addItem("Load Graph");
		menu1.getMenu().getItem(0).addActionListener(this);
		menu1.getMenu().getItem(1).addActionListener(this);
		this.get_mb().add(menu1.getMenu());

		// graph options
		Menu menu2 = new Menu("Graph Options");
		menu2.addItem("Add Node");
		menu2.addItem("Connect");
		menu2.addItem("Remove Node");
		menu2.addItem("Remove Edge");
		menu2.getMenu().getItem(0).addActionListener(this);
		menu2.getMenu().getItem(1).addActionListener(this);
		menu2.getMenu().getItem(2).addActionListener(this);
		menu2.getMenu().getItem(3).addActionListener(this);
		this.get_mb().add(menu2.getMenu());

		this.setJMenuBar(this.get_mb());
	}

	/**
	 * painting a line after label 'algorithmes'
	 * @param g - graphics
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// line for label 1
		g.setColor(Color.WHITE);
		g.setFont((new Font("Arial", Font.PLAIN, 40)));
		g.drawLine(0, 170, 300, 170);

	}

	/**
	 * this function listen to each event(click on gui buttons/menu)
	 * then preform an action and printing to the text Field
	 * using JOptionPane.showInputDialog to read user input and
	 * surrounded with try/catch because methods could throw
	 * Exception if there is wrong input from the user
	 * @param e - event for the gui windo
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// click on show Graph
		if(e.getActionCommand().equals("Show Graph")) {
			//set_draw(new Draw(this.get_graphGui().get_graphAlgo()));
			if(this.get_graphGui().get_graphAlgo().get_graph().isEmpty()) {
				this.get_draw().drawEmptyGraph();
				StdDraw.Visible();
			}
			else {
				this.get_draw().draw_graph(0, new ArrayList<node_data>());
				StdDraw.Visible();
			}
			this.get_console().setText("");
		}
		// is connected
		if (e.getActionCommand().equals("is Connected")) {
			this.get_console().setText("is Connected: " + String.valueOf(this.get_graphGui().isConnected()));
		}
		// shortest path dist
		if (e.getActionCommand().equals("Shortest Path Dist")) {
			try {
				String name=JOptionPane.showInputDialog(this,"Please Enter src and dest node\n"
						+ "In format of int,int - src,dest"); 
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					double length = this.get_graphGui().shortestPathDist(Integer.parseInt(split[0]),
							Integer.parseInt(split[1]));
					if(length != Integer.MAX_VALUE) {
						this.get_console().setText("shortest Path Dist length is: " + String.valueOf(length));
					}
					else {
						this.get_console().setText("shortest Path Dist length is: Infinity");
					}
				}
			}
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}
		// shortest path
		if (e.getActionCommand().equals("Shortest Path")) {
			try {
				String name=JOptionPane.showInputDialog(this,"Please Enter src and dest node\n"
						+ "Format of int,int - src,dest"); 
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					ArrayList<node_data> list = (ArrayList<node_data>) this.get_graphGui().shortestPath(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
					if(list != null) {
						this.get_console().setText("shortest Path between node "+split[0]+" to "+split[1]+" is marked with orange");
						this.get_draw().draw_graph(1,list);
						StdDraw.Visible();
					}
					else {
						this.get_console().setText("There isn't a path between node "+split[0]+" to "+split[1]+"");
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException err) { 
				this.get_console().setText("Err: Wrong input format");	
			} 
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// TSP
		if (e.getActionCommand().equals("TSP")) {
			try {
				String name=JOptionPane.showInputDialog(this,"Please Enter list of different nodes\n"
						+ "In format of int,int,int.... - node.key3,node.key2,node.key3.."); 

				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					ArrayList<Integer> list = new ArrayList<Integer>();
					for(int i = 0; i < split.length; i++) {
						list.add(Integer.parseInt(split[i]));
					}
					ArrayList<node_data> Tsp = (ArrayList<node_data>) this.get_graphGui().TSP(list);
					if(Tsp != null) {
						this.get_console().setText("TSP result is marked with orange");
						this.get_draw().draw_graph(1,Tsp);
						StdDraw.Visible();
					}
					else {
						this.get_console().setText("There isn't a path between the given nodes list");
					}
				}
			}
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on file -> save graph
		if(e.getActionCommand().equals("Save Graph")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter name of file");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					this.get_graphGui().save(name);
					this.get_console().setText("The file save in your project directory");
				}
			}
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on file -> load graph
		if(e.getActionCommand().equals("Load Graph")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter file\n"
						+ "that are save in your project directory");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					this.get_graphGui().init(name);
					this.get_console().setText("The file load from project directory");
					set_draw(new Draw(this.get_graphGui().get_graphAlgo()));
					StdDraw.Visible();
					this.get_draw().draw_graph(0, new ArrayList<node_data>());
				}
			}
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on options -> add node
		if(e.getActionCommand().equals("Add Node")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter Node location\n"
						+ "In format of double,double.. - node.x,node.y");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					nodeData n = new nodeData(new Point3D(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
					this.get_graphGui().get_graphAlgo().addNode(n);
					this.get_draw().draw_graph(0, new ArrayList<node_data>());
					StdDraw.Visible();
					this.get_console().setText("Node succesfully add - Show Graph to observe");
				}
			}
			catch(ArrayIndexOutOfBoundsException err) { 
				this.get_console().setText("Err: Wrong input format");	
			} 
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on options -> connect
		if(e.getActionCommand().equals("Connect")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter src,dest node keys and weight for edge\n"
						+ "In format of int,int,double.. - src.key,dest.key,weight");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					this.get_graphGui().get_graphAlgo().connect(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Double.parseDouble(split[2]));
					this.get_draw().draw_graph(0, new ArrayList<node_data>());
					StdDraw.Visible();
					this.get_console().setText("Connect succesfully - Show Graph to observe");
				}
			}
			catch(ArrayIndexOutOfBoundsException err) { 
				this.get_console().setText("Err: Wrong input format");	
			} 
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on options -> remove Node
		if(e.getActionCommand().equals("Remove Node")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter node key for removal\n"
						+ "In format of int.. - node.key");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					this.get_graphGui().get_graphAlgo().removeNode(Integer.parseInt(name));
					this.get_draw().draw_graph(0, new ArrayList<node_data>());
					StdDraw.Visible();
					this.get_console().setText("Node "+name+" is now removed - Show Graph to observe");
				}
			}
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}

		// on options -> remove edge
		if(e.getActionCommand().equals("Remove Edge")) {
			try {
				String name = JOptionPane.showInputDialog(this,"Please Enter src,dest key for remove the Edge between them\n"
						+ "In format of int,int.. - src.key,dest.key");
				if(name == null) {
					this.get_console().setText("Err: no input is entered please try again");
				}
				else {
					String[] split = name.split(",");
					this.get_graphGui().get_graphAlgo().removeEdge(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
					this.get_draw().draw_graph(0, new ArrayList<node_data>());
					StdDraw.Visible();
					this.get_console().setText("Node "+name+" is now removed - Show Graph to observe");
				}
			}
			catch(ArrayIndexOutOfBoundsException err) { 
				this.get_console().setText("Err: Wrong input format");	
			} 
			catch(Exception err) {
				this.get_console().setText("Err: "+err.getMessage());
			}
		}
	}


	public Graph_Algo get_graphGui() {
		return _graph_gui;
	}

	public JPanel get_panel1() {
		return _panel1;
	}

	public JTextField get_console() {
		return _console;
	}


	public JPanel get_panel2() {
		return _panel2;
	}
	public JPanel get_panel3() {
		return _panel3;
	}

	public Draw get_draw() {
		return _d;
	}
	public int get_changes() {
		return _changes;
	}

	public JButton get_show_graph() {
		return _show_graph;
	}
	public JMenuBar get_mb() {
		return _mb;
	}

	/**** private data ****/
	private void set_panel2(JPanel _p) {
		this._panel2 = _p;
	}

	private void set_show_graph(JButton _show_graph) {
		this._show_graph = _show_graph;
	}

	private void set_mb(JMenuBar _mb) {
		this._mb = _mb;
	}

	private void set_panel3(JPanel _p) {
		this._panel3 = _p;
	}
	private void set_graphGui(Graph_Algo _graph_gui) {
		this._graph_gui = _graph_gui;
	}
	private void set_panel1(JPanel _p) {
		this._panel1 = _p;
	}

	private void set_console(JTextField _console) {
		this._console = _console;
	}

	private void set_draw(Draw _d) {
		this._d = _d;
	}

	private void set_changes(int _changes) {
		this._changes = _changes;
	}

	private int _changes;
	private Draw _d;
	private Graph_Algo _graph_gui;
	private JPanel _panel1, _panel2, _panel3;
	private JTextField _console;
	private JButton _show_graph;
	private JMenuBar _mb;
}
