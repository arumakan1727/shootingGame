package syoribuShooting;

public class ScoreResult
{
    private final int score;
    private final int comboMax;
    private final int hitCount;

    ScoreResult(int score, int comboMax, int hitCount)
    {
        this.score = score;
        this.comboMax = comboMax;
        this.hitCount = hitCount;
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
}
