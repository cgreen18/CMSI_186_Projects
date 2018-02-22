/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  DiceSet.java
 *  Purpose       :  Provides a class describing a set of dice
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-09
 *  Description   :  This class provides everything needed (pretty much) to describe a set of dice.  The
 *                   idea here is to have an implementing class that uses the Die.java class.  Includes
 *                   the following:
 *                   public DiceSet( int k, int n );                  // Constructor for a set of k dice each with n-sides
 *                   public int sum();                                // Returns the present sum of this set of dice
 *                   public void roll();                              // Randomly rolls all of the dice in this set
 *                   public void rollIndividual( int i );             // Randomly rolls only the ith die in this set
 *                   public int getIndividual( int i );               // Gets the value of the ith die in this set
 *                   public String toString();                        // Returns a stringy representation of this set of dice
 *                   public static String toString( DiceSet ds );     // Classwide version of the preceding instance method
 *                   public boolean isIdentical( DiceSet ds );        // Returns true iff this set is identical to the set ds
 *                   public static void main( String[] args );        // The built-in test program for this class
 *
 *  Notes         :  Stolen from Dr. Dorin pretty much verbatim, then modified to show some interesting
 *                   things about Java, and to add this header block and some JavaDoc comments.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-09  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class DiceSet {

  /**
   * private instance data
   */
   private int count;
   private int sides;
   private Die[] ds = null;

   // public constructor:
  /**
   * constructor
   * @param  count int value containing total dice count
   * @param  sides int value containing the number of pips on each die
   * @throws IllegalArgumentException if one or both arguments don't make sense
   * @note   parameters are checked for validity; invalid values throw "IllegalArgumentException"
   */
   public DiceSet( int countArg, int sidesArg ) {
      count = countArg;
      sides = sidesArg;
      ds = new Die[ countArg ];
      for(int i = 0;i<countArg;i++){
        ds[i] = new Die(sidesArg);

      }

   }

  /**
   * @return the sum of all the dice values in the set
   */
   public int sum() {
     int sumToReturn = 0;
     for (int i = 0; i < count; i++){
        sumToReturn += ds[i].getValue();
     }
      return sumToReturn;

   }

  /**
   * Randomly rolls all of the dice in this set
   *  NOTE: you will need to use one of the "toString()" methods to obtain
   *  the values of the dice in the set
   */
   public void roll() {
     int sumToReturn = 0;
     for (int i = 0; i < count; i++){
        sumToReturn += ds[i].roll();
     }
   }

  /**
   * Randomly rolls a single die of the dice in this set indexed by 'dieIndex'
   * @param  dieIndex int of which die to roll
   * @return the integer value of the newly rolled die
   * @trhows IllegalArgumentException if the index is out of range
   */
   public int rollIndividual( int dieIndex ) {
      return ds[dieIndex].roll();
   }

  /**
   * Gets the value of the die in this set indexed by 'dieIndex'
   * @param  dieIndex int of which die to roll
   * @trhows IllegalArgumentException if the index is out of range
   */
   public int getIndividual( int dieIndex ) {
      return ds[dieIndex].getValue();
   }

  /**
   * @return Public Instance method that returns a String representation of the DiceSet instance
   */
   public String toString() {
      StringBuilder strToReturn = new StringBuilder();
      for (int i = 0; i < count; i++){
        strToReturn.append(ds[i].toString());
      }
      return strToReturn.toString();
   }

  /**
   * @return Class-wide version of the preceding instance method
   */
   public static String toString( DiceSet dsArg ) {
      return dsArg.toString();
   }

  /**
   * @return  true iff this set is identical to the set passed as an argument
   */
   public boolean isIdentical( DiceSet ds2 ) {
      int[] indexDiceUsed =  new int[ds2.length];

      if (ds.length != ds2.length)
      {
        return false;
      }
      else if(ds.count != ds2.count){
        return false;
      }
      else if(ds.sides != ds.sides){
        return false;
      }

      for (int i = 0; i < ds.length; i++){
        for (int j = 0; j < ds.length; i++){
          if(ds.getIndividual(i) == ds2.getIndividual(j) && (indexDiceUsed[j] != 1) ){
            indexDiceUsed[j] == 1;
          }
        }
      }
      for (int k = 0;k < indexDiceused;k++){
        if( indexDiceUsed[k] == 0){
          return false;
        }
      }

      return true;
   }
  /**
   * A little test main to check things out
   */
   public static void main( String[] args ) {
      int countIn = Integer.parseInt(args[0]);
      int sidesIn = Integer.parseInt(args[1]);
      DiceSet myDS = new DiceSet(countIn,sidesIn);
      myDS.roll();
      System.out.println(myDS.toString());
      System.out.println();
      System.out.println(myDS.getIndividual(2));
      myDS.rollIndividual(2);
      System.out.println(myDS.getIndividual(2));
      System.out.println(myDS.toString());
      DiceSet secondDS = myDS; //same reference location
      System.out.println(secondDS.toString());
      DiceSet thirdDS = new DiceSet(countIn,sidesIn);   //same initials
      System.out.println(thirdDS.toString());
      System.out.println(myDS.isIdentical(secondDS));
      System.out.println(myDS.isIdentical(thirdDS));
   }

}
