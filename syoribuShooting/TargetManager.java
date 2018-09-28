package syoribuShooting;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class TargetManager
{
    private ArrayList<Target> targets = new ArrayList<>();
    
    public TargetManager()
    {
        
    }
    
    public void update(final Game game)
    {
        for (ListIterator<Target> it = targets.listIterator(); it.hasNext();)
        {
            final Target elem = it.next();
            elem.update(game);
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
    

}
