package syoribuShooting;

import syoribuShooting.sprite.Bounds;
import syoribuShooting.sprite.CircleBounds;
import syoribuShooting.sprite.Motion;
import syoribuShooting.sprite.MoveTarget;

public class TargetFactory
{
    private TargetFactory() {}

    public enum TargetType
    {
        rankA,
        rankB,
        rankC;

        private TargetType() {}

        public static TargetType getTypeByID(int id)
        {
            switch (id)
            {
                case 0: return rankA;
                case 1: return rankB;
                case 2: return rankC;
                default: throw new IndexOutOfBoundsException();
            }
        }
    }

    public static MoveTarget createTarget(final TargetType type)
    {
        return createTarget(type, 0, 0);
    }

    public static MoveTarget createTarget(final TargetType type, int centerX, int centerY)
    {
        return createTarget(type, centerX, centerY, Motion.NO_MOVE);
    }

    public static MoveTarget createTarget(final TargetType type, int centerX, int centerY, final Motion motion)
    {
        switch (type)
        {
            case rankA:
                return new MoveTarget(GameConfig.img_targetA, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 500;
                    }

                    @Override
                    public Bounds getBounds() { return new CircleBounds(getCenterX(), getCenterY(), 65); }
                };

            case rankB:
                return new MoveTarget(GameConfig.img_targetB, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 300;
                    }
                };

            case rankC:
                return new MoveTarget(GameConfig.img_targetC, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 100;
                    }
                };

            default:
                return null;
        }
    }
}
