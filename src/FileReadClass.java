
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author flavi
 */
public class FileReadClass {
    public String filename;
    public FileReader myFileReader;
    public BufferedReader buff_reader;
    String line;
    
    public FileReadClass(String fileName_arg)
	{
            filename=fileName_arg;
	}
    
    public void openFile()
	{
            try
            {
                myFileReader= new FileReader(filename);
                buff_reader = new BufferedReader(myFileReader);			
            }
            catch(FileNotFoundException e)
            {
                    System.out.println("File Not found in class: FileReader");
            }
	}
    
    public String readFile()
	{
		
            try
            {
                line = buff_reader.readLine();

            }
            catch(IOException e)
            {
                    System.out.println("Exception in readFile class: FileReader");
            }

            return line;
        }
    
    public void closeFile()
	{
            try
            {
                buff_reader.close();
                myFileReader.close();
            }
            catch(Exception e)
            {
            System.out.println("File close function in class: FileReader");
            }
	}	
}
