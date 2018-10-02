package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.Target;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameStage
{
    protected final static int TIMER_INTERVAL = 20; // ミリ秒指定

    abstract public void update(final Game game);
    abstract public void draw(final Graphics2D g2d);
    abstract public int getTimeLimitMillis();

    protected final TargetManager targetManager;
    private BufferedImage backImage;
    private Target hitTarget;
    private final Timer timer;
    private final TimerTask timerTask;
    private int elapsed;
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
        this.setBackImage(img);
        this.targetManager.initialize();
        this.timer = new Timer();

        this.timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                setElapsed(getElapsedMillis() + TIMER_INTERVAL);
            }
        };
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

    public void initTimer()
    {
        this.setElapsed(0);
    }

    public void startTimer()
    {
        this.timer.scheduleAtFixedRate(this.timerTask, 0, TIMER_INTERVAL);
    }

    public void stopTimer()
    {
        this.timer.cancel();
    }

    public boolean isOverTimeLimit()
    {
        return this.getElapsedMillis() >= this.getTimeLimitMillis();
    }

    public synchronized int getElapsedMillis()
    {
        return this.elapsed;
    }

    private synchronized void setElapsed(int e)
    {
        this.elapsed = e;
    }
}
