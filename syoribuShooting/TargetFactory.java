package syoribuShooting;

import syoribuShooting.sprite.Bounds;
import syoribuShooting.sprite.CircleBounds;
import syoribuShooting.sprite.Motion;
import syoribuShooting.sprite.Target;

public class TargetFactory
{
    private TargetFactory() {}

    public static Target createTarget(final TargetType type)
    {
        return createTarget(type, 0, 0);
    }

    public static Target createTarget(final TargetType type, int centerX, int centerY)
    {
        return createTarget(type, centerX, centerY, Motion.NO_MOVE);
    }

    public static Target createTarget(final TargetType type, int centerX, int centerY, final Motion motion)
    {
        switch (type)
        {
            case rankA:
                return new Target(GameConfig.img_targetA, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 500;
                    }

                    @Override
                    protected void _update()
                    { }

                    @Override
                    public Bounds getBounds() { return new CircleBounds(getCenterX(), getCenterY(), 70); }
                };

            case rankB:
                return new Target(GameConfig.img_targetB, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 300;
                    }

                    @Override
                    protected void _update()
                    {
                    }
                };

            case rankC:
                return new Target(GameConfig.img_targetC, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 100;
                    }

                    @Override
                    protected void _update()
                    {
                    }
                };

            default:
                return null;
        }
    }
}
