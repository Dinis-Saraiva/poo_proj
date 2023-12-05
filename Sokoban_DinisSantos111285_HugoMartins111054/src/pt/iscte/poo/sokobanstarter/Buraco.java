package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interactable{

	public Buraco(Point2D point2d) {
		super(point2d, 0, "Buraco",false);
	}

	@Override
	public boolean interaction(Moveable g) {
		if(g instanceof Palete) {
			super.setTrasposable(true);
			g.setTrasposable(true);
			((Moveable)g).move(getPosition());
			return true;
			}
		if(g instanceof Empilhadora)
			GameEngine.getInstance().resetLevel();
		GameEngine.getInstance().remove(g);
		return true;
		
	}
}