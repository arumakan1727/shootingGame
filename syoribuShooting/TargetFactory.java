package syoribuShooting;

import syoribuShooting.sprite.Motion;
import syoribuShooting.sprite.MoveTarget;

public class TargetFactory
{
    private TargetFactory() {}

    public enum TargetType
    {
        RANK_A,
        RANK_B,
        RANK_C;

        private TargetType() {}

        public static TargetType getTypeByID(int id)
        {
            switch (id)
            {
                case 0: return RANK_A;
                case 1: return RANK_B;
                case 2: return RANK_C;
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
            case RANK_A:
                return new MoveTarget(GameConfig.img_targetA, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 500;
                    }
                };

            case RANK_B:
                return new MoveTarget(GameConfig.img_targetB, centerX, centerY, motion)
                {
                    @Override
                    public int getScore(int screenX, int screenY)
                    {
                        return 300;
                    }
                };

            case RANK_C:
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
