package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.StopWatch;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameStage
{
    protected final static int TIMER_INTERVAL = 20; // ミリ秒指定

    abstract public void initialize();
    abstract public void update(final Game game);
    abstract public void draw(final Graphics2D g2d);
    abstract public int getTimeLimitMillis();

    protected final TargetManager targetManager;
    protected final StopWatch stopWatch;
    private BufferedImage backImage;
    private Target hitTarget;
    private State state;

    public enum State
    {
        INITIAL_WAITING,
        SHOOTING,
        TIME_OVER,
    }

    public GameStage(final TargetManager manager, BufferedImage img)
    {
        this.targetManager = manager;
        this.stopWatch = new StopWatch();
        this.setBackImage(img);
        this.targetManager.initialize();
        this.setState(State.INITIAL_WAITING);

        this.initialize();
    }


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

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

}
