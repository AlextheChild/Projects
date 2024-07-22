import java.util.*;
import java.io.*;

public class Methods {
    public void reset(ArrayList<ArrayList<String>> allKeysLeft, String level) {
        allKeysLeft.clear();
        ArrayList<String> allKeys = new ArrayList<String>();
        if (level.equals("all")) {
            String[] keysA = { "Ab", "A", "Bb", "B/Cb", "C", "C#/Db", "D", "Eb", "E", "F", "F#/Gb", "G" };
            for (String key : keysA) {
                allKeys.add(key);
            }
            for (int i = 0; i < 11; i++) {
                ArrayList<String> n = new ArrayList<String>();
                n.addAll(allKeys);
                allKeysLeft.add(n);
            }
        } else if (level.equals("nine")) {
            String[] keysA = { "C", "C#/Db", "D", "Eb", "E", "F" };
            for (String key : keysA) {
                allKeys.add(key);
            }
            for (int i = 0; i < 11; i++) {
                ArrayList<String> n = new ArrayList<String>();
                n.addAll(allKeys);
                allKeysLeft.add(n);
            }
        } else if (level.equals("ten")) {
            String[] keysA = { "F#/Gb", "G", "Ab", "A", "Bb", "B/Cb" };
            for (String key : keysA) {
                allKeys.add(key);
            }
            for (int i = 0; i < 11; i++) {
                ArrayList<String> n = new ArrayList<String>();
                n.addAll(allKeys);
                allKeysLeft.add(n);
            }
        }
    }

    ArrayList<String> selectedKeys = new ArrayList<String>();

    public void getRandomKeys() {
        for (int i = 0; i < Main.allKeysLeft.size(); i++) {
            int index = (int) (Math.random() * Main.allKeysLeft.get(i).size());

            selectedKeys.add(Main.allKeysLeft.get(i).get(index));

            Main.allKeysLeft.get(i).remove(index);
        }
    }

    public void createTable() throws FileNotFoundException {
        File table = new File("src/table.txt");
        PrintWriter tableWriter = new PrintWriter(table);
        tableWriter.println("Scales: ");
        tableWriter.println();
        tableWriter.println("Four octave: " + selectedKeys.get(0));
        tableWriter.println("Formula pattern: " + selectedKeys.get(1));
        tableWriter.println("Chromatic: " + selectedKeys.get(2));
        tableWriter.println("Solid Octaves: " + selectedKeys.get(3));
        tableWriter.println("Broken Octaves: " + selectedKeys.get(4));

        tableWriter.println("Chords: ");
        tableWriter.println();
        tableWriter.println("Broken: " + selectedKeys.get(5));
        tableWriter.println("Solid: " + selectedKeys.get(6));
        tableWriter.println("Alternate pattern: " + selectedKeys.get(7));
        tableWriter.println("Dominant/Diminished 7th: " + selectedKeys.get(8));

        tableWriter.println("Arpeggios: ");
        tableWriter.println();
        tableWriter.println("Tonic: " + selectedKeys.get(9));
        tableWriter.println("Dominant/Diminished 7th: " + selectedKeys.get(10));

        tableWriter.close();
    }

    public void saveKeysLeft() throws FileNotFoundException {
        File keysLeft = new File("src/keysLeft.txt");
        PrintWriter keysLeftWriter = new PrintWriter(keysLeft);
        for (int i = 0; i < Main.allKeysLeft.size(); i++) {
            for (int q = 0; q < Main.allKeysLeft.get(i).size(); q++) {
                keysLeftWriter.print(Main.allKeysLeft.get(i).get(q) + " ");
            }
            keysLeftWriter.println();
        }
        keysLeftWriter.close();
    }

    public void getKeysLeft() throws FileNotFoundException{
        Scanner keysLeftScanner = new Scanner(new File("src/keysLeft.txt"));

        String line;
        while(keysLeftScanner.hasNext()){
            line = keysLeftScanner.nextLine();
            for(char c: line.toCharArray()){
                
            }
        }
        keysLeftScanner.close();
    }
}