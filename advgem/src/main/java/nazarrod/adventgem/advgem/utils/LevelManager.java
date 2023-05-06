package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.GameData;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LevelManager {
    /**
    * Level Manager class is used to manage Level
     */
    public static void createNewLevel(GameData gameData){
        /*
        * Create new profile in ./UserProfiles directory.
        * Function creates user directory and config file
        * */
        System.err.printf("Try to save new leve level file %s\n",gameData.getLevelName());
        try {
            boolean dirCreated = new File("./Levels/"+gameData.getLevelName()).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");

            String filePath = "./Levels/"+gameData.getLevelName()+"/gamedata.dat";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("Level file can't be created");

            //Write basic level info to config file
            try (OutputStream outputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
                objectOutputStream.writeObject(gameData);
                System.err.printf("Your level was saved, Level name: %s\n",gameData.getLevelName());
            } catch (IOException e){
                System.err.printf("Error occurred while saving %s Level Data.\n",gameData.getLevelName());
                e.printStackTrace();
            }

        }
        catch (IOException e){
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static GameData loadLevel(String filePath){
        GameData gameData = null;
        try (InputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
            gameData = (GameData) objectInputStream.readObject();
            System.err.printf("Level %s was successfully read\n",filePath);
        } catch (IOException | ClassNotFoundException e){
            System.err.printf("Error occurred while reading %s LevelData\n",filePath);
            e.printStackTrace();
        }
        return gameData;
    }

    public static List<String> getLevels(){
        String dirPath = "./Levels/";
        File directory = new File(dirPath);
        String[] levelList = directory.list();
        assert levelList != null; //TODO handle this error properly
        return Arrays.stream(levelList).toList();
    }
}
