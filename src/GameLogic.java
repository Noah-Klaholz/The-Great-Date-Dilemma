public class GameLogic {

    int index = 0;

    public GameLogic() {}

    public void next(GUI gui) {
        //TO-DO: Call the next action in the story (Text/Choice/MiniGame) -> Parse a file for certain keywords (Background, Choice, [Name], ...)
        // Use Delimiter to split found String in each line into parts, always only read line at index
        //Test Code:
        gui.drawingPanel.text = false;
        gui.drawingPanel.displayedText = "";
        gui.drawingPanel.textIndex = 0;
        index++;
        if(index == 1) gui.setBackgroundImage("Media/TestBackground2.png");
        if(index == 2) gui.text("Hallo, ich bin ein TestText");
        if(index == 3) gui.text("Ich bin der zweite TestText");
        if(index == 4) gui.setBackgroundImage("Media/TestBackground1.png");
        System.out.println("next gets called" + index);
    }
}