package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.GameData;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LevelManager {

    private static void save(GameData gameData,String filePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(gameData);
            System.err.printf("Your level was saved, Level name: %s\n",gameData.getLevelName());
        } catch (IOException e){
            System.err.printf("Error occurred while saving %s Level Data.\n",gameData.getLevelName());
            e.printStackTrace();
        }
    }
    public static void createNewLevel(GameData gameData){
        System.err.printf("Try to save new leve level file %s\n",gameData.getLevelName());
        try {
            boolean dirCreated = new File("./Levels/"+gameData.getLevelName()).mkdirs();
            if(!dirCreated)throw new IOException("Directory can't be created");
            String filePath = "./Levels/"+gameData.getLevelName()+"/gamedata.dat";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("file can't be created");
            save(gameData,filePath);
        }
        catch (IOException e){
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void makeNewSave(GameData gameData){
        try {
            boolean dirCreated = new File("./Saves/").mkdirs();
            String filePath = "./Saves/gamedata.dat";
            File myObj = new File(filePath);
            myObj.createNewFile();
            save(gameData,filePath);
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
            return null;
        }
        return gameData;
    }

    public static List<String> getLevels(){
        String dirPath = "./Levels/";
        File directory = new File(dirPath);
        if(directory.list() == null){
            new File("./Levels").mkdirs();
        }
        String[] levelList = directory.list();
        assert levelList != null;
        return Arrays.stream(levelList).toList();
    }
}
