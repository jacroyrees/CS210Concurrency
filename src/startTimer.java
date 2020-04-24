public class startTimer implements Runnable{



    private void startTimer(){
       int timeLeft = 5;
        System.out.println("Time Till Module Selection Starts: ");
        while (timeLeft>0){
            System.out.println(timeLeft+" seconds");
            try {
                timeLeft--;
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            }
            catch (InterruptedException e) {
            }
        }

    }




    @Override
    public void run() {
        this.startTimer();
    }
}
