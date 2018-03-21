/** SoccerSim.java
* run by command line.  Takes varArgs in and simulates a soccer field with balls
*Conor Green
*/

public class SoccerSim{


  public SoccerSim(){


  }

  private String validateArgs(String inputArgs[]){

    if (inputArgs.length == 0){   //checks there are arguments
      throw new IllegalArgumentException("Enter arguments.");
    }
    elseif (inputArgs.length % 4 != 0 || inputArgs.length % 4 != 1){  //if not possible number of arguments entered
      throw new IllegalArgumentException("Enter the arguments as sets of four numbers, possibly followed by a single \ni.e. java SoccerSim 2 23 34 3 .7")
    }

    if (){

    }


  }

  private String validate timeSliceArg(String timeSliceArg){

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
