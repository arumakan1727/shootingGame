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

    public FadeOutNumberImage(BufferedImage[] imgs, int num, int time)
    {
        this(imgs, num, time, 0, 0, 100);
    }

    public FadeOutNumberImage(BufferedImage[] imgs, int num, int time, int x, int y, int zoom)
    {
        super(imgs, num);
        this.opacity = new Transition(1.5, 0);
        opacity.setAddition(Transition.calcAddition(opacity, GameConfig.FPS, time));
        setX(x);
        setY(y);
        setZoom(zoom);
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
