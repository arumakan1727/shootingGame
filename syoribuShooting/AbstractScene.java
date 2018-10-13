package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class AbstractScene
{
    abstract public void initialize();
    abstract public void update(final Game game);
    abstract public void draw(final Graphics2D g2d);

    public enum State
    {
        WAIT_SHOOTING,
        SHOOTING,
        TIME_OVER,
    }

    private BufferedImage backImage;

    public AbstractScene() {}

    public AbstractScene(final BufferedImage backImage)
    {
        this.setBackImage(backImage);
    }


    public BufferedImage getBackImage()
    {
        return backImage;
    }

    public void setBackImage(BufferedImage backImage)
    {
        this.backImage = backImage;
    }
}
