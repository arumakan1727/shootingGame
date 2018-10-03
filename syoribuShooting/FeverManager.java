package syoribuShooting;

import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Graphics2D;

public class FeverManager
{
    private static final int FEVER_NEEDS_COMBO = 10;
    private static final int FEVER_TIME = 10 * 1000;
    private final StopWatch stopWatch;
    private State state;

    public enum State
    {
        NORMAL,
        FEVER
    }

    public FeverManager()
    {
        this.stopWatch = new StopWatch();
        this.setState(State.NORMAL);

    }

    public void update(final Game game)
    {
        switch (this.state)
        {
            case NORMAL:
                if (game.getPlayer().getComboCount() >= FEVER_NEEDS_COMBO)
                {
                    this.setState(State.FEVER);
                    stopWatch.initTimer(FEVER_TIME);
                    stopWatch.startTimer();
                }
                break;
            case FEVER:
                if (stopWatch.isOverTimeLimit())
                {
                    this.setState(State.NORMAL);
                    game.getPlayer().setComboCount(0);
                }
                break;
        }
    }

    public void draw(Graphics2D g2d)
    {
        if (this.isFever())
        {
            g2d.setColor(Color.YELLOW);
            g2d.drawString("FEVER!!", 30, 180);
        }
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public State getState()
    {
        return this.state;
    }

    public boolean isFever()
    {
        return this.state == State.FEVER;
    }
}
