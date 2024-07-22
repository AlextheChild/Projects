import java.util.*;
import java.io.*;

public class Methods {
    File alliterated, anagram, wordle;

    /**
     * Creates a new text file with as many alliterations as the user wants.
     * 
     * @param reps       - The number of alliterations to make.
     * @param names      - The file that contains every name.
     * @param adjectives - The file that contains every adjective.
     * @param nouns      - The file that contains every noun.
     * @return void
     */
    public void alliterate(int reps, File names, File adjectives, File nouns) throws FileNotFoundException {
        alliterated = new File("src/altered_files/alliterated.txt");
        PrintWriter w = new PrintWriter(alliterated);

        Scanner numNamesScanner = new Scanner(names);

        int numNames = 0;

        // find out how many names there are
        while (numNamesScanner.hasNext()) {
            numNamesScanner.nextLine();
            numNames++;
        }

        numNamesScanner.close();

        String startLetter = "";
        String name = "";
        String adjective;
        String noun;

        ArrayList<String> adjectivesList = new ArrayList<String>();
        ArrayList<String> nounsList = new ArrayList<String>();

        // make however many alliterations
        for (int i = 0; i < reps; i++) {
            Scanner nameScanner = new Scanner(names);
            Scanner adjectiveScanner = new Scanner(adjectives);
            Scanner nounScanner = new Scanner(nouns);

            // get random name
            int nameIndex = (int) (Math.random() * (numNames));
            for (int q = 0; q < nameIndex; q++) {
                name = nameScanner.nextLine();
                startLetter = name.substring(0, 1);
            }

            // get adjectives that start with startLetter
            while (adjectiveScanner.hasNext()) {
                String ad = adjectiveScanner.nextLine();
                if (ad.substring(0, 1).equals(startLetter)) {
                    adjectivesList.add(ad);
                }
            }

            // get random adjective that stars with startLetter
            adjective = adjectivesList.get((int) (Math.random() * (adjectivesList.size() - 1)));

            // get nouns that start with startLetter
            while (nounScanner.hasNext()) {
                String nou = nounScanner.nextLine();
                if (nou.substring(0, 1).equals(startLetter)) {
                    nounsList.add(nou);
                }
            }

            // get random noun that starts with startLetter
            noun = nounsList.get((int) (Math.random() * (nounsList.size() - 1)));

            w.println(name + "'s " + adjective + " " + noun);

            adjectivesList.clear();
            nounsList.clear();
            nameScanner.close();
            adjectiveScanner.close();
            nounScanner.close();
        }
        w.close();
    }

    // nag a ram
    public void anagramGuesser(File words) throws FileNotFoundException {
        anagram = new File("src/game_files/anagram.txt");
        PrintWriter w = new PrintWriter(anagram);

        Scanner numWordsScanner = new Scanner(words);

        int numWords = 0;

        // find out how many words there are
        while (numWordsScanner.hasNext()) {
            numWordsScanner.nextLine();
            numWords++;
        }

        numWordsScanner.close();

        // get random word
        Scanner wordScanner = new Scanner(words);
        int nameIndex = (int) (Math.random() * (numWords));
        String word = "";
        for (int q = 0; q < nameIndex; q++) {
            word = wordScanner.nextLine();
        }
        wordScanner.close();

        String gameWord = "";
        ArrayList<Character> wordLetters = new ArrayList<Character>();
        for (int i = 0; i < word.length(); i++) {
            wordLetters.add(word.charAt(i));
        }
        // somehow get a random order of indexes

        for (Character c : wordLetters) {
            gameWord += c;
        }

        w.println("Your anagram is " + gameWord + ". ");

        w.close();
    }

    // literally wordle
    public void wordle(File words) throws FileNotFoundException {
        wordle = new File("src/game_files/wordle.txt");
        PrintWriter w = new PrintWriter(wordle);

        Scanner wordScanner = new Scanner(words);
        ArrayList<String> fiveLetterWords = new ArrayList<String>();

        // add 5 letter words to the arrayList
        while (wordScanner.hasNext()) {
            String word = wordScanner.next();
            if (word.length() == 5) {
                fiveLetterWords.add(word);
            }
        }

        // randomly choose one

        wordScanner.close();
        w.close();
    }

    /**
     * Allows the GUI to display the text on the alliterations file.
     * 
     * @return The text of the alliterations file.
     */
    public String getAlliteratedText() throws FileNotFoundException {
        String text = "<html>";
        Scanner s = new Scanner(alliterated);

        // loop through the alliterated file and set text to the file's text
        while (s.hasNext()) {
            text += s.nextLine();
            text += "<br>";
        }
        text += "</html>";

        s.close();
        return text;
    }
}
