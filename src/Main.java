import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;


public class Main {

    public static String moduleChoices[] = new String[14]; //Reads in the list of modules and the criteria for the modules
    public static ArrayList<Module> moduleList = new ArrayList<>(); // Converts it to an ArrayList of Modules on offer



    public static void main(String[] args) throws IOException, InterruptedException {
        Random random = new Random(); //Imports random as the variable random which enables random values to be generated
        ModuleLoader loader = new ModuleLoader();

        loader.moduleLoader(); //Reads in the file and adds each module to an Object ArrayList
        //System.out.println(moduleList.get(8).getModuleTitle());


        /*
         * Reads the module options available to the students at the start of simulation, with all the required criteria
         * of:  Module Title, Module Coordinator, Module Code and Module Capacity. This has been included to demonstrate
         * my understanding of the main thread running isolated from the rest of the Threads, by showing the output
         * before any threads have ran.
         *
         * Gets the details by using getters setup within the ModuleObjects class
         */
       System.out.println("______________________________________________________________________________________________________________________________");
        System.out.println("The Following Module Options are Available For Students: ");
        for(int i = 0; i < moduleList.size();i++){
            System.out.println(moduleList.get(i).getModuleTitle() +", which is taught by: " + moduleList.get(i).getModuleCoordinator()
                    + " with the module code: " + moduleList.get(i).getModuleCode() + " and has: " +
                    moduleList.get(i).getStudentLimit() + " spaces");
        }
        System.out.println("______________________________________________________________________________________________________________________________");

        /*Initialises the Lists -> 2 Thread Lists (Enroller and Transferrer) along with the List of students,
         *I decided on this way to demonstrate my understanding of Thread efficiency (more threads, more Computationally
         * expensive, aswell as being able to demonstrate that the Threads operate and as deadlock isn't possible to cure,
         * prevention is the best bet and so by using the simulation in the structure which I have, enables me to incorporate
         * a larger number of threads(than maybe 1 enroller and 1 transferrer being passed Student and Module lists) and
         * illustrate that my methods of deadlock prevention work effectively on a large number of threads
         */
        ArrayList<Student> studentArrayList = new ArrayList<>();
        ArrayList<Thread> moduleEnrollerArrayList = new ArrayList<>();
        ArrayList<Thread> moduleTransferrerArrayList = new ArrayList<>();



        //Creates a random number of students which are enrolling onto modules (upto 20) as shown using the bound
        int randomNumberStudents = random.nextInt(17)+3;

        /*Creates a random student number for the student in the simulation, this value is always six digits long,
        the addition of "+ 100000" is to demonstrate that the random value will simulate from 0 to the bound, by always
        adding an additional 100000, the value is consistently a random 6 digit value, in line with generic (albeit now
        changing to incorporating 7-digit values) Swansea University student numbering
         */
        int randomStudentNumber = random.nextInt(899999) + 100000;


        /* Loops through from i = 0 -> randomNumberStudents, creating a studentObject, enrollerThread and transferrerThread,
        for each simulated student which will enroll, passing in the list of modules to choose from each
         */
        for(int i = 0; i < randomNumberStudents; i++){

            Student student = new Student(randomStudentNumber);
            studentArrayList.add(student);

            Thread enrollerThread = new Thread(new ModuleEnroller(moduleList, student));
            moduleEnrollerArrayList.add(enrollerThread);

            Thread transferThread = new Thread(new ModuleTransferrer(moduleList, student));
            moduleTransferrerArrayList.add(transferThread);

            //picks a new random student number ready for the next iteration of the loop
            randomStudentNumber = random.nextInt(899999) + 100000;
        }




        /*Additional information to get notified of the exact random specifications of that simulation, each iteration
        will be completely unique of the previous or future due to the core functionalities of Threads and Random
        */
        System.out.println("There are: " + studentArrayList.size() + " students enrolling into modules today!");

       /*Initialises the startCountdown Thread, which counts down from 5, enabling the user to read all the information
        at the start of the program e.g. Modules, students Enrolling etc.*/
        Thread startCountDown = new Thread(new startTimer());

        /*Initialises the timeRemainingCounter Thread, which counts down from 20 for the simulation to allow transferral
        as-well as transferrals, and then also using another condition of counting down from 30 to enable the user to transfer
        modules once the enrolment is over for an extra 10 seconds
         */
        Thread moduleSelectorTimer = new Thread(new TimeRemainingTimer());


        /* Starts the counter, enabling the run() method to run, and joins the counter to prevent other threads from
        carrying out their operations until the run() methods process(counting down from 5) is complete
         */
        startCountDown.start();
        startCountDown.join();

        /*Once the countdown timer is complete, the moduleSelectorTimer begins to run, this time it is not joined so the,
        transferrer and enroller threads can run concurrently with it.
        */
        moduleSelectorTimer.start();


        //Starts the students enroller and transferrer threads by looping through each List and using the .start();

        for(int i = 0; i < moduleEnrollerArrayList.size();i++){
            moduleEnrollerArrayList.get(i).start();
            moduleTransferrerArrayList.get(i).start();
        }


        /*Now the other threads have used .start() the moduleSelectorTimer thread can now be joined, along with all the
        others by using the same technique as previous of iterating through the lists and using .join() to enable the
        currently running threads to stop executing until the threads it joins with stop executing, e.g. all current
        threads which have used .start() will be the only ones able to function until the join() are ended
         */
        moduleSelectorTimer.join();

        for(int j = 0; j < moduleEnrollerArrayList.size();j++){
            moduleEnrollerArrayList.get(j).join();
            moduleTransferrerArrayList.get(j).join();
        }




        /*Iterates through the now updated student array(as stated previously this main thread wont execute the following
        code until the Threads are completed) and outputs the number of modules the student is enrolled onto, and using
        a nested for loop then outputs the titles of the modules which the student is enrolled onto using variables i
        for the number of modules, and j for the output of moduleTitle
        */

        System.out.println("_____________________________________________________________");
            System.out.println("THE FOLLOWING IS THE LIST OF THE MODULES WHICH STUDENTS HAVE ENROLLED ONTO, AS WELL AS THE MODULE NUMBERS: ");

        for(int i = 0; i < studentArrayList.size();i++){
            System.out.println(studentArrayList.get(i).getStudentNumber() +" is enrolled onto: " + studentArrayList.get(i).moduleList.size() + " modules");
            for(int j = 0; j <studentArrayList.get(i).moduleList.size();j++){
                System.out.println(studentArrayList.get(i).moduleList.get(j).getModuleTitle());

            }
            System.out.println("_____________________________________________________________");
        }

        //Outputs the number of students enrolled onto each module
        for(int i = 0; i < moduleList.size();i++){
            System.out.println(moduleList.get(i).getModuleTitle() + " has: " + moduleList.get(i).studentsEnrolled.size() + " students enrolled");
        }


    }




}
