/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  HighRoll.java
 *  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-14
 *  Description   :
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HighRoll{



   public static void main( String args[] ) {


      int highScore = 0;
      DiceSet mySet = null;

      System.out.println( "\n   Welcome to HighRoll!!\n" );
      System.out.println( "     Press the 'q' key to quit the program." );

      // This line uses the two classes to assemble an "input stream" for the user to type
      // text into the program
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );


      if (args.length == 2){
        int countArg = Integer.parseInt(args[0]);
        int sidesArg = Integer.parseInt(args[1]);
        mySet = new DiceSet(countArg,sidesArg);

      }
      else {
        boolean breakVar = true;
        while(breakVar){
          System.out.print("Please enter the number of dice and sides desired. \n" +
          "sides must be greater than 4.  Enter the data as two integers seperated by a dash i.e 4-8"+
          "\n>>");
          try{
            String inputLine = input.readLine();
            if (inputLine.indexOf("-") != -1){
              int countIn = Integer.parseInt(inputLine.substring(0,inputLine.indexOf("-")));
              int sidesIn = Integer.parseInt(inputLine.substring(inputLine.indexOf("-")+1,inputLine.length()));
              mySet = new DiceSet(countIn,sidesIn);
              breakVar = false;
            }
            else if (inputLine.indexOf("-") == -1 && inputLine.length() > 0){
              System.out.println("Please put a space between the numbers");
            }
            else{
              System.out.println("Please enter a valid format");
            }
          }
          catch( IOException ioe ) {
             System.out.println( "Caught IOException" );
          }
        }
      }





      while( true ) {

         String inputLine = null;

         try {


            System.out.print("\nYour options are: \nROLL ALL THE DICE \nROLL A SINGLE DIE \nCALCULATE THE SCORE FOR THIS SET \n" +
                        "SAVE THIS SCORE AS THE HIGH SCORE \nDISPLAY THE HIGH SCORE \nENTER 'Q' or 'q' TO QUIT THE PROGRAM\n\n");

           String inputLine3 = input.readLine();

           if (inputLine3.toUpperCase().equals("ROLL ALL THE DICE")){
             mySet.roll();
             System.out.println("Rolled\n");
           }
           else if (inputLine3.toUpperCase().equals("ROLL A SINGLE DIE")){
             System.out.println("What die do you want to roll?");
             try{
               String inputLine2 = input.readLine();

               mySet.rollIndividual(Integer.parseInt(inputLine2));
             }
             catch ( IOException ioe ) {
                System.out.println( "Caught IOException" );
             }
           }
           else if (inputLine3.toUpperCase().equals("CALCULATE THE SCORE FOR THIS SET")){
             Integer setSum = new Integer(mySet.sum());
             System.out.println("The score is "+ setSum.toString() + "\n");
           }
           else if (inputLine3.toUpperCase().equals("SAVE THIS SCORE AS THE HIGH SCORE")){
             highScore = mySet.sum();
           }
           else if (inputLine3.toUpperCase().equals("DISPLAY THE HIGH SCORE")){
             Integer tempHighScore = new Integer(highScore);
             System.out.println("The high score is " + tempHighScore.toString() + "\n");
           }
           else if('Q' == inputLine3.toUpperCase().charAt(0)){
             break;
           }

         }
         catch( IOException ioe ) {
            System.out.println( "Caught IOException" );
         }
      }
   }
}
