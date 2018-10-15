package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.FlagState;
import syoribuShooting.system.InputEventManager;
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

public class ScoreManager
{
    private static final int BASE_FEVER_POINT = 30;
    private FeverGauge feverGauge;

    private int score;
    private int comboCount;

    public ScoreManager()
    {
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
            int targetScore = hitTarget.getScore(px, py);
            addComboCount(1);
            addScore(targetScore * (isFever()? 2 : 1));

            int feverAddPoint;
            if (feverGauge.isFever()) {
                feverAddPoint = min(comboCount * targetScore / 80, 30);
            } else {
                feverAddPoint = BASE_FEVER_POINT + min(comboCount * targetScore / 80, 100);
            }
            this.feverGauge.addPoint(feverAddPoint);
        }
    }

    private void updateFeverState()
    {
        switch (feverGauge.getFeverState()) {
            case JUST_NOW_FALSE:
                intoNormalMode();
                break;
            case JUST_NOW_TRUE:
                intoFeverMode();
                break;
        }
    }

    private void drawScore(final Graphics2D g2d)
    {
        g2d.setFont(new Font(Font.SERIF, Font.BOLD, 40));

        g2d.setColor(Color.RED);
        g2d.drawString("Score: " + getScore(), 25, 50);
        g2d.drawString("Combo: " + getComboCount(), 25, 90);
    }

}
class FeverGauge
{

    private static final int LT_X = 25;     // LT は LeftTop の略
    private static final int LT_Y = 30;
    private static final int WIDTH  = 540;
    private static final int HEIGHT = 185;
    private static final int BAR_LT_X = LT_X + 20;
    private static final int BAR_WIDTH = 445;
    private static final int MAX_FEVER_POINT = 1000;
    private static final int FEVER_TIME = 10 * 1000;
    private static final int TRANSITION_TIME_NORMAL = 250;
    private static final int TRANSITION_TIME_FEVER = 150;
    private static final double ADDITION_FEVER_STATE = Transition.calcAddition(MAX_FEVER_POINT, 0, GameConfig.FPS, FEVER_TIME);

    private final BufferedImage img_frame, img_bar_normal, img_bar_fever, img_back;
    private Transition feverPoint;
    private FlagState feverState;

    FeverGauge()
    {
        img_frame       = readImage("fever-frame.png");
        img_bar_normal  = readImage("fever-greenBar.png");
        img_bar_fever   = readImage("fever-cyanBar.png");
        img_back        = readImage("fever-back.png");
        feverPoint      = new Transition(0, 0);
        feverState      = FlagState.FALSE;
    }

    void update()
    {
        feverPoint.update();
        if (isFever())
        {
            if (feverPoint.getNowVal() >= MAX_FEVER_POINT) {
                downToZero();
            }

            
            if(feverPoint.getNowVal() <= 0) {
                feverPoint.setAddition(0);
                feverState = feverState.nextState();
            }
            else if (feverPoint.getAddition() > 0 && feverPoint.isDone()) {
                downToZero();
            }
            else if (feverState == FlagState.JUST_NOW_TRUE) {
                feverState = feverState.nextState();
            }
        }
        else
        {
            if (feverPoint.getNowVal() >= MAX_FEVER_POINT) {
                downToZero();
                feverState = feverState.nextState();
            } else if (feverState == FlagState.JUST_NOW_FALSE) {
                feverState = feverState.nextState();
            }
        }
    }

    private void downToZero()
    {
        feverPoint.setTargetVal(0);
        feverPoint.setAddition(ADDITION_FEVER_STATE);
    }

    void draw(Graphics2D g2d)
    {
        Shape defaultShape = g2d.getClip();
        Shape gaugeClip = new Rectangle(LT_X, LT_Y, WIDTH, HEIGHT);

        // バックの描画
        g2d.setClip(gaugeClip);
        g2d.drawImage(img_back, LT_X, LT_Y, null);

        // バーの描画
        g2d.setClip(getBarClip(feverPoint.getNowVal()));
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
        return feverState.isTrue();
    }

    FlagState getFeverState()
    {
        return this.feverState;
    }

    void addPoint(double val)
    {
        double nextVal;
        if (isFever()) {
            nextVal = feverPoint.getNowVal() + val;
        } else {
            nextVal = feverPoint.getTargetVal() + val;
        }
        if (nextVal + 30 > MAX_FEVER_POINT) nextVal = MAX_FEVER_POINT;

        feverPoint.setTargetVal(nextVal);
        feverPoint.setAddition(Transition.calcAddition(feverPoint, GameConfig.FPS, isFever()? TRANSITION_TIME_FEVER : TRANSITION_TIME_NORMAL));
    }

    private Rectangle getBarClip(double nowPoint)
    {
        // デフォルトのバーの長さ * 現在のポイントがMAXにしめる割合
        return new Rectangle(BAR_LT_X, LT_Y, (int)(BAR_WIDTH * (nowPoint / MAX_FEVER_POINT)), HEIGHT);
    }
}

