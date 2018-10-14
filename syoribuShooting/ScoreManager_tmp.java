package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.FlagState;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.StopWatch;
import syoribuShooting.system.Transition;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.readImage;
import static java.lang.Math.min;

public class ScoreManager_tmp
{
    private static final int FEVER_TIME = 10 * 1000;
    private final StopWatch stopWatch;
    private FeverGauge feverGauge;

    private int score;
    private int comboCount;

    public ScoreManager_tmp()
    {
        this.stopWatch = new StopWatch();
        this.feverGauge = new FeverGauge();
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
            this.feverGauge.addPoint(15 + min(comboCount * comboCount / 3, 100));
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
        g2d.setFont(new Font(Font.SERIF, Font.BOLD, 40));

        g2d.setColor(Color.RED);
        g2d.drawString("Score: " + getScore(), 25, 50);
        g2d.drawString("Combo: " + getComboCount(), 25, 90);
    }

    class FeverGauge
    {
        private static final int LT_X = 25;
        private static final int LT_Y = 30;
        private static final int WIDTH  = 540;
        private static final int HEIGHT = 185;
        private static final int BAR_LT_X = LT_X + 20;
        private static final int BAR_WIDTH = 445;
        private static final int FEVER_POINT = 1000;

        private final BufferedImage img_frame, img_bar_normal, img_bar_fever, img_back;
        private Transition point;
        private FlagState feverState;
        private double addition;

        FeverGauge()
        {
            img_frame       = readImage("fever-frame.png");
            img_bar_normal  = readImage("fever-greenBar.png");
            img_bar_fever   = readImage("fever-cyanBar.png");
            img_back        = readImage("fever-back.png");
            this.point      = new Transition(0, 0, GameConfig.FPS);
            this.feverState = FlagState.FALSE;
        }

        void update()
        {
        }

        void draw(Graphics2D g2d)
        {
            Shape defaultShape = g2d.getClip();
            Shape gaugeClip = new Rectangle(LT_X, LT_Y, WIDTH, HEIGHT);

            // バックの描画
            g2d.setClip(gaugeClip);
            g2d.drawImage(img_back, LT_X, LT_Y, null);

            // バーの描画
            g2d.setClip(getBarClip(point.getNowVal()));
            if (isFever()) {
                g2d.drawImage(img_bar_fever, LT_X, LT_Y, null);
            } else {
                g2d.drawImage(img_bar_normal, LT_X, LT_Y, null);
            }

            g2d.setClip(gaugeClip);
            g2d.drawImage(img_frame, LT_X, LT_Y, null);

            g2d.setClip(defaultShape);
        }

        boolean isFever()
        {
            return point.getTargetVal() >= FEVER_POINT;
        }

        double getPoint()
        {
            return this.point.getNowVal();
        }

        void addPoint(double val)
        {
            setPoint(getPoint() + val);
        }

        void setPoint(double val)
        {
            // この更新でフィーバになる
            // TODO ここで JUST_NOW_TRUE は危険
            if (val + 15 >= FEVER_POINT)
            {
                val = FEVER_POINT;
                feverState = FlagState.JUST_NOW_TRUE;
                point.setNextValue(0, FEVER_TIME);
                addition = point.getAddition();
            }
            else
            {
                // 300ミリ秒で val にする
                point.setNextValue(val, 300);
            }
        }

        int getFeverPoint()
        {
            return FEVER_POINT;
        }

        private Rectangle getBarClip(double nowPoint)
        {
            return new Rectangle(LT_X, LT_Y, (int)(BAR_WIDTH * (nowPoint / FEVER_POINT)), HEIGHT);
        }
    }
}

