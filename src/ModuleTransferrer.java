import java.util.ArrayList;
import java.util.Random;

public class ModuleTransferrer implements Runnable{
    private Student student;
    private Module ModuleUnenrol;
    private Module ModuleEnrol;

    private ArrayList<Module> moduleOptions;


    //Allows the instantiation of this particular Transferrer with the student and moduleOptions
    public ModuleTransferrer( ArrayList<Module> moduleList, Student student) {
        this.student = student;

        this.moduleOptions = moduleList;
    }



/*This is the actual runnable of transferrer, where the student is simulated to have decided to transfer from one of their
current modules onto a module which they are not currently enrolled onto
 */
    @Override
    public void run() {
        Random random = new Random();
        int randomInt = 0;
        int randomModuleFrom = 0;
        Module moduleUnenrol;
        Module moduleEnrol;
        int randomModuleTo = 0;
        try {
            /*Puts the thread to sleep before any action is taken as a precautionary deadlock and Error-Handling measure
            where the enrollment threads would have completed atleast one enrolment before a transfer occurs -> although
            additional measures are also incorporated further down
            */
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Whilst the public static variable in the counter thread is greater than 0, the transferrals can continue to
        occur, a different variable is used than enroller to allow students a greater duration of time to transfer than
        enrol, as stated previously this is to ensure the simulation is as similar to a real-life process as possible
        e.g. a student doesn't like their module so decides after a few lectures to transfer to a different one
         */
        while(TimeRemainingTimer.transferTime > 0){

            /*Ensures that the student can infact transfer from a module into another module because they are indeed
            already enrolled onto a module
             */
            if(student.moduleList.size() > 1){
                try{

                    /*Sets a random time for the thread to sleep, in this instance I have decided on a much greater
                    sleep time than the enroller, this is mainly due to the fact that a student is much less likely to
                    transfer from and to a module than enrol onto a module -> a student MUST enrol onto x of N modules
                    but the only criteria for transferral is IF they want to, the module must NOT be full
                     */
                    randomInt = random.nextInt(5000);
                    Thread.sleep(randomInt);
                    /*After the sleep, will then again check to see if there's time left to enrol after the sleep, to remove
                    transferrals from occuring when time is up(not necessary but is added functionality)*/
                    if(TimeRemainingTimer.transferTime > 0) {
                        randomModuleFrom = random.nextInt(student.moduleList.size()); //Select random module to leave from students list
                        randomModuleTo = random.nextInt(moduleOptions.size()); // select a random module to enrol onto
                        moduleEnrol = moduleOptions.get(randomModuleTo); //make a reference object of the modules
                        moduleUnenrol = student.moduleList.get(randomModuleFrom);
                        this.ModuleEnrol = moduleEnrol; //sets the reference variable of this to the enrol and unenerol module
                        this.ModuleUnenrol = moduleUnenrol;//of this iteration
                        System.out.println(student.getStudentNumber() + " is trying to transfer from: " + moduleUnenrol.getModuleTitle() +
                                " and onto: " + moduleEnrol.getModuleTitle()); //Provide feedback and illsutration to the terminal of what is happening


                        /*By doing an acquire and release seperately we are removing hold and wait aswell as circular wait
                        from possible deadlocks by ensuring each thread only has access to one of the locks at once
                         */

                        //Acquire the synchronized lock, perform the enrolment, release
                        student.acquire();
                        System.out.println(student.getStudentNumber() + " is trying to acquire: " + moduleEnrol.getModuleTitle());
                        ModuleEnrol.acquire();
                        System.out.println(student.getStudentNumber() + " acquired: " + moduleEnrol.getModuleTitle());
                        ModuleEnrol.studentEnrol(student);
                        moduleEnrol.release();
                        System.out.println(student.getStudentNumber() + " released : " + moduleEnrol.getModuleTitle());


                        //acquire the synchronized lock, perform the unenrolment, release
                        System.out.println(student.getStudentNumber() + " is trying to acquire: " + moduleUnenrol.getModuleTitle());
                        ModuleUnenrol.acquire();
                        System.out.println(student.getStudentNumber() + " acquired: " + moduleUnenrol.getModuleTitle());
                        ModuleUnenrol.studentUnEnrol(student);
                        ModuleUnenrol.release();
                        System.out.println(student.getStudentNumber() + " released: " + moduleUnenrol.getModuleTitle());
                        student.release();
                        System.out.println(student.getStudentNumber() + " has completed their transfer from: " + moduleUnenrol.getModuleTitle() +
                                " and onto: " + moduleEnrol.getModuleTitle());

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



                    // System.out.println(student.getStudentNumber() + " has released the module");
                    // System.out.println("______________________________________________");
                }
            }
            // System.out.println("hi");
        }


