package nazarrod.adventgem.advgem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ProfileManager {
    public void createNewProfile(String nickname,String password){
        System.err.printf("Try to create new profile %s %s\n",nickname,password);
        try {
            boolean dirCreated = new File("./UserProfiles/"+nickname).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");

            String filePath = "./UserProfiles/"+nickname+"/userConfig.txt";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("Account file can't be created");

            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(password);
            myWriter.close();
            System.err.println("Your account was successfully created, your login is:"+nickname);
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * TO-DO
     * add:
     * getProfiles function that returns list of created profiles
     * deleteProfile (self-explanatory)
     * checkPassword - checks if entered password corresponds profilePassword
     * changeProfilePassword (self-explanatory)
     */
}
