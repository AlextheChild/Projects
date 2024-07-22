import java.util.*;
import java.io.*;

public class Actions {
    File codeText = new File("src/textFiles/code.txt");

    public void setup(ArrayList<String> code) throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/GUI.java"));
        PrintWriter w = new PrintWriter(codeText);

        // ! omit lines
        String line;
        while (s.hasNext()) {
            line = s.nextLine();
            w.println(line);
            code.add(line);
        }

        s.close();
        w.close();
    }

    public void addButton(ArrayList<String> code, String buttonName, int timesAdded) throws FileNotFoundException {
        PrintWriter w = new PrintWriter(codeText);
        for (int i = 0; i < code.size(); i++) {
            if (code.get(i).equals("        " + "//" + " --------- buttons --------- //")) {
                code.add(i + 2 + (5 * timesAdded), "        " + buttonName + " = new JButton(\"" + buttonName + "\")");
                code.add(i + 3 + (5 * timesAdded), "        " + buttonName + ".addActionListener(this);");
                code.add(i + 4 + (5 * timesAdded), "        " + buttonName + ".add(" + buttonName + ")");
                code.add(i + 5 + (5 * timesAdded), "        " + "buttonPanel.add(" + buttonName + ")");
                code.add(i + 6 + (5 * timesAdded), "");
                i += 5;
            }
        }

        for (String s : code) {
            w.println(s);
        }
        w.close();
    }

    public String getTextText() throws FileNotFoundException {
        String code = "";
        Scanner s = new Scanner(codeText);
        // loop through the file and set text to the file's text
        while (s.hasNext()) {
            code += s.nextLine();
            code += "\n";
        }

        s.close();
        return code;
    }
}