import java.util.*;
import java.io.*;

public class FileChanger {
    Scanner s;
    File altered;
    PrintWriter w;

    // ! should do sentences, not lines
    /**
     * Creates a new text file by taking the original and reversing the words on
     * every line.
     * 
     * @param original - The original file to change.
     * @param allFiles - The ArrayList with every file in it.
     * @return void
     */
    public void reverseSentence(File original) throws FileNotFoundException {
        setUpFiles(original, "Reversed");

        Scanner wordScanner = new Scanner(original);
        String line;
        String word;
        String lineChecker;
        ArrayList<String> words = new ArrayList<String>();

        // loop through each line
        while (s.hasNext()) {
            line = s.nextLine();
            lineChecker = "";

            // check if you're still on the same line
            while (!lineChecker.equals(line + " ") && wordScanner.hasNext() && !line.equals("")) {
                // go to next word
                word = wordScanner.next();
                lineChecker += word + " ";

                // add to the arrayList
                words.add(word);
            }

            // print the arrayList but flipped
            for (int i = words.size() - 1; i >= 0; i--) {
                w.print(words.get(i) + " ");
            }
            w.println();
            words.clear();
        }

        wordScanner.close();
        s.close();
        w.close();
    }

    /**
     * Creates a new text file with every digit in the original summed at the bottom
     * and removed from the text.
     * 
     * @param original - The original file to change.
     * @param allFiles - The ArrayList with every file in it.
     * @return void
     */
    public void sumDigits(File original) throws FileNotFoundException {
        setUpFiles(original, "DigitsSummed");

        String textLine;
        int numbers = 0;
        while (s.hasNext()) {
            textLine = s.nextLine();
            // adds all numbers to numbers
            for (int i = 0; i < textLine.length(); i++) {
                if (Character.isDigit(textLine.charAt(i))) {
                    numbers += Character.getNumericValue(textLine.charAt(i));

                }
            }
        }
        w.println("Sum of all numbers in this file: " + numbers);
        w.println();

        Scanner sc = new Scanner(original);

        String line;
        while (sc.hasNext()) {
            line = sc.nextLine();
            // changes the line
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    line = line.substring(0, i) + line.substring(i + 1, line.length());
                    i--;
                }

            }
            w.println(line);
        }

        sc.close();
        s.close();
        w.close();
    }

    /**
     * Creates a new text file with the original writer's ego level at the top, and
     * un-biases the file.
     * 
     * @param original - The original file to change.
     * @param allFiles - The ArrayList with every file in it.
     * @return void
     */
    public void measureEgo(File original) throws FileNotFoundException {
        setUpFiles(original, "WithEgoMeasured");

        // find egoLevel
        String word;
        int egoLevel = 0;
        while (s.hasNext()) {
            word = s.next();
            if (word.equals("I") || word.equals("me") || word.equals("you")) {
                egoLevel++;
            } else if (word.equals("my") || word.equals("your")) {
                egoLevel++;
            } else if (word.equals("mine") || word.equals("yours")) {
                egoLevel++;
            } else if (word.equals("I'd") || word.equals("you'd")) {
                egoLevel++;
            } else if (word.equals("I'll") || word.equals("you'll")) {
                egoLevel++;
            } else if (word.equals("I've") || word.equals("you've")) {
                egoLevel++;
            }
        }

        w.println("The writer has an ego level of " + egoLevel + ". ");
        w.println();

        // change words
        String line;
        String lineChecker;
        Scanner lineScanner = new Scanner(original);
        Scanner wordScanner = new Scanner(original);

        // go to next line
        while (lineScanner.hasNext()) {
            line = lineScanner.nextLine();
            lineChecker = "";

            // check if you're still on the same line
            while (!lineChecker.equals(line + " ") && wordScanner.hasNext() && !line.equals("")) {
                // go to next word
                word = wordScanner.next();
                lineChecker += word + " ";

                // change the word
                if (word.equals("I") || word.equals("me") || word.equals("you")) {
                    w.print("they ");
                } else if (word.equals("my") || word.equals("your")) {
                    w.print("their ");
                } else if (word.equals("mine") || word.equals("yours")) {
                    w.print("theirs ");
                } else if (word.equals("I'd") || word.equals("you'd")) {
                    w.print("they'd ");
                } else if (word.equals("I'll") || word.equals("you'll")) {
                    w.print("they'll ");
                } else if (word.equals("I've") || word.equals("you've")) {
                    w.print("they've ");
                } else {
                    w.print(word + " ");
                }
            }
            w.println();
        }

        lineScanner.close();
        wordScanner.close();
        s.close();
        w.close();
    }

    /**
     * Creates a new text file with only the lines of the original text file that
     * contain the word that the user wants.
     * 
     * @param wantedWord - The word that the user wants to find.
     * @param original   - The original file to change.
     * @param allFiles   - The ArrayList with every file in it.
     * @return void
     */
    public void findWord(String wantedWord, File original) throws FileNotFoundException {
        setUpFiles(original, "WordFound");
        Scanner wordScanner = new Scanner(original);

        String line = "";
        String word = "";
        String lineChecker;
        boolean hasWords = false;

        // loop through every line
        while (s.hasNext()) {
            line = s.nextLine();
            lineChecker = "";

            // check if you're still on the same line
            while (!lineChecker.equals(line + " ") && wordScanner.hasNext() && !line.equals("")) {
                // go to next word
                word = wordScanner.next();
                lineChecker += word + " ";

                // print the line
                if (word.toUpperCase().equals(wantedWord.toUpperCase())) {
                    w.println(line);
                    hasWords = true;
                }
            }
        }

        if (!hasWords) {
            w.println("The word entered doesn't appear in this text. ");
        }

        wordScanner.close();
        s.close();
        w.close();
    }

    /**
     * Does basic setup for every method in the FileChanger class, e.g. creating the
     * new altered file.
     * 
     * @param original - The original file to change.
     * @param version  - What the altered file should be called.
     * @return void
     */
    public void setUpFiles(File original, String version) throws FileNotFoundException {
        // get name of altered file
        int slashIndex = 0;
        for (int i = 0; i < original.getAbsolutePath().length(); i++) {
            if ((original.getAbsolutePath().charAt(i) + "").equals("/")) {
                slashIndex = i;
            }
        }

        // get rid of the file type
        int dotIndex = 0;
        for (int i = 0; i < original.getAbsolutePath().length(); i++) {
            if ((original.getAbsolutePath().charAt(i) + "").equals(".")) {
                dotIndex = i;
            }
        }

        String og = original.getAbsolutePath().substring(slashIndex + 1,
                dotIndex);

        altered = new File("src/altered_files/" + og + version + ".txt");
        s = new Scanner(original);
        w = new PrintWriter(altered);
    }

    /**
     * Allows the GUI to display the text on the altered file.
     * 
     * @return The text of the most recently altered file
     */
    public String getTextText(ArrayList<File> allFiles) throws FileNotFoundException {
        String text = "<html>";
        Scanner s = new Scanner(altered);

        // loop through the file and set text to the file's text
        while (s.hasNext()) {
            text += s.nextLine();
            text += "<br>";
        }
        text += "</html>";

        allFiles.add(altered);
        s.close();
        return text;
    }
}