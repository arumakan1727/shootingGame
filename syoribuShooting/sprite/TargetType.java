package syoribuShooting.sprite;

public enum TargetType
{
    rankA,
    rankB,
    rankC,
    ITEM_SCORE_UP,
    ITEM_TIME_DECREASE;

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

