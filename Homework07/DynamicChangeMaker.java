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
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

//standard JavaDocs:
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Description
 *  @param  gint Description
 *  @return BrobInt description
 *  @exception  Exception description
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.util.*;



public class DynamicChangeMaker{

    private int[] classDenoms;
    private int target;
    private Tuple[][] ansArr;

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public DynamicChangeMaker(String argDenom, String argTarg){

        try{
            validateStringArgs(argDenom,argTarg);
        }
        catch(IllegalArgumentException iae){
            System.out.println("BAD DATA");
            System.out.println(iae.toString());
        }

        //args already validated
        classDenoms = strToIntArr(reformArg(argDenom));
        target = Integer.parseInt(argTarg);

        ansArr = new Tuple[classDenoms.length][target+1];

    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private void validateStringArgs(String argDenom,String argTarg) throws IllegalArgumentException{
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

            if(temp <= 1){
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
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static void validateArgs(int[] argArr,int argTarg) throws IllegalArgumentException{
        if(argArr.length == 0){
            throw new IllegalArgumentException("No values found in the denomination array.");
        }
        else if(argTarg <= 0){
            throw new IllegalArgumentException("Enter a positive, non-zero target value.");
        }

        for(int i = 0; i < argArr.length;i++){
            if(argArr[i] <= 0){
                throw new IllegalArgumentException("Enter all positive, non-zero denomination values.");
            }
            for(int j = i+1;j<argArr.length;i++){
                if(argArr[i] == argArr[j]){
                    throw new IllegalArgumentException("Enter the denominations without duplicates.");
                }
            }
        }
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private String[] reformArg(String argsIn){
        int i = 0;  //lagging increment
        int j = 0;  //increment
        ArrayList templist = new ArrayList(0);

        while(argsIn.indexOf(',',j+1) >= 0){   //indexof= -1 iff it no more commas to find
            j = argsIn.indexOf(',',j);
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
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private int[] strToIntArr(String[] strArg){
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
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static void makeChangeWithDynamicProgramming(int[] argArr, int argTarg){
        try{
            validateArgs(argArr,argTarg);
        }
        catch(IllegalArgumentException iae){
            System.out.println("BAD DATA");
            System.out.println(iae.toString());
            System.exit(1);
        }

        int[] denoms = argArr;
        int target = argTarg;
        int[] temp;
        Tuple tempTuple;
        Tuple[][] ansArr = new Tuple[denoms.length-1][target];

        int[] zeroArr = new int[argArr.length];
        for(int i = 0; i < zeroArr.length;i++){zeroArr[i] = 0;}//creates zero array

        for(int i = 0; i<denoms.length;i++){
            ansArr[i][0] = new Tuple(zeroArr);
        }

        for(int i = 0; i < denoms.length;i++){ //denoms
            for(int j = 1; j <= target;j++){ //target
                if(j-denoms[i]>=0){
                    temp = copyArr(zeroArr);
                    temp[i] = 1;
                    ansArr[i][j] = new Tuple(temp);
                }
                else{
                    ansArr[i][j] = Tuple.IMPOSSIBLE;
                }

                if(j>=denoms[i]){
                    tempTuple = copyTuple(ansArr[i][j-denoms[i]]);
                    ansArr[i][j] = compareTuples(ansArr[i][j],tempTuple);
                }

                if(i>0){
                    tempTuple = copyTuple(ansArr[i-1][j]);
                    ansArr[i][j] = compareTuples(ansArr[i][j],tempTuple);
                }
            }
        }

        tempTuple = ansArr[denoms.length-1][argTarg];
        System.out.println(tempTuple.toString());
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static Tuple compareTuples(Tuple firstTuple,Tuple secondTuple){
        if(firstTuple.isImpossible() && secondTuple.isImpossible()){
            return new Tuple(new int[0]);
        }
        else if(firstTuple.isImpossible()){
            return secondTuple;
        }
        else if(secondTuple.isImpossible()){
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
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static Tuple copyTuple(Tuple argTuple){
        Tuple tupleToReturn = new Tuple(argTuple.length());

        for(int i = 0; i < tupleToReturn.length();i++){
            tupleToReturn.setElement(i,argTuple.getElement(i));
        }

        return tupleToReturn;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private static int[] copyArr(int[] argArr){
        int[] arrToReturn = new int[argArr.length];

        for(int i = 0; i <arrToReturn.length;i++){
            arrToReturn[i] = argArr[i];
        }

        return arrToReturn;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private int[] getDenomsArr(){
        return classDenoms;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    private int getTarget(){
        return target;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Description
     *  @param  gint Description
     *  @return BrobInt description
     *  @exception  Exception description
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static void main(String[] args){  //args[0] is of form "3,5,7,8" = denominations
    //args[1] in form "34" = target
        if(args.length !=2){
            System.out.println("BAD DATA");
            System.out.println("You must enter two parameters for denominations and target");
            System.exit(2);
        }

        DynamicChangeMaker myDyn = new DynamicChangeMaker(args[0],args[1]);
        myDyn.makeChangeWithDynamicProgramming(myDyn.getDenomsArr(),myDyn.getTarget());

    }

}
