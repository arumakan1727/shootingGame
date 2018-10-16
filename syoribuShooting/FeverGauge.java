package syoribuShooting;

import syoribuShooting.system.FlagState;
import syoribuShooting.system.Transition;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.readImage;

class FeverGauge
{

    private static final int LT_X = 15;     // LT は LeftTop の略
    private static final int LT_Y = -10;
    private static final int WIDTH  = 540;
    private static final int HEIGHT = 185;
    private static final int BAR_LT_X = LT_X + 20;
    private static final int BAR_WIDTH = 445;
    private static final int MAX_FEVER_POINT = 1000;
    private static final int FEVER_TIME = 8 * 1000;
    private static final int TRANSITION_TIME_NORMAL = 250;
    private static final int TRANSITION_TIME_FEVER = 50;
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
