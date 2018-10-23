package syoribuShooting;

import syoribuShooting.sprite.FadeOutNumberImage;
import syoribuShooting.sprite.NumberImage;
import syoribuShooting.sprite.Target;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static syoribuShooting.GameConfig.readImage;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class ScoreManager
{
    private static final int LT_X_SCORE = 30;
    private static final int LT_Y_SCORE = 50;
    private static final int LT_X_COMBO = LT_X_SCORE;
    private static final int LT_Y_COMBO = 200;
    private static final int BASE_FEVER_POINT = 20;
    private static BufferedImage img_num[] = new BufferedImage[10];

    private FeverGauge feverGauge;
    private NumberImage comboValueImg;
    private BufferedImage img_combo;
    private List<NumberImage> hitPointImages;
    private int score;
    private int comboCount;

    public ScoreManager()
    {
        this.feverGauge = new FeverGauge();

        for (int i = 0; i < 10; i++) {
            img_num[i] = readImage("num" + i + "-red.png");
        }
        img_combo       = readImage("combo.png");

        comboValueImg   = new NumberImage(img_num, 0);
        hitPointImages  = new LinkedList<>();
        setScore(0);
        setComboCount(0);

        comboValueImg.setZoom(35);
        comboValueImg.setX(LT_X_COMBO);
        comboValueImg.setY(LT_Y_COMBO);
    }

    public void intoFeverMode() {}
    public void intoNormalMode() {}

    public void notifyHitTarget(TargetEvent e)
    {
        checkHit(e.getTarget(), e.getMouseX(), e.getMouseY());
    }

    public void update()
    {
        for(Iterator<NumberImage> itr = hitPointImages.listIterator(); itr.hasNext();)
        {
            NumberImage elem = itr.next();
            if (elem.isDisposed()) {
                try {
                    itr.remove();
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }
            elem.update();
        }

        this.feverGauge.update();
        this.updateFeverState();
    }

    public void draw(Graphics2D g2d)
    {
        this.drawScore(g2d);
        this.feverGauge.draw(g2d);

        for (NumberImage elem : hitPointImages)
        {
            elem.draw(g2d);
        }
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
        comboValueImg.setNum(getComboCount());
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
            targetScore = targetScore * (isFever()? 2 : 1);
            addComboCount(1);
            addScore(targetScore);

            int feverAddPoint;
            if (feverGauge.isFever()) {
                feverAddPoint = min(comboCount * targetScore / 80, 30);
            } else {
                feverAddPoint = BASE_FEVER_POINT + min(comboCount * targetScore / 80, 100);
            }
            this.feverGauge.addPoint(feverAddPoint);

            FadeOutNumberImage pointImg = new FadeOutNumberImage(img_num, targetScore, 400);

            // zoom と setCenterX,Y() の順番注意
            pointImg.zoomWithHeight(40);
            pointImg.setCenterY(py);
            pointImg.setCenterX(px);
            hitPointImages.add(pointImg);
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
        g2d.drawString("SCORE: " + getScore(), LT_X_SCORE, LT_Y_SCORE);

        // コンボ数が2以上の時だけコンボ数を描画
        if (getComboCount() >= 2) {
            comboValueImg.draw(g2d);
            g2d.drawImage(img_combo,
                    LT_X_COMBO + comboValueImg.getWidth() * max(2, comboValueImg.getDigitLen()) + 15,
                    LT_Y_COMBO + comboValueImg.getHeight() - img_combo.getHeight(),
                    null);
        }
    }

}

