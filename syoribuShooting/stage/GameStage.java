package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameStage
{
    protected final static int TIMER_INTERVAL = 20; // ミリ秒指定

    abstract public void initialize();
    abstract public void update(final Game game);
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

    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
        this.targetManager.draw(g2d);
        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 70));
        g2d.setColor(Color.GREEN);
        int t = this.stopWatch.getRemainTime();
        if (t  < 0) t = 0;
        g2d.drawString("Time: " + t/1000 + "." + t%1000 / 100, GameConfig.WINDOW_WIDTH - 500, 80);
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
