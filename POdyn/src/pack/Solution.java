package pack;

public class Solution {

	private boolean[][] etatRobot; //vaut 1 si le robot i fonctionne a la seconde t 
	protected Instance instance; 
	
	public static final int NB_ROBOT=5;
	public static final int T_MAX=86000; 
	public static final int NB_lignes=42836; 
	//Getter
	public boolean[][] getEtatRobot(){
		return this.etatRobot; 
	}
	
	//Setters
	public void setEtatRobot(int i, int t, boolean b){
		this.etatRobot[i][t]=b; 
	}
	public Solution(Instance instance){
		this.instance=instance; 
		this.etatRobot= new boolean[NB_ROBOT][T_MAX]; 
		for(int i=0; i<NB_ROBOT;i++){
			for(int t=0; t<T_MAX;t++){
				this.setEtatRobot(i,t,false); 
			}
		}
	}
	public static double[] utilisation (Solution sol){
		//Determination du pourcentage d'utilisation de chaque robot 
		double [] pourcentage=new double[5]; 
		for (int r=0;r<5;r++){
			int p=0; 
			for(int t=0;t<T_MAX;t++){
				if(sol.getEtatRobot()[r][t]){
					p++; 
				}
			}
			System.out.println("Nombre de bacs pris en charge par le robot "+r+"="+p);
			pourcentage[r]=Math.round(p*100.0*100.0/42836.0); 
			pourcentage[r]=pourcentage[r]/100.0; 
	
		}
		return pourcentage; 
	
	}
	public static double utilisationtot(Solution sol){
		double tot=0.0; 
		for(int r=0;r<5;r++){
			tot+=Solution.utilisation(sol)[r];
		}
		return tot; 	
		
	}
}
