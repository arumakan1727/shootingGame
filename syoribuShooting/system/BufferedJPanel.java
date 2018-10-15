package syoribuShooting.system;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class BufferedJPanel extends JPanel implements ScreenBuffer
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
    public void draw(DrawTask drawTask)
    {
        final Graphics2D g2d = (Graphics2D)this.getGraphics();
        drawTask.draw(g2d);
        g2d.dispose();
        Toolkit.getDefaultToolkit().sync();
    }
}
