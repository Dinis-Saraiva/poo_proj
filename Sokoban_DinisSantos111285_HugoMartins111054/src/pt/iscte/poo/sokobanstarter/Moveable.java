package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Moveable extends GameElement{

	public Moveable(Point2D position, int layer, String name) {
		super(position, layer, name,false);
	}
	
	public boolean validMove(Direction direcao) {
		List<GameElement> resultado=new ArrayList<>();
		Point2D position = super.getPosition().plus(direcao.asVector());
		resultado=GameEngine.getInstance().select(p->p.getPosition().equals(position));
		for(GameElement g:resultado) {
			if(!g.isTrasposable()) {
				if(g instanceof Interactable) {
					if(((Interactable)g).interaction(this))
						return true;
				}
				return false;
			}
		}
		move(position);
		return true;
	}
	public void move(Point2D position) {
		setPosition(position);
	}

}
