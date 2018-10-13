package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.readImage;

public class ScoreManager
{
    private static final int FEVER_TIME = 10 * 1000;
    private final StopWatch stopWatch;
    private FeverGauge feverGauge;

    private int score;
    private int comboCount;

    public ScoreManager()
    {
        this.stopWatch = new StopWatch();
        this.feverGauge = new FeverGauge(
                readImage("fever_frame.png"),
                readImage("fever_greenBar.png"),
                readImage("fever_back.png")
        );
        setScore(0);
        setComboCount(0);
    }

    public void intoFeverMode() {}
    public void intoNormalMode() {}

    public void update(final Game game, final BaseStage stage)
    {
        final InputEventManager eventManager = game.getEventManager();

        if (eventManager.justNowMousePressed(MouseEvent.BUTTON1))
        {
            checkHit(stage.getHitTarget(),
                    eventManager.mousePressedX(),
                    eventManager.mousePressedY());
        }

        this.feverGauge.update();
        this.updateFeverState();
    }

    public void draw(Graphics2D g2d)
    {
        this.drawScore(g2d);
        this.feverGauge.draw(g2d);
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
        return feverGauge.isFever();
    }

    private void checkHit(final Target hitTarget, int px, int py)
    {

        if (hitTarget == null) {
            this.setComboCount(0);
        }
        else {
            this.addComboCount(1);
            this.addScore(hitTarget.getScore(px, py) * (isFever()? 2 : 1));
            this.feverGauge.addPoint(10 + 5 * comboCount);
        }
    }

    private void updateFeverState()
    {
        if (this.isFever())
        {
            if (!stopWatch.isRunning())
            {
                stopWatch.initTimer(FEVER_TIME);
                stopWatch.startTimer();
                intoFeverMode();
            }

            if (stopWatch.getElapsed() >= FEVER_TIME)
            {
                this.feverGauge.setPoint(0);
                stopWatch.stopTimer();
                intoNormalMode();
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
}

class FeverGauge
{
    private static final int LEFT_TOP_X = 30;
    private static final int LEFT_TOP_Y = 30;
    private static final int WIDTH  = 700;
    private static final int HEIGHT = 240;
    private static final int BAR_LT_X = LEFT_TOP_X + 26;
    private static final int BAR_WIDTH = 576;
    private static final int FEVER_POINT = 1000;

    private final BufferedImage frame, bar, back;
    private int point;
    private Rectangle rectClip;
    private int barWidthTarget;
    private int widthAddition;

    FeverGauge(BufferedImage frame, BufferedImage bar, BufferedImage back)
    {
        this.frame  = frame;
        this.bar    = bar;
        this.back   = back;
        this.point = 0;
        this.rectClip = new Rectangle(BAR_LT_X, LEFT_TOP_Y, 0, HEIGHT);
        this.barWidthTarget = 0;
        this.widthAddition = 0;
    }

    void update()
    {
        rectClip.width += widthAddition;
        if (widthAddition > 0 && rectClip.width > barWidthTarget) rectClip.width = barWidthTarget;
        if (rectClip.width < 0) rectClip.width = 0;
    }

    void draw(Graphics2D g2d)
    {
        Shape defaultShape = g2d.getClip();
        Shape guageClip = new Rectangle(LEFT_TOP_X, LEFT_TOP_Y, WIDTH, HEIGHT);

        g2d.setClip(guageClip);
        g2d.drawImage(back, LEFT_TOP_X, LEFT_TOP_Y, null);

        g2d.setClip(this.rectClip);
        g2d.drawImage(bar, LEFT_TOP_X, LEFT_TOP_Y, null);

        g2d.setClip(guageClip);
        g2d.drawImage(frame, LEFT_TOP_X, LEFT_TOP_Y, null);

        g2d.setClip(defaultShape);
    }

    boolean isFever()
    {
        return getPoint() >= FEVER_POINT;
    }

    int getPoint()
    {
        return this.point;
    }

    void addPoint(int val)
    {
        setPoint(getPoint() + val);
    }

    void setPoint(int val)
    {
        this.point = val;
        if (this.point > FEVER_POINT) this.point = FEVER_POINT;

        this.barWidthTarget = (int)(BAR_WIDTH * ((double)point / FEVER_POINT));
        if (barWidthTarget > FEVER_POINT) barWidthTarget = BAR_WIDTH;

        widthAddition = (barWidthTarget - rectClip.width) / 8;
        if (widthAddition < -15) widthAddition = -15;
    }

    int getFeverPoint()
    {
        return FEVER_POINT;
    }
}

