package pack;

public class Solution {

	private boolean[][] etatRobot; //vaut 1 si le robot i fonctionne a la seconde t 
	protected Instance instance; 
	private int[][][] etatRobot2;
	
	public static final int NB_ROBOT=5;
	public static final int T_MAX=100000; 
	public static final int NB_lignes=42836; 
	//Getter
	public boolean[][] getEtatRobot(){
		return this.etatRobot; 
	}
	public int[][][] getEtatRobot2(){
		return this.etatRobot2; 
	}
	//Setters
	public void setEtatRobot(int i, int t, boolean b){
		this.etatRobot[i][t]=b; 
	}
	public void setEtatRobot2(int i, int d, int t, int b){
		this.etatRobot2[i][d][t]=b; 
	}
	/*public Solution(Instance instance){
		this.instance=instance; 
		this.etatRobot= new boolean[NB_ROBOT][T_MAX]; 
		for(int i=0; i<NB_ROBOT;i++){
			for(int t=0; t<T_MAX;t++){
				this.setEtatRobot(i,t,false); 
			}
		}
	}*/
	public Solution(Instance instance){
		this.instance=instance; 
		int nbDest=instance.getNbredest(); 
		this.etatRobot2= new int[NB_ROBOT][nbDest][T_MAX]; 
		for(int i=0; i<NB_ROBOT;i++){
			for(int t=0; t<T_MAX;t++){
				for(int d=0;d<nbDest;d++){
					this.setEtatRobot2(i,d,t,0);
				}
			}
		}
	}
	
	public static double[] utilisation (Solution sol){
		//Determination du pourcentage d'utilisation de chaque robot 
		double [] pourcentage=new double[5]; 
		int[] nbBacs=new int[5]; 
		for (int r=0;r<5;r++){
			for(int t=0;t<T_MAX;t++){
				if(sol.getEtatRobot()[r][t]){
					nbBacs[r]++;  
				}
			}
			System.out.println("Nombre de bacs pris en charge par le robot "+r+"="+nbBacs[r]);
			pourcentage[r]=Math.round(nbBacs[r]*100.0*100.0/42836.0); 
			pourcentage[r]=pourcentage[r]/100.0; 
	
		}
		return pourcentage; 
	
	}
	public static double utilisation2 (Solution sol, int r){
		//Determination du pourcentage d'utilisation du robot r
		double pourcentage; 
		int comp=0; //compteur du nombre de bacs total
		for(int d=0;d<sol.getEtatRobot2()[r].length;d++){//on parcourt toutes les destinations du robot r
			int cop=comp; //Compteur du nombre de bacs par destination
			for(int t=0;t<T_MAX;t++){
				if(sol.getEtatRobot2()[r][d][t]==1){
					comp++;  
					//System.out.println("t:"+t);
				}
			}
			if(comp>cop){
			//	System.out.println("destination "+ sol.instance.getListeDest().get(d)+":"+ (comp-cop)/8); 
			}
		}	
		int nbBacs=comp/8; 
		System.out.println("Nombre de bacs pris en charge par le robot"+r+" ="+nbBacs);
		pourcentage=Math.round(nbBacs*100.0*100.0/42836.0)/100.0;  
	
		return pourcentage; 
	
	}
	public static int getnbBacs (Solution sol, int r){
		//Determination du nb de bacs traites par le robot r
		int comp=0; //compteur du nombre de bacs total
		for(int d=0;d<sol.getEtatRobot2()[r].length;d++){//on parcourt toutes les destinations du robot r
			int cop=comp; //Compteur du nombre de bacs par destination
			for(int t=0;t<T_MAX;t++){
				if(sol.getEtatRobot2()[r][d][t]==1){
					comp++;  
					//System.out.println("t:"+t);
				}
			}
			if(comp>cop){
			//	System.out.println("destination "+ sol.instance.getListeDest().get(d)+":"+ (comp-cop)/8); 
			}
		}	
		int nbBacs=comp/8; 
		//System.out.println("Nombre de bacs pris en charge par le robot"+r+" ="+nbBacs);
		
	
		return nbBacs; 
	
	}
	public static double utilisationtot(Solution sol){
		double tot=0.0; 
		for(int r=0;r<5;r++){
			tot+=Solution.utilisation2(sol,r);
		}
		return tot; 	
		
	}
}
