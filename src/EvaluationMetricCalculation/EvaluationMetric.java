/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvaluationMetricCalculation;

/**
 *
 * @author ranak
 */







/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
//import sun.security.util.Password;

/**
 *
 * @author ranak
 * 
 */


public class EvaluationMetric {
    
    private static final String FILENAME = "//home//ranak//Desktop//Rock.txt"; // Source File
    
   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        double ProbabilityMatric;
        double IndexMatric;
        double PartialGuessMatric;
        double NistEntropy;
        double Log2BasedIndex; 
        
        double LamdaBeta =0.0;
        double SumOfPiMultiplyI =0.0;
        double LamdaMuAlpha=0.0;
        long MuAlpha =0; 
        
        String Dataset = "RockYou";
        long totalAccountPasswordInDataSet = 32603388;
        long indexCount=0;
      

        String FileLocation = "//home//ranak//Desktop//PassWordMatric"; // location of output file
        File createDirectory = new File(FileLocation);
        createDirectory.mkdir();  // Output file directory created
        String FileNameWithLocation = "//home//ranak//Desktop//PassWordMatric//StatisticalMetricsForIndividualPasswordStrengthtest.csv";  //Filename with location
        File createFile = new File(FileNameWithLocation); // Output file created
        
        if(createFile.exists()){ // If file allready exist, delete first 
            createFile.delete();
        }
        createFile.createNewFile(); // Create File
        FileWriter fileWriter = new FileWriter(createFile,true); //Create file reader for the file
        fileWriter.write("\n");
        FileReader fr = new FileReader(FILENAME); //Created file reader for read from the source file
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(FILENAME));
    
       
	
         //Create instance for Calculation class
         MetricCalculations equations = new MetricCalculations(Dataset,totalAccountPasswordInDataSet); 
         
        //Header of the output file
        String TextTobeWrite = "Password "+"Log2BasedIndex "+"Count "+"ProbabilityMatric " +"IndexMatric " +"PartialGuessMatric "+"NistEntropy";
        fileWriter.write(TextTobeWrite+"\n");
        
        
        // Run the loop until the last line of input file
        // it will read every line of input line one by one
        while((sCurrentLine = br.readLine())!=null){
            
            indexCount++;
            
            //as data is stored space separated
            // first the data separated by space
            String[] splitedCurrentLine = sCurrentLine.split(" "); 
            
            /**
             * Data might not be found at the very first point of the line, 
             * there might have space initially
             * it will look for until it find any character in each line
             */
            int checkValueInsplitedArray=0;
            while(splitedCurrentLine[checkValueInsplitedArray].trim().isEmpty()){
               checkValueInsplitedArray++; 
            }
            // First Token is the count of the index for corresponding password
            long Count=Long.parseLong(splitedCurrentLine[checkValueInsplitedArray].trim()) ;
            
            // Second Token is the password
            
            /**
             * Second token might be empty in only one case
             * empty password is also a password 
             * we have to set a login to check if the second token is empty or not
             * if empty then initialize "" 
             */
            String Password= "";
            if(checkValueInsplitedArray<splitedCurrentLine.length-1){
                Password= splitedCurrentLine[checkValueInsplitedArray+1].trim();
            }
            
            // Get the log2BasedIndex
            Log2BasedIndex = equations.twoBasedLog(indexCount);
            // convert into upto 2 decimal value
            Log2BasedIndex = equations.ShowDecimalUpto2(Log2BasedIndex);
           
            // Get the Probability Matric value
            ProbabilityMatric = equations.ProbabilityMatric(Count);
            ProbabilityMatric = equations.ShowDecimalUpto2(ProbabilityMatric);
            
            // Get the Index Matric value
            IndexMatric = equations.IndexMatric(indexCount);
            IndexMatric = equations.ShowDecimalUpto2(IndexMatric);
            
            // prior calculation for partial guessing metric
            LamdaBeta+=equations.Pi(Count);
            LamdaMuAlpha=LamdaBeta;
            MuAlpha = indexCount;
            SumOfPiMultiplyI +=equations.Pi(Count)*indexCount;
            
            // get the value of PartialGuessMatric using the above calculations
            PartialGuessMatric = equations.PartialGuessingMatric(LamdaMuAlpha,MuAlpha,SumOfPiMultiplyI);
            PartialGuessMatric = equations.ShowDecimalUpto2(PartialGuessMatric);
                        
            // get NIST entropy
            NistEntropy = equations.NistEntropy(Password);
            
            //Write the results of the metric space separted in the file
            TextTobeWrite = Password.trim()+" "+Log2BasedIndex+" "+Count+" "+ProbabilityMatric+" "+IndexMatric+" "+PartialGuessMatric+" "+NistEntropy;
            fileWriter.write(TextTobeWrite+"\n");
            
              
        }
        fileWriter.close();
         
    }    
}
