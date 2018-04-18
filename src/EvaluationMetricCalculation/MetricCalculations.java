/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvaluationMetricCalculation;

import java.text.DecimalFormat;

/**
 *
 * @author ranak
 */
public class MetricCalculations {
    
    
    String Dataset;
    long totalAccountPasswordInDataSet;
    public MetricCalculations(String DataSetName , long totalAccPass){
        
        Dataset = DataSetName;
        totalAccountPasswordInDataSet = totalAccPass;
        
    }
    
     
    public double  ProbabilityMatric(long count){
     
        long PassCount = count;
        double  ProbabilityMatricResult =(-1)*(twoBasedLog(Pi(count)));
        return ProbabilityMatricResult;
       
    }
    
    
    
    public double  IndexMatric(long IndexOfPassword){
     
        long index = IndexOfPassword;
        double IndexMatricResult= twoBasedLog((2*IndexOfPassword)-1);
        return IndexMatricResult;
    }
    
    public double  PartialGuessingMatric(double LamdaMuAlpha,double MuAlpha,double SumOfPiMultiplyI){
       
        double GAlpha= ((1-LamdaMuAlpha)*MuAlpha)+SumOfPiMultiplyI;
        double tiltGAplha = twoBasedLog(((2*GAlpha)/LamdaMuAlpha)-1)+twoBasedLog(1/(2-LamdaMuAlpha));
        //System.out.println(tiltGAplha);
        
        return tiltGAplha;
    }
    
    public double  NistEntropy(String Password){
     
        String targetPass = Password;
        double NistValue=0.0;
        long lengthOfPass = targetPass.length();
        
        if(lengthOfPass<9){
            NistValue = 4+((lengthOfPass-1)*2);
            //System.out.println("less then 9");
        }
        else if(lengthOfPass>9 || lengthOfPass<21){
            NistValue = 4+(7*2)+(lengthOfPass-8)*1.5;
            //System.out.println("greater then 9");
        }
        else {
            NistValue =4+(7*2)+(12*1.5)+(lengthOfPass-20);
            //System.out.println("greater then 21");
            
        } 
        
        if(targetPass.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$") || targetPass.matches("^(.*?[A-Z]){1,}.*$")){
            
            NistValue+=6;
            //System.out.println("bonous 6");
        
        }
        
        
        return NistValue;
    }
    
    public double Pi(long count){
        
        
        long passCount = count;
        
        double resultOfPi =((double)passCount/totalAccountPasswordInDataSet);
        
        return resultOfPi;
    
        //System.out.println(twoBasedLog(resultOfPi)+":   ff"); 
    }
    
    public double twoBasedLog(double value){
        
        double targetValue=value;
        double logedValue= Math.log(value)/Math.log(2.0);
        return logedValue;
    }
    
    public double  ShowDecimalUpto2(double value){
     
        double targetValue=value;
        return  Double.parseDouble(new DecimalFormat("##.##").format(targetValue));
    }
    
}
