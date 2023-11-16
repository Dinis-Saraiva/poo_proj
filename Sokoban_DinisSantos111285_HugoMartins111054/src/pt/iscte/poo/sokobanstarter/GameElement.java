package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	private Point2D position;
	private String name;
	private int layer;
	
	private boolean trasposable;
	private boolean moveable;
	private boolean pickable;

	

	public boolean isMoveable() {
		return moveable;
	}

	public GameElement(Point2D position, int layer,String name,boolean trasposable, boolean moveable,
			boolean pickable) {
		super();
		this.position = position;
		this.name = name;
		this.layer = layer;
		this.trasposable = trasposable;
		this.moveable = moveable;
		this.pickable = pickable;
	}

	public boolean isTrasposable() {
		return trasposable;
	}

	public static GameElement createElement(char c, Point2D p) {
	
		if(c=='E') {
			Empilhadora e =new Empilhadora(p);
			GameEngine.getInstance().setBobcat(e);
			return e;
			}
		if(c=='C')
			return new Caixote(p);
		else if(c=='X')
			return new Alvo(p);
		else if(c=='B') 
			return new Bateria(p);
		else if(c=='#')
			return new Parede(p);
		else if(c==' ')
			return new Chao(p);
		else if(c=='=') 
			 return new Vazio(p);
		else if(c=='O')
			return new Buraco(p);
		else if(c=='P')
			return new Palete(p);
		else if(c=='M')
			return new Martelo(p);
		else if(c=='%')
			return new ParedeRachada(p);
		else if(c=='T')
			return new Teleporte(p);
	
		return new Chao(p);
		}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isPickable() {
		return pickable;
	}

	@Override
	public  String getName() {
		
		return name;
	}


	@Override
	public Point2D getPosition() {
		return  position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}