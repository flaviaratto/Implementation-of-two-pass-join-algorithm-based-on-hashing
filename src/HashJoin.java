
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
public class HashJoin {
    public static String TableName;
    public static int M;
    public static int blockSize;
    public static int relationType;
    public static int noOfTimesToLoad;
    public static FileReadClass myReader;
    public static int position;
    public static String FileName;
    public static FileWriteClass[] fileWriter;
    
    public int totaltuples;
    static List<String> lines;
    
    public HashJoin(String relationNameIP,int M_Blocks,int blockSizeIP,HashMap<String,Integer> relationNumberIP, int TotalTuplesIP)
	{
            noOfTimesToLoad = 0;
            TableName = relationNameIP;
            M = M_Blocks;  //Main memory 15 blocks
            lines = new ArrayList<>();
            blockSize = blockSizeIP;  //Number of tuples per block 8
            fileWriter = new FileWriteClass[M];		
            relationType = (int)relationNumberIP.get(relationNameIP);   //0 for R, 1 for S
            totaltuples = TotalTuplesIP;
            	
	}
    
    public  void computeNoOfTimeToLoad()
	{
            double result = ( (double)1.0*totaltuples/(double)(blockSize*1.0));
            noOfTimesToLoad =(int) Math.ceil(result);		
	}
    
    public int createHashedSublists(String x,int total_tuples)
        {
            createHashedFiles(x);
            myReader = new FileReadClass(TableName);
            myReader.openFile();
            
            for(int i=0;i<noOfTimesToLoad;i++)
                {
                    readLines();
                    hashAndWrite();
                    lines.clear();
                }
            
            myReader.closeFile();
            closeHashedFiles();
            return 2*total_tuples;
        }
    
    public static void createHashedFiles(String x)
	{
            
            if(relationType==0)
                FileName=MainClass.SHashFile;
            else
                FileName=MainClass.RHashFile+x;
            
            for(int i=0;i<M;i++)
		{
                    fileWriter[i] = new FileWriteClass(FileName+i);
                    fileWriter[i].createFile();
		}
            
        }
    
    public static void readLines()
	{
            
            try {
                String line=myReader.buff_reader.readLine();
                int counter = 0;
                
                while(line!=null)
                    {
                        lines.add(counter,line);
                        counter++;
                        if(counter<blockSize)
                            line = myReader.buff_reader.readLine();
                        else
                            break;
                    }
                
            } catch (IOException ex) {
                Logger.getLogger(HashJoin.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    
    public static void hashAndWrite()
	{
            String line;
            String operand;
            int hashIndex;

            for(int i=0;i<lines.size();i++)
            {
                line = lines.get(i);
                String[] myArray = line.split("-");
                operand = myArray[0];
                int hashCode = operand.hashCode();
                
                hashIndex = Math.abs(operand.hashCode())%(M);
                //System.out.println(hashIndex);
                fileWriter[hashIndex].writeline(line);
            }
        }
    
    public static void closeHashedFiles()
	{
            for(int i=0;i<M;i++)
		{
                    fileWriter[i].closeFile();
		}
        }
      
}
