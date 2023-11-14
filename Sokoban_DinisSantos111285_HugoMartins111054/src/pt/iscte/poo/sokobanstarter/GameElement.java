package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	private Point2D position;
	private String name;
	private int layer;

	public GameElement(Point2D position, int layer, String name) {
		super();
		this.position = position;
		this.layer = layer;
		this.name = name;
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
		//else if(c=='=') 
			// new Chao(p); adicionar vazio
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