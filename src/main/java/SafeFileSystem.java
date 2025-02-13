import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SafeFileSystem {
    private static final String SAVE_FILE = "save.dat";

    public static void saveGame(int storyIndex, Set<Integer> achievements, Set<Integer> addedAchievements,boolean playerSaved) {
        SaveData data = new SaveData(storyIndex, achievements, addedAchievements, playerSaved);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(data);
            System.out.println("Saved successfully");
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static SaveData loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed");
            return new SaveData(0, new HashSet<>(), new HashSet<>(),false);
        }
    }
}
