import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class txtParser {

    File file;
    ArrayList<String> Story = new ArrayList<>();

    public txtParser(File file) {
        this.file = file;
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                Story.add(scanner.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    public void parseLine(int lineNumber) {

    }
}
