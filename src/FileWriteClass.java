
import java.io.File;
import java.io.FileWriter;
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
public class FileWriteClass {
    public String filename;
    public File file_x;
    public FileWriter writer_x;

    public FileWriteClass(String filename_args)
    {
            filename = filename_args;
    }

    public void createFile() 
    {
        try
        {
            file_x = new File(filename);
            file_x.createNewFile();
            writer_x = new FileWriter(file_x);
        }		
        catch(IOException e)
        {
            System.out.println("File IO exception in FileWriter Class");
        }
    }

    public void writeline(String myline)
    {
        try
        {
            writer_x.write(myline);
            writer_x.write("\n");
        }
        catch(IOException e)
        {
                System.out.println("File IO exception in FileWriter Class");
        }
    }

    public void closeFile()
    {
        try {
            writer_x.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Exception in myclass");
        }
    }

    
}
