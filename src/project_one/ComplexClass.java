/**
 * @author Abdulgafar Obeitor 007632
 * University of Nottingham
 * Compiled 27/4/2014
 * Version 1
 */

package project_one;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Abdulgafar : COMPLEX CLASS
 */
public class ComplexClass {
    //stored as realNo + complexNo i or abs<arg with arg in degrees
    private float complexNo;
    private float realNo;
    private float abs;
    private float angle;
    private ComplexClass(float a, float b)
    {
        complexNo = b;realNo = a;//store the real and imag part
        angle = (float)Math.toDegrees(Math.atan(complexNo/realNo));//calculate and store the angle in degree
        abs = (float)Math.pow(Math.pow(realNo, 2)+Math.pow(complexNo, 2),0.5);

        //use the angle to calculate and store abs value
    }
    public static float roundOff(float val)
    {//rounding off, was designed mainly because converting to radian in double takes so much it gets approx
        //wrong answer.. like cos 90 should be zero but is about 2.6x 10E-16
        //this function would be used program wide though wherever rounding off is a good idea
        DecimalFormat twoDForm = new DecimalFormat("#.####");
     return Float.valueOf(twoDForm.format(val));
    }
    public static ComplexClass newComplxPolar(float a, float b)
    {//create a new Complex class in polar and store the real par and imag part by acos(b)+asin(b)i
        float c = (float)Math.cos(Math.toRadians(b));
        float d = (float)Math.sin(Math.toRadians(b));
      ComplexClass temp = new ComplexClass((a*roundOff(c)),(a*roundOff(d)));
      return temp;
    }
    public static ComplexClass newComplxCartesian(float r, float imag)
    {//create by taking the real and imaginary value direct
        ComplexClass temp = new ComplexClass(r,imag);
        return temp;
    }
    public static String DisplayPolar(ComplexClass x)
    {//display in polar form
        String POL_ = x.abs+"<"+x.angle;
        return POL_;
    }
    public static String DisplayCartesian(ComplexClass x)
    {//display in cartesian form
        String CART_;
     if(x.complexNo<0)CART_ = roundOff(x.realNo)+""+roundOff(x.complexNo)+"i";
     else
     CART_ = roundOff(x.realNo)+"+"+roundOff(x.complexNo)+"i";//else a+bi
        return CART_;
    }
    public static ComplexClass addition(ComplexClass x, ComplexClass y)
    {//addition, add real parts add imag parts
        float r = x.realNo + y.realNo;
        float i = x.complexNo + y.complexNo;
        ComplexClass temp = new ComplexClass(r,i);
        return temp;
    }
    public static ComplexClass subtraction(ComplexClass x, ComplexClass y)
    {
        float r = x.realNo - y.realNo;
        float i = x.complexNo - y.complexNo;
        ComplexClass temp = new ComplexClass(r,i);
        return temp;
    }
    public static ComplexClass multiply(ComplexClass x, ComplexClass y)
    {//multiplication by
        float r = (x.realNo*y.realNo)+(0-(x.complexNo*y.complexNo));
        float i = (x.realNo*y.complexNo)+(x.complexNo*y.realNo);
        ComplexClass temp = new ComplexClass(r,i);
        return temp;
    }
    public double getAbs()
            {
        return this.abs;
    }
    public double getArg()
    {return this.angle;
    }
    public static ComplexClass divide(ComplexClass x, ComplexClass y)
    {
        //division is done in polar form a<b / x<y
        //real part  = a/x; imag part = b-y
        float abs_ = x.abs/y.abs;
        float ang_ = x.angle-y.angle;
        ComplexClass temp = ComplexClass.newComplxPolar(abs_, ang_);//create via polar constructing function
        return temp;
    }
    public static ComplexClass toComplex(String str)//convert string to complex
    {
      if(str.contains("<"))
      {
          int i = str.indexOf('<');
          float absVal = Float.parseFloat(str.substring(0, i));
          float argVal = Float.parseFloat(str.substring(i+1));
          ComplexClass temp  = newComplxPolar(absVal, argVal);
          return temp;
      }
else{
            if(str.contains("+"))//if the string contains + then it is x+bi
            {
                int i = str.indexOf('+');
                String str1 = str.substring(0, i);
                String str2 = str.substring(i,str.length()-1);
                if(str2.contentEquals("+"))str2="1";//if it is just i, then store as 1i
                return new ComplexClass(Float.parseFloat(str1),Float.parseFloat(str2));
            }
            else if(str.substring(1).contains("-"))//used subst from 1 just incase the first val is a minus
            {
                int i = str.lastIndexOf('-');
                String str1 = str.substring(0, i);
                String str2 = str.substring(i, str.length()-1);
                if(str2.contentEquals("-"))str2="-1";//if it is just i, then store as 1i
                return new ComplexClass(Float.parseFloat(str1),Float.parseFloat(str2));
            }
        else
        {if(str.endsWith("i")){//if ends with i, set the imag part to the double val of str and real part to zero
            if(str.substring(0, str.length()-1).isEmpty())//if the number is empty then change to 1i
             return new ComplexClass(0,1);//if its just i
            else
             return new ComplexClass(0,Float.parseFloat(str.substring(0, str.length()-1)));
         }
            else //store the real part as val and the imag part as zero
            return new ComplexClass(Float.parseFloat(str),0);}
    }}
    public static String ComplexCalc(String UserInput)
    {
        StringBuilder Input = new StringBuilder(UserInput);//first put the whole string here so if no operation it is returned
        String set = "";
        char[] operators ={'+','-','x','÷'};
        ArrayList <String> calcarray = new ArrayList();
        for (int i =0; i<Input.length();i++)
        {
            if((Input.charAt(i)=='+')||(Input.charAt(i)=='x')||(Input.charAt(i)=='-')
                   ||(Input.charAt(i)=='÷')){//once we get to an operator
                if(!set.equals(""))calcarray.add(set);//if set is not empty store it
                set = "";//reset it
                for (int j=0;j<operators.length;j++){//check which operator and store it
                if(Input.charAt(i)==operators[j])calcarray.add(operators[j]+"");
            }}
 else//if not an operator then store the content to set string
     set = set+Input.charAt(i);}
        if(!set.equals(""))
            calcarray.add(set);//if at the end set is not empty, store it
       int end = calcarray.size();
       
        for ( int i=0;i<end;i++){
            System.out.println(calcarray.get(i));
            //browse through the arraylist
            if(calcarray.get(i).equals("+"))
                     {
    ComplexClass fnum = toComplex(calcarray.get(i-1));//store the first parameter
    ComplexClass snum = toComplex(calcarray.get(i+1));//store second parameter
    ComplexClass ans = addition(fnum,snum);//get the ans
     calcarray.set(i, DisplayCartesian(ans));//change the sign in the array to the ans
     calcarray.remove(i+1);calcarray.remove(i-1);//remove the first and last parameters
     i=0;end-=2;//restart i and reduce end by the two that were deleted
            }
            else if(calcarray.get(i).equals("-"))
            {
     ComplexClass fnum = toComplex(calcarray.get(i-1));//store the first parameter
     ComplexClass snum = toComplex(calcarray.get(i+1));//store second parameter
     ComplexClass ans = subtraction(fnum,snum);//get the ans
     calcarray.set(i, DisplayCartesian(ans));//change the sign in the array to the ans
     calcarray.remove(i+1);calcarray.remove(i-1);//remove the first and last parameters
 i=0;end-=2;
 }
            else if(calcarray.get(i).equals("x"))
            {
     ComplexClass fnum = toComplex(calcarray.get(i-1));//store the first parameter
     ComplexClass snum = toComplex(calcarray.get(i+1));//store second parameter
     ComplexClass ans = multiply(fnum,snum);//get the ans
     calcarray.set(i, DisplayCartesian(ans));//change the sign in the array to the ans
     calcarray.remove(i+1);calcarray.remove(i-1);//remove the first and last parameters
 i=0;end-=2;
 }   else if(calcarray.get(i).equals("÷"))
            {
     if((calcarray.get(i+1).equals("0"))||(calcarray.get(i+1).equals("0i")))
     throw new ArithmeticException("Math Error");//throw divide by zero error
     ComplexClass fnum = toComplex(calcarray.get(i-1));//store the first parameter
     ComplexClass snum = toComplex(calcarray.get(i+1));//store second parameter
     ComplexClass ans = divide(fnum,snum);//get the ans
     calcarray.set(i, DisplayCartesian(ans));//change the sign in the array to the ans
     calcarray.remove(i+1);calcarray.remove(i-1);//remove the first and last parameters
 i=0;end-=2;
 }
        }
        
        return calcarray.get(0);
}

}
