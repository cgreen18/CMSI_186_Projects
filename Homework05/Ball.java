//Ball class knows position and velocity
/** Ball.java
* knows position and velocity.  Can compute new position after one tick
*Conor Green
*/

public class Ball{

  private double timeSlice;
  private double xPos;    //feet
  private double yPos;   //feet
  private double xVel;          //feet/second
  private double yVel;

  public Ball( double timeSliceArg,double xPosArg, double yPosArg, double xVelArg, double yVelArg){
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

    if (xPos > 0.5*fieldX || xPos < -0.5*fieldX || yPos > 0.5*fieldY || yPos < -0.5*fieldY){
      return 0;   //0 means it is off the field
    }

    return 1;    //1 means it is on the field
  }

  public int isMoving(){
    if(Math.sqrt(Math.pow(xVel,2)+Math.pow(yVel,2)) <= 2.0/12.0){
      xVel = 0.0;
      yVel = 0.0;
      return 0;
    }

    return 1;   //moving
  }

  public static void main(String args[]){
      Ball myBall = new Ball(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]),Double.parseDouble(args[4]));
      //Ball myBall = new Ball(1,200,50,150,100); 
      System.out.println(myBall.myToString()[0]);
      System.out.println(myBall.myToString()[1]);
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.myToString()[0]);
      System.out.println(myBall.myToString()[1]);
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.myToString()[0]);
      System.out.println(myBall.myToString()[1]);
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.myToString()[0]);
      System.out.println(myBall.myToString()[1]);
      myBall.move();
      myBall.reCalcVels();
      System.out.println(myBall.myToString()[0]);
      System.out.println(myBall.myToString()[1]);
      myBall.move();
      myBall.reCalcVels();
  }

}
