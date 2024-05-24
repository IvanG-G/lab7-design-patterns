public class Async {

    public static void main(String[] args) {
        asyncSum(3, 9);
    }

    private static void asyncSum(int number, int number2){
        Thread newThread = new Thread(() -> {
            try{
                Thread.sleep(1000);
                System.out.println(number + " + " + number2 + " = " + (number + number2));
            }
            catch(Exception e ){
                System.out.println(e.getMessage());
            }
        });
        System.out.println("Before started async method");
        newThread.start();
        System.out.println("Executed async method");
    }
}