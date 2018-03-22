//Ball class knows position and velocity
/** Ball.java
* knows position and velocity.  Can compute new position after one tick
*Conor Green
*/

public class Ball{

  private double timeSlice;
  private static double xPos;    //feet
  private static double yPos;   //feet
  private double xVel;          //feet/second
  private double yVel;

  public Ball(double timeSliceArg, double xPosArg, double yPosArg, double xVelArg, double yVelArg){
    timeSlice = timeSliceArg;
    xPos = xPosArg;
    yPos = yPosArg;
    xVel = xVelArg;
    yVel = yVelArg;
  }


  public void move(){
    xPos += xVel*timeSlice;
    yPos += yVel*timeSlice;
  }

  public void reCalcVels(){
    xVel = xVel - ((xVel*.01)*timeSlice);       //adjusts velocity at 1% per second
    yVel = yVel - ((yVel*.01)*timeSlice);
  }

  public String[] myToString(){
    String[] strToReturn = new String[2];

    strToReturn[0] = String.format(" is currently at: (%4.2f,%4.2f) \n", xPos,yPos);
    strToReturn[1] = String.format("And has a velocity of: (%4.2f,%4.2f) \n\n",xVel,yVel);

    return strToReturn;
  }

  public double getXPos(){
    return xPos;
  }

  public double getYPos(){
    return yPos;
  }

  public int onField(double fieldX, double fieldY){

    if (xPos > fieldX/2 || xPos < -fieldX/2 || yPos > fieldY/2 || yPos < -fieldY/2){
      return 0;   //0 means it is off the field
    }

    return 1;    //1 means it is on the field
  }

  public int isMoving(){
    if(Math.sqrt(Math.pow(xVel,2)+Math.pow(yVel,2)) <= 1/12){
      return 0;
    }

    return 1;   //moving
  }

  public static void main(String args[]){
      Ball myBall = new Ball(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]),Double.parseDouble(args[4]));
      System.out.println(myBall.toString());
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.toString());
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.toString());
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.toString());
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.toString());
      myBall.move();
      myBall.reCalcVels();
  }

}
