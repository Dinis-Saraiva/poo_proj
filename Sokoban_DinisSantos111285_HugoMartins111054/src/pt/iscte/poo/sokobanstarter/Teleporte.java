package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interactable {
	private Teleporte saida;
	public Teleporte(Point2D point2d) {
		super(point2d, 1, "Teleporte",false);
		connectTP();
	}

	@Override
	public boolean interaction(Moveable g) {
		 List<GameElement> resultado=GameEngine.getInstance().select(p->p.getPosition().equals(saida.getPosition()));
		for(GameElement t:resultado) {
			//verifica se algo esta a bloquear a saida
			if(!t.isTrasposable()&&!(t instanceof Teleporte)){
				return false;
			}
		}
		g.move(saida.getPosition());
		if(g instanceof Empilhadora)
			((Empilhadora)g).decreaseEnergy();
		return true;
	}
	public void setSaida(Teleporte saida) {
		this.saida = saida;
	}

	private void connectTP() {
		List<GameElement> teleportes=GameEngine.getInstance().select(p->p instanceof Teleporte);
		for(GameElement t:teleportes) {
			if(!t.getPosition().equals(this.getPosition())) {
				this.setSaida((Teleporte)t);
				((Teleporte)t).setSaida(this);
			}
		}
		
	}
}