package syoribuShooting.sprite;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AnimationProcessor
{
    private final List<Animation> animations;

    public AnimationProcessor()
    {
        animations = new LinkedList<>();
    }

    public void update()
    {
        ListIterator<Animation> itr = animations.listIterator();
        while (itr.hasNext())
        {
            Animation elem = itr.next();
            if (elem.isDisposed())
            {
                itr.remove();
                continue;
            }

            elem.update();
        }
    }

    public void draw(Graphics2D g2d)
    {
        for (int i = 0; i < animations.size(); ++i)
        {
            final Animation elem = animations.get(i);
            elem.draw(g2d);
        }
    }

    public void add(Animation animation)
    {
        this.animations.add(animation);
    }

}
