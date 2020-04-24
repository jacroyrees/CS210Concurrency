import java.util.ArrayList;
import java.util.Random;

public class ModuleEnroller implements Runnable{


    private ArrayList<Module> moduleChoices;
    private Student student;
    //Passes in the student who is enrolling aswell as the ModuleList to choose from
    public ModuleEnroller(ArrayList<Module> moduleArrayList, Student student){
        this.moduleChoices = moduleArrayList;
        this.student = student;
    }
    @Override
    public void run() {
        Random random = new Random();
        /*intialises random as Random() to create a random Sleep time, Module and number of
        modules which the student will be enrolling on, this is solely for the purpose of simulation as in a real world
        scenario the user will obviously decide what number of modules*/
        int randomInt = 0;
        int randomModule = 0;
        int randomnumberOfModules = random.nextInt(moduleChoices.size()); //upto the amount of modules available
        Module moduleEnrol; //the module which the user will be enrolling onto

        /*set to 2 due to the fact it will continue to run due to the thread
        sleeping time - beyond the enrollment closure*/
        while (TimeRemainingTimer.timeLeft > 2) {

            try {
                //Sets the amount of time the thread should randomly sleep for throughout each iteration
                randomInt = random.nextInt(2000); //upto 1 second
                Thread.sleep(randomInt); //implements sleep();
                if(TimeRemainingTimer.timeLeft > 0) { //after sleep, checks to see if there is still time left

                    randomModule = random.nextInt(moduleChoices.size()); //instantiates the module it will be enrolled onto
                    moduleEnrol = moduleChoices.get(randomModule); //gets the physical object


                    /* checks to see if the module chosen is full, if it is -> in a real-life situation it is much more likely
                    that the student will just select another module, which is what i've tried to do here - make the simulation as
                    realistic as possible.
                    */
                    while(moduleEnrol.moduleFull()){

                        System.out.println("MODULE FULL!");

                        moduleEnrol = moduleChoices.get(random.nextInt(moduleChoices.size()));
                    }
                    System.out.println(student.getStudentNumber() + " is trying to enrol onto: " + moduleEnrol.getModuleTitle());





                    //Acquires the module its going to enrol onto, enrols the student and then releases it for other threads
                    System.out.println(student.getStudentNumber() + " is trying to acquire: " + moduleEnrol.getModuleTitle());
                    student.acquire();
                    moduleEnrol.acquire();
                    System.out.println(student.getStudentNumber() + " has acquired: " + moduleEnrol.getModuleTitle());
                    moduleEnrol.studentEnrol(student);

                    moduleEnrol.release();
                    System.out.println(student.getStudentNumber() + " has released: " + moduleEnrol.getModuleTitle());
                    student.release();
                    System.out.println(student.getStudentNumber() + " has completed their enrollment onto: " + moduleEnrol.getModuleTitle());
                } //System.out.println(student.getStudentNumber() + " has released the module");
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
        }

    }
}

