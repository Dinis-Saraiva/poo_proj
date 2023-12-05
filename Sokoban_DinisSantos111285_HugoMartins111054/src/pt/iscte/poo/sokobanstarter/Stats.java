package pt.iscte.poo.sokobanstarter;

import java.io.FileWriter;
import java.io.IOException;

public class Stats {
	

	private FileWriter myWriter;
	private int total=0;
	public Stats(String username) {
		try {
		myWriter= new FileWriter(username+"-Ultimo jogo.txt");
		 myWriter.write(username+"\n"+"\n");
		} catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	public void addStat(int score,String lvl) {
		try {
			  if(score==-1)
				  myWriter.write(lvl+" ---- PERDEU"+"\n");
			  else {
				  myWriter.write(lvl+" ---- GANHOU com um score de "+score+"\n");
				  total+=score;
			  	}
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public void endStats() {
		try {
			 myWriter.write("\n\n\n SCORE TOTAL=="+total);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
