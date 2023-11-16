package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Moveable{
	public int getEnergia() {
		return energia;
	}
	private int energia=100;
	public Empilhadora(Point2D position) {
		super(position, 2, "Empilhadora_D");
	}
	public void tryMove(int key) {
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Direction novaDirecao=Direction.directionFor(key);
		Point2D newPosition = super.getPosition().plus(novaDirecao.asVector());	//
		changeImage(key);
		if(validMove(novaDirecao)){
			move(newPosition);
			energia--;
		}
		
	}
	private void changeImage(int key) {
		switch(key) {
			case KeyEvent.VK_DOWN:
				setName("Empilhadora_D");
				break;
			case KeyEvent.VK_UP:
				setName("Empilhadora_U");
				break;
			case KeyEvent.VK_LEFT:
				setName("Empilhadora_L");
				break;
			case KeyEvent.VK_RIGHT:
				setName("Empilhadora_R");
				break;
		}
	}
	@Override
	public boolean validMove(Direction direcao) {
		List<GameElement> resultado=new ArrayList<>();
		Point2D position = super.getPosition().plus(direcao.asVector());
		resultado=GameEngine.getInstance().select(p->p.getPosition().equals(position));
		for(GameElement g:resultado) {
			if(!g.isTrasposable()) {
				if(g.isMoveable()) {
					if(g instanceof Moveable)
						return ((Moveable) g).validMove(direcao);
					}
				return false;
				}
		}
		return true;
	}

}