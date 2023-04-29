package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.GameData;

import java.io.*;

public class LevelManager {
    /**
    * Level Manager class is used to manage Level
     */
    public static void createNewLevel(GameData gameData){
        /*
        * Create new profile in ./UserProfiles directory.
        * Function creates user directory and config filegit
        * */
        System.err.printf("Try to save new leve level file %s\n",gameData.getLevelname());
        try {
            boolean dirCreated = new File("./Levels/"+gameData.getLevelname()).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");

            String filePath = "./Levels/"+gameData.getLevelname()+"/gamedata.dat";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("Level file can't be created");

            //Write basic level info to config file
            try (OutputStream outputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
                objectOutputStream.writeObject(gameData);
                System.err.printf("Your level was saved, Level name: %s\n",gameData.getLevelname());
            } catch (IOException e){
                System.err.printf("Error occurred while saving %s Level Data.\n",gameData.getLevelname());
                e.printStackTrace();
            }

        }
        catch (IOException e){
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static GameData readLevel(String fileName){
        GameData gameData = null;
        String filePath = "./Levels/"+fileName+"/gamedata.dat";
        try (InputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
            gameData = (GameData) objectInputStream.readObject();
            System.err.println("Level was successfully read");
        } catch (IOException | ClassNotFoundException e){
            System.err.printf("Error occurred while reading %s LevelData\n",filePath);
            e.printStackTrace();
        }
        return gameData;
    }

}
