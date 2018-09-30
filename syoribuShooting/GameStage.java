package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameStage
{
    protected final TargetManager targetManager;
    private BufferedImage backImage;
    private Target hitTarget;
    
    public GameStage(final TargetManager manager, BufferedImage img)
    {
        this.targetManager = manager;
        this.setBackImage(img);
        this.targetManager.initialize();
    }
    
    abstract public void update(final Game game);
    
    abstract public void draw(final Graphics2D g2d);

    public BufferedImage getBackImage()
    {
        return backImage;
    }

    public void setBackImage(BufferedImage backImage)
    {
        this.backImage = backImage;
    }

    public Target getHitTarget()
    {
        return hitTarget;
    }

    public void setHitTarget(Target hitTarget)
    {
        this.hitTarget = hitTarget;
    }
}
