package syoribuShooting;

import syoribuShooting.sprite.Effect;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class EffectManager
{
    private final List<Effect> effects;

    public EffectManager()
    {
        effects = new LinkedList<>();
    }

    public void update(final Game game)
    {
        ListIterator<Effect> itr = effects.listIterator();
        while (itr.hasNext())
        {
            Effect elem = itr.next();
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
        for (int i = 0; i < effects.size(); ++i)
        {
            final Effect elem = effects.get(i);
            elem.draw(g2d);
        }
    }

    public void addEffect(Effect effect)
    {
        this.effects.add(effect);
    }

}
