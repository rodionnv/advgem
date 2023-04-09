package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.GameData;

import java.io.*;

public class LevelManager {
    /*
    * Level Manager class is used to manage Level
     */
    public boolean createNewLevel(GameData gameData){
        /*
        * Create new profile in ./UserProfiles directory.
        * Function creates user directory and config filegit
        * */
        System.err.printf("Try to save new leve lfile %s\n",gameData.getLevelname());
        try {
            boolean dirCreated = new File("./Levels/"+gameData.getLevelname()).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");

            String filePath = "./Levels/"+gameData.getLevelname()+"/levelconfig.txt";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("Level file can't be created");

            //Write basic level info to config file
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(gameData.getLevelname());
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(gameData.getPlaygroundWidth()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(gameData.getPlaygroundHeight()));
            bufferedWriter.close();

            //Read basic level info (temp)
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String newlevelname = bufferedReader.readLine();
            System.out.println(newlevelname);
            String line;
            while ((line = bufferedReader.readLine()) != null)
                System.out.println(Integer.parseInt(line));

            bufferedReader.close();
            System.err.println("Your level was successfully saved, levelname is:"+gameData.getLevelname());
            return true;
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

}
