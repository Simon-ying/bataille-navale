package ensta.model.ship;

import ensta.util.Orientation;

public class Carrier extends AbstractShip{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Carrier(String name, Orientation o) {
		super(name, 'C', 5, o);
	}
	public Carrier(Orientation o) {
		super("Default_Carrier", 'C', 5, o);
	}
	public Carrier() {
		super("Default_Carrier", 'C', 5, Orientation.EAST);
	}


}
