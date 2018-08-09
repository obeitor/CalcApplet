/**
 * @author Abdulgafar Obeitor 007632
 * University of Nottingham
 * Compiled 27/4/2014
 * Version 1
 */

package project_one;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 * @author Abdulgafar :USER ENTRY CLASS FOR MATRIX USING PANEL
 */
public class MatrixEntry extends JFrame{
public JTextField[][] MatrixInput;//a 2Dimensional array of textfield corresponding to the matrix
 public static float data[][];//temporary storage for newly created matrix
 public static boolean done = false;//to check if data has been entered successfully
 private int rows;//storing rows
 private int columns;//storing columns
 public MatrixEntry(int row, int column)
    {//constructor
     super("Enter Matrix Values");
     done = false;//whenever a new one is created set done as false
     rows =row;//Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows"));
     columns = column;//Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns"));
     GridLayout layout = new GridLayout(0, columns);//the textfield would be arranged column wise to fit the matrix formation
     MatrixInput = new JTextField[rows][columns];//create the textfield
     setSize(1500,750);//set size.. this size is capable of carrying 30 without problem...
     setLayout(layout);//put the gridlayout into JFrame layout
     data = new float[rows][columns];//initialize the data array
     for (int i=0;i<rows;i++)
         for (int j=0;j<columns;j++)
         {
             MatrixInput[i][j] = new JTextField();//create text field per matrix column and row
             MatrixInput[i][j].setSize(65, 20);//not really necessary as the gridlayout overrides
             add(MatrixInput[i][j]);//put the text field into the Frame
         }
     JButton OK = new JButton("OK");//OK button for user to complete
     OK.setSize(105, 15);//not necessary either
     add(OK);//put button into frame
     setVisible(true);//generate the frame
        OK.addActionListener(new ActionListener()
        {//action listener for the OK button
            @Override
            public void actionPerformed(ActionEvent e) {
                OK_DONE(e);
            }
        });
 }
 private void OK_DONE(ActionEvent e)
 {   int i=0,j=0;
     try{//once ok is pressed, try to store data... inappropriate input or empty textfields would be caught
     for ( i = 0;i<rows;i++)
         for ( j = 0;j<columns;j++)
             data[i][j] = Float.parseFloat(MatrixInput[i][j].getText());
          setVisible(false);//when complete close the frame
     done  = true;//set done to true
     //when done close and set done to true
     }catch(NumberFormatException exc)
     {JOptionPane.showMessageDialog(rootPane, "Invalid or Empty Entry at row "+(i+1)+" column "+(j+1));
      //print error message
     }
     }
}
