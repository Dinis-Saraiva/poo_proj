package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interactable{

	public ParedeRachada(Point2D point2d) {
		super(point2d, 1, "ParedeRachada",false);
	}

	@Override
	public boolean interaction(Moveable g) {
		if(g instanceof Empilhadora) {
			if(((Empilhadora) g).hasMartelo()) {
				GameEngine.getInstance().remove(this);
				return true;
			}
		}
		return false;
	}
}