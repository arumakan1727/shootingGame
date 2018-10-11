package syoribuShooting.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BufferedResponsivePanel extends JPanel implements BufferedRenderer
{
    private final Image buffer;
    private final int realHeight, realWidth;
    private final int virtualHeight, virtualWidth;
    private Graphics2D g2d;
    
    public BufferedResponsivePanel(int virtualWidth, int virtualHeight, int realWidth, int realHeight)
    {
        super(false);
        final Dimension dimension = new Dimension(realWidth, realHeight);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.realHeight = realHeight;
        this.realWidth  = realWidth;
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;

        this.buffer = new BufferedImage(
                virtualWidth,
                virtualHeight,
                BufferedImage.TYPE_INT_ARGB_PRE);
    }
    
    @Override
    public void setBuffering()
    {
        
    }

    @Override
    public Graphics2D getRenderer()
    {
        this.g2d = ((Graphics2D) this.buffer.getGraphics());
        return g2d;
    }

    @Override
    public void flipBuffer()
    {
        this.getGraphics().drawImage(buffer, 0, 0, realWidth, realHeight, null);
//        Toolkit.getDefaultToolkit().sync();
    }

}
