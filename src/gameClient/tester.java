package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int scenario_num = 23;
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		MyGameGUI f = new MyGameGUI();
	}

}
