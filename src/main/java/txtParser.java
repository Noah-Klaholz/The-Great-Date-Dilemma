import java.io.*;
import java.util.ArrayList;

public class txtParser {

    ArrayList<String> story;

    public txtParser(String filepath) throws IOException {
        story = new ArrayList<>();
        loadFile(filepath);
    }

    private void loadFile(String filepath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                story.add(line);
            }
        }
    }

    public String parseLine(int lineNumber) {
        if(lineNumber >= story.size()) {
            System.out.println("Line number out of bounds: " + lineNumber);
            return "Error: Line number out of bounds";
        }
        return story.get(lineNumber);
    }
}
