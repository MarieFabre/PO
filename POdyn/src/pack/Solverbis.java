package pack;

import java.util.ArrayList;

public class Solverbis {
	
	public static final int NB_ROBOT=5;
	public static final int NB_LIGNE=42835; 
	public static final int T_MAX=100000; 
	
	public static Solution solve2(Instance instance){
		Solution sol=new Solution(instance); 
	
		int L=instance.getDateArrivee().length; 
		int nbDest=instance.getNbredest(); 
		int[] nbBacs=new int[nbDest]; 
		ArrayList<String> copy=instance.getListeDest(); 
		int[] maxtps=new int[nbDest]; 
		
		for(int j=0;j<L;j++){
			String d=instance.getDestination()[j];
			int t=instance.getDateArrivee()[j]; 
			int nd=copy.indexOf(d); 
			
			for(int r=0;r<NB_ROBOT;r++ ){
				for(int k=0;k<8;k++){
					if(d.equals(instance.getDestiRobot()[r][k])){
							//int cri=Solution.getnbBacs(sol,r); 
							if(t>maxtps[nd]){//Si le robot n'est pas deja occup� sur cette destination
								if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
									nbBacs[nd]++; //On ajoute un bac au chariot 
									for(int T=t;T<t+8;T++){ //Le chargement prend 8 secondes 
										sol.setEtatRobot2(r, nd, T, 1);
									}
									maxtps[nd]=t+7; 
									
								}
								else { //Changement de chariot � faire 
									int M=t+10; //recherche du max tps des destinations du robot r
									for(int kk=0;kk<8;kk++){
										if (maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]>M){
											M=maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]; 
										}
									}
									
									
									if(M>t){
										//System.out.println("M:"+M); 
										int[][] recopie=new int[nbDest][M-t+1];//on cr�e une copie de etat robot pour les 1 deja plac�s pour les autres destiantions
										for(int dest=0;dest<nbDest;dest++){
											for(int tps=t; tps<M+1; tps++){
												recopie[dest][tps-t]=sol.getEtatRobot2()[r][dest][tps]; 
											}
										}
										//On recopie la suite apres le temps de changement 
										for(int dest=0;dest<nbDest;dest++){
											for(int tps=t+20; tps<t+20+M-t+1; tps++){
												sol.setEtatRobot2(r, dest, tps, recopie[dest][tps-t-20]);
											}
										}
									}
				
									for(int T=t;T<t+20;T++){ //Le changement prend 60s
										for(int p=0;p<nbDest;p++){ //Toutes les destinations du robot sont immobilis�es 
											sol.setEtatRobot2(r, p, T, -1);
										}
									}
									for(int desti=0;desti<nbDest;desti++){
										maxtps[desti]+=20; 
									}
									nbBacs[nd]=1;
									for(int T=t+20;T<t+28;T++){
										sol.setEtatRobot2(r, nd, T, 1);
									}
									maxtps[nd]=t+27;
									/*int cri2=Solution.getnbBacs(sol,r); 
									if(cri2-cri!=1){
										System.out.println("m:"+M+ "; t: "+t); 
									}*/
								}
							}
							else { //Si le robot est deja occup� sur cette destination
								t=maxtps[nd]+1; 
								if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
									nbBacs[nd]++; 
									for(int T=t;T<t+8;T++){
										sol.setEtatRobot2(r, nd, T, 1);
									}
									maxtps[nd]+=8; 
								}
								else { //changement de chariot � faire 
									
									int M=t+10; //recherche du max tps des desitnations du robot r
									for(int kk=0;kk<8;kk++){
										if (maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]>M){
											M=maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]; 
										}
									}
									if(M>t){
									int[][] recopie=new int[nbDest][M-t+1];//on cr�e une copie de etat robot pour les 1 deja plac�s pour les autres destiantions
										for(int dest=0;dest<nbDest;dest++){
											for(int tps=t; tps<M+1; tps++){
												recopie[dest][tps-t]=sol.getEtatRobot2()[r][dest][tps]; 
											}
										}
										//On recopie la suite apres le temps de changement 
										for(int dest=0;dest<nbDest;dest++){
											for(int tps=t+20; tps<t+20+M-t+1; tps++){
												sol.setEtatRobot2(r, dest, tps, recopie[dest][tps-t-20]);
											}
										}
									}
									
									for(int T=t;T<t+20;T++){ //Le changement prend 60s
										for(int p=0;p<nbDest;p++){ //Toutes les destinations du robot sont immobilis�es 
											sol.setEtatRobot2(r, p, T, -1);
										}
									}
									
									for(int desti=0;desti<nbDest;desti++){
										maxtps[desti]+=20; 
									}
									nbBacs[nd]=1;
									for(int T=t+20;T<t+28;T++){
										sol.setEtatRobot2(r, nd, T, 1);
									}
									maxtps[nd]+=8;
									
								}
							}
						}						
						
						
					}
				}
			}
		
		
		
		return sol; 

	}
}