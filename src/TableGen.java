
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author flavi
 */
public class TableGen {
    
    public static List getRandom(int min, int max, int num){
        
        List<Integer> numbers= new ArrayList<Integer>();		// arraylist to store keys in each node
        List<Integer> rand_array= new ArrayList<Integer>();
 
        for(int i = min; i < max+1; i++)
        {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for(int j =0; j < num; j++)
        {
            rand_array.add(numbers.get(j));
        }
        return rand_array;
    }
    
    public static List getRandomNonUnique(int min, int max, int num){
        
        List<Integer> numbers= new ArrayList<Integer>();		// arraylist to store keys in each node
        List<Integer> rand_array= new ArrayList<Integer>();
        //Random random1 = new Random();  
        //rand_array = random1.ints(num, min, max);
        Random random = new Random();
        for(int j =0; j < num; j++)
        {
            rand_array.add(random.nextInt(max - min) + min);
        }
                
        return rand_array;
    }
    
    
    public static List getRandomFromTable(int num, List B){
        
        List<Integer> rand_array= new ArrayList<Integer>();
        
        Random random = new Random();
        for(int j =0; j < num; j++)
        {
            
            int rand_index = random.nextInt(B.size());
            int a = (int) B.get(rand_index);

            rand_array.add(a);
        }
                
        return rand_array;
    }
    
    public static List getRandomFromTableUnique(int num, List B){
        
        List<Integer> rand_array= new ArrayList<Integer>();
        
        Random random = new Random();
        for(int j =0; j < num; j++)
        {
            
            int rand_index = random.nextInt(B.size());
            while(rand_array.contains(B.get(rand_index))){
                rand_index = random.nextInt(B.size());
            }
            int a = (int) B.get(rand_index);

            rand_array.add(a);
        }
                
        return rand_array;
    }
    
}
