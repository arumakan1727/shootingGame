package syoribuShooting.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BufferedVolatilePanel extends JPanel implements ScreenBuffer
{
    private VolatileImage buffer = null;
    private final int realHeight, realWidth;
    private final int virtualHeight, virtualWidth;

    public BufferedVolatilePanel(int virtualWidth, int virtualHeight, int realWidth, int realHeight)
    {
        super(false);
        final Dimension dimension = new Dimension(realWidth, realHeight);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.realHeight = realHeight;
        this.realWidth  = realWidth;
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
    }

    private void initVolatileImage()
    {
        if (buffer == null) {
            buffer = this.getGraphicsConfiguration().createCompatibleVolatileImage(virtualWidth, virtualHeight);
        }
    }

    @Override
    public void setBuffering()
    {
        initVolatileImage();
    }

    private void validateVolatileImage() {
        GraphicsConfiguration gc = this.getGraphicsConfiguration();

        if (buffer.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
            buffer = this.getGraphicsConfiguration().createCompatibleVolatileImage(virtualWidth, virtualHeight);
        }
    }

    @Override
    public void draw(DrawTask drawTask){
        initVolatileImage();
        do {
            validateVolatileImage();
            Graphics2D g2d = buffer.createGraphics();
            drawTask.draw(g2d);
            g2d.dispose();
            this.getGraphics().drawImage(buffer, 0, 0, realWidth, realHeight, this);
        } while (buffer.contentsLost());
        
        Toolkit.getDefaultToolkit().sync();
    }

}
