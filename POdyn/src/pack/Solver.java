package pack;

public class Solver {
	
	public static final int NB_ROBOT=5;
	public static final int NB_LIGNE=42835; 
	
	public static Solution solve(Instance instance){
		Solution sol=new Solution(instance); 
	
		int L=instance.getDateArrivee().length; 
		System.out.println(instance.Nbredest);
		for(int j=0;j<L;j++){
			
			System.out.println("getdestination j="+j+":"+instance.getDestination()[j]+" "+instance.Nbredest);
			
			for(int i=0;i<NB_ROBOT;i++ ){
				for(int k=0;k<8;k++){
					if(instance.getDestination()[j].equals(instance.getDestiRobot()[i][k])){
						sol.setEtatRobot(i, instance.getDateArrivee()[j], true);
					}
				}
			}
		}
		
		
		return sol; 

	}
	
}
