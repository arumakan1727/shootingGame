package syoribuShooting.system;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class BufferedJPanel extends JPanel implements BufferedRenderer
{
    public BufferedJPanel(int width, int height)
    {
        super(true);
        final Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
    }
    @Override
    public void setBuffering()
    {
        System.out.println("DoubleBuffered: " + this.isDoubleBuffered());
        this.setDoubleBuffered(true);
    }

    @Override
    public Graphics2D getRenderer()
    {
        return (Graphics2D)this.getGraphics();
    }

    @Override
    public void flipBuffer()
    {
        Toolkit.getDefaultToolkit().sync();
    }
}
