/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Clock.java
 *  Purpose       :  Provides a class defining methods for the ClockSolver class
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
 *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.lang.*;


public class Clock {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double INVALID_ARGUMENT_VALUE = -1.0;
   private static final double MAXIMUM_DEGREE_VALUE = 360.0;
   private static final double HOUR_HAND_DEGREES_PER_SECOND = 0.00834;
   private static final double MINUTE_HAND_DEGREES_PER_SECOND = 0.1;
   private static final double DEFAULT_EPSILON_VALUE              = 0.1;

   private double targAng;
   private double timeSlice;
   private double totSecs;
   private double epsilon;

  /**
   *  Constructor goes here
   */
   public Clock() {
     System.out.println("Angle argument must be specified.");
     System.exit(0);
   }

    public Clock(double angleArg) {
      targAng = angleArg%360.0;
      timeSlice = DEFAULT_TIME_SLICE_IN_SECONDS;
      totSecs = 0.0;
      epsilon = DEFAULT_EPSILON_VALUE;
    }

    public Clock(double angleArg, double tFrameArg) {
      targAng = angleArg%360.0;
      timeSlice = tFrameArg;
      totSecs = 0.0;
     epsilon = DEFAULT_EPSILON_VALUE;
    }
   public Clock(double angleArg, double tFrameArg, double epsArg) {
     targAng = angleArg%360.0;
     timeSlice = tFrameArg;
     totSecs = 0.0;
     epsilon = epsArg;
}

   public Clock(String[] args) {   //assuming args were already checked
     targAng = Double.parseDouble(args[0]);
     timeSlice = Double.parseDouble(args[1]);
     totSecs = 0.0;
     epsilon = Double.parseDouble(args[2]);
   }

  /**
   *  Methods go here
   *
   *  Method to calculate the next tick from the time increment
   *  @return double-precision value of the current clock tick
   */
   public double tick() {
     totSecs += timeSlice;
      return totSecs;
   }

  /**
   *  Method to validate the angle argument
   *  @param   argValue  String from the main programs args[0] input
   *  @return  double-precision value of the argument
   *  @throws  NumberFormatException
   */
   public static double validateAngleArg( String argValue ) throws NumberFormatException {
     double angToReturn = 0.0;

     try{
       angToReturn = Double.parseDouble(argValue);
     }
     catch(NumberFormatException nfe){
       throw new NumberFormatException("Enter double value");
     }
      return angToReturn;
   }

  /**
   *  Method to validate the optional time slice argument
   *  @param  argValue  String from the main programs args[1] input
   *  @return double-precision value of the argument or -1.0 if invalid
   *  note: if the main program determines there IS no optional argument supplied,
   *         I have elected to have it substitute the string "60.0" and call this
   *         method anyhow.  That makes the main program code more uniform, but
   *         this is a DESIGN DECISION, not a requirement!
   *  note: remember that the time slice, if it is small will cause the simulation
   *         to take a VERY LONG TIME to complete!
   */
   public static double validateTimeSliceArg( String argValue ) {
     double timeToReturn = 0.0;
     try{
       timeToReturn = Double.parseDouble(argValue);
     }
     catch(NumberFormatException nfe){
       throw new NumberFormatException("Enter double value");
     }
      return timeToReturn;
   }



   public double getEpsVal(){
     return epsilon;
   }

   public double getTargAng(){
     return targAng;
   }

  /**
   *  Method to calculate and return the current position of the hour hand
   *    with respect to zero
   *  @return double-precision value of the hour hand location
   */
   public double getHourHandAngle() {
      return (totSecs*HOUR_HAND_DEGREES_PER_SECOND);
   }

  /**
   *  Method to calculate and return the current position of the minute hand
   *  @return double-precision value of the minute hand location
   */
   public double getMinuteHandAngle() {
      return ((totSecs*MINUTE_HAND_DEGREES_PER_SECOND)%360.0);
   }

  /**
   *  Method to calculate and return the angle between the hands
   *  @return double-precision value of the angle between the two hands
   */
   public double getHandAngle() {
      double tempDbl= Math.abs(getMinuteHandAngle()-getHourHandAngle());
      if (tempDbl > 180){
        return(360 - tempDbl);
      }
      else{
        return(tempDbl);
      }


      // work on when it goes past 180 degrees
   }

  /**
   *  Method to fetch the total number of seconds
   *   we can use this to tell when 12 hours have elapsed
   *  @return double-precision value the total seconds private variable
   */
   public double getTotalSeconds() {
      return totSecs;
   }

   public String calcTime(){
     StringBuilder strToReturn = new StringBuilder();
     strToReturn.append(calcHour());

     return strToReturn.toString();
   }

   private int calcHour(){
     return (int)totSecs/3600;

   }

   private int calcMin(){
     return (int)(totSecs - 3600*calcHour())/60;
   }

   public int calcSecs(){
     return (int)(totSecs - 3600*calcHour() - 60*calcMin());
   }

   public double calcLeftOverSecs(){
     double quickFormat = ((int)(1000.0*totSecs)-1000*(int)totSecs)/1000.0;  //returns 3 digits past decimal
     return quickFormat;
   }








  /**
   *  Method to return a String representation of this clock
   *  @return String value of the current clock
   */
   public String toString() {
      StringBuilder strToReturn = new StringBuilder();


        if(calcHour() == 0){
          strToReturn.append("12");
        }
        else if (Integer.toString(calcHour()).length()<2){
            strToReturn.append("0");
            strToReturn.append(calcHour());
        }
        else{
            strToReturn.append(calcHour());
        }
        strToReturn.append(":");

        if (Integer.toString(calcMin()).length()<2){
            strToReturn.append("0");
            strToReturn.append(calcMin());
        }
        else{
            strToReturn.append(calcMin());
        }
        strToReturn.append(":");

        if (Integer.toString(calcSecs()).length()<2){
            strToReturn.append("0");
            strToReturn.append(calcSecs());
        }
        else{
            strToReturn.append(calcSecs());
        }
        strToReturn.append(":");
        strToReturn.append(calcLeftOverSecs());     //automatically the precision
                                                  //as given by timeSlice




      return strToReturn.toString();
   }


  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  be sure to make LOTS of tests!!
   *  remember you are trying to BREAK your code, not just prove it works!
   */
   public static void main( String args[] ) {
     double angleArg = 0.0;
     double timeSliceArg = 0.0;
     double epsArg =0.0;

     System.out.println( "\nCLOCK CLASS TESTER PROGRAM\n" +
                         "--------------------------\n" );

     System.out.println("This code will only work if you input at least one argument");
      if (args.length == 0){
        System.out.println("Angle argument must be specified.");
        System.exit(0);
      }


      System.out.println( "Now, it is putting the arguments through validation");

      if (args.length == 1){
        try{
          angleArg = validateAngleArg(args[0]);
        }
        catch(NumberFormatException nfe){
          System.out.println("Enter correct format");
          System.exit(2);
        }
        timeSliceArg = DEFAULT_TIME_SLICE_IN_SECONDS;
        epsArg = DEFAULT_EPSILON_VALUE;
      }
      else if (args.length == 2){
        try{
          angleArg = validateAngleArg(args[0]);
        }
        catch(NumberFormatException nfe){
          System.out.println("Enter correct format");
          System.exit(2);
        }
        try{
          timeSliceArg = validateTimeSliceArg(args[1]);
        }
        catch(NumberFormatException nfe){
          System.out.println("Enter correct format");
          System.exit(2);
        }
        epsArg = DEFAULT_EPSILON_VALUE;
      }
      else if(args.length == 3){
        try{
          angleArg = validateAngleArg(args[0]);
        }
        catch(NumberFormatException nfe){
          System.out.println("Enter correct format");
          System.exit(2);
        }
        try{
          timeSliceArg = validateTimeSliceArg(args[1]);
        }
        catch(NumberFormatException nfe){
          System.out.println("Enter correct format");
          System.exit(2);
        }
        epsArg = Double.parseDouble(args[2]);
      }

      System.out.println("If this prints, then the arguments were valid");


      System.out.println( "  Creating a new clock: " );
      Clock clock = new Clock(angleArg,timeSliceArg,epsArg);  //
      System.out.println( "    New clock created: " + clock.toString() );

      System.out.println("Showing that the program can tick and can display the time at any point");
      System.out.println("The toString method uses four methods to calculate the time and formate well");
      for (int i = 0; i < 230; i += 6){
        clock.tick();
        System.out.println(clock.toString());
      }

      System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
      try { System.out.println( (0.0 == clock.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }
      Clock clock2 = new Clock(21.2188, .233);  //
      System.out.println( "    New clock created: " + clock2.toString() );
      System.out.println( "    Testing validateAngleArg()....");
      System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
      try { System.out.println( (0.0 == clock2.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }

      System.out.println("Showing that the program can tick and can display the time at any point");
      for (int i = 0; i < 230; i += 6){
        clock2.tick();
        System.out.println(clock2.toString());
      }

      System.out.println("The program can even tell the hour and minutes seperately");
      for (int i = 0; i < 230; i += 6){
        clock2.tick();
        System.out.println(String.valueOf(clock2.calcMin()));
        System.out.println(String.valueOf(clock2.calcHour()));
        System.out.println(String.valueOf(clock2.calcSecs()));
        System.out.println(String.valueOf(clock2.calcLeftOverSecs()));
      }
   }
}
