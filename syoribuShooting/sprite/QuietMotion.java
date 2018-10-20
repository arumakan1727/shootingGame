package syoribuShooting.sprite;

public class QuietMotion extends Motion
{
    private final int aliveTime;

    public QuietMotion(int aliveTime)
    {
        this(null, aliveTime);
    }
    public QuietMotion(Sprite sprite, int aliveTime)
    {
        super(sprite);
        this.aliveTime = aliveTime;
    }

    @Override
    protected void onMove(long elapsedTime)
    {
        // 出現時間を過ぎた
        if (elapsedTime - getMoveStartedTime() >= aliveTime)
        {
            this.sprite.setDisposed(true);
        }
    }

}
