package ensta.model.ship;

import ensta.util.Orientation;

public class Battleship extends AbstractShip {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Battleship(String name, Orientation o) {
		super(name, 'B', 2, o);
	}
	public Battleship(Orientation o) {
		super("Default_Battle_Ship", 'B', 2, o);
	}
	public Battleship() {
		super("Default_Battle_Ship", 'B', 2, Orientation.EAST);
	}


}
