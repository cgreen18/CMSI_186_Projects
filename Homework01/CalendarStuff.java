/**
 *  File name     :  CalendarStuff.java
 *  Purpose       :  Provides a class with supporting methods for CountTheDays.java program
 *  Author        :  B.J. Johnson (prototype)
 *                    Conor Green
 *  Date          :  2017-01-02 (prototype)
 *  Author        :  Conor Green
 *  Date          :  17/1/18
 *  Description   :  This file provides the supporting methods for the CountTheDays program which will
 *                   calculate the number of days between two dates.  It shows the use of modularization
 *                   when writing Java code, and how the Java compiler can "figure things out" on its
 *                   own at "compile time".  It also provides examples of proper documentation, and uses
 *                   the source file header template as specified in the "Greeter.java" template program
 *                   file for use in CMSI 186, Spring 2017.
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ----------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-01-02  B.J. Johnson  Initial writing and release
 *  @version 1.0.1  2017-12-25  B.J. Johnson  Updated for Spring 2018
 */
public class CalendarStuff {

  /**
   * A listing of the days of the week, assigning numbers; Note that the week arbitrarily starts on Sunday
   */
   private static final int SUNDAY    = 1;
   private static final int MONDAY    = SUNDAY    + 1;
   private static final int TUESDAY = MONDAY + 1;
   private static final int WEDNESDAY = TUESDAY + 1;
   private static final int THURSDAY = WEDNESDAY +1;
   private static final int FRIDAY = THURSDAY +1;
   private static final int SATURDAY = FRIDAY +1;

  /**
   * A listing of the months of the year, assigning numbers; I suppose these could be ENUMs instead, but whatever
   */
   private static final int JANUARY    = 1;
   private static final int FEBRUARY   = JANUARY   + 1;
   private static final int MARCH   = JANUARY   + 2;
   private static final int APRIL   = JANUARY   + 3;
   private static final int MAY   = JANUARY   + 4;
   private static final int JUNE   = JANUARY   + 5;
   private static final int JULY   = JANUARY   + 6;
   private static final int AUGUST   = JANUARY   + 7;
   private static final int SEPTEMBER   = JANUARY   + 8;
   private static final int OCTOBER   = JANUARY   + 9;
   private static final int NOVEMBER   = JANUARY   + 10;
   private static final int DECEMBER   = JANUARY   + 11;
  // you can finish these on your own, too

  /**
   * An array containing the number of days in each month
   *  NOTE: this excludes leap years, so those will be handled as special cases
   *  NOTE: this is optional, but suggested
   */
   private static int[]    days        = { 0,31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };   //the 0 is so I can just treat the months as 1-12  
   private static int[]   daysLeap     = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };   //instead of 0-11

  /**
   * The constructor for the class
   * Not to be used since there are no variables to be defined
   */
   public CalendarStuff() {

   }

  /**
   * A method to determine if the year argument is a leap year or not<br />
   *  Leap years are every four years, except for even-hundred years, except for every 400
   * @param    year  long containing four-digit year
   * @return         boolean which is true if the parameter is a leap year
   */
   public static boolean isLeapYear( long year ) {
      if (year % 4 != 0){
        return false;
      }
      else if (year % 100 != 0){
        return true;
      }
      else if (year % 400 == 0 ){
        return true;
      }
      else {
        return false;
      }

   }

  /**
   * A method to calculate the days in a month, including leap years
   * @param    month long containing month number, starting with "1" for "January"
   * @param    year  long containing four-digit year; required to handle Feb 29th
   * @return         long containing number of days in referenced month of the year
   * notes: remember that the month variable is used as an indix, and so must
   *         be decremented to make the appropriate index value
   */
   public static long daysInMonth( long month, long year ) {
     boolean leaping = isLeapYear(year);
     long dayCount = 0;

     if (leaping){
       dayCount = daysLeap[(int)month];
     }
     else {
       dayCount = days[(int)month];
     }

     return dayCount;

   }

  /**
   * A method to determine if two dates are exactly equal
   * @param    month1 long    containing month number, starting with "1" for "January"
   * @param    day1   long    containing day number
   * @param    year1  long    containing four-digit year
   * @param    month2 long    containing month number, starting with "1" for "January"
   * @param    day2   long    containing day number
   * @param    year2  long    containing four-digit year
   * @return          boolean which is true if the two dates are exactly the same
   */
   public static boolean dateEquals( long month1, long day1, long year1, long month2, long day2, long year2 ) {

     if (year1 == year2){
       if(month1==month2){
         if(day1==day2){
           return true;
         }
         else{
           return false;
         }
       }
       else{
         return false;
       }
     }
     else{
       return false;
     }

   }

  /**
   * A method to compare the ordering of two dates
   * @param    month1 long   containing month number, starting with "1" for "January"
   * @param    day1   long   containing day number
   * @param    year1  long   containing four-digit year
   * @param    month2 long   containing month number, starting with "1" for "January"
   * @param    day2   long   containing day number
   * @param    year2  long   containing four-digit year
   * @return          int    -1/0/+1 if first date is less than/equal to/greater than second
   */
   public static int compareDate( long month1, long day1, long year1, long month2, long day2, long year2 ) {

     if(dateEquals(month1,day1,year1,month2,day2,year2)){
       return 0;
     }

     if(year1 > year2){
       return 1;
     }
     else if(year2 > year1){
      return -1;
     }
     else{
       if(month1 > month2){
         return 1;
       }
       else if(month2 > month1){
        return -1;
       }
       else{
         if(day1 > day2){
           return 1;
         }
         else{
          return -1;
         }


       }
     }


   }

  /**
   * A method to return whether a date is a valid date
   * @param    month long    containing month number, starting with "1" for "January"
   * @param    day   long    containing day number
   * @param    year  long    containing four-digit year
   * @return         boolean which is true if the date is valid
   * notes: remember that the month and day variables are used as indices, and so must
   *         be decremented to make the appropriate index value
   */
   public static boolean isValidDate( long month, long day, long year ) {


      if (year < 0){
        return false;
      }

      if(month < 1 || month > 12){
        return false;
      }

      if (day < 1){
        return false;
      }
          else if(isLeapYear(year) && daysInMonth(month,year) < daysLeap[(int)month]){
        return false;
      }
      else if(!(isLeapYear(year)) && daysInMonth(month,year) < days[(int)month]){
          return false;
        }
      return true;
   }

  /**
   * A method to return a string version of the month name
   * @param    month long   containing month number, starting with "1" for "January"
   * @return         String containing the string value of the month (no spaces)
   */
   public static String toMonthString( int month ) {
      switch( month - 1 ) {
         case JANUARY: return "JANUARY";
         case FEBRUARY: return "FEBRUARY";
         case MARCH: return "MARCH";
         case APRIL: return "APRIL";
         case MAY: return "MAY";
         case JUNE: return "JUNE";
         case JULY: return "JULY";
         case AUGUST: return "AUGUST";
         case SEPTEMBER: return "SEPTEMBER";
         case OCTOBER: return "OCTOBER";
         case NOVEMBER: return "NOVEMBER";
         case DECEMBER: return "DECEMBER";
         default: throw new IllegalArgumentException( "Illegal month value given to 'toMonthString()'." );
      }
   }

  /**
   * A method to return a string version of the day of the week name
   * @param    day int    containing day number, starting with "1" for "Sunday"
   * @return       String containing the string value of the day (no spaces)
   */
   public static String toDayOfWeekString( int day ) {
      switch( day - 1 ) {
         case MONDAY: return "MONDAY";
         case TUESDAY: return "TUESDAY";
         case WEDNESDAY: return "WEDNESDAY";
         case THURSDAY: return "THURSDAY";
         case FRIDAY: return "FRIDAY";
         case SATURDAY: return "SATURDAY";
         case SUNDAY: return "SUNDAY";
         default: throw new IllegalArgumentException( "Illegal day value given to 'toDayOfWeekString()'." );
      }
   }

  /**
   * A method to return a count of the total number of days between two valid dates
   * @param    month1 long   containing month number, starting with "1" for "January"
   * @param    day1   long   containing day number
   * @param    year1  long   containing four-digit year
   * @param    month2 long   containing month number, starting with "1" for "January"
   * @param    day2   long   containing day number
   * @param    year2  long   containing four-digit year
   * @return          long   count of total number of days
   */
   public static long daysBetween( long month1, long day1, long year1, long month2, long day2, long year2 ) {
      long dayCount = 0;
      int yrDiff = 0;
      int numLeaps = 0;
      int whichIsHigher = compareDate(month1,day1,year1,month2,day2,year2);
      
      if(whichIsHigher == 0){
          dayCount = 0;
          return dayCount;  //to break out in special case
        }
      
      yrDiff = (int)(whichIsHigher*(year1 - year2));
      
      numLeaps += yrDiff/4;
      numLeaps -= yrDiff/100;
      numLeaps += yrDiff/400;

      dayCount = 365*yrDiff;  //turn into long
      dayCount += numLeaps;
      
      if(whichIsHigher == -1){
          if(isLeapYear(year2)){
             for (int i = 1; i < month2 ;i++){
                 dayCount += daysLeap[i];
            }
             for (int i = 1; i < month1 ;i++){
                 dayCount -= daysLeap[i];
            }
            dayCount += day2 -1;
            dayCount -= day1 -1;
           }
         else{
             for (int i = 1; i < month2 ;i++){
                 dayCount += days[i];
            }
            for (int i = 1; i < month1 ;i++){
                 dayCount -= days[i];
            }
            dayCount += day2 -1 ;
            dayCount -= day1 -1;
            }
         if(isLeapYear(year1) && (month1>2)){
             dayCount-=1;
            }
            
         dayCount -= day1;
        }
        else{
            if(isLeapYear(year1)){
             for (int i = 1; i < month1 ;i++){
                 dayCount += daysLeap[i];
            }
            for (int i = 1; i < month2 ;i++){
                 dayCount -= daysLeap[i];
            }
            dayCount += day1 -1 ;
            dayCount -= day2 -1;
           }
         else{
             for (int i = 1; i < month1 ;i++){
                 dayCount += days[i];
            }
            for (int i = 1; i < month2 ;i++){
                 dayCount -= days[i];
            }
            dayCount += day1 -1 ;
            dayCount -= day2 -1;
            }
         if(isLeapYear(year2) && (month2>2)){
             dayCount-=1;
            }
         dayCount -=day2;
        }
      dayCount ++;
      return dayCount;
   }

}
