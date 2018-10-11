package syoribuShooting.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BufferedPanel extends JPanel implements BufferedRenderer
{
    private final BufferedImage buffer;
    private final int realHeight, realWidth;
    private final int vertualHeight, vertualWidth;
    private Graphics2D g2d;
    
    public BufferedPanel(int vertualWidth, int vertualHeight, int realWidth, int realHeight)
    {
        super();
        final Dimension dimension = new Dimension(realWidth, realHeight);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.realHeight = realHeight;
        this.realWidth  = realWidth;
        this.vertualWidth = vertualWidth;
        this.vertualHeight = vertualHeight;
        
        this.buffer = new BufferedImage(
                vertualWidth, 
                vertualHeight, 
                BufferedImage.TYPE_4BYTE_ABGR_PRE);
    }
    
    @Override
    public void setBuffering()
    {
        
    }

    @Override
    public Graphics2D getRenderer()
    {
        this.g2d = this.buffer.createGraphics();
        return g2d;
    }

    @Override
    public void flipBuffer()
    {
        if(g2d != null) g2d.dispose();
        
//        if (realWidth == vertualWidth) {
//            this.getGraphics().drawImage(buffer, 0, 0, null);
//        } else {
            this.getGraphics().drawImage(buffer, 0, 0, realWidth, realHeight, null);
//        }
        
        Toolkit.getDefaultToolkit().sync();
    }

}
