/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  ClockSolver.java
 *  Purpose       :  The main program for the ClockSolver class
 *  @see
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

public class ClockSolver {
  /**
   *  Class field definintions go here
   */
   private static final double MAX_TIME_SLICE_IN_SECONDS  = 1800.00;
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double DEFAULT_EPSILON_VALUE              = 0.1;      // small value for double-precision comparisons

  /**
   *  Constructor
   *  This just calls the superclass constructor, which is "Object"
   */
   public ClockSolver() {

      super();
   }


   public static String[] handleInitialArguments( String args[] ) {
     // args[0] specifies the angle for which you are looking
     //  your simulation will find all the angles in the 12-hour day at which those angles occur
     // args[1] if present will specify a time slice value; if not present, defaults to 60 seconds
     // you may want to consider using args[2] for an "angle window"

      System.out.println( "\n   Hello world, from the ClockSolver program!!\n\n" ) ;
      if( 0 == args.length ) {
         System.out.println( "   Sorry you must enter at least one argument\n" +
                             "   Usage: java ClockSolver <angle> [timeSlice]\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }
      double angleArg = 0.0;
      double timeSliceArg = 0.0;
      double epsArg =0.0;

      String[] argsToReturn = new String[3];


       if (args.length == 1){
         try{
           angleArg = Clock.validateAngleArg(args[0]);
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
           angleArg = Clock.validateAngleArg(args[0]);
         }
         catch(NumberFormatException nfe){
           System.out.println("Enter correct format");
           System.exit(2);
         }
         try{
           timeSliceArg = Clock.validateTimeSliceArg(args[1]);
         }
         catch(NumberFormatException nfe){
           System.out.println("Enter correct format");
           System.exit(2);
         }
         epsArg = DEFAULT_EPSILON_VALUE;
       }
       else if(args.length == 3){
         try{
           angleArg = Clock.validateAngleArg(args[0]);
         }
         catch(NumberFormatException nfe){
           System.out.println("Enter correct format");
           System.exit(2);
         }
         try{
           timeSliceArg = Clock.validateTimeSliceArg(args[1]);
         }
         catch(NumberFormatException nfe){
           System.out.println("Enter correct format");
           System.exit(2);
         }
         epsArg = Double.parseDouble(args[2]);

       }
       argsToReturn[0]= String.valueOf(angleArg);
       argsToReturn[1]= String.valueOf(timeSliceArg);
       argsToReturn[2]= String.valueOf(epsArg);

       return argsToReturn;
   }


  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  @param  args  String array of the arguments from the command line
   *                args[0] is the angle for which we are looking
   *                args[1] is the time slice; this is optional and defaults to 60 seconds
   */
   public static void main( String args[] ) {
      ClockSolver clockSolve = new ClockSolver();
      //Clock clock = new Clock(12.3);     //creates dummy clock for validating angle arguments
      double[] timeValues = new double[3];

      String[] argsStringArray = handleInitialArguments( args );

      Clock myClock = new Clock(argsStringArray);
      while( myClock.getTotalSeconds() <= 12*60*60 ) {
          myClock.tick();
         if(Math.abs(myClock.getHandAngle()-myClock.getTargAng()) <= myClock.getEpsVal() ){
           System.out.print("At ");
           System.out.print(myClock.toString());
           System.out.print(" the clock reaches: ");
           System.out.println(myClock.getTargAng());

         }
      }
      System.exit( 0 );
   }
}
