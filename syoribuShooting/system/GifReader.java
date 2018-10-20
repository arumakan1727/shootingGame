package syoribuShooting.system;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GifReader
{
    public static ArrayList<BufferedImage> readGif(InputStream is)
    {
        ArrayList<BufferedImage> images = new ArrayList<>();
        Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("gif");
        ImageReader reader = null;

        if (iter.hasNext()) reader = iter.next();
        else throw new RuntimeException();

        try {
            reader.setInput(ImageIO.createImageInputStream(is));

            int count = reader.getNumImages(true);
            for (int i = 0; i < count ; ++i)
            {
                images.add(reader.read(i));
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return images;
    }

    private GifReader() {}
}
