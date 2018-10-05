package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import syoribuShooting.sprite.Target;
import syoribuShooting.sprite.Target.State;
import syoribuShooting.stage.GameStage;

public class TargetManager
{
    private List<Target> targets = new LinkedList<>();
    private final InputEventManager eventManager;
    
    public TargetManager(final InputEventManager manager)
    {
        this.eventManager = manager;
    }
    
    public void update(final Game game)
    {
        for (ListIterator<Target> it = targets.listIterator(); it.hasNext();)
        {
            final Target elem = it.next();
            elem.update(game);
            if (elem.getState() == State.DISPOSE)
            {
                try {
                    it.remove();
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }
        }

        GameStage stage = game.getNowStage();
        Target hitTarget = null;

        if (eventManager.isMouseReleased(MouseEvent.BUTTON1))
        {
            hitTarget = checkHit(eventManager.mouseReleasedX(), eventManager.mouseReleasedY());
            if (hitTarget != null)
            {
                hitTarget.setState(State.BREAK);
            }
        }
        stage.setHitTarget(hitTarget);
    }
    
    public void draw(final Graphics2D g2d)
    {
        for (Target elem : targets)
        {
            elem.draw(g2d);
        }
    }
    
    public void initialize()
    {
        targets.clear();
    }
    
    public Target checkHit(int px, int py)
    {
        for (ListIterator<Target> it = targets.listIterator(targets.size()); it.hasPrevious();)
        {
            final Target elem = it.previous();
            if (elem.isClickable() && elem.getBounds().isContain(px, py))
            {
                return elem;
            }
        }
        
        return null;
    }
    
    public void add(Target t)
    {
        this.targets.add(t);
    }
    
    public int size()
    {
        return targets.size();
    }

    public boolean isEmpty()
    {
        return targets.isEmpty();
    }

    public List<Target> getTargets()
    {
        return targets;
    }

}
