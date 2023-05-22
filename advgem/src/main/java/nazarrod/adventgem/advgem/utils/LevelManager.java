package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.GameData;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LevelManager {
    private final static Logger logger = Logger.getLogger(LevelManager.class.getName());
    private static boolean alreadySet = false;

    private static void setLogger(){
        if(alreadySet)return;
        alreadySet = true;
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        boolean dirCreated = new File("./Logs/").mkdirs();
        try {
            fh = new FileHandler("./Logs/level_manager_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
    private static void save(GameData gameData,String filePath) throws IOException {
        setLogger();
        try (OutputStream outputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(gameData);
            logger.info("Your level was saved, Level path: "+filePath);
        } catch (IOException e){
            logger.info("Error occurred while saving "+filePath);
        }
    }
    public static void createNewLevel(GameData gameData){
        setLogger();
        logger.info("Try to save new leve level file ./Levels/"+gameData.getLevelName());
        try {
            boolean dirCreated = new File("./Levels/"+gameData.getLevelName()).mkdirs();
            if(!dirCreated){
                logger.warning("Directory can't be created, level with such name probably already exists or filename contains forbidden symbols");
                throw new IOException("Directory can't be created");
            }
            String filePath = "./Levels/"+gameData.getLevelName()+"/gamedata.dat";
            File myObj = new File(filePath);
            boolean fileCreated = myObj.createNewFile();
            if(!fileCreated)throw new IOException("file can't be created");
            save(gameData,filePath);
        }
        catch (IOException ignored){

        }
    }

    public static void makeNewSave(GameData gameData){
        setLogger();
        logger.info("Try to create new save for "+gameData.getLevelName());
        try {
            boolean dirCreated = new File("./Saves/").mkdirs();
            String filePath = "./Saves/gamedata.dat";
            File myObj = new File(filePath);
            myObj.createNewFile();
            save(gameData,filePath);
        }
        catch (IOException e){
            logger.warning("An error occured");
            e.printStackTrace();
        }
    }

    public static GameData loadLevel(String filePath){
        setLogger();
        GameData gameData = null;
        try (InputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
            gameData = (GameData) objectInputStream.readObject();
            logger.info("level "+filePath+" was successfully read");
        } catch (IOException | ClassNotFoundException e){
            logger.warning("level "+filePath+" probably does'nt exists or contains outdated version of the gamedata");
            return null;
        }
        return gameData;
    }

    public static List<String> getLevels(){
        setLogger();
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
