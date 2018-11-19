package pack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Instance {

	private int[] dateArrivee; 
	private String[] destination; 
	private String[][] destiRobot; 
	public int Nbredest;
	public static final int NB_lignes=42836; 
	//Getters 
	
	public int[] getDateArrivee(){
		return this.dateArrivee; 
	}
	public String[] getDestination(){
		return this.destination; 
	}
	public int getNbredest(){
		return this.Nbredest;
	}
	public String[][] getDestiRobot(){
		return this.destiRobot; 
	}
	//Constructeur: lecture fichier Excel 
	public Instance() throws IOException{
		File file=new File("ressources/donnes2.csv"); 
		FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        this.dateArrivee =  new int[NB_lignes];
        this.destination = new String[NB_lignes];
        
        
       String ligne = br.readLine();
       int k=0; 
       for ( ligne = br.readLine(); ligne != null; ligne = br.readLine()) {
    	   String[] l= ligne.split(";");
    	   dateArrivee[k]=Integer.parseInt(l[0]); 
    	   destination[k]=l[1]; 
    	  k++;
       }
       
        br.close(); 
        fr.close(); 	
      
        
        ArrayList<String> copy= new ArrayList<String>();
        copy.add(0,destination[0]);
        
        for(int i=0;i<NB_lignes;i++){
        	int S=0;
        
        	for(int j=0;j<copy.size();j++){
        		if(destination[i].equals((copy.get(j)))){
        			S++;
        		}
        		
        	}
        	if(S==0){
        		
        		copy.add(0,destination[i]);
        	}
        }
        Nbredest=copy.size();
        File file2=new File("ressources/affectation.csv"); 
		FileReader fr2 = new FileReader(file2);
        BufferedReader br2 = new BufferedReader(fr2);
        this.destiRobot=new String[5][8]; 
        String ligne2 = br2.readLine();
        for ( ligne2 = br2.readLine(); ligne2 != null; ligne2 = br2.readLine()) {
    	   String[] l2= ligne2.split(";");
    	   int i=0; 
    	   while(destiRobot[Integer.parseInt(l2[1])][i]!=null && i<8){
    	   		i++; 
    	   }	
    	   destiRobot[Integer.parseInt(l2[1])][i]=l2[0]; 
        }
        br2.close(); 
        fr2.close(); 	
		
		/*try{
			FileInputStream fichier = new FileInputStream("ressources/donnes.xlsx");
			Workbook wb=WorkbookFactory.create(fichier); 
			Sheet sheet=wb.getSheet("rslt");
			//Calcul du nombre de lignes 
			int top = sheet.getFirstRowNum();
	        int bottom = sheet.getLastRowNum();
	        int length = bottom - top; 
	        this.dateArrivee =  new int[length-1];
	        this.destination = new String[length-1];
	        //Ecriture de dateArrivee et destination 
	        for (int i = 1; i < length; i++){
	        	Row line = sheet.getRow(i); 
	            dateArrivee[i-1] = (int)line.getCell(1).getNumericCellValue();
	            destination[i-1]=line.getCell(2).getStringCellValue(); 
	        }
	        Sheet sheet2=wb.getSheet("Affectation");
	        this.destiRobot=new String[5][8]; 
	        for(int j=0;j<8;j++){ //Boucle pour les 8 destinations
	        	int num=1; 
	        	for(int k=0;k<5;k++){ //Boucle sur les 5 robots 
	        		Row line = sheet.getRow(num); //On prend la ligne d'indice num
	        		String desti=line.getCell(0).getStringCellValue(); 
		        	destiRobot[k][j]=desti; 
		        	num++; 
	        	}
	        }
	        
	        wb.close(); 
	        fichier.close(); 
		
		}catch (IOException e){
			e.printStackTrace();
		}*/
	
	
		
	}
}
