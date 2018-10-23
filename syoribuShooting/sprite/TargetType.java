package syoribuShooting.sprite;

public enum TargetType
{
    rankA(500),
    rankB(300),
    rankC(100),
    scoreUp(0),
    timeDecrease(0);

    private final int defaultScore;

    TargetType(int defaultScore)
    {
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

