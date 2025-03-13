import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class txtParser {

    ArrayList<String> story;

    public txtParser(String filepath) throws IOException {
        story = new ArrayList<>();
        loadFile(filepath);
    }

    private void loadFile(String filepath) throws IOException {
        InputStream is = getClass().getResourceAsStream(filepath);
        if (is == null) {
            throw new IOException("Resource not found: " + filepath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                story.add(line);
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
