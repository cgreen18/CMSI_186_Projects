//timer.  has tick() and getCurrTime() and toSTring()
/** Timer.java
* knows time and timeSlice.  Can increment total by slice
*Conor Green
*/


public class Timer{

  private double totalSeconds;
  private double timeSlice;

  public Timer(double timeSliceArg){
    timeSlice = timeSliceArg;
    totalSeconds = 0.0;
  }

  public void tick(){
    totalSeconds += timeSlice;
  }

  public double getCurrTime(){
    return totalSeconds;
  }

}
