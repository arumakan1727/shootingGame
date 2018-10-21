package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.InputEventManager;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetManager
{
    private Map<Integer, List<Target>> targetMap;
    private Target hitTarget;

    public TargetManager()
    {
        targetMap = new HashMap<>();
    }

    public void update(final Game game)
    {
        InputEventManager eventManager = Main.getEventManager();
    }

    public void draw(Graphics2D g2d)
    {
        for (Map.Entry<Integer, List<Target>> entry : targetMap.entrySet()) {
            for (Target target : entry.getValue()) {
                target.draw(g2d);
            }
        }
    }

    public void setTargetList(int key, List<Target> targetList)
    {
        targetMap.put(key, targetList);
    }

    public List<Target> getTargetList(int key)
    {
        return targetMap.get(key);
    }

    public Target getHitTarget()
    {
        return hitTarget;
    }
}
