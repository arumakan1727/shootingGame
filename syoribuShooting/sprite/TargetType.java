package syoribuShooting.sprite;

public enum TargetType
{
    rankA(0, 500),
    rankB(1, 300),
    rankC(2, 100),
    scoreUp(3, 0),
    timeDecrease(4, 0);

    public final int ID;
    private final int defaultScore;

    TargetType(int id, int defaultScore)
    {
        this.ID = id;
        this.defaultScore = defaultScore;
    }

    public int getDefaultScore()
    {
        return defaultScore;
    }

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

