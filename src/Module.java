import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Module {

    /* creates the private variables of the modules and a synchronized list of studentEnrolled
    * By using the synchronized list, it removes two different threads accessing this objects studentsEnrolled at once
    * which when a basic arraylist is used, it causes NullPointerExceptions of removing and adding etc. at the same time
    */
    private String moduleTitle;
    private String moduleCoordinator;
    private String moduleCode;
    private int studentLimit;
    public List<Student> studentsEnrolled = Collections.synchronizedList(new ArrayList<Student>());
    private boolean isTakenEnrol;



    //Public method to instantiate the object and pass in the following parameters:
    public Module(String title, String moduleCoordinator, String moduleCode, int studentLimit) {
        this.moduleTitle = title;
        this.moduleCode = moduleCode; //Makes the variables instantiated as the reference objects
        this.moduleCoordinator = moduleCoordinator;
        this.studentLimit = studentLimit;

    }


    /*Synchronized method to allow a student to enrol onto the module aslong as the module isn't full, the student isn't
    already enrolled onto the module

     */
    public synchronized void studentEnrol(Student student) throws InterruptedException {
        if (!moduleFull()) {
            if (!student.moduleList.contains(this)) {
                // synchronized(student.list){
                student.moduleList.add(this);
                //}
                studentsEnrolled.add(student);


            }
        }

    }

    /*The equivelant but for unenrolling the students, if the student is currently enrolled onto this then they are able
    to unenrol, it removes the module from the students synchronized list aswell as the student from the modules
     */

    public synchronized void studentUnEnrol(Student student) throws InterruptedException {
        if (student.moduleList.contains(this)) {

            student.moduleList.remove(this);
            this.studentsEnrolled.remove(student);
        }



    }

/*Checks to see whether the module is full and if so doesn't allow enrollment, I decided not to implement wait in this
  scenario solely for the reason that I do not think a student would wait for someone to drop out of a module, they would
  simply pick another module and then transfer over if possible later down the line in the module picker
 */
    public boolean moduleFull() {

        if (studentsEnrolled.size() < studentLimit) {

            return false;
        } else {

            return true;

        }
    }

    /*uses acquire and release to allow the synchronized methods (enrol and unenrol) to truly work individually, the
    threads trying to access the methods whilst their in use, will have to wait for the current threads to finish using
    the method
     */
    public synchronized void acquire() throws InterruptedException {
        while (isTakenEnrol) {
            wait();
        }
        isTakenEnrol = true;
        notifyAll();
    }

    public synchronized void release() {
        isTakenEnrol = false;
        notifyAll();
    }


    

    public String getModuleTitle() {
        return moduleTitle;
    }

    public String getModuleCoordinator() {
        return moduleCoordinator;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public int getStudentLimit() {
        return studentLimit;
    }


}
