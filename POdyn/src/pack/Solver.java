package pack;

import java.util.ArrayList;

public class Solver {
	
	public static final int NB_ROBOT=5;
	public static final int NB_LIGNE=42835; 
	public static final int T_MAX=100000; 
	
	public static Solution solve(Instance instance){
		Solution sol=new Solution(instance); 
	
		int L=instance.getDateArrivee().length; 
		//int nbr=0;
		for(int j=0;j<L;j++){
			
			for(int i=0;i<NB_ROBOT;i++ ){
				for(int k=0;k<8;k++){
					if(instance.getDestination()[j].equals(instance.getDestiRobot()[i][k])){
						sol.setEtatRobot(i, instance.getDateArrivee()[j], 1);
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
		int[] maxtps=new int[5]; 
		
		for(int j=0;j<L;j++){
			String d=instance.getDestination()[j];
			int t=instance.getDateArrivee()[j]; 
			int nd=copy.indexOf(d); 
			
			for(int r=0;r<NB_ROBOT;r++ ){
				for(int k=0;k<8;k++){
					if(d.equals(instance.getDestiRobot()[r][k])){
						if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
							if(sol.getEtatRobot2()[r][nd][t]==0){ //Si le robot n'est pas deja occupé sur cette destination
								nbBacs[nd]++; //On ajoute un bac au chariot 
								for(int T=t;T<t+8;T++){ //Le chargement prend 8 secondes 
									sol.setEtatRobot2(r, nd, T, 1);
								}
								if(t+8>maxtps[r]){
									maxtps[r]=t+8; 
								}
							}
							else { //Si le robot est deja occupé sur cette destination
								while(sol.getEtatRobot2()[r][nd][t]!=0){ //On recherche le t pour lequel il est dispo
									t++; 
								}
								nbBacs[nd]++; 
								for(int T=t;T<t+8;T++){
									sol.setEtatRobot2(r, nd, T, 1);
								}
								if(t+8>maxtps[r]){
									maxtps[r]=t+8; 
								}
							}
						}
						else { //Si le chariot doit etre changé 
							//System.out.println("tpsmax"+ r +":"+maxtps[r]);
							//System.out.println("t:"+t);
							if(maxtps[r]>t){
							int[][] recopie=new int[nbDest][maxtps[r]-t+1];//on crée une copie de etat robot pour les 1 deja placés pour les autres destiantions
							for(int dest=0;dest<nbDest;dest++){
								for(int tps=t; tps<maxtps[r]+1; tps++){
									recopie[dest][tps-t]=sol.getEtatRobot2()[r][dest][tps]; 
								}
							}
							//On recopie la suite apres le temps de changement 
							for(int dest=0;dest<nbDest;dest++){
								for(int tps=t+60; tps<t+60+maxtps[r]-t+1; tps++){
									sol.setEtatRobot2(r, dest, tps, recopie[dest][tps-t-60]);
								}
							}
							}
							for(int T=t;T<t+60;T++){ //Le changement prend 60s
								for(int p=0;p<nbDest;p++){ //Toutes les destinations du robot sont immobilisées 
									sol.setEtatRobot2(r, p, T, -1);
								}
							}
							nbBacs[nd]=1;
							for(int T=t+60;T<t+68;T++){
								sol.setEtatRobot2(r, nd, T, 1);
							}
							if(t+68>maxtps[r]){
								maxtps[r]=t+68; 
							}
							
							
							

						}
						
						
					}
				}
			}
		}
		
		
		return sol; 

	}
}
