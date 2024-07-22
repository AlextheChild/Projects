import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JFrame;

public class FileChooser {

    // returns selected file
    public static File getFileObject() {
        JFrame jf = new JFrame();

        JFileChooser chooser = new JFileChooser();
        try {
            // set up to be pointing to current directory:
            File f = new File(new File(".").getCanonicalPath());
            chooser.setCurrentDirectory(f);

            // Open the file choosing dialog:
            int didTheyCancel = chooser.showOpenDialog(jf);
            if (didTheyCancel == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                return selectedFile;
            } else
                return null;
        } catch (Exception e) {
            System.out.println("Problem with getting file path!");
            return null;
        }
    }

    // returns the file's pathname
    public static String getFilePath() {
        JFrame jf = new JFrame();

        JFileChooser chooser = new JFileChooser();
        try {
            // set up to be pointing to current directory:
            File f = new File(new File(".").getCanonicalPath());
            chooser.setCurrentDirectory(f);

            // Open the file choosing dialog:
            int didTheyCancel = chooser.showOpenDialog(jf);
            if (didTheyCancel == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                return selectedFile.getAbsolutePath();
            } else
                return "Opening canceled!";
        } catch (Exception e) {
            return "Problem with getting file path!";
        }
    }
}