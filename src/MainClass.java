
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author flavi
 */
public class MainClass {
    public static FileWriteClass fileWriter;
    public static FileReadClass fileReader;
    
    public static String Table_R1; 
    public static String Table_R2; 
    public static String Table_S;
    public static int total_tuples_R1;
    public static int total_tuples_R2;
    public static int total_tuples_S;
    
    public static HashMap<String,Integer> relationNumber;
    
    public static final int TuplesPerBlock= 8;  //Tuples per block
    public static final int NumOfBlocks= 15; //M
    public static final int TotalBlockSize= 15*8; //15*8
    public static final String RHashFile = "Hash_R_";
    public static final String SHashFile = "Hash_S_";
    public static int number_of_IO_1;
    
    
    public static void writetodisk(List col1, List col2, String filename)
	{
            fileWriter = new FileWriteClass(filename);
            fileWriter.createFile();
            for(int i=0;i<col1.size();i++)
            {
                fileWriter.writeline(col1.get(i).toString()+"-"+col2.get(i).toString());
                //fileWriter.writeline(col2.get(i).toString());
            }
            fileWriter.closeFile();
	}
    
    //Temporary
    public static void readfromdisk(String filename)
	{
            String line;
            List<Integer> OP = new ArrayList<Integer>();
            fileReader = new FileReadClass(filename);
            fileReader.openFile();
            
            try {
                //System.out.println(Arrays.toString(myArray));
                line = fileReader.buff_reader.readLine();
                while(line!=null){
                    System.out.println(line); 
                    line = fileReader.buff_reader.readLine();
                }
                                
            } catch (IOException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fileReader.closeFile();
	}
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        Table_R1 ="Table_R1"; 
        Table_R2 ="Table_R2"; 
        Table_S ="Table_S";
        
        relationNumber = new HashMap<>();
        relationNumber.put(Table_S, 0);
        relationNumber.put(Table_R1, 1);
        
        List<Integer> S_B;		
        List<Integer> S_C;
        List<Integer> R_B1;
        List<Integer> R_B2;
        List<Integer> R_A1;
        List<Integer> R_A2;
        List<Integer> B_20;
        
        
        //Generate a relation S(B, C) of 5,000 tuples
        TableGen t = new TableGen();
        System.out.println("Generating Table S(B, C)");
        S_B = t.getRandom(10000,50000,5000);
        System.out.println("Number of tuples in Table S: "+ S_B.size());
        System.out.print("Tuples: ");
        S_C = t.getRandomNonUnique(1,500,5000);
        for(int i=0;i<S_B.size();i++)
            {
                System.out.print(S_B.get(i).toString()+"-"+S_C.get(i).toString()+", ");
            }
        System.out.println("\n");
        total_tuples_S=S_B.size();
        int B_S = total_tuples_S/TuplesPerBlock;
        System.out.println("Number of blocks in Table S - B(S): "+B_S);
        //Storing S in virtual disk
        writetodisk(S_B, S_C, "Table_S");
        
        //Applying Hash on tables S
        String x = "0";
        HashJoin hashObj = new HashJoin(Table_S, NumOfBlocks, TotalBlockSize,relationNumber, total_tuples_S);
        hashObj.computeNoOfTimeToLoad();
        int S_Hash_IO = hashObj.createHashedSublists(x, total_tuples_S)/total_tuples_S;

        System.out.println("*********************************************");
        System.out.println("\n");
        
        
        //Experiment 1
        //Generate a relation R1(B, A) of 1,000 tuples
        System.out.println("Experiment 1");
        System.out.println("Generating Table R1(B, A) for Experiment 1");
        R_B1 = t.getRandomFromTable(1000,S_B);
        System.out.println("Number of tuples in Table R1: "+ R_B1.size());
        System.out.print("Tuples: ");    
        R_A1 = t.getRandomNonUnique(1,100,1000);
        for(int i=0;i<R_B1.size();i++)
            {
                System.out.print(R_B1.get(i).toString()+"-"+R_A1.get(i).toString()+", ");
            }
        total_tuples_R1=R_B1.size();
        int B_R1 = total_tuples_R1/TuplesPerBlock;    //Total blocks in R1
        writetodisk(R_B1, R_A1, "Table_R1");
        System.out.println("\n");
        System.out.println("Number of blocks in Table R1 - B(R1): "+ B_R1);
        
        //Applying Hash on tables R1
        hashObj = new HashJoin(Table_R1, NumOfBlocks, TotalBlockSize,relationNumber, total_tuples_R1);
        hashObj.computeNoOfTimeToLoad();
        x = "1_";
        int R1_Hash_IO = hashObj.createHashedSublists(x, total_tuples_R1)/total_tuples_R1;
        number_of_IO_1 = S_Hash_IO+R1_Hash_IO;
        
        //Performing Natural join
        HashJoin_Step2 finHashObj = new HashJoin_Step2(NumOfBlocks, Table_R1, Table_S);
        System.out.println("");
        System.out.println("Calculating number of disk I/O's");
        System.out.println("Reading Table S from disk for hashing: B(S)");
        System.out.println("Reading Table R1 from disk for hashing: B(R1)");
        System.out.println("Writing to disk after hashing Table S: B(S)");
        System.out.println("Writing to disk after hashing Table R1: B(R1)");
        System.out.println("Reading each bucket pair in main memory for computing natural join : B(S) + B(R1)");
        System.out.print("Total Number of Disk I/O's by the algorithm: 3*(B(R1) + B(S)) = ");
        System.out.println(3*(B_R1+B_S));
        
        //Randomly picking 20 B-values,
        B_20 = t.getRandomFromTableUnique(20,R_B1);
        System.out.println("The 20 randomly picked B - values: "+B_20);
        System.out.println(" ");
        System.out.println("The result of natural join of Table R1 and Table S where B-values are the picked values printed above");
        finHashObj.performjoin(B_20,x);
        System.out.println("\n");
        
        
        //Experiment 2
        //Generate a relation R2(B, A) of 1,200 tuples
        System.out.println("*********************************************");
        System.out.println("\n");
        System.out.println("Experiment 2");
        System.out.println("Generating Table R2 (B, A) for Experiment 2");
        R_B2 = t.getRandomNonUnique(20000,30000,1200);
        System.out.println("Number of tuples in Table R2: "+R_B2.size());
        System.out.print("Tuples: ");
        R_A2 = t.getRandomNonUnique(1,100,1200);

        for(int i=0;i<R_B2.size();i++)
            {
                System.out.print(R_B2.get(i).toString()+"-"+R_A2.get(i).toString()+", ");
            }
        System.out.println("\n");
        total_tuples_R2=R_B2.size();
        int B_R2 = total_tuples_R2/TuplesPerBlock;    //Total blocks in R2
        System.out.println("Number of blocks in Table R2 - B(R1): "+ B_R2);
        writetodisk(R_B2, R_A2, "Table_R2");
        
        relationNumber = new HashMap<>();
        relationNumber.put(Table_S, 0);
        relationNumber.put(Table_R2, 1);
        
        //Applying Hash on tables R2
        hashObj = new HashJoin(Table_R2, NumOfBlocks, TotalBlockSize,relationNumber, total_tuples_R2);
        hashObj.computeNoOfTimeToLoad();
        String y = "2_";
        int R2_Hash_IO = hashObj.createHashedSublists(y, total_tuples_R2)/total_tuples_R2;
        
        //Performing Natural join
        HashJoin_Step2 finHashObj2 = new HashJoin_Step2(NumOfBlocks, Table_R2, Table_S);
        System.out.println("");
        System.out.println("Calculating number of disk I/O's");
        System.out.println("Reading Table S from disk for hashing: B(S)");
        System.out.println("Reading Table R2 from disk for hashing: B(R2)");
        System.out.println("Writing to disk after hashing Table S: B(S)");
        System.out.println("Writing to disk after hashing Table R2: B(R2)");
        System.out.println("Reading each bucket pair in main memory for computing natural join : B(S) + B(R2)");
        System.out.print("Total Number of Disk I/O's by the algorithm: 3*(B(R2) + B(S)) = ");
        System.out.println(3*(B_R2+B_S));
        
        System.out.println("The entire result of natural join of Table R2 and Table S");
        finHashObj.performjoin(B_20,y);
        readfromdisk(y+Table_R2+"_"+Table_S+"_join");    
    }
    
}
