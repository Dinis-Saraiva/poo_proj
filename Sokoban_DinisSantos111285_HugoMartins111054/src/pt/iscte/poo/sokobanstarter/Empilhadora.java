package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Moveable{
	private final int ENERGIA_INICIAL=100;		//energia inicial
	private int energia;
	private boolean martelo=false;
	
	public Empilhadora(Point2D p) {
		super(p, 2, "Empilhadora_D");
		setEnergia(ENERGIA_INICIAL);
	}

	public void tryMove(int key) {
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Direction novaDirecao=Direction.directionFor(key);
		changeImage(key);
		if(validMove(novaDirecao)){
			move(super.getPosition().plus(novaDirecao.asVector()));
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
		Point2D position = super.getPosition().plus(direcao.asVector());
		List<GameElement> resultado=GameEngine.getInstance().select(p->p.getPosition().equals(position));
		boolean result=true;
		for(GameElement g:resultado) {
			if(!g.isTrasposable())
				if(g instanceof Moveable)
					if(!((Moveable) g).validMove(direcao))
						return false;
		}
		for(GameElement g:resultado) {
			if(!g.isTrasposable()) {
				if(g instanceof Moveable)
					continue;
				if(g instanceof Pickable) {
					((Pickable)g).pick();
					continue;
				}
				if(g instanceof Interactable) {
					if(((Interactable)g).interaction(this))
						result=false;
					continue;
				}
				return false;
			}
		}
		return result;
	}
	
	
	public void setEnergia(int energia) {
		this.energia = energia;
	}
	public int getEnergia() {
		return energia;
	}
	public boolean hasMartelo() {
		return martelo;
	}
	public void addMartelo() {
		martelo=true;
	}
	public void decreaseEnergy() {
		energia--;
	}

}