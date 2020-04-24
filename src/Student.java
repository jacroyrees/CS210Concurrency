import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Student {
    private boolean isTaken;
//Student class, just holds the list of modules the student has enrolled into as well as the student number
    public Student(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    private int studentNumber;


    List<Module> moduleList = Collections.synchronizedList(new ArrayList());

    public int getStudentNumber() {
        return studentNumber;
    }

    public synchronized void acquire() throws InterruptedException {
        while (isTaken) {
            wait();
        }
        isTaken = true;
        notifyAll();
    }

    public synchronized void release() {
        isTaken = false;
        notifyAll();
    }


}
