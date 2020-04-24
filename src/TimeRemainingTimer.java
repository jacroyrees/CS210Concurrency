public class TimeRemainingTimer implements Runnable{

    public static int timeLeft;
    public static int transferTime;
    private void timeLeft(){
        timeLeft = 20;
        transferTime = 30;
        System.out.println("_____________________________________________________________");
        System.out.println("MODULE SELECTION STARTED");
        System.out.println("_____________________________________________________________");
        while (timeLeft>0 || transferTime > 0){

            try {
                if(timeLeft > 0) {
                    timeLeft--;
                }
                transferTime--;
                if(transferTime == 10 && timeLeft == 0){
                    System.out.println("_____________________________________________________________");
                    System.out.println("Module Selection is Now Over!");
                    System.out.println("There is still time to transfer Modules!");
                    System.out.println("_____________________________________________________________");
                }

                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            }
            catch (InterruptedException e) {
            }
        }
        System.out.println("_____________________________________________________________");
        System.out.println("Module Transferral and Enrollment is now finished!");


    }

    @Override
    public void run() {
        this.timeLeft();
    }
}
