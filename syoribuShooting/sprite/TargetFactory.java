package syoribuShooting.sprite;

import syoribuShooting.GameConfig;

import static syoribuShooting.sprite.TargetType.*;

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
                return new Target(rankA, GameConfig.img_targetA, centerX, centerY, motion)
                {
                    @Override
                    protected void _update()
                    { }

                    @Override
                    public Bounds getBounds() { return new CircleBounds(getCenterX(), getCenterY(), 70); }
                };

            case rankB:
                return new Target(rankB, GameConfig.img_targetB, centerX, centerY, motion)
                {
                    @Override
                    protected void _update()
                    {
                    }
                };

            case rankC:
                return new Target(rankC, GameConfig.img_targetC, centerX, centerY, motion)
                {
                    @Override
                    protected void _update()
                    {
                    }
                };

            case ITEM_SCORE_UP:
                return new Item(ITEM_SCORE_UP, GameConfig.img_scoreUP, centerX, centerY, motion);

            case ITEM_TIME_DECREASE:
                return new Item(ITEM_TIME_DECREASE, GameConfig.img_timeDecrease, centerX, centerY, motion);

            default:
                return null;
        }
    }
}
