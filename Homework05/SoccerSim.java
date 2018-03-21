/** SoccerSim.java
* run by command line.  Takes varArgs in and simulates a soccer field with balls
*Conor Green
*/

public class SoccerSim{

  public static final double FIELD_X_LEN = 1000.0;
  public static final double FIELD_Y_LEN = 500.0;

  public SoccerSim(){


  }

  private String[] validateArgs(String inputArgs[]) throws NumberFormatException, IllegalArgumentException {

    if (inputArgs.length == 0){   //checks there are arguments
      throw new IllegalArgumentException("Enter arguments.");
    }
    elseif (inputArgs.length % 4 != 0 && inputArgs.length % 4 != 1){  //if not possible number of arguments entered
      throw new IllegalArgumentException("Enter the arguments as sets of four numbers, possibly followed by a single \ni.e. java SoccerSim 2 23 34 3 .7")
    }

    if (inputArgs.length % 4 == 0){  //no timeSlice
      for (int i = 0; i < inputArgs.length; i += 2){
        try{
          mySim.valiPosArgs(inputArgs[i],intputArgs[i+1]);
          mySim.valiVelArgs(inputArgs[i+2],intputArgs[i+3]);
        }
        catch(IllegalArgumentException iae){
          throw iae;
        }
        catch(NumberFormatException nfe){
          throw nfe;
        }
      }
    }
    elseif (inputArgs.length % 4 == 1){  //timeSlice entered
      for (int i = 0; i < inputArgs.length - 1; i += 2){
        try{
          mySim.valiPosArgs(inputArgs[i],intputArgs[i+1]);
          mySim.valiVelArgs(inputArgs[i+2],intputArgs[i+3]);
        }
        catch(IllegalArgumentException iae){
          throw iae;
        }
        catch(NumberFormatException nfe){
          throw nfe;
        }
      }
      try{
        mySim.valiTimeSliceArg(inputArgs[inputArgs.length-1]);
      }
      catch(IllegalArgumentException iae){
        throw iae;
      }
      catch(NumberFormatException nfe){
        throw nfe;
      }
    }

    return inputArgs;  //will only return if no exceptions are thrown
  }

  private String valiTimeSliceArg(String timeSliceArg) throws NumberFormatException, IllegalArgumentException{
    double dblTimeSlice;

    try{
      dblTimeSlice = Double.parseDouble(timeSliceArg);
    }
    catch(NumberFormatException nfe){
      throw nfe;
    }

    if (dblTimeSlice <= 0 ){
      throw new IllegalArgumentException("Enter a positive time slice argument");
    }

    return timeSliceArg;
  }

  private String[] valiPosArgs(String xPosArg, String yPosArg) throws NumberFormatException, IllegalArgumentException{
    double dblXPos;
    double dblYPos;
    String[] strMatToReturn = new String[2];

    try{
      dblXPos = Double.parseDouble(xPosArg);
      dblYPos = Double.parseDouble(yPosArg);
    }
    catch(NumberFormatException nfe){
      throw nfe;
    }

    if (dblXPos<= -(FIELD_X_LEN/2) || dblYPos <= -(FIELD_Y_LEN/2) || dblXPos > (FIELD_X_LEN/2) || dblYPos > (FIELD_Y_LEN/2) ){
      throw new IllegalArgumentException("Enter valid position values");
    }

    strMatToReturn[0] = xPosArg;
    strMatToReturn[1] = yPosArg;
    return strMatToReturn;
  }

  private String valiVelArgs(String xVelArg, String yVelArg) throws NumberFormatException{
    double dblXVel;
    double dblYVel;
    String[] strMatToReturn = new String[2];

    try{
      dblXVel = Double.parseDouble(xVelArg);
      dblYVel = Double.parseDouble(yVelArg);
    }
    catch(NumberFormatException nfe){
      throw nfe;
    }

    strMatToReturn[0] = xPosArg;
    strMatToReturn[1] = yPosArg;
    return strMatToReturn;
  }

  public static void main(String args[]){

    SoccerSim mySim = new SoccerSim();

    try{
      mySim.validateArgs(args);
    }
    catch(IllegalArgumentException iae){
      System.out.println(iae.toString());
      System.exit(1);
    }
    catch(NumberFormatException nfe){
      System.out.println(nfe.toString());
      System.exit(2);
    }

  }

}
