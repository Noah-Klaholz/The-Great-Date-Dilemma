public class GameLogic {

    // Gives the line # in Story.txt that is being read by the txtParser
    int index = 0;
    txtParser parser;

    public boolean activeChoice = false;

    public GameLogic() {
        try {
            parser = new txtParser(CONST.storyFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void next(GUI gui) {
        //Calls the next action in the story (Text/Choice/MiniGame) -> Parse a file for certain keywords (Background, Choice, [Name], ...)

        String line = parser.parseLine(index);
        String[] switchCase = line.split(":");

        switch (switchCase[0]) {
            case "Choice":
                gui.showChoice(createChoice(switchCase[1]));
                break;
            case "Background":
                gui.setBackgroundImage(switchCase[1]);
                break;
            case "Ending": // TO-DO
                break;
            case "Achievement": // TO-DO
                break;
            default:
                gui.drawingPanel.text = false;
                gui.drawingPanel.displayedText = "";
                gui.drawingPanel.textIndex = 0;
                gui.text(line);
                break;
        }
        System.out.println("Next gets called with index " + index + " and switchCase " + switchCase[1]);
        index++;

        /* Test Code:
        if(index == 1) gui.setBackgroundImage("Media/TestBackground2.png");
        if(index == 2) gui.text("Hallo, ich bin ein TestText");
        if(index == 3) gui.text("Ich bin der zweite TestText");
        if(index == 4) gui.setBackgroundImage("Media/TestBackground1.png");
        if(index == 5) gui.showChoice(createChoice(""));
        System.out.println("next gets called" + index);

         */
    }

    public void startGame(GUI gui) {
        next(gui);
    }

    // Gets called in GUI after choice is chosen and reads the next line (jump line) by calling next()
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
            jumps[i] = Integer.parseInt(jumpStrings[i]);
        }
        for (int i = 0; i < labels.length; i++) {
            System.out.println(labels[i]);
            System.out.println(jumps[i]);
        }
        return new Choice(labels,jumps);
    }
}