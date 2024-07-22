import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ImageCopier implements ClipboardOwner {
    public ImageCopier(Image image) {
        TransferableImage trans = new TransferableImage(image);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }

    @Override
    public void lostOwnership(Clipboard clip, Transferable trans) {
    }

    class TransferableImage implements Transferable {
        Image image;

        public TransferableImage(Image i) {
            this.image = i;
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }
            return false;
        }
    }
}