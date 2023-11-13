package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

//import java.util.Random;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora implements ImageTile{

	private Point2D position;
	private String imageName;
	
	public Empilhadora(Point2D initialPosition){
		position = initialPosition;
		imageName = "Empilhadora_D";
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public void move(int key) {
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Direction novaDirecao=Direction.directionFor(key);
		Point2D newPosition = position.plus(novaDirecao.asVector());	//
		changeImage(key);
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 ){
			position = newPosition;
		}
	}
	public void changeImage(int key) {
		switch(key) {
			case KeyEvent.VK_DOWN:
				imageName="Empilhadora_D";
				break;
			case KeyEvent.VK_UP:
				imageName="Empilhadora_U";
				break;
			case KeyEvent.VK_LEFT:
				imageName="Empilhadora_L";
				break;
			case KeyEvent.VK_RIGHT:
				imageName="Empilhadora_R";
				break;
		}
	}
}