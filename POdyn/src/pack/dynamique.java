package pack;

import java.util.ArrayList;
import java.util.Arrays;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;

public class dynamique {
	// pour seul robot
	
	protected Instance instance; 
	public static final int T_MAX=86400; 
	public static final int NB_lignes=42836; 
	public static final int pasdetemps=43200; 
	public static final double eps=0.001; 
	public static final int tps1=4; 
	public static final int tps2=100; 
	public static final double coeff1=0.5; 
	public static final double coeff2=0.5; 
	// pas de temps en seconde, on commence avec 1h soit 3600 secondes
	
	public static Solution Dynamique (Instance instance) throws IloException { 		
		Solution sol = new Solution(instance);

		IloCplex cplex = new IloCplex();
		int[] date_arrivee =instance.getDateArrivee();
		String[] destination = instance.getDestination();
		int Cmax;
		Cmax=T_MAX/pasdetemps;
		//nombre de destinations
		int Nbredest;
		
		Nbredest=instance.getNbredest();
		ArrayList<String> ListDest=instance.getListeDest();
		
		
		
		int[][] n = new int[Cmax][Nbredest];
		
		for (int c=0;c<Cmax;c++){
			for(int d=0;d<Nbredest;d++){
				for(int i=0;i<NB_lignes;i++){
					
					if(((date_arrivee[i])<=86400)&&((date_arrivee[i])>=0)){
					
							if(destination[i].equals(ListDest.get(d))){
								
								n[c][d]++;
							
						}
						
					}
				
				}
			}
		}
		
		IloIntVar[][] destrobot = new IloIntVar[Cmax][Nbredest];
		for (int c=0;c<Cmax;c++){
			for(int d=0;d<Nbredest;d++){
				destrobot[c][d] = cplex.intVar(0, 1, "destrobot_" + c+ "_" + d);
			}
		}
		// on crée la variable binaire valeurabs pour linéariser la valeur absolue.
		
		IloIntVar[][] valeurabs = new IloIntVar[Cmax][Nbredest];
		for (int c=0;c<Cmax-1;c++){
			for(int d=0;d<Nbredest;d++){
				valeurabs[c][d] = cplex.intVar(0, 1, "valeurabs_" + c+ "_" + d);
			}
		}
	// contraintes pour la valeur absolue
		int[][] z = new int[Cmax][Nbredest];
		for (int c=0;c<Cmax-1;c++){
			for(int d=0;d<Nbredest;d++){
				int Amax=1;
				int Amin=1;
				IloIntVar a1;
				a1=cplex.intVar(0,0);
				IloIntVar a2;
				a2=cplex.intVar(0,0);
				cplex.addLe(a1,valeurabs[c][d]);
				cplex.addGe(a1,0);
				IloLinearNumExpr expr=cplex.linearNumExpr();
				expr.addTerm(a2,1);
				expr.addTerm(valeurabs[c][d],-1);
				cplex.addLe(expr,1);
				cplex.addGe(a2,0);
				IloLinearNumExpr expr3=cplex.linearNumExpr();
				IloLinearNumExpr expr4=cplex.linearNumExpr();
				expr3.addTerm(destrobot[c][d],-0.5);
				expr3.addTerm(destrobot[c+1][d],+0.5);
				expr3.addTerm(valeurabs[c][d],1);
				cplex.addGe(expr3,eps);
				expr4.addTerm(destrobot[c][d],-0.5);
				expr4.addTerm(destrobot[c+1][d],+0.5);
				expr4.addTerm(valeurabs[c][d],1);
				cplex.addLe(expr4,1);
				IloLinearNumExpr expr2=cplex.linearNumExpr();
				expr2.addTerm(a1,1);
				expr2.addTerm(a2,1);
				cplex.addEq(z[c][d],expr2);
			}
		}
		
		IloIntVar[] Z = new IloIntVar[Cmax];
		for (int c=0;c<Cmax;c++){
			Z[c]=cplex.intVar(0,0);
		}
		int S;
		
		for(int c=0;c<Cmax;c++){
			S=0;
			for (int d=0;d<Nbredest;d++){
			
				S=S+z[c][d];
			}
			Z[c]=cplex.intVar(S,S);
		}
		
		//fonction objective
		
		IloLinearNumExpr fctobj;
		fctobj=cplex.linearNumExpr();
		for (int c=0;c<Cmax;c++){
			
			
			for (int d=0;d<Nbredest;d++){
				
				fctobj.addTerm(n[c][d]*tps1*coeff1,destrobot[c][d]);
			}
			fctobj.addTerm(-1*coeff2*tps2,Z[c]);
			
			
		}
		cplex.addMaximize(fctobj);
		
		// contraintes
		
		//contrainte 1 : un robot ne peut avoir que 8 destinations
		
		for (int c=0;c<Cmax;c++){
			IloLinearNumExpr expr=cplex.linearNumExpr();
			for(int d=0;d<Nbredest;d++){
				expr.addTerm(destrobot[c][d],1);
			}
			cplex.addEq(expr,8);
		}
		
		//afficher une solution
		
		boolean result = cplex.solve();
		
		
		

		
		if (result) 
		{
			sol = new Solution(instance);
		
			for (int c=0;c<Nbredest;c++){
				int S1=0;
				System.out.println(n[c][instance.copy.indexOf("Y02")]);
				for (int d=0;d<Nbredest;d++){
					
					if(cplex.getValue(destrobot[c][d])==1){
						
						
											
						S1++;
					}
					
				
				}
				System.out.println(S1);
			}
			
		}	
		return sol;
	
	

	}
}

