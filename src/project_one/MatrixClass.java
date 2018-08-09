/**
 * @author Abdulgafar Obeitor 007632
 * University of Nottingham
 * Compiled 27/4/2014
 * Version 1
 */

package project_one;

import javax.swing.JOptionPane;

/**
 * @author Abdulgafar : MATRIX CLASS
 */
public class MatrixClass {
//Matrix class
    private int row;//to store its number of row
    private int column;//to store column
    public String Matname;//to store the name for the matrix
    private float[][] data;//store data in two dimensional array
    private static int matrixCounter=0;//count the number of matrices created
    private MatrixClass(String name,int m, int n, float[][] array)
    {//since private, a matrix can only be created by another function defined
        //create a new 2dimensional array, with m rows and n columns
        data = array;row=m;column=n;
        Matname = name;//name is MAT1 MAT2 etc

    }
    public int getRow(){return row;}
    public int getColumn(){return column;}
    public static MatrixClass MatrixCreator(int m, int n, float[][] array)
    {//this funtion is to monitor how many matrix created, should not exceed 5
        try{
        matrixCounter++;//to count number of matrices created directly not from an arithmetic operation
        if((m<=0)||(n<=0))throw new IllegalArgumentException("Matrix Cannot have less than 1 Row or Column");
        String name = "MAT"+matrixCounter;//store a name with the matrix counter
        return new MatrixClass(name,m,n,array);}
        catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());return null;}
    }
    public static void MatrixContentChanger(MatrixClass a,float[][]array,int m,int n)
    {//since java does not allow destructors, best way is to replace already created matrix items rather than delete
        a.row=m;a.column=n;//change the row and column as well
        a.data = array;
    }
    public static MatrixClass addMatrix(MatrixClass a, MatrixClass b)
    {//first if row a is not equal to row b or columns are not equal throw exception
        if ((a.row!=b.row)||(a.column!=b.column))throw new ArithmeticException("The row and Columns are not equal to allow Operation");
        float[][] ans = new float[a.row][b.column];//create new array to store ans
        for (int i=0;i<a.row;i++)
            for (int j=0;j<a.column;j++)
            {//add each element
                ans[i][j]=a.data[i][j]+b.data[i][j];
            }
        return new MatrixClass("MatAns",a.row,a.column,ans);//return the answer
    }
    public static MatrixClass minusMatrix(MatrixClass a, MatrixClass b)
    {//same as plus but with minus finish
        if ((a.row!=b.row)||(a.column!=b.column))throw new ArithmeticException("The row and Columns are not equal to allow Operation");
        float[][] ans = new float[a.row][b.column];//create new array to store ans
        for (int i=0;i<a.row;i++)
            for (int j=0;j<a.column;j++)
            {//subtract each element
                ans[i][j]=a.data[i][j]-b.data[i][j];//subtract each b from a
            }
        return new MatrixClass("MatAns",a.row,b.column,ans);//return ans
    }
    private static float [][] Matproduct;
    public static MatrixClass multiplyMatrix(final MatrixClass a, final MatrixClass b)
            {//nummber of columns of first must equal number of rows of second
        if (a.column!=b.row)throw new ArithmeticException("The row and Columns are not equal to allow Operation");
        Matproduct = new float[a.row][b.column];//create new array to store ans
        if(Sc_Calc.NoOfProcessors<=1){//if only one processor then cannot multithread
        float sum =0;//to store the sum of the dot product
        for (int i=0;i<a.row;i++){
            for (int j=0;j<a.column;j++){
                for (int k=0;k<b.column;k++)
            {//sum up the product of row a and column b
                    sum = sum+(a.data[i][k]*b.data[k][j]);
            }
                Matproduct[i][j]=sum;sum=0;//store sum in the ans array and reset sum
            }
        }
        }
        else//if more than one processor then multithread
        {
            Thread thread1 = new Thread()//do half
            {
                @Override
                public void run()
                {float sum =0;//to store the sum of the dot product
                 for (int i=0;i<a.row/2;i++){
            for (int j=0;j<(a.column);j++){
                for (int k=0;k<b.column;k++)
            {
                    sum = sum+(a.data[i][k]*b.data[k][j]);
            }
                Matproduct[i][j]=sum;sum=0;//store sum in the ans array and reset sum
            }
        }
                }
            };
            Thread thread2 = new Thread()//do second half
            {
                @Override
                public void run()
                {float sum =0;//to store the sum of the dot product
        for (int i=(a.row/2);i<a.row;i++){
            for (int j=0;j<a.column;j++){
                for (int k=0;k<b.column;k++)
            {
                    sum = sum+(a.data[i][k]*b.data[k][j]);
            }
                Matproduct[i][j]=sum;sum=0;//store sum in the ans array and reset sum
            }

        }
                }
            };try{//put the starting and ending in a try function
            thread1.start();thread2.start();thread1.join();thread2.join();
            }catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());}
            }
        return new MatrixClass("MatAns",a.row,b.column,Matproduct);
    }
public static MatrixClass inverseMatrix(MatrixClass a)
        {//to find inverse of matrix i.e 1/a
    float[][] ans = new float[a.row][a.column];
    for (int i=0;i<a.row;i++)
        for (int j=0;j<a.column;j++)
            ans[i][j] = 1/a.data[i][j];// divide 1 by each data element
    return new MatrixClass("MatAns",a.row,a.column,ans);//return answer
}
public static MatrixClass divideMatrix(MatrixClass a, MatrixClass b)
    {
    MatrixClass ans = multiplyMatrix(a,inverseMatrix(b));
    //multiply a with inverse of b;
    return ans;//return answer
}
public static MatrixClass transposeMatrix(MatrixClass a)
    {//transpose, invert the rows with columns
    float[][] ans = new float[a.column][a.row];
    for (int i=0;i<a.row;i++)
        for (int j=0;j<a.column;j++)
            ans[j][i]= a.data[i][j];//exchange the rows with columns ..transpose
    return new MatrixClass("MatAns",a.column,a.row,ans);
}
public static String displayMatrix(MatrixClass a)
    {//to display the matrix
    String str=a.Matname+":\n";//first put the matrix name in the string
    for(int i=0; i<a.row;i++){
        for(int j=0;j<a.column;j++)
            str = str+""+a.data[i][j]+"\t";//add data in one row
        str = str+"\n";//add a new line after every row
        }
    return str;
}
public static float detMatrix(float[][] a)
    {//to calculate determinant
    float det = 0;
    int m=-1 ;//the multiplier which is either 1 or -1 depending on what row
    try{
    if(a.length!=a[0].length)throw new Exception("Must be a Square Matrix");
    if(a.length==1)return a[0][0];//if the matrix is 1x1 then return the value as thats the det
    for (int i=0; i<a.length;i++)
    {
        float[][] matSmall = new float[a.length-1][a.length-1];
        for (int j=1;j<(a.length);j++)
        {//
            for (int k=0;k<a.length;k++)
            {
                if(k<i)matSmall[j-1][k]=a[j][k];
                else if(k>i)matSmall[j-1][k-1] = a[j][k];
            }
        }
        m = (-1)*m;
        det += m*a[0][i]*(detMatrix(matSmall));
    }
    }catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());}
    return det;
}
public static float[][] getMatrix(MatrixClass a)
    {
    return a.data;//to get the matrix as 2D array
}
}
