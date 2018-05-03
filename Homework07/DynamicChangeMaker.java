/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  DynamicChangeMaker.java
 * Purpose    :  Learning exercise to implement dynamic programming to make the best change
 * @author    :  Conor Green
 * Date       :  4/24/18
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework07.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  04-25-18    Conor Green  Initial writing and begin coding
 *  1.1.0  04-27-18    Conor Green  Revision.  Uploaded 1.0.0 to github
 *  1.2.0  05-01-18    Conor Green  Made makeChangeWithDynamicProgramming static.  Uploaded this version to github
 *  1.3.0  05-01-18    Conor Green  Nearly final version
 *  1.3.5  05-01-18    Conor Green  Final version!
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


import java.util.*;


public class DynamicChangeMaker{



    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Constructor w/o arguments just in case. Uses java Object super()
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public DynamicChangeMaker(){
        super();
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to validate arguments given as strings *for calling through command line
     *  @param  argDenom- the denominations in String form w commas
     *  @param argTarg- the target in String form
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static void validateStringArgs(String argDenom,String argTarg) throws IllegalArgumentException{
        int temp = -1;
        String[] reformDenom;
        int[] argDenomArr;

        try{
            temp = Integer.parseInt(argTarg);
        }
        catch(NumberFormatException nfe){
            System.out.println("BAD DATA");
            System.out.println(nfe.toString());
            System.exit(1);
        }

        if(temp <= 0){
            throw new IllegalArgumentException("The target must be greater than 0.\n");
        }

        reformDenom = reformArg(argDenom);

        for(int i = 0; i < reformDenom.length;i++){
            try{
                temp = Integer.parseInt(reformDenom[i]);
            }
            catch(NumberFormatException nfe){
                System.out.println("BAD DATA");
                System.out.println(nfe.toString());
                System.exit(1);
            }

            if(temp <= 0){
                throw new IllegalArgumentException("Each denomination must be greater than 1.\n");
            }
        }

        argDenomArr = strToIntArr(reformDenom);

        for(int i = 0; i < argDenomArr.length;i++){
            for(int j=i+1;j<argDenomArr.length;j++){
                if(argDenomArr[i] == argDenomArr[j]){
                    throw new IllegalArgumentException("You cannot enter duplicate denominations.\n");
                }
            }
        }
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to validate arguements given as integers  *for calling static makeChange... method
     *  @param  argArr- the denominations int array
     *  @param argTarg- the target value as an integer
     *  @exception  IllegalArgumentException throws when data is negative, zero, or duplicated
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static void validateArgs(int[] argArr,int argTarg) throws IllegalArgumentException{
        if(argArr.length == 0){
            throw new IllegalArgumentException("No values found in the denomination array.");
        }
        else if(String.valueOf(argTarg).length()==0){
                throw new IllegalArgumentException("Enter a positive, non-zero target value.");
        }
        else if(argTarg <= 0){
            throw new IllegalArgumentException("Enter a positive, non-zero target value.");
        }

        for(int i = 0; i < argArr.length;i++){
            if(argArr[i] <= 0){
                throw new IllegalArgumentException("Enter all positive, denomination values greater than one.");
            }
            for(int j = i+1;j<argArr.length;j++){
                if(argArr[i] == argArr[j]){
                    throw new IllegalArgumentException("Enter the denominations without duplicates.");
                }
            }
        }
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to reform the denomination String into a String Array
     *  The String argument has commas and this parses the ints between commas
     *  @param  argsIn- the String argument
     *  @return String[]  returns the string argument parsed between commas
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static String[] reformArg(String argsIn){
        int i = 0;  //lagging increment
        int j = 0;  //increment
        ArrayList templist = new ArrayList(0);

        while(argsIn.indexOf(',',j+1) >= 0){   //indexof= -1 iff it no more commas to find
            j = argsIn.indexOf(',',j+1);
            templist.add(argsIn.substring(i,j));
            i = j;
        }
        templist.add(argsIn.substring(j+1));

        String[] strArr = new String[templist.size()];
        for(int w = 0; w < strArr.length;w++){
            strArr[w]=templist.get(w).toString();
        }

        return strArr;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to turn a String array into an int array  *for making change from command line
     *  @param  strArg- a String array with integers in the slots
     *  @return int[] w all the ints parsed
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static int[] strToIntArr(String[] strArg){
        int[] arrToReturn = new int[strArg.length];

        for(int i = 0; i<strArg.length;i++){
            try{
                arrToReturn[i] = Integer.parseInt(strArg[i]);
            }
            catch(NumberFormatException nfe){
                System.out.println("BAD DATA");
                System.out.println(nfe.toString());
                System.exit(1);
            }
        }

        return arrToReturn;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to make change out of the denominations into the target.
     *  Static, so it runs w/ the correct arguments without instanciating a new object
     *  @param  argArr- the denominations int array
     *  @param argTarg- the target value as an integer
     *  @return Tuple  returns the best possible Tuple for the parameters.  Returns the IMPOSSIBLE Tuple if it is impossible
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static Tuple makeChangeWithDynamicProgramming(int[] argArr, int argTarg){
        try{
            validateArgs(argArr,argTarg);
        }
        catch(IllegalArgumentException iae){
            System.out.println("BAD DATA");
            System.out.println(iae.toString());
            System.out.println("\n------------ IMPORTANT MESSAGE --------------");
            System.out.println("Usually I would System.exit() here but the test harness needs to keep running.\n");
            return Tuple.IMPOSSIBLE;
            //System.exit(1);  //exit() removed so DynamicChangemakerTestHarness can keep testing
        }

        int[] denoms = argArr;
        int target = argTarg;
        int[] temp;
        Tuple tempTuple;
        Tuple[][] ansArr = new Tuple[denoms.length][target+1];

        int[] zeroArr = new int[denoms.length];
        for(int i = 0; i < zeroArr.length;i++){zeroArr[i] = 0;}//creates zero array

        for(int i = 0; i<denoms.length;i++){
            ansArr[i][0] = new Tuple(zeroArr);
        }



        for(int i = 0; i < denoms.length;i++){ //denoms-rows
            for(int j = 1; j <= target;j++){ //target-columns
                if((j-denoms[i])>=0){
                    temp = copyArr(zeroArr);
                    temp[i] = 1;
                    ansArr[i][j] = new Tuple(temp);
                }
                else{
                    ansArr[i][j] = Tuple.IMPOSSIBLE;
                }

                if((j-denoms[i])>=0){

                    ansArr[i][j] = addTuples(ansArr[i][j],ansArr[i][j-denoms[i]]);
                }

                if(i>0){

                    ansArr[i][j] = compareTuples(ansArr[i][j],ansArr[i-1][j]);
                }
            }
        }

        tempTuple = ansArr[denoms.length-1][argTarg];
        //System.out.println(tempTuple.toString());
        if(tempTuple.length()==0){
            return Tuple.IMPOSSIBLE;
        }
        return tempTuple;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to compare two Tuples and return the "better" one
     *  @param  firstTuple- the first Tuple to compare
     *  @param  secondTuple- the seconde Tuple to compare
     *  @return Tuple  the "better" Tuple
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static Tuple compareTuples(Tuple firstTuple,Tuple secondTuple){
        if(firstTuple.isImpossible() && secondTuple.isImpossible()){
            return new Tuple(new int[0]);
        }
        else if(firstTuple.isImpossible() || firstTuple.length()==0){
            return secondTuple;
        }
        else if(secondTuple.isImpossible() || secondTuple.length()==0){
            return firstTuple;
        }


        if(firstTuple.total()<secondTuple.total()){
            return firstTuple;
        }
        else if(firstTuple.total()>secondTuple.total()){
            return secondTuple;
        }
        else{
            return firstTuple;
        }

        //return new Tuple(new int[0]);
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to add Tuples.  First checks "bad" cases then prompts Tuple to .add()
     *  @param  firstTuple- the first Tuple to compare
     *  @param  secondTuple- the seconde Tuple to compare
     *  @return Tuple  the sum of the two Tuples.  Returns IMPOSSIBLE Tuple if one is impossible
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static Tuple addTuples(Tuple firstTuple, Tuple secondTuple){
        if (firstTuple.isImpossible() || secondTuple.isImpossible()){
            return Tuple.IMPOSSIBLE;
        }
        else if(firstTuple.length() == 0 || secondTuple.length() == 0){
            return Tuple.IMPOSSIBLE;
        }
        else{
            return firstTuple.add(secondTuple);
        }
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to copy an array without messing with the original.  Avoids that addressing error
     *  @param  argArr- int array argument to be copied
     *  @return int[]  the copied array
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static int[] copyArr(int[] argArr){
        int[] arrToReturn = new int[argArr.length];

        for(int i = 0; i <arrToReturn.length;i++){
            arrToReturn[i] = argArr[i];
        }

        return arrToReturn;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  The main() of DynamicChangeMaker.java    Validates the arguements and then runs
     * makeChangeWithDynamicProgramming() method w/ validated and reformed arguemnts
     *  @param  args-  a String array given by the command line
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static void main(String[] args){  //args[0] is of form "3,5,7,8" = denominations
    //args[1] in form "34" = target
        if(args.length !=2){
            System.out.println("BAD DATA");
            System.out.println("You must enter two parameters for denominations and target");
            System.exit(2);
        }

        System.out.println(args[0]);
        System.out.println(args[1]);

        try{
            validateStringArgs(args[0],args[1]);
        }
        catch(IllegalArgumentException iae){
            System.out.println("BAD DATA");
            System.out.println(iae.toString());
        }

        System.out.println("stuck");

        int[] denominations=strToIntArr(reformArg(args[0]));
        //System.out.println("denoms");
        int targetValue = Integer.parseInt(args[1]);
        //System.out.println("target");
        System.out.println(DynamicChangeMaker.makeChangeWithDynamicProgramming(denominations,targetValue).toString());

    }

}
