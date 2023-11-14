package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

//import java.util.Random;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{

	public Empilhadora(Point2D position) {
		super(position, 2, "Empilhadora_D");
	}
	public void move(int key) {
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Direction novaDirecao=Direction.directionFor(key);
		Point2D newPosition = super.getPosition().plus(novaDirecao.asVector());	//
		changeImage(key);
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 ){
			super.setPosition(newPosition);
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
}