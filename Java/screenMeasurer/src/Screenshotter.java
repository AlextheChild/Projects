import java.util.List;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Screenshotter {
    final String saveFolderPath = "C:/Users/Alex/Desktop/";

    public Screenshotter(int x, int y, int w, int h, boolean save) throws AWTException, IOException {
        Robot r = new Robot();
        BufferedImage image;
        MultiResolutionImage mrImage = r.createMultiResolutionScreenCapture(new Rectangle(x, y, w, h));
        List<Image> resolutionVariants = mrImage.getResolutionVariants();
        if (resolutionVariants.size() > 1) {
            image = (BufferedImage) resolutionVariants.get(1);
        } else {
            image = (BufferedImage) resolutionVariants.get(0);
        }

        // save
        if (save) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
            LocalDateTime now = LocalDateTime.now();
            String[] timeArray = dtf.format(now).split(" ");

            ImageIO.write(image, "png",
                    new File(saveFolderPath +
                            "Screenshot " + timeArray[0] + " at " + timeArray[1] + ".png"));
        }

        // copy to clipboard
        new ImageCopier(image);
    }
}