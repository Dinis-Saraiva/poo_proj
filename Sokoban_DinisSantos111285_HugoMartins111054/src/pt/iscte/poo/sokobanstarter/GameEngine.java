package pt.iscte.poo.sokobanstarter;

//import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private final int MAX_LEVEL=6;		//Nivel Maximo
	private static String FICHEIRO="level5.txt";	//Nome do Nivel atual

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<GameElement> board;	// Lista dos objetos
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private String username;			//Nome do utilizador
	private Stats stats;

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		board=new ArrayList<>();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		
		// Criar o cenario de jogo
		username=ImageMatrixGUI.getInstance().askUser("Qual o username");
		scanWarehouse();
		stats=new Stats(username);
		
		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban: Player- "+ username + ", energia="+bobcat.getEnergia());
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada

		if (Direction.isDirection(key)) {  // se a tecla for UP/DOWN/LEFT/RIGHT, manda a empilhadora mover
			bobcat.tryMove(key);
			}
		if(key==KeyEvent.VK_SPACE)
			resetLevel();
		if(key==KeyEvent.VK_ESCAPE)
			endGame();
		gui.update();                  // redesenha a lista de ImageTiles na GUI, 
		                              // tendo em conta as novas posicoes dos objetos
		gui.setStatusMessage("Sokoban: Player- "+ username + ", energia="+bobcat.getEnergia());
		if(levelEnded()) {
			nextLevel();
		}
	}


	// Criacao da planta do armazem - so' chao neste exemplo 
	private void createWarehouse() {

		for (int y=0; y<GRID_HEIGHT; y++)
			for (int x=0; x<GRID_HEIGHT; x++)
				add(new Chao(new Point2D(x,y)));		
	}
	//scan a um warehouse escolhido manualmente
	public void scanWarehouse() {
		createWarehouse();      // criar o armazem
		try {

			Scanner sc= new Scanner(new File(FICHEIRO));
			String s= new String();
			char[] line;
			int countLine=0;
			
			while(sc.hasNextLine()) {
				s = sc.nextLine();
				line=s.toCharArray();
				for(int column=0; column<line.length; column++) {
					add(GameElement.createElement(line[column],new Point2D(column, countLine)));
				}
				countLine++;
			}
			sc.close();
			
		} catch(FileNotFoundException e) {
			System.err.println("Ficheiro  nao encontrado");
		}
		gui.update();
		
	}

	public void setBobcat(Empilhadora bobcat) {
		this.bobcat = bobcat;
	}
	
	public void add(GameElement e) {
		board.add(e);
		gui.addImage(e);
	}
	public void remove(GameElement e) {
		board.remove(e);
		gui.removeImage(e);
	}
	//Verifica se o nivel acabou verificando primeiro se ficou sem energia
	// e em segundo se passou o nivel ou seja todos os alvos têm um caixote
	public boolean levelEnded() {
		boolean hasCaixote=false;
		if(bobcat.getEnergia()<=0) {
			stats.addStat(-1, FICHEIRO);
			return true;
			}
			for(GameElement g:board) {
				if(g instanceof Alvo) {
					for(GameElement i:board) 
						if(i.getPosition().equals(g.getPosition()))
							if(i instanceof Caixote)
								hasCaixote=true;	
					if (!hasCaixote)
						return false;
				} 
				hasCaixote=false;
			}
			stats.addStat(bobcat.getEnergia(), FICHEIRO);
			return true;
	}
	//Funcao que reseta o nivel
	public void resetLevel() {
		gui.clearImages();
		board=new ArrayList<GameElement>();
		scanWarehouse();
		gui.setStatusMessage("Sokoban: Player- "+ username + ", energia="+bobcat.getEnergia());
	}
	//Funçao que dá load ao próximo nivel
	public void nextLevel() {
		//Apaga o nivel anterior
		gui.clearImages();
		board=new ArrayList<GameElement>();
		//Proximo Nivel
		@SuppressWarnings("resource")
		int nivel = new Scanner(FICHEIRO).useDelimiter("\\D+").nextInt();
		if(nivel<MAX_LEVEL) {
			nivel++;
		FICHEIRO=("level"+nivel+".txt");
		scanWarehouse();
		gui.setStatusMessage("Sokoban: Player- "+ username + ", energia="+bobcat.getEnergia());
		}else
			endGame();
	}

	public Empilhadora getBobcat() {
		return bobcat;
	}
	
	public void endGame() {
		stats.endStats();
		System.exit(1);	
		}
	//Funcao generica que 
	//retorna:uma lista de elementos do jogo com o seguinte predicado
	public List<GameElement> select(Predicate<GameElement> pred) {
		List<GameElement> resultado=new ArrayList<>();
		for(GameElement g:board) {
			if(pred.test(g))
				resultado.add(g);
		}
		return resultado;
	}

}
