package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.InputEventManager;
import syoribuShooting.sprite.HitEffect1;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.StopWatch;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static syoribuShooting.GameConfig.*;

public abstract class BaseStage
{
    abstract public int getTimeLimit();
    abstract public boolean shouldBeFinished();
    abstract protected void _init();
    abstract protected void _update(final Game game);

    private List<Target> targets = new LinkedList<>();
    private Target hitTarget = null;
    private BufferedImage backImage;
    private State state;
    public final int STATE_ID;
    protected final StopWatch stopWatch = new StopWatch();

    public enum State
    {
        WAITING(false),
        SHOOTING(true),
        DISAPPEAR(true),
        FINISHED(false);

        public final boolean isTargetMove;

        State(boolean targetMove)
        {
            this.isTargetMove = targetMove;
        }
    }

    public BaseStage(BufferedImage backImage, int stageID)
    {
        this.STATE_ID = stageID;
        this.setBackImage(backImage);
        this.setState(State.WAITING);
    }

    public void initialize()
    {
        System.out.println("Init elem: " + targets.size());
        if (this.getState() != State.WAITING) {
            throw new IllegalStateException("Now state=" + getState() + ". initialize must be WAITING");
        }
        this.stopWatch.initTimer();
        _init();
    }

    public void update(final Game game)
    {
        if (shouldBeFinished())
        {
            this.makeAllDisappear();
            this.setState(State.DISAPPEAR);
            if (noTargets()) {
                this.setState(State.FINISHED);
                return;
            }
        }
        if (! getState().isTargetMove) return;

        final InputEventManager eventManager = game.getEventManager();
        boolean isTouchingEntity = false;

        // リスト中のターゲットを全てアップデートする
        for (ListIterator<Target> it = targets.listIterator(); it.hasNext();)
        {
            final Target elem = it.next();
            elem.update(stopWatch.getElapsed());

            // マウスカーソルが的に触れていればフラグを立てる
            if (elem.isClickable() && elem.getBounds().isContain(eventManager.mouseX(), eventManager.mouseY()))
            {
                isTouchingEntity = true;
            }

//            System.out.println(elem);

            // 画面外になったら
            final int x = ((int) elem.getXdefault());
            final int y = ((int) elem.getYdefault());
            if (x < OUTER_WINDOW_MINUS
                    || y < OUTER_WINDOW_MINUS
                    || OUTER_WINDOW_PLUS < x
                    || OUTER_WINDOW_PLUS < y)
            {
                elem.setState(Target.State.DISPOSE);
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
            hitTarget.setState(Target.State.DISAPPEAR);
            game.getEffectManager().addEffect(
                    new HitEffect1(
                            eventManager.mouseReleasedX(),
                            eventManager.mouseReleasedY(),
                            false)
            );
        }

        _update(game);
    }

    public void draw(final Graphics2D g2d)
    {
        for (final Target elem : this.targets)
        {
            elem.draw(g2d);
        }
    }

    public void makeAllDisappear()
    {
        for (final Target elem : getTargetList() ) {
            elem.setState(Target.State.DISAPPEAR);
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

    public List<Target> getTargetList()
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

    public StopWatch getStopWatch()
    {
        return this.stopWatch;
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

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        System.out.println("Stage.setState=" + state);
        this.state = state;
        if (state == State.SHOOTING) {
            this.stopWatch.restartTimer();
            System.out.println(stopWatch.isRunning());
        } else {
            this.stopWatch.stopTimer();
        }
    }
}
