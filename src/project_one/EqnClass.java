/**
 * @author Abdulgafar Obeitor 007632
 * University of Nottingham
 * Compiled 27/4/2014
 * Version 1
 */

package project_one;

import javax.swing.JOptionPane;

/**
 * @author Abdulgafar : EQUATION CLASS
 */
public class EqnClass {
//This class just holds the 4 functions for calculating the four tyes of equations
    public static String Eqn1Solver(float a1,float a2,float b1, float b2,float c1,float c2)
    {//for ax+by=c
        float eqn1 = c2-((a2*c1)/a1);//had to develop the equations mathematically before entering here
        float eqn2 = b2-((a2*b1)/a1);
        y = eqn1/eqn2;//
        x = (c1-(y*b1))/a1;
        //String ans = "X = "+x+" Y = "+y;
        String ans = "X = "+ComplexClass.roundOff(x)+" Y = "+ComplexClass.roundOff(y);
        return ans;

    }
    private static float x,y,z;//had to make them static to work them in thread classes and outside Eqn2Solver
    public static String Eqn2Solver(float a1,float a2,float a3,float b1,float b2,float b3,float c1,float c2,
            float c3, float d1, float d2, float d3)
    {//for ax+by+cz=d solved by cramer's rule
        String ans = "ERROR";//incase it didnt complete
        final float[][] mat = new float[3][3];
        mat[0][0] = a1;mat[1][0] = a2;mat[2][0] = a3;
        mat[1][0] = b1;mat[1][1] = b2;mat[2][1] = b3;
        mat[2][0] = c1;mat[1][2] = c2;mat[2][2] = c3;
        final float[][] matX = new float[3][3];
        matX[0][0] = d1;matX[1][0] = d2;matX[2][0] = d3;
        matX[1][0] = b1;matX[1][1] = b2;matX[2][1] = b3;
        matX[2][0] = c1;matX[1][2] = c2;matX[2][2] = c3;
        final float[][] matY = new float[3][3];
        matY[0][0] = a1;matY[1][0] = a2;matY[2][0] = a3;
        matY[1][0] = d1;matY[1][1] = d2;matY[2][1] = d3;
        matY[2][0] = c1;matY[1][2] = c2;matY[2][2] = c3;
        final float[][] matZ = new float[3][3];
        matZ[0][0] = a1;matZ[1][0] = a2;matZ[2][0] = a3;
        matZ[1][0] = b1;matZ[1][1] = b2;matZ[2][1] = b3;
        matZ[2][0] = d1;matZ[1][2] = d2;matZ[2][2] = d3;
        if(Sc_Calc.NoOfProcessors<=1){//if only one processor cannot multithread
             x = MatrixClass.detMatrix(matX)/MatrixClass.detMatrix(mat);
             y = MatrixClass.detMatrix(matY)/MatrixClass.detMatrix(mat);
             z = MatrixClass.detMatrix(matZ)/MatrixClass.detMatrix(mat);
             ans = "X = "+ComplexClass.roundOff(x)+" Y = "+ComplexClass.roundOff(y)+" Z = "+ComplexClass.roundOff(z);//before continuing

        }
 else{//else multithread them
            try{
        Thread Xthread = new Thread(){
        @Override//thread to solve matrix X determinant
        public void run(){ x = MatrixClass.detMatrix(matX)/MatrixClass.detMatrix(mat);
        }};
        Thread Ythread = new Thread(){
        @Override
        public void run(){ y = MatrixClass.detMatrix(matY)/MatrixClass.detMatrix(mat);
        }};
        Thread Zthread = new Thread(){
        @Override
        public void run(){ z = MatrixClass.detMatrix(matZ)/MatrixClass.detMatrix(mat);
        }};
        Xthread.start();Ythread.start();Zthread.start();//run the matrixes concurrently
        Xthread.join();Ythread.join();Zthread.join();//wait for all of them to finish first
        ans = "X = "+ComplexClass.roundOff(x)+" Y = "+ComplexClass.roundOff(y)+" Z = "+ComplexClass.roundOff(z);//before continuing
        //return ans;//the values are rounded off
        }catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());}}
        return ans;
    }
    public static String Eqn3Solver(float a,float b, float c)
    {//for ax^2+bx+c=0
        double D1,D2;String ans;
        D1=(b*b)-4*a*c;//b^2 - 4ac
        D2=(-1)*b;//negate b
        if(D1>0)
        {//if the roots are real
            D1 = Math.sqrt(D1);
            ans = "X1: "+ComplexClass.roundOff((float)((D2+D1)/(2*a)))+" X2: "+ComplexClass.roundOff((float)((D2-D1)/(2*a)));
        }
 else if(D1==0)
        {//if two equal roots
     D2 = D2/(2*a);
     ans = "X: "+ ComplexClass.roundOff((float)D2)+" Twice";
 }
 else
 {//if complex roots
            D1 = D1*(-1); //change D1 to positive
            D1 = Math.sqrt(D1);//then find the square root
            ComplexClass X1=ComplexClass.newComplxCartesian((float)(D2/(2*a)),(float)(D1/(2*a)));
            ComplexClass X2=ComplexClass.newComplxCartesian((float)(D2/(2*a)),(float)(D1/(-2*a)));
            //create two new complex numbers
            ans = "X1: "+ComplexClass.DisplayCartesian(X1)+" X2: "+ComplexClass.DisplayCartesian(X2);
            //store the answers as string
 }return ans;//return ans
    }
    
}
