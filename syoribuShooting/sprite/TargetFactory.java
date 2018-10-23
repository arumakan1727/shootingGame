package syoribuShooting.sprite;

import syoribuShooting.GameConfig;

import java.awt.image.BufferedImage;

import static syoribuShooting.sprite.TargetType.*;

public class TargetFactory
{
    private TargetFactory() {}

    private static final BufferedImage targetImages[] = new BufferedImage[TargetType.values().length];

    public static void setTargetImage(int ID, final BufferedImage img)
    {
        targetImages[ID] = img;
    }

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
                return new Target(rankA, targetImages[rankA.ID], centerX, centerY, motion)
                {
                    @Override
                    protected void _update() {}

                    @Override
                    public Bounds getBounds() { return new CircleBounds(getCenterX(), getCenterY(), 70); }
                };

            case rankB:
                return new Target(rankB, targetImages[rankB.ID], centerX, centerY, motion)
                {
                    @Override
                    protected void _update() {}
                };

            case rankC:
                return new Target(rankC, targetImages[rankC.ID], centerX, centerY, motion)
                {
                    @Override
                    protected void _update() {}
                };

            case scoreUp:
                return new Item(scoreUp, targetImages[scoreUp.ID], centerX, centerY, motion);

            case timeDecrease:
                return new Item(timeDecrease, targetImages[timeDecrease.ID], centerX, centerY, motion);

            default:
                return null;
        }
    }
}
