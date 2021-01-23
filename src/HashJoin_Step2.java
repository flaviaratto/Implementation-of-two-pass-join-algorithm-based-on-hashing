
import java.io.IOException;
import java.util.ArrayList;
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
public class HashJoin_Step2 {
    
    public static int M;
    public static HashMap<Integer,List<String>> finalHashMap;
    public static FileReadClass[] fileReaderForTableR;
    public static FileReadClass[] fileReaderForTableS;
    public static FileReadClass fileReaderForResult1;
    public static String Table_R;
    public static String Table_S;
    public static FileWriteClass fileWriter;
    
    public HashJoin_Step2(int M_IP,String RelationR,String RelationS)
	{
            M = M_IP;
            finalHashMap = new HashMap<Integer,List<String>>();
            fileReaderForTableR = new FileReadClass[M];
            fileReaderForTableS = new FileReadClass[M];
            Table_R=RelationR;
            Table_S=RelationS;
	}
    
    public static void performjoin(List B_20, String x)
	{
            fileWriter = new FileWriteClass(x+Table_R+"_"+Table_S+"_join");
            fileWriter.createFile();
	
            openFiles(x);
            
            for(int i=0;i<M;i++)
		{
                    prepareHashStructure();
                    hashSmallRelation(i);

                    hashBigRelation(i);
                    finalHashMap.clear();
                }
            closeFiles();
            fileWriter.closeFile(); 
            if (x=="1_"){
                printoutputfile(B_20, x);
            }
            
        }
    
    
    
    public static void openFiles(String x)
	{
            for(int i=0;i<M;i++)
            {
                fileReaderForTableR[i]= new FileReadClass(MainClass.RHashFile+x+i);
                fileReaderForTableR[i].openFile();


                fileReaderForTableS[i]= new FileReadClass(MainClass.SHashFile+i);
                fileReaderForTableS[i].openFile();
            }
	}
    
    public static void prepareHashStructure()
	{
            for(int i=0;i<M;i++)
		{			
                    finalHashMap.put(i, new ArrayList<String>());
		}
        }
    
    public static void hashSmallRelation(int i)
	{
            String line;
            
            try {
                line = fileReaderForTableR[i].buff_reader.readLine();
                while (line!=null){
                    finalHashMap.get(i).add(line);
                    line = fileReaderForTableR[i].buff_reader.readLine();

                }

            } catch (IOException ex) {
                Logger.getLogger(HashJoin_Step2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public static void hashBigRelation(int i)
	{
            String line;
            try {
                line = fileReaderForTableS[i].buff_reader.readLine();
                while(line!=null){
                    
                    String[] split2 = line.split("-");
                    String Soperand = split2[0];
                    
                    List<String> theList = finalHashMap.get(i);//this belongs to R
                    //System.out.println(theList);
                    for(int j=0;j<theList.size();j++)
			{
                            String myLine = theList.get(j);
                            String[] split1= myLine.split("-");
                            String Roperand = split1[0];
                            if(Soperand.compareTo(Roperand)==0)
				{
                                    StringBuilder myStringBuilder = new StringBuilder();
                                    myStringBuilder.append(myLine);
                                    myStringBuilder.append("-");
                                    myStringBuilder.append(split2[1]);  
                                    
                                    fileWriter.writeline(myStringBuilder.toString());
                                    myStringBuilder = null;	   
                                }
                        }
                    line = fileReaderForTableS[i].buff_reader.readLine();
                }
                
                
            } catch (IOException ex) {
                Logger.getLogger(HashJoin_Step2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public static void closeFiles()
	{
            for(int i=0;i<M;i++)
            {
                fileReaderForTableR[i].closeFile();
                fileReaderForTableS[i].closeFile();
            }
	}
    
    public static void printoutputfile(List B_20, String x)
	{
            fileReaderForResult1 = new FileReadClass(x+Table_R+"_"+Table_S+"_join");
            
            String line;
            for(int j=0;j<B_20.size();j++)
                {
                    fileReaderForResult1.openFile();
                    try {
                        line = fileReaderForResult1.buff_reader.readLine();
                        while(line!=null){
                            String[] split1 = line.split("-");
                            String key = split1[0];
                            if(key.compareTo(B_20.get(j).toString())==0)
				{
                                    System.out.println(line);
                                }
                            line = fileReaderForResult1.buff_reader.readLine();
                        }    
                    } catch (IOException ex) {
                        Logger.getLogger(HashJoin_Step2.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    fileReaderForResult1.closeFile();
                } 
	} 
}
