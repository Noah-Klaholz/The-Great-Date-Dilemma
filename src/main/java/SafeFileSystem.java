import java.io.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class SafeFileSystem {
    private static final String SAVE_FILE = getSaveFilePath();

    private static String getSaveFilePath() {
        String userHome = System.getProperty("user.home"); // Get user home directory
        Path savePath = Paths.get(userHome,".TheGreatDateDilemma", "save.dat"); // Change "MyGame" to your app name
        try {
            Files.createDirectories(savePath.getParent()); // Ensure the directory exists
        } catch (IOException e) {
            System.err.println("Could not create save directory: " + e.getMessage());
        }
        return savePath.toString();
    }

    public static void saveGame(int storyIndex, Set<Integer> achievements, Set<Integer> addedAchievements, boolean playerSaved) {
        SaveData data = new SaveData(storyIndex, achievements, addedAchievements, playerSaved);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(data);
            System.out.println("Saved successfully to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static SaveData loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed, starting new game.");
            return new SaveData(0, new HashSet<>(), new HashSet<>(), false);
        }
    }
}
