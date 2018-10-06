package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.InputEventManager;
import syoribuShooting.sprite.HitEffect1;
import syoribuShooting.sprite.Target;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractStage
{
    abstract public void initialize();

    private List<Target> targets = new LinkedList<>();
    private Target hitTarget = null;
    private BufferedImage backImage;

    public AbstractStage(BufferedImage backImage)
    {
        this.setBackImage(backImage);
    }

    public void update(final Game game)
    {
        final InputEventManager eventManager = game.getEventManager();
        boolean isTouchingEntity = false;

        // リスト中のターゲットを全てアップデートする
        for (ListIterator<Target> it = targets.listIterator(); it.hasNext();)
        {
            final Target elem = it.next();
            elem.update();

            // マウスカーソルが的に触れていればフラグを立てる
            if (elem.isClickable() && elem.getBounds().isContain(eventManager.mouseX(), eventManager.mouseY()))
            {
                isTouchingEntity = true;
            }

            // stateがDISPOSEならリストから消す
            if (elem.getState() == Target.State.DISPOSE)
            {
                try {
                    it.remove();
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }
        }

        // 何らかにマウスカーソルが当たっていれば緑の照準カーソルに、そうでなければ通常の照準カーソルにする
        if (isTouchingEntity) {
            game.setCursor(GameConfig.shootingCursorGreen);
        } else {
            game.setCursor(GameConfig.shootingCursor);
        }

        // クリックされて的に当たっていればhitTargetをセットし、ヒットエフェクト
        final Target hitTarget = this.checkHit(eventManager);
        this.setHitTarget(hitTarget);
        if (hitTarget != null)
        {
            hitTarget.setState(Target.State.BREAK);
            game.getEffectManager().addEffect(
                    new HitEffect1(
                            eventManager.mouseReleasedX(),
                            eventManager.mouseReleasedY(),
                            false)
            );
        }

    }

    public void draw(final Graphics2D g2d)
    {
        for (final Target elem : this.targets)
        {
            elem.draw(g2d);
        }
    }

    public Target getHitTarget()
    {
        return hitTarget;
    }

    public void setHitTarget(Target hitTarget)
    {
        this.hitTarget = hitTarget;
    }

    public List<Target> getTargets()
    {
        return targets;
    }

    public boolean noTargets()
    {
        return targets.isEmpty();
    }

    public BufferedImage getBackImage()
    {
        return backImage;
    }

    public void setBackImage(BufferedImage backImage)
    {
        this.backImage = backImage;
    }

    private final Target checkHit(InputEventManager eventManager)
    {
        if (eventManager.isMouseReleased(MouseEvent.BUTTON1))
        {
            int px = eventManager.mouseX();
            int py = eventManager.mouseY();
            for (ListIterator<Target> it = targets.listIterator(targets.size()); it.hasPrevious(); )
            {
                final Target elem = it.previous();
                if (elem.isClickable() && elem.getBounds().isContain(px, py))
                {
                    return elem;
                }
            }
        }

        return null;
    }
}
