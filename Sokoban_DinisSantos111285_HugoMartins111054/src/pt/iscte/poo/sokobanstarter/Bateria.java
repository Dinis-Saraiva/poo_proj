package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends Pickable {
	private static final int BATERIA=50;	//quantidade de energia que uma bateria dá
	public Bateria(Point2D point2d) {
		super(point2d, 1, "Bateria");
	}

	@Override
	public void effect() {
		GameEngine.getInstance().getBobcat().setEnergia(GameEngine.getInstance().getBobcat().getEnergia()+BATERIA);
	}
}
