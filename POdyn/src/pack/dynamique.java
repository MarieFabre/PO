package pack;

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
	public static final int T_MAX=86000; 
	public static final int NB_lignes=42836; 
	public static final int pasdetemps=3600; 
	public static final int tps1=4; 
	public static final int tps2=6; 
	public static final double coeff1=0.7; 
	public static final double coeff2=0.3; 
	// pas de temps en seconde, on commence avec 1h soit 3600 secondes
	
	public static Solution Dynamique (Instance instance) throws IloException { 		
		Solution sol = new Solution(instance);

		IloCplex cplex = new IloCplex();
		IloIntVar[] date_arrivee = new IloIntVar[NB_lignes];
		IloIntVar[] destination = new IloIntVar[NB_lignes];
		int Cmax;
		Cmax=T_MAX/pasdetemps;
		//nombre de destinations
		int Nbredest;
		Nbredest=instance.getNbredest();
		
		

		
		int[][] n = new int[Cmax][Nbredest];
		int P;
		for (int c=0;c<Cmax-1;c++){
			for(int i=0;i<NB_lignes;i++){
				if((cplex.getValue(date_arrivee[i])<=(c+1)*pasdetemps)&&(cplex.getValue(date_arrivee[i])>=c*pasdetemps)){
					for(int d=0;d<Nbredest;d++){
						P=0;
						if(destination[i].equals(d)){
						P++;
						}
						n[c][d]=P;
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
		int[][] z = new int[Cmax][Nbredest];
		for (int c=0;c<Cmax-1;c++){
			for(int d=0;d<Nbredest;d++){
				z[c][d] = (int) Math.abs(cplex.getValue(destrobot[c][d])-cplex.getValue(destrobot[c+1][d]));
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
			cplex.addLe(expr,8);
		}
		
		//afficher une solution
		
		boolean result = cplex.solve();

		
		if (result) 
		{
			sol = new Solution(instance);
			for (int c=0;c<Cmax;c++){
				for (int d=0;d<Nbredest;d++){
					System.out.println(destrobot[c][d]);
				}
			}
		}	
		return sol;
	
	

	}
}
