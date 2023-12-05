package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Pickable extends GameElement{
	public Pickable(Point2D position, int layer,String name) {
		super(position, layer, name, false);
	}
	public void pick() {
		effect();
		GameEngine.getInstance().remove(this);
	}
	public abstract void effect();
}
