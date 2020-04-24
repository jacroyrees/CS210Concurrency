import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleLoader extends Main {


    /*Reads in the required file and adds each line to the array moduleChoices, until a line with null is reached
     */
    private static void fileReader() throws IOException {
        File f = new File("moduleInfo");
        BufferedReader in = new BufferedReader(new FileReader(f));
        try {

            String line = in.readLine();
            int i = 0;
            while (line != null) {

                moduleChoices[i] = line;
               // System.out.println(moduleChoices[i]);
                line = in.readLine();
                i++;

            }
        } finally {
            in.close();
        }

    }

/*Loads the ArrayList of modules with the modules, iterating through the Array and splitting according to the requirements
from the moduleInfo e.g. Code, Title, Coordinator etc.
 */
    public static ArrayList<Module> moduleLoader() throws IOException {
        fileReader();

        for (int i = 0; i < moduleChoices.length-1; i++) {
            String moduleTitle = moduleChoices[i].split(", ")[0];
            String moduleCoordinator = moduleChoices[i].split(", ")[1];
            String moduleCode = moduleChoices[i].split(", ")[2];
            int studentLimit = Integer.parseInt(moduleChoices[i].split(", ")[3]);

            Module module = new Module(moduleTitle,moduleCoordinator, moduleCode,studentLimit);
            moduleList.add(module);

        }
        return moduleList;
    }






}


