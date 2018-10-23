package syoribuShooting.sprite;

import syoribuShooting.GameConfig;
import syoribuShooting.system.Transition;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FadeOutNumberImage extends NumberImage
{
    private Transition opacity;

    public FadeOutNumberImage(BufferedImage[] imgs, int num, int time, int fps)
    {
        super(imgs, num);
        this.opacity = new Transition(2.0, 0);
        opacity.setAddition(Transition.calcAddition(opacity, fps, time));
    }

    @Override
    public void update()
    {
        super.update();
        if (opacity.isDone()) {
            this.setDisposed(true);
        } else {
            opacity.update();
            addY(-2);
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        Composite defaultComposite, newComposite;

        defaultComposite = g2d.getComposite();

        newComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                (float) Math.min(1.0, opacity.getNowVal()) );

        g2d.setComposite(newComposite);
        super.draw(g2d);
        g2d.setComposite(defaultComposite);
    }
}
