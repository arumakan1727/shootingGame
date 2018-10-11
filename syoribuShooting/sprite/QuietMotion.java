package syoribuShooting.sprite;

public class QuietMotion extends Motion
{
    private final long aliveTime;

    @Override
    protected void onMove(long elapsedTime)
    {
        System.out.println("QuietMotion:" + aliveTime + " ,elapsed:" + (elapsedTime - getMoveStartedTime()));
        // 出現時間を過ぎた
        if (elapsedTime - getMoveStartedTime() >= aliveTime)
        {
            this.sprite.setDisposed(true);
        }
    }

    public QuietMotion(Sprite sprite, int aliveTime)
    {
        super(sprite);
        this.aliveTime = aliveTime;
    }
}
