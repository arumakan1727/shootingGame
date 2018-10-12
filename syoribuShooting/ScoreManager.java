package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class ScoreManager
{
    private static final int FEVER_NEEDS_COMBO = 10;
    private static final int FEVER_TIME = 10 * 1000;
    private final StopWatch stopWatch;
    private boolean isFever;

    private int score;
    private int comboCount;

    public ScoreManager()
    {
        this.stopWatch = new StopWatch();
        setScore(0);
        setComboCount(0);
        setFever(false);
    }

    public void update(final Game game, final BaseStage stage)
    {
        final InputEventManager eventManager = game.getEventManager();

        if (eventManager.justNowMousePressed(MouseEvent.BUTTON1))
        {
            checkHit(stage.getHitTarget(),
                    eventManager.mousePressedX(),
                    eventManager.mousePressedY());
        }

        this.updateFeverState();
    }

    public void draw(Graphics2D g2d)
    {
        this.drawScore(g2d);
        this.drawFever(g2d);
    }

    int getScore()
    {
        return this.score;
    }

    void setScore(int s)
    {
        this.score = s;
    }

    void addScore(int s)
    {
        this.setScore(getScore() + s);
    }

    int getComboCount()
    {
        return comboCount;
    }

    void setComboCount(int comboCount)
    {
        this.comboCount = comboCount;
    }
    void addComboCount(int addition)
    {
        this.setComboCount(getComboCount() + addition);
    }

    public boolean isFever()
    {
        return isFever;
    }

    public void setFever(boolean isFever)
    {
        this.isFever = isFever;
    }

    private void checkHit(final Target hitTarget, int px, int py)
    {

        if (hitTarget == null) {
            this.setComboCount(0);
        }
        else {
            this.addComboCount(1);
            this.addScore(hitTarget.getScore(px, py));
        }
    }

    private void updateFeverState()
    {
        if (this.isFever())
        {
            if (stopWatch.isOverTimeLimit())
            {
                this.setFever(false);
            }
        }
        else
        {
            if (getComboCount() >= FEVER_NEEDS_COMBO)
            {
                this.setFever(true);
                stopWatch.initTimer(FEVER_TIME);
                stopWatch.startTimer();
            }
        }
    }

    private void drawScore(final Graphics2D g2d)
    {
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 54));

        g2d.setColor(Color.RED);
        g2d.drawString("Score: " + getScore(), 40, 60);
        g2d.drawString("Combo: " + getComboCount(), 40, 120);
    }

    private void drawFever(final Graphics2D g2d)
    {
        if (this.isFever())
        {
            g2d.setColor(Color.YELLOW);
            g2d.drawString("FEVER!!", 30, 180);
        }
    }
}

