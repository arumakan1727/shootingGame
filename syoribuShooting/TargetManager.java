package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

import syoribuShooting.Target.State;

public class TargetManager
{
    private ArrayList<Target> targets = new ArrayList<>();
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
        if (eventManager.mouseClicked(MouseEvent.BUTTON1))
        {
            final Target hitTarget = checkHit(eventManager.mouseX(), eventManager.mouseY());
            if (hitTarget != null)
            {
                hitTarget.setState(State.DISPOSE);
            }            
        }
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
            if (elem.getBounds().isContain(px, py))
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

}
