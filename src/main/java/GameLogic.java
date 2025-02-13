import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class GameLogic {

    txtParser StoryParser;
    txtParser achievementsParser;

    // Gives the line # in Story.txt that is being read by the txtParser
    int index = 0;
    public boolean activeChoice = false;

    // Safe File System init
    public Set<Integer> achievements = new HashSet<>();
    public Set<Integer> addedAchievements = new HashSet<>();

    public GameLogic() {
        try {
            StoryParser = new txtParser(CONST.storyFilePath);
            achievementsParser = new txtParser(CONST.achievementFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void next(GUI gui) {
        //Calls the next action in the story (Text/Choice/MiniGame) -> Parse a file for certain keywords (Background, Choice, [Name], ...)
        String line = StoryParser.parseLine(index);
        String[] switchCase = line.split(":");

        try {
            String operator = switchCase[1];
            // System.out.println("Next gets called with index " + index + " and switchCase " + operator);
            switch (switchCase[0]) {
                case "Choice":
                    gui.showChoice(createChoice(operator));
                    break;
                case "Background":
                    gui.setBackgroundImage(operator);
                    break;
                case "Ending":
                    index = 0;
                    saveGame(gui, false);
                    gui.showEndingScreen();
                    break;
                case "Achievement": // TO-DO
                    System.out.println("Achievement added: " + achievementsParser.parseLine(Integer.parseInt(operator)-1));
                    achievements.add(Integer.parseInt(operator)-1); // Adjust for 1 based index
                    addedAchievements.add(Integer.parseInt(operator)-1); // Adjust for 1 based index
                    gui.showAchievement(achievementsParser.parseLine(Integer.parseInt(operator)-1)); // Adjust for 1 based index
                    break;
                default:
                    gui.gameDrawingPanel.text = false;
                    gui.gameDrawingPanel.displayedText = "";
                    gui.gameDrawingPanel.textIndex = 0;
                    gui.text(line);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error with line " + (index+1));
            System.out.println(e.getMessage());
        }
        index++;
    }

    public void startGame(GUI gui) {
        SaveData saveData = SafeFileSystem.loadGame();
        index = 0;
        achievements = saveData.getAchievements();
        addedAchievements = new HashSet<>();
        saveData.setPlayerSaved(false);
        gui.showGame();
        next(gui);
    }

    /*
    public void loadGame(GUI gui) {
        SaveData saveData = SafeFileSystem.loadGame();
        int lastIndex = saveData.getStoryIndex();
        achievements = saveData.getAchievements();
        addedAchievements = saveData.getAddedAchievements();
        saveData.setPlayerSaved(false);
        loadGameFromIndex(lastIndex, gui);
        gui.showGame();
        next(gui);
    }
     */

    private void loadGameFromIndex(int lastIndex, GUI gui) {
        if(index >= lastIndex) return;
        next(gui);
        SwingUtilities.invokeLater(() -> {loadGameFromIndex(lastIndex, gui);});
    }

    public void saveGame(GUI gui, boolean playerSaved) {
        SafeFileSystem.saveGame(index, achievements, addedAchievements, playerSaved);
        gui.mainMenu();
    }

    // Gets called in drawingPanel after choice is chosen and reads the next line (jump line) by calling next()
    public void choose(GUI gui, Choice choice, int index) {
        this.index = choice.lineNumbers[index];
        activeChoice = false;
        gui.removeChoice();
        next(gui);
    }

    // Creates a new Choice based on a given line read by the parser
    public Choice createChoice(String line) {
        String[] labels = line.split(";")[0].split("#");
        String[] jumpStrings = line.split(";")[1].split("#");
        int[] jumps = new int[jumpStrings.length];
        for (int i = 0; i < jumps.length; i++) {
            jumps[i] = Integer.parseInt(jumpStrings[i])-1; //Adjust for zero based index
        }
        return new Choice(labels,jumps);
    }
}