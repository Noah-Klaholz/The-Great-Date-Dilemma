import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SaveData implements Serializable {
    private final int storyIndex;
    private final Set<Integer> achievements;
    private final Set<Integer> addedAchievements;
    private boolean playerSaved;

    public SaveData(int storyIndex, Set<Integer> achievements, Set<Integer> addedAchievements, boolean playerSaved) {
        this.storyIndex = storyIndex;
        this.achievements = new HashSet<>(achievements);
        this.addedAchievements = new HashSet<>(addedAchievements);
        this.playerSaved = playerSaved;
    }

    // Add getters
    public int getStoryIndex() { return storyIndex; }
    public Set<Integer> getAchievements() { return new HashSet<>(achievements); }
    public boolean getPlayerSaved() { return playerSaved; }
    public Set<Integer> getAddedAchievements() { return new HashSet<>(addedAchievements); }

    public void setPlayerSaved(boolean playerSaved) {this.playerSaved = playerSaved; }
}
