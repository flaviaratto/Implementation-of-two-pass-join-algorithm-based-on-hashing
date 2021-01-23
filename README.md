# Implementation-of-two-pass-join-algorithm-based-on-hashing

Implemented the two-pass join algorithm based on hashing on two relations R(A, B) and S(B, C) using randomly generated data.


**Assumptions -**  
•  Each block can hold upto 8 tuples of the relations  
•  Virtual main memory of 15 blocks  
•  Virtual disk whose size is unlimited  


## Steps to run the application - ##

 1. The application is compiled using JDK7. Please ensure you have **JDK 7 or higher** installed. You need Java to run the application. Install according to your system if it is not already installed.  
  https://www.oracle.com/java/technologies/javase-downloads.html
  
 2. The Project2_Part2_V2.jar file present in the ExecutableFiles --> dist folder.   
    Run the program in COMMAND LINE by typing in the command -  
     ```
    java -jar "Project2_Part2_V2.jar"  
     ```  
    Please make sure you are in the directory where the .jar file is located while running the command.
    If the above method does not work, make sure the environment variables have been set correctly for Java.  
    
    DON'T just double click the jar file here. Doing that will not let you see the console output which has the results to be printed.

**Output -**   
You will see the required results printed on the console.  
Also, all the files generated for the relations, hash bucket and join ouput will be created in the same folder where the jar file is located.  

**Note:** If you want to view the source code and run that in NetBeans, the source code is present in the src folder   
MainClass.java is the main file
