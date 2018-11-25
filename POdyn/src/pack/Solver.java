package pack;

import java.util.ArrayList;

public class Solver {
	
	public static final int NB_ROBOT=5;
	public static final int NB_LIGNE=42835; 
	
	public static Solution solve(Instance instance){
		Solution sol=new Solution(instance); 
	
		int L=instance.getDateArrivee().length; 
		//int nbr=0;
		for(int j=0;j<L;j++){
			
			for(int i=0;i<NB_ROBOT;i++ ){
				for(int k=0;k<8;k++){
					if(instance.getDestination()[j].equals(instance.getDestiRobot()[i][k])){
						sol.setEtatRobot(i, instance.getDateArrivee()[j], true);
						//nbr++;
					}
				}
			}
		}
		
		//System.out.println("nbr:" + nbr);
		return sol; 

	}
	public static Solution solve2(Instance instance){
		Solution sol=new Solution(instance); 
	
		int L=instance.getDateArrivee().length; 
		int nbDest=instance.getNbredest(); 
		int[] nbBacs=new int[nbDest]; 
		ArrayList<String> copy=instance.getListeDest(); 
		
		for(int j=0;j<L;j++){
			
			for(int r=0;r<NB_ROBOT;r++ ){
				for(int k=0;k<8;k++){
					String d=instance.getDestination()[j];
					int t=instance.getDateArrivee()[j]; 
					int nd=copy.indexOf(d); 
					
					if(d.equals(instance.getDestiRobot()[r][k])){
						if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
							if(sol.getEtatRobot2()[r][nd][t]==0){ //Si le robot n'est pas deja occupé sur cette destination
								nbBacs[nd]++; //On ajoute un bac au chariot 
								for(int T=t;T<t+9;T++){ //Le chargement prend 8 secondes 
									sol.setEtatRobot2(r, nd, T, 1);
								}
							}
							else { //Si le robot est deja occupé sur cette destination
								while(sol.getEtatRobot2()[r][nd][t]!=0){ //On recherche le t pour lequel il est dispo
									t++; 
								}
								nbBacs[nd]++; 
								for(int T=t;T<t+9;T++){
									sol.setEtatRobot2(r, nd, T, 1);
								}
							}
						}
						else { //Si le chariot doit etre changé 
							for(int T=t;T<t+61;T++){ //Le changement prend 60s
								for(int p=0;p<8;p++){ //Toutes les destinations du robot sont immobilisées 
									sol.setEtatRobot2(r, nd, T, -1);
								}
							}
							nbBacs[nd]=0; 
						}
						
						
					}
				}
			}
		}
		
		
		return sol; 

	}
}
