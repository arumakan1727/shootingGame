package syoribuShooting.sprite;

import syoribuShooting.Game;

import java.awt.image.BufferedImage;

public abstract class MoveTarget extends Target
{
    protected Motion motion;

    public MoveTarget(final BufferedImage img, double centerX, double centerY, final Motion motion)
    {
        super(img, centerX, centerY);
        this.motion = motion;
    }

    public MoveTarget(final BufferedImage img, double centerX, double centerY)
    {
        this(img, centerX, centerY, Motion.NO_MOVE);
    }

    @Override
    public void update(Game game)
    {
        super.update(game);
        this.motion.move();
    }

    public void setMotion(final Motion motion)
    {
        this.motion = motion;
    }

    public Motion getMotion()
    {
        return this.motion;
    }

}
