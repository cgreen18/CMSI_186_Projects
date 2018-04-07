public class Fibonacci{

    private BrobInt[] brobIntArr;

    public static void main(String[] args){
        int target = Integer.parseInt(args[0]);

        if(target == 0){
            System.out.println("Zeroth value of the Fibonacci series is trivial.");
            System.out.println("Zeroth is zero in a way.");
            System.exit(1);
        }
        else if(target == 1 || target == 2){
            System.out.println("First and second values of the Fibonacci series are trivial.");
            System.out.println("It is: 1");
            System.exit(2);
        }

        brobIntArr = new BrobInt[target];
        brobIntArr[0] = new BrobInt("1");
        brobIntArr[1] = new BrobInt("2");

        for(int i = 2; i <=target ; i++){
            calcValue(i);
        }

        System.out.printf("The %d(th) value in the Fibonacci sequence is: \n",target);
        System.out.println(brobIntArr[target].toString());
    }

    public BrobInt calcValue(int indexArg){
        return (brobIntArr[indexArg-1].addByte(brobIntArr[indexArg-2]));
    }

}
