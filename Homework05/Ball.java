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

  public String toString(){
    String strToReturn = "";
    strToReturn = String.format("The ball's current position is: (%4.2f,%4.2f) \n", xPos,yPos);
    strToReturn = strToReturn + String.format("The ball's current velocity is: (%4.2f,%4.2f) \n",xVel,yVel);
    return strToReturn;
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
