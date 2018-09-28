package syoribuShooting;

import java.awt.Graphics2D;

public abstract class GameStage
{
    private final TargetManager targetManager;
    
    public GameStage(final TargetManager manager)
    {
        this.targetManager = manager;
    }
    
    abstract public void update(Game game);
    
    abstract public void draw(Graphics2D g2d);
}
