package syoribuShooting.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BufferedResponsivePanel extends JPanel implements ScreenBuffer
{
    private final BufferedImage buffer;
    private final int realHeight, realWidth;

    public BufferedResponsivePanel(int virtualWidth, int virtualHeight, int realWidth, int realHeight)
    {
        super(false);
        final Dimension dimension = new Dimension(realWidth, realHeight);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.realHeight = realHeight;
        this.realWidth  = realWidth;

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
    public void draw(final DrawTask drawTask)
    {
        Graphics2D g2d = buffer.createGraphics();
        drawTask.draw(g2d);
        g2d.dispose();
        this.getGraphics().drawImage(buffer, 0, 0, realWidth, realHeight, null);
//        Toolkit.getDefaultToolkit().sync();
    }

}
