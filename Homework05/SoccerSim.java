/** SoccerSim.java
* run by command line.  Takes varArgs in and simulates a soccer field with balls
*Conor Green
*/

public class SoccerSim{

  public static final double FIELD_X_LEN = 1000.0;  //feet
  public static final double FIELD_Y_LEN = 500.0;   //feet
  public static final double BALL_WEIGHT = 1.0;   //pounds
  public static final double BALL_RADIUS = 4.45/12.0;  //feet
  public static final String DEFAULT_TIME_SLICE = "1.0";  //seconds


  private double numBalls;
  private Ball[] ballArr;
  private double totalSeconds;
  private double timeSlice;

  public SoccerSim(String inputArgs[]){  //inputArgs have already been vlaidated
    numBalls = (int)(inputArgs.length/4);
    totalSeconds = 0.0;
    timeSlice = Double.parseDouble(inputArgs[inputArgs.length-1]);

    for (int i = 0; i < numBalls*4-1; i+=4){
      ballArr[(i/4)] = new Ball(timeSlice,Double.parseDouble(inputArgs[i]),Double.parseDouble(inputArgs[i+1]),Double.parseDouble(inputArgs[i+2]),Double.parseDouble(inputArgs[i+3]));
    }

  }

  private static String[] validateArgs(String inputArgs[]) throws NumberFormatException, IllegalArgumentException {
    String[] strMatToReturn;


    if (inputArgs.length == 0){   //checks there are arguments
      throw new IllegalArgumentException("Enter arguments.");
    }
    else if(inputArgs.length % 4 != 0 && inputArgs.length % 4 != 1){  //if not possible number of arguments entered
      throw new IllegalArgumentException("Enter the arguments as sets of four numbers, possibly followed by a single (time slice) argument \ni.e. java SoccerSim 2 23 34 3 .7");
    }

    if (inputArgs.length % 4 == 0){  //no timeSlice
      for (int i = 0; i < inputArgs.length; i += 2){
        try{
          valiPosArgs(inputArgs[i],inputArgs[i+1]);
          valiVelArgs(inputArgs[i+2],inputArgs[i+3]);
        }
        catch(IllegalArgumentException iae){
          throw iae;
        }
        catch(NumberFormatException nfe){
          throw nfe;
        }
      }

      strMatToReturn = new String[inputArgs.length + 1];
      for(int i =0;i<inputArgs.length-1;i++){
        strMatToReturn[i] = inputArgs[i];
      }
      strMatToReturn[inputArgs.length] = DEFAULT_TIME_SLICE;

    }
    else if (inputArgs.length % 4 == 1){  //timeSlice entered
      for (int i = 0; i < inputArgs.length - 1; i += 2){
        try{
          valiPosArgs(inputArgs[i],inputArgs[i+1]);
          valiVelArgs(inputArgs[i+2],inputArgs[i+3]);
        }
        catch(IllegalArgumentException iae){
          throw iae;
        }
        catch(NumberFormatException nfe){
          throw nfe;
        }
      }
      try{
        valiTimeSliceArg(inputArgs[inputArgs.length-1]);
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

  private static String valiTimeSliceArg(String timeSliceArg) throws NumberFormatException, IllegalArgumentException{
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

  private static String[] valiPosArgs(String xPosArg, String yPosArg) throws NumberFormatException, IllegalArgumentException{
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

  private static String[] valiVelArgs(String xVelArg, String yVelArg) throws NumberFormatException{
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

    strMatToReturn[0] = xVelArg;
    strMatToReturn[1] = yVelArg;
    return strMatToReturn;
  }//ends valiVelArgs()

  private double[] collisionDetect(){
    double[] collReport = new double[7];
    collReport[0] = -1; //flag
    String[] strCatch = new String[2];

    for (int i = 0; i < numBalls - 1; i++){
      for(int j = i+1; j < numBalls; j++){
        if(calcDist(ballArr[i],ballArr[j]) <= BALL_RADIUS){
          collReport[0] = totalSeconds;
          collReport[1] = i;
          collReport[2] = j;
          collReport[3] = ballArr[i].getXPos();
          collReport[4] = ballArr[i].getYPos();
          collReport[5] = ballArr[j].getXPos();
          collReport[6] = ballArr[j].getYPos();
        }
      }
    }

    return collReport;    //returns collReport[0]=-1 if no collision
  }//ends collisionDetect()

  private double calcDist(Ball ballOne, Ball ballTwo){
    return (Math.sqrt(Math.pow(ballOne.getXPos()-ballTwo.getXPos(),2)+Math.pow(ballOne.getYPos()-ballTwo.getYPos(),2)));
  }//ends calcDist()

  private boolean ballsOnField(){
    boolean booToReturn = true;
    int onFieldSum = 0;

    for (int i = 0; i < numBalls;i++){
        onFieldSum += ballArr[i].onField(FIELD_X_LEN,FIELD_Y_LEN);   //adds 0 if off field, 1 if within field
    }

    return (onFieldSum != 0);   //true iff at least one ball on
                                //false iff no balls on field
  }//ends ballsOnField()

  private boolean ballsMoving(){
    int ballsMovingSum = 0;

    for (int i = 0; i < numBalls; i++){
      ballsMovingSum += ballArr[i].isMoving();
    }

    return (ballsMovingSum != 0);
  }

  public String toString(){
    StringBuilder strToReturn = new StringBuilder();
    String[] strCatch = new String[2];

    for (int i = 0; i < numBalls; i++){
      strCatch = ballArr[i].myToString();
      strToReturn.append("Ball ");
      strToReturn.append(i);
      strToReturn.append(strCatch[0]);
      strToReturn.append(strCatch[1]);
    }

    return strToReturn.toString();
  }//end toString()

  public double getTimeSlice(){
    return timeSlice;
  }

  public static void main(String args[]){
    double[] firstColl = new double [7];
    firstColl[0] = -1;    //flag that no coll has been recorded yet

    try{
      SoccerSim mySim = new SoccerSim(validateArgs(args));
    }
    catch(IllegalArgumentException iae){
      System.out.println(iae.toString());
      System.exit(1);
    }
    catch(NumberFormatException nfe){
      System.out.println(nfe.toString());
      System.exit(2);
    }

    SoccerSim mySim = new SoccerSim(validateArgs(args));

    Timer myTimer = new Timer(mySim.getTimeSlice());

    while (mySim.ballsOnField() && mySim.ballsMoving()){
      System.out.println(mySim.toString());

      if (mySim.collisionDetect()[0] != -1){
        firstColl = mySim.collisionDetect();
      }

      myTimer.tick();
    }//end main while

    //final print
    if(firstColl[0] != 1){
      System.out.printf("The first collision occured at %4.3f seconds between balls %d and %d. \n",firstColl[0],firstColl[1],firstColl[2]);
      System.out.printf("Ball %d was at (%4.2f,%4.2f) and ball %d was at (%4.2f,%4.2f).\n",firstColl[1],firstColl[2],firstColl[3],firstColl[4],firstColl[5],firstColl[6]);
    }
    else{
      System.out.println("NO COLLISION POSSIBLE.");
    }

  }//end main()

}//end SoccerSim
