package syoribuShooting;

import syoribuShooting.sprite.HitEffect1;
import syoribuShooting.sprite.Target;
import syoribuShooting.stage.AbstractStage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Player
{
    private static Player ourInstance = new Player();
    private int score;
    private int comboCount;
    private boolean touchingTarget;

    public static Player getInstance()
    {
        return ourInstance;
    }

    private Player()
    {
        setScore(0);
        setComboCount(0);
        setTouchingTarget(false);
    }

    public void update(Game game)
    {
        final AbstractStage stage = game.getNowStage();
        final InputEventManager eventManager = game.getEventManager();

        if (eventManager.isMouseReleased(MouseEvent.BUTTON1))
        {
            Target hitTarget = stage.getHitTarget();
            if (hitTarget == null) {
                this.setComboCount(0);
            }
            else {
                this.addComboCount(1);
                this.addScore(hitTarget.getScore(eventManager.mouseX(), eventManager.mouseY()));
                game.getEffectManager().addEffect(new HitEffect1(eventManager.mouseReleasedX(), eventManager.mouseReleasedY(), false));
            }
        }

        if (GameConfig.isEnableCursor())
        {
            if (isTouchingTarget()) {
                game.setCursor(GameConfig.shootingCursorGreen);
            } else {
                game.setCursor(GameConfig.shootingCursor);
            }

        }
        this.setTouchingTarget(false);
    }

    public void draw(Graphics2D g2d)
    {
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 54));

        g2d.setColor(Color.RED);
        g2d.drawString("Score: " + getScore(), 40, 60);
        g2d.drawString("Combo: " + getComboCount(), 40, 120);
    }

    public int getScore()
    {
        return this.score;
    }
    public void setScore(int s)
    {
        this.score = s;
    }
    public void addScore(int s)
    {
        this.setScore(getScore() + s);
    }

    public int getComboCount()
    {
        return comboCount;
    }

    public void setComboCount(int comboCount)
    {
        this.comboCount = comboCount;
    }
    public void addComboCount(int addition)
    {
        this.setComboCount(getComboCount() + addition);
    }

    public boolean isTouchingTarget()
    {
        return touchingTarget;
    }

    public void setTouchingTarget(boolean touchingTarget)
    {
        this.touchingTarget = touchingTarget;
    }
}
