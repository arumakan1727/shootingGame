package syoribuShooting;

public class ScoreResult
{
    private final int score;
    private final int comboMax;
    private final int hitCount;
    private final int criticalCount;

    ScoreResult(int score, int comboMax, int hitCount, int criticalCount)
    {
        this.score = score;
        this.comboMax = comboMax;
        this.hitCount = hitCount;
        this.criticalCount = criticalCount;
    }

    public int getScore()
    {
        return score;
    }

    public int getComboMax()
    {
        return comboMax;
    }

    public int getHitCount()
    {
        return hitCount;
    }

    public int getCriticalCount()
    {
        return criticalCount;
    }
}
