package nazarrod.adventgem.advgem.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LevelManager {
    /*
    * Level Manager class is used to manage Level
     */
    public boolean createNewProfile(String levelname){
        /*
        * Create new profile in ./UserProfiles directory.
        * Function creates user directory and config filegit
        * */
        System.err.printf("Try to save new leve lfile %s %s\n",levelname);
        try {
            boolean dirCreated = new File("./Levels/"+levelname).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");

            String filePath = "./Levels/"+levelname+"/levelconfig.txt";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("Level file can't be created");

            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(levelname);
            myWriter.close();
            System.err.println("Your level was successfully saved, levelname is:"+levelname);
            return true;
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

}
