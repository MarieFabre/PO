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
							if(t<=maxtps[nd]){ //le demarrage doit se faire apres le dernier demarrage sur cette desti
								t=maxtps[nd]+1; 
							}
							//un changement de chariot d'une autre destination ne doit pas etre simultané 
							while(sol.getEtatRobot2()[r][nd][t]==-1 || sol.getEtatRobot2()[r][nd][t+1]==-1 || sol.getEtatRobot2()[r][nd][t+2]==-1 || sol.getEtatRobot2()[r][nd][t+3]==-1|| sol.getEtatRobot2()[r][nd][t+4]==-1|| sol.getEtatRobot2()[r][nd][t+5]==-1|| sol.getEtatRobot2()[r][nd][t+6]==-1|| sol.getEtatRobot2()[r][nd][t+7]==-1 ){
								t++;
							}	
							if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
								nbBacs[nd]++; //On ajoute un bac au chariot 
								for(int T=t;T<t+8;T++){ //Le chargement prend 8 secondes 
									sol.setEtatRobot2(r, nd, T, 1);
								}
								maxtps[nd]=t+7; 
									
							}
							else { //Changement de chariot à faire 
								int M=t+100; //recherche du max tps des destinations du robot r
								for(int kk=0;kk<8;kk++){
									if (maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]>M){
										M=maxtps[copy.indexOf(instance.getDestiRobot()[r][kk])]; 
									}
								}	
								if(M>t){
								//System.out.println("M:"+M); 
									int[][] recopie=new int[nbDest][M-t+1];//on crée une copie de etat robot pour les 1 deja placés pour les autres destiantions
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
									for(int p=0;p<nbDest;p++){ //Toutes les destinations du robot sont immobilisées 
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
							}//fin else changement de chariot
							
						}//fin if destination robot 
					}//fin  boucle desti robot 
				}//fin boucle robot 
			//fin boucle lignes 
			
		return sol; 
	}

	public static Solution solve3(Instance instance){ 
		Solution sol=new Solution(instance); 
		
		int L=instance.getDateArrivee().length; 
		int nbDest=instance.getNbredest(); 
		int[] nbBacs=new int[nbDest]; 
		ArrayList<String> copy=instance.getListeDest(); 
		int[] maxtps=new int[NB_ROBOT]; 
		
		for(int j=0;j<L;j++){
			String d=instance.getDestination()[j];
			int t=instance.getDateArrivee()[j]; 
			int nd=copy.indexOf(d); 
			
			for(int r=0;r<NB_ROBOT;r++ ){
				for(int k=0;k<8;k++){
					if(d.equals(instance.getDestiRobot()[r][k])){
							//int cri=Solution.getnbBacs(sol,r); 
							if(t<=maxtps[r]){ //le demarrage doit se faire apres le dernier demarrage sur cette desti
								t=maxtps[r]+1; 
							}
							
							if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
								nbBacs[nd]++; //On ajoute un bac au chariot 
								for(int T=t;T<t+8;T++){ //Le chargement prend 8 secondes 
									sol.setEtatRobot(r, T, 1);
								}
								maxtps[r]=t+7; 
									
							}
							else { //Changement de chariot à faire 
								
								for(int T=t;T<t+60;T++){ //Le changement prend 60s
								
									sol.setEtatRobot(r,T, -1);
									
								}
								nbBacs[nd]=1;
								for(int T=t+60;T<t+68;T++){
									sol.setEtatRobot(r, T, 1);
								}
								maxtps[r]=t+67;
									/*int cri2=Solution.getnbBacs(sol,r); 
									if(cri2-cri!=1){
										System.out.println("m:"+M+ "; t: "+t); 
									}*/
								}
							}//fin else changement de chariot
							
						}//fin if destination robot 
					}//fin  boucle desti robot 
				}//fin boucle robot 
			//fin boucle lignes 
		for(int r=0;r<5;r++){
			System.out.println("tmax="+maxtps[r]); 
		}
		
		return sol; 

}
	public static Solution solve4(Instance instance, dynamique dyna){ //pour le modèle dynamique 
		Solution sol=new Solution(instance); 
		
		int L=instance.getDateArrivee().length; 
		int nbDest=instance.getNbredest(); 
		int[] nbBacs=new int[nbDest]; 
		ArrayList<String> copy=instance.getListeDest(); 
		int[] maxtps=new int[NB_ROBOT]; 
		
		
		for(int j=0;j<L;j++){
			String d=instance.getDestination()[j];
			int t=instance.getDateArrivee()[j]; 
			int nd=copy.indexOf(d); 
			
			
			for(int r=0;r<NB_ROBOT;r++ ){
				for(int k=0;k<8;k++){
					
					//Calcul de la date à laquelle le robot est disponible 
					if(t<=maxtps[r]){ 
						t=maxtps[r]+1; 
					}
					//Détermination du créneau correspondant 
					int c=0;
					while(t>c*dyna.getPAS_TPS()){
						c++; 
					}
					
					if(d.equals(dyna.getDestiRobot()[r][k][c])){
							
							if(nbBacs[nd]<15){ //Si le chariot n'est pas rempli
								nbBacs[nd]++; //On ajoute un bac au chariot 
								for(int T=t;T<t+8;T++){ //Le chargement prend 8 secondes 
									sol.setEtatRobot(r, T, 1);
								}
								maxtps[r]=t+7; 
									
							}
							else { //Changement de chariot à faire 
								
								for(int T=t;T<t+60;T++){ //Le changement prend 60s
								
									sol.setEtatRobot(r,T, -1);
									
								}
								nbBacs[nd]=1;
								for(int T=t+60;T<t+68;T++){
									sol.setEtatRobot(r, T, 1);
								}
								maxtps[r]=t+67;
									/*int cri2=Solution.getnbBacs(sol,r); 
									if(cri2-cri!=1){
										System.out.println("m:"+M+ "; t: "+t); 
									}*/
								}
							}//fin else changement de chariot
							
						}//fin if destination robot 
					}//fin  boucle desti robot 
				}//fin boucle robot 
			//fin boucle lignes 
			
		return sol; 

}
}
