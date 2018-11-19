package pack;

import java.io.IOException;

import ilog.concert.IloException;

public class Main {

	public static void main(String[] args) throws IloException{
		
		Instance inst;
 
		
		try {
			inst = new Instance();
			for(int i=0;i<10/*inst.getDateArrivee().length*/;i++){
				System.out.println("DateArrivee:"+inst.getDateArrivee()[i]+"Destination: "+inst.getDestination()[i]);
			}
			for(int j=0;j<5;j++){
				for (int k=0;k<8;k++){
					System.out.println("EtatRobot:"+j+","+k+":"+inst.getDestiRobot()[j][k]);
				}
			}
			
			/*Solution sol=Solver.solve(inst);
			double [] pourcentage=Solution.utilisation(sol); 
			for(int r=0;r<5;r++){
				System.out.println("Le pourcentage d'utilisation du robot "+r+" est: "+pourcentage[r]+"%");
			
			}
			System.out.println("Nombre de destinations:"+inst.getNbredest()); */
			Solution solu=dynamique.Dynamique(inst);
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
