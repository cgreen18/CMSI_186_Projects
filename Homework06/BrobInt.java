/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  Conor Green
 * Date       :  4/6/18
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *  1.2.0   4/6/18  Conor Green     Wrote the basic constructor, toString, and other basic methods
 *  1.3.0   4/18/18 Conor Green     Compilable. All methods written
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;

public class BrobInt {

   public static final BrobInt NEGONE  = new BrobInt("-1");
   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
   public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
   public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
   public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
   public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
   public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
   public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
   public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
   public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
   public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
   public static final BrobInt MAX_INT  = new BrobInt( new Integer( Integer.MAX_VALUE ).toString() );
   public static final BrobInt MIN_INT  = new BrobInt( new Integer( Integer.MIN_VALUE ).toString() );
   public static final BrobInt MAX_LONG = new BrobInt( new Long( Long.MAX_VALUE ).toString() );
   public static final BrobInt MIN_LONG = new BrobInt( new Long( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
   private String internalValue;        // internal String representation of this BrobInt
   private boolean   isPositive;         // "0" is positive, "1" is negative
   private String reversed;        // the backwards version of the internal String representation
   private byte[] byteVersion;      // byte array for storing the string values; uses the reversed string

   private static final String USAGE ="Enter a huge integer with (or without) the sign '+' or -'";

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use
   *  @param  value  String value to make into a BrobInt
   */
   public BrobInt( String valueArg ) {
      try{
          validateDigits(valueArg);
      }
      catch(IllegalArgumentException iae){
          System.out.println(iae.toString());
          System.out.println(USAGE);
          System.exit(1);
      }

      if(valueArg.charAt(0) == '-'){
          isPositive = false;
          internalValue = new String(valueArg.substring(1));
      }
      else if(valueArg.charAt(0) == '+') {
          isPositive = true;
          internalValue = new String(valueArg.substring(1));
      }
      else{
          isPositive = true;
          internalValue = new String(valueArg.substring(0));
      }

      byteVersion = strToByte(internalValue);

      reversed = new String(reverser(internalValue));

   }

   /**
    *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
    *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
    *   for later use
    *  @param  value  String value to make into a BrobInt
    */
    public BrobInt( BrobInt brobArg, boolean isPosArg) {
       internalValue = new String(brobArg.toString().substring(1));
       isPositive = isPosArg;
       byteVersion = brobArg.getByteArr();
       reversed = new String(reverser(internalValue));
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void validateDigits(String strArg) throws IllegalArgumentException {

       if(strArg.charAt(0) == '+' || strArg.charAt(0) == '-'){
          for(int i =1;i<strArg.length();i++){
              try{
                  Integer.parseInt(strArg.substring(i,i+1));
              }
              catch(NumberFormatException nfe){
                  throw new IllegalArgumentException("Non-number inputs.");
              }
          }
      }//ends if
      else{
          for(int i =0;i<strArg.length();i++){
              try{
                  Integer.parseInt(strArg.substring(i,i+1));
              }
              catch(NumberFormatException nfe){
                  throw new IllegalArgumentException("Non-number inputs.");
              }
          }
      }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of this BrobInt
   *  @return BrobInt that is the reverse of the value of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt reverser() {
      StringBuffer strForBrob = new StringBuffer();
      if(isPositive){
          strForBrob.append("+");
      }
      else{
          strForBrob.append("-");
      }
      strForBrob.append(this.reverser(internalValue));
      return (new BrobInt(strForBrob.toString()));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of a BrobIntk passed as argument
   *  Note: static method
   *  @param  gint         BrobInt to reverse its value
   *  @return BrobInt that is the reverse of the value of the BrobInt passed as argument
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt reverser( BrobInt gint ) {
       return gint.reverser();
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to reverse the value of a string passed as argument
    *  Note: static method
    *  @param  strArg         String to reverse its value
    *  @return String that is the reverse of the value of the String passed as argument
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static String reverser(String strArg){
       return new String( new StringBuffer( strArg ).reverse() );

   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to create a byte[] from a string (without "+" or "-")
    *  Note: static method
    *  @param  strArg   string to break into byte[]
    *  @return byte[]  byte array with each element being 2 digits of string and final value is last digit (if odd)
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static byte[] strToByte(String strArg){
      //int buff = 0;
      // if(strArg.charAt(0)=='+'||strArg.charAt(0)=='-'){
        //int buff = 1;
       //}
       byte[] byteArrToReturn = new byte[(strArg.length()/2)+(strArg.length()%2)];  //half size (plus 1 if odd size)
       int modder = strArg.length()%2;
       
       if(modder == 1){
          byteArrToReturn[0] = Byte.parseByte(strArg.substring(0,1));
       }
       else{
           byteArrToReturn[0]=Byte.parseByte(strArg.substring(0,2));
       }

        for(int i =1; i<byteArrToReturn.length-1; i+=1){
            byteArrToReturn[i] = Byte.parseByte(strArg.substring(2*i-modder,2*(i+1)-modder    ));
        }
        if(strArg.length()>=2){
            byteArrToReturn[byteArrToReturn.length-1] = Byte.parseByte(strArg.substring(strArg.length()-2));
        }
        //if(strArg.length()%2 == 1){
        //    byteArrToReturn[byteArrToReturn.length-1] = Byte.parseByte(strArg.substring(strArg.length()-1));
        //}

       return byteArrToReturn;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobIntk passed as argument to this BrobInt using byte array
   *  @param  gint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt addByte( BrobInt gint ) {
       String strForBrob = new String("");
       boolean finalPositive = true;

       if(this.compareTo(gint) >= 0){ //this>arg
           if(isPositive && gint.getPositive()){
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = true;
           }
           else if(isPositive && !(gint.getPositive())){
               strForBrob = subHelper(byteVersion,gint.getByteArr());
               finalPositive = true;
           }
           else if(!(isPositive) && gint.getPositive()){
               strForBrob = subHelper(byteVersion,gint.getByteArr());
               finalPositive = false;
           }
           else{
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = false;
           }
       }
       else if (this.compareTo(gint) < 0){ //arg>this
           if(isPositive && gint.getPositive()){
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = true;
           }
           else if(isPositive && !(gint.getPositive())){
               strForBrob = subHelper(gint.getByteArr(),byteVersion);
               finalPositive = false;
           }
           else if(!(isPositive) && gint.getPositive()){
               strForBrob = subHelper(gint.getByteArr(),byteVersion);
               finalPositive = true;
           }
           else{
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = false;
           }
       }



       StringBuilder tempBuild = new StringBuilder();
       if(finalPositive){
           tempBuild.append("+");
       }
       else{
           tempBuild.append("-");
       }
       tempBuild.append(this.trimStr(strForBrob));

       return new BrobInt(tempBuild.toString());

   }

   public String addHelper(byte[] bArrOne, byte[] bArrTwo){
       boolean carry = false;
       StringBuilder strForBrob = new StringBuilder();
       int holder;

       for(int i = 0; i<bArrTwo.length;i++){
           holder = (int)bArrOne[i] + (int)bArrTwo[i];
           if(carry){
               holder+=1;
           }

            if(holder>=100){
                carry = true;
                holder-=100;
                strForBrob.append(holder);
            }
            else{
                strForBrob.append(holder);
                carry = false;
            }
       }
       for(int i = bArrTwo.length; i<bArrOne.length;i++){
           strForBrob.append(bArrOne[i]);
       }

       return strForBrob.toString();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobIntk passed as argument to this BrobInt using int array
   *  @param  gint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt addInt( BrobInt gint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
   *  @param  gint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtractByte( BrobInt gint ) {
       String strForBrob = new String("");
       boolean finalPositive = true;

       if(this.compareTo(gint) >= 0){ //this>arg
           if(isPositive && gint.getPositive()){
               strForBrob = subHelper(byteVersion,gint.getByteArr());
               finalPositive = true;
           }
           else if(isPositive && !(gint.getPositive())){
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = true;
           }
           else if(!(isPositive) && gint.getPositive()){
               strForBrob = addHelper(byteVersion,gint.getByteArr());
               finalPositive = false;
           }
           else{
               strForBrob = subHelper(byteVersion,gint.getByteArr());
               finalPositive = false;
           }
       }
       else if (this.compareTo(gint) < 0){ //arg>this
           if(isPositive && gint.getPositive()){
               strForBrob = subHelper(gint.getByteArr(),byteVersion);
               finalPositive = false;
           }
           else if(isPositive && !(gint.getPositive())){
               strForBrob = addHelper(gint.getByteArr(),byteVersion);
               finalPositive = true;
           }
           else if(!(isPositive) && gint.getPositive()){
               strForBrob = addHelper(gint.getByteArr(),byteVersion);
               finalPositive = false;
           }
           else{
               strForBrob = subHelper(gint.getByteArr(),byteVersion);
               finalPositive = true;
           }
       }

       StringBuilder tempBuild = new StringBuilder();
       if(finalPositive){
           tempBuild.append("+");
       }
       else{
           tempBuild.append("-");
       }
       tempBuild.append(this.trimStr(strForBrob));

       return new BrobInt(tempBuild.toString());
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using int array
   *  @param  gint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtractInt( BrobInt gint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

   public String subHelper(byte[] bArrOne, byte[] bArrTwo){
       boolean carry = false;
       StringBuilder strForBrob = new StringBuilder();
       int holder;

       for(int i = 0; i<bArrTwo.length;i++){
           holder = (int)bArrOne[i] - (int)bArrTwo[i];
           if(carry){
               holder-=1;
           }

            if(holder<0){
                carry = true;
                holder+=100;
                strForBrob.append(holder);
            }
            else{
                strForBrob.append(holder);
                carry = false;
            }
       }
       for(int i = bArrTwo.length; i<bArrOne.length;i++){
           strForBrob.append(bArrOne[i]);
       }

       return strForBrob.toString();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  gint         BrobInt to multiply by this
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt gint ) {
       byte[] bArrForBrob = new byte[gint.getByteArr().length+byteVersion.length +1];
       //boolean finalPositive = !(gint.getPositive() ^ isPositive);
       BrobInt outBrob = new BrobInt("0");
       BrobInt brobOne = new BrobInt(internalValue);
       BrobInt brobTwo = new BrobInt(gint.toString().substring(1));

       while(!((brobTwo.compareTo(ONE) == 0 || brobTwo.compareTo(NEGONE)  == 0) && brobTwo.getByteArr()[brobTwo.getByteArr().length] == 1)){
           if(brobTwo.remainder(TWO) == ONE || brobTwo.remainder(TWO) == NEGONE){
               outBrob = outBrob.addByte(brobOne);
           }
           brobOne = brobOne.multiply(TWO);
           brobTwo = brobTwo.divide(TWO);
       }

       outBrob = outBrob.addByte(brobOne);

       return outBrob;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  gint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt gint ) throws ArithmeticException {//this/arg
      BrobInt brobToReturn = new BrobInt("0");
      String temp;
      BrobInt holder = new BrobInt("0");
      BrobInt currOut = new BrobInt("0");

      if(gint.compareTo(TWO) == 0){
          throw new ArithmeticException("Cannot divide by zero");
      }
      else if(this.compareTo(gint) < 0 ){
          return ZERO;
      }

      for(int i =0; i < gint.toString().substring(1).length(); i++){
          holder = this.multiply(currOut);
          temp = gint.toString().substring(2*i+1,2*(i+1)+1);
          temp = holder.toString().substring(1).concat(temp);
          holder = new BrobInt(temp);
          currOut = holder.divide(gint);
          if(currOut.compareTo(TEN) < 0){
              brobToReturn = new BrobInt(brobToReturn.toString().concat("0"));
              brobToReturn = new BrobInt(brobToReturn.toString().concat(currOut.toString().substring(0)));
          }
          else{
              brobToReturn = new BrobInt(brobToReturn.toString().concat(currOut.toString().substring(0)));
          }
      }

      if(!(isPositive)){
          String a = new String("-");
          brobToReturn = new BrobInt(a.concat(brobToReturn.toString().substring(1)));
      }

      return brobToReturn;

   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  @param  gint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt gint ) {
       return this.subtractByte(gint.multiply(this.divide(gint)));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to compare a BrobInt passed as argument to this BrobInt
   *  @param  gint  BrobInt to add to this
   *  @return int   that is one of neg/0/pos if this BrobInt lower/equals/greater the argument
   *  NOTE: this method performs a lexicographical comparison using the java String "compareTo()" method
   *        THAT was easy.....
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt gint ) {
      if(this.toString().length() > gint.toString().length()){
          return 1;
      }
      else if(this.toString().length() < gint.toString().length()){
          return -1;
      }
      else if(this.toString().charAt(0)== '-' && gint.toString().charAt(0) == '-'){
          return (this.toString().compareTo( gint.toString() ));
      }
      else if(this.toString().charAt(0)== '-' && gint.toString().charAt(0) == '+'){
          return -1;
      }
      else if(this.toString().charAt(0)== '+' && gint.toString().charAt(0) == '-'){
          return 1;
      }
      else{
          return (this.toString().compareTo( gint.toString() ));
      }

   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  gint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
   *        also using the java String "equals()" method -- THAT was easy, too..........
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt gint ) {
      return (this.toString().equals( gint.toString() ));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a BrobInt given a long value passed as argument
   *  @param  value         long type number to make into a BrobInt
   *  @return BrobInt  which is the BrobInt representation of the long
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt gi = null;
      try {
         gi = new BrobInt( new Long( value ).toString() );
      }
      catch( NumberFormatException nfe ) {
         System.out.println( "\n  Sorry, the value must be numeric of type long." );
      }
      return gi;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return a private variable btyeVersion
    *  @return byte[] byteVersion
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public byte[] getByteArr(){
       return byteVersion;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return a private variable isPositive
    *  @return boolean isPositive
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean getPositive(){
       return isPositive;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a String representation of this BrobInt
   *  @return String  which is the String representation of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString() {
      StringBuilder strToReturn = new StringBuilder();

      if(isPositive){
          strToReturn.append("+");
      }
      else{
          strToReturn.append("-");
      }

      for( int i = 0; i < byteVersion.length; i++ ) {
          strToReturn.append(byteVersion[i]);
      }

      return strToReturn.toString();
   }

   private byte[] trimByteArr(byte[] byteArg){
       int locat = 0;

       for(int i = byteArg.length - 1; i >= 0;i--){
           if(byteArg[i]!=0){
               locat = i;
               break;
           }
       }

       byte[] byteOut = new byte[byteArg.length-locat];

       for(int i = 0; i < byteOut.length;i++ ){
           byteOut[i] = byteArg[i+locat];
       }

       return byteOut;
   }

   private String trimStr(String strArg){
       byte[] byStr = this.strToByte(strArg);

       byStr = this.trimByteArr(byStr);

       StringBuilder strOut = new StringBuilder();

       for(int i = 0; i < byStr.length; i++){
           strOut.append(byStr[i]);
       }

       return byStr.toString();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to display an Array representation of this BrobInt as its bytes
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void toArray( byte[] d ) {
      System.out.println( Arrays.toString( d ) );
   }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  the main method redirects the user to the test class
   *  @param  args  String array which contains command line arguments
   *  note:  we don't really care about these
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
      System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

      System.exit( 0 );
   }
}
