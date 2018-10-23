package syoribuShooting;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.geom.RoundRectangle2D;

import static syoribuShooting.GameConfig.*;
import static java.lang.Math.*;

public class ResultScene extends AbstractScene
{
    private static final int AREA_LT_X  = 400;
    private static final int AREA_LT_Y  = 150;
    private static final int AREA_WIDTH = 1100;
    private static final int AREA_HEIGHT= 700;
    private static final int ROW_MOVE   = 100;
    private static final int PADDING_LEFT   = 50;
    private static final int PADDING_TOP    = 100;
    private static final int ROW_HEIGHT     = 64;
    private static final int ROW_MARGIN     = 50;
    private static final int ROW_GX         = AREA_LT_X + PADDING_LEFT;
    private static final int ROW_SX         = ROW_GX + ROW_MOVE;
    private static final int ROW_SCORE_SY   = AREA_LT_Y + PADDING_TOP;

    private ScoreResult scoreResult;
    private Row row_score, row_maxCombo;
    private int cycle = 0;
    private String nickname = null;

    ResultScene(ScoreResult result)
    {
        scoreResult = result;
        setBackImage(readImage("back01.jpg"));
        row_score   = new Row("Score", scoreResult.getScore(), ROW_SX, ROW_SCORE_SY, ROW_GX);
        row_maxCombo= new Row("maxCombo", scoreResult.getComboMax(), ROW_SX + 70, ROW_SCORE_SY + ROW_HEIGHT+ ROW_MARGIN, ROW_GX);
    }

    @Override
    public void initialize(Game game)
    {
        Main.getCursorManager().changeCurrentCursor(Cursor.DEFAULT_CURSOR);
        cycle = 0;
    }

    @Override
    public void finish(Game game)
    {

    }

    @Override
    public void update(Game game, SceneManager.SceneChanger sceneChanger)
    {
        row_score.update(cycle >= 70);
        row_maxCombo.update(cycle >= 70);
        ++cycle;

        if ((nickname == null || nickname.isEmpty()) && (cycle > 180) && cycle % 80 == 0) {
            nickname = JOptionPane.showInputDialog(Main.getWindow().getPane(), "ニックネームを入力してください");
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(getBackImage(), 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, null);
        g2d.setColor(new Color(240, 240, 240, 200));
        g2d.fill(new RoundRectangle2D.Float(AREA_LT_X, AREA_LT_Y, AREA_WIDTH, AREA_HEIGHT, 50, 50));

        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, ROW_HEIGHT));
        row_score.draw(g2d);
        row_maxCombo.draw(g2d);
    }

    static class Row
    {
        private static final Color COLOR_TAG = new Color(35, 35, 35);
        private static final Color COLOR_LINE= new Color(90, 90, 90, 128);
        private static final int VALUE_X = AREA_LT_X + AREA_WIDTH - 400;
        private String tag;
        private int value, tmpVal;
        private int tmpAdd;
        private float opacity;
        private int x, y;
        private int goalX;
        private boolean visible;

        Row(String tag, int value, int x, int y, int goalX)
        {
            this.tag = tag;
            this.value = value;
            this.tmpVal = -1;
            this.x = x;
            this.y = y;
            this.goalX = goalX;
            this.opacity = 0;
            this.visible = false;

            this.tmpAdd = 1 + (value / FPS);
        }

        void update(boolean showValue)
        {
            if (visible) {
                if (opacity < 1.0) {
                    opacity = min(opacity + 0.05f, 1.0f);
                }
            }

            x = max(x-5, goalX);
            if (x <= goalX + 70) {
                visible = true;
            }

            if (showValue) {
                if (tmpVal < value) {
                    tmpVal = min(tmpVal+tmpAdd, value);
                }
            }
        }

        void draw(Graphics2D g2d)
        {
            g2d.setColor(COLOR_LINE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(AREA_LT_X+30, y+10, AREA_LT_X + AREA_WIDTH - 80, y+10);
            if(!visible) {
                return;
            }

            {
                g2d.setColor(COLOR_TAG);
                final Composite defaultComposite = g2d.getComposite();
                final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
                g2d.setComposite(alpha);

                g2d.drawString(tag, x, y);

                g2d.setComposite(defaultComposite);
            }
            if (tmpVal >= 0) {
                g2d.drawString(String.valueOf(tmpVal), VALUE_X, y);
            }

        }
    }
}

