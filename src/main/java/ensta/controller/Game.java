package ensta.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ensta.ai.PlayerAI;
import ensta.model.Board;
import ensta.model.Coords;
import ensta.model.Hit;
import ensta.model.Player;
/*
import ensta.model.ship.BattleShip;
import ensta.model.ship.Carrier;
import ensta.model.ship.Destroyer;
import ensta.model.ship.Submarine;
*/
import ensta.util.ColorUtil;
import ensta.model.ship.*;
public class Game {
	/*
	 * *** Constante
	 */
	public static final File SAVE_FILE = new File("savegame.dat");

	/*
	 * *** Attributs
	 */
	private Player player1;
	private Player player2;
	private boolean isNultiplayer;
//	private List<AbstractShip> ships1 = new ArrayList<>();
//	private List<AbstractShip> ships2 = new ArrayList<>();
	private Scanner sin;

	/*
	 * *** Constructeurs
	 */
	public Game() {
	}

	public Game init() throws Exception {
		if (!loadSave()) {
			// TODO init boards
			Board b1 = new Board("player 1");
			Board b2 = new Board("player 2");
//			ships1.add(new Destroyer());
//			ships1.add(new Submarine());
//			ships1.add(new Submarine());
//			ships1.add(new Battleship());
//			ships1.add(new Carrier());
//			ships2.add(new Destroyer());
//			ships2.add(new Submarine());
//			ships2.add(new Submarine());
//			ships2.add(new Battleship());
//			ships2.add(new Carrier());
			// TODO init this.player1 & this.player2
			this.player1 = new Player(b1, b2, createDefaultShips());
			this.player2 = new PlayerAI(b2, b1, createDefaultShips());
			// TODO place player ships
			this.player2.putShips();
			this.player1.putShips();
		}
		return this;
	}

	/*
	 * *** Méthodes
	 */
	public void run() throws Exception {
		Coords coords = new Coords();
		Board b1 = player1.getBoard();
		Board b2 = player2.getBoard();
		Hit hit;

		// main loop
		b1.print();
		boolean done;
		do {
			hit = player1.sendHit(coords); // TODO player1 send a hit
			boolean strike = hit != Hit.MISS; // TODO set this hit on his board (b1)
			b2.setHit(hit, coords);
			b2.print(true);
			done = updateScore();
			
			System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

			// save();

			if (!done && !strike) {
				do {
					hit = player2.sendHit(coords); // TODO player2 send a hit.
					b1.setHit(hit, coords);
					strike = hit != Hit.MISS;
					if (strike) {
						b1.print();
					}
					System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
					done = updateScore();

					if (!done) {
//						save();
					}
				} while (strike && !done);
			}
			b1.print();
		} while (!done);

		SAVE_FILE.delete();
		System.out.println(String.format("joueur %d gagne", player1.isLose() ? 2 : 1));
//		sin.close();
	}

	private void save() {
//		try {
//			// TODO bonus 2 : uncomment
//			 if (!SAVE_FILE.exists()) {
//			 SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
//			 }
//
//			// TODO bonus 2 : serialize players
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private boolean loadSave() {
//		if (SAVE_FILE.exists()) {
//			try {
//				// TODO bonus 2 : deserialize players
//
//				return true;
//			} catch (IOException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
		return false;
	}
	
	private boolean updateScore() {
		for (Player player : new Player[] { player1, player2 }) {
			int destroyed = 0;
			for (AbstractShip ship : player.getShips()) {
				if (ship.isSunk()) {
					destroyed++;
				}
			}

			player.setDestroyedCount(destroyed);
			player.setLose(destroyed == player.getShips().length);
			if (player.isLose()) {
				return true;
			}
		}
		return false;
	}
	

	private String makeHitMessage(boolean incoming, Coords coords, Hit hit) {
		String msg;
		ColorUtil.Color color = ColorUtil.Color.RESET;
		switch (hit) {
		case MISS:
			msg = hit.toString();
			break;
		case STRIKE:
			msg = hit.toString();
			color = ColorUtil.Color.RED;
			break;
		default:
			msg = hit.toString() + " coulé";
			color = ColorUtil.Color.RED;
		}
		msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords.getY())),
				(coords.getX() + 1), msg);
		return ColorUtil.colorize(msg, color);
	}
	
	private static List<AbstractShip> createDefaultShips() {
		return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new Battleship(),
				new Carrier() });
	}
	
}
