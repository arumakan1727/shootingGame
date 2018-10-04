package syoribuShooting.sprite;

import syoribuShooting.Game;

import java.awt.image.BufferedImage;

public abstract class MoveTarget extends Target
{
    protected final Motion motion;

    public MoveTarget(final BufferedImage img, double centerX, double centerY, final Motion motion)
    {
        super(img, centerX, centerY);
        this.motion = motion;
    }

    @Override
    public void update(Game game)
    {
        super.update(game);
        this.motion.move(this);
    }

}
