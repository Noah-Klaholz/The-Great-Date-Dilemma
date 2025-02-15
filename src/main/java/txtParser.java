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
            //int i = 1;
            while ((line = br.readLine()) != null) {
                //if(line.contains("Achievement")){ System.out.println(line + " :" + i); }
                story.add(line);
                //i++;
            }
        }
    }

    public String parseLine(int lineNumber) {
        if (lineNumber > story.size()) {
            System.out.println("Line number out of bounds: " + lineNumber + story.size());
            return "Error: Line number out of bounds";
        }
        String line = story.get(lineNumber);
        if (!line.startsWith("//") && !line.equals("\n")) {
            if (line.contains("//")) System.out.println(line);
            return line;
        }
        return "Comment: " + line;
    }
}
