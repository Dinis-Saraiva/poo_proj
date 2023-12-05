package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends Pickable {

	public Martelo(Point2D point2d) {
		super(point2d, 1, "Martelo");
	}

	@Override
	public void effect() {
		GameEngine.getInstance().getBobcat().addMartelo();
		
	}
}