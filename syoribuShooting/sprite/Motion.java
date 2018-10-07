package syoribuShooting.sprite;

public abstract class Motion
{
    abstract protected void onMove(long elapsedTime);

    private double speed;
    private double acceleration;
    private int startDelay;
    protected final Sprite sprite;
    private long moveStartedTime = -1;

    public static final Motion NO_MOVE = new Motion(null)
    {
        @Override
        protected void onMove(long elapsedTime) {}
    };

    public Motion(final Sprite sprite)
    {
        this(sprite, 0);
    }

    public Motion(final Sprite sprite, double speed)
    {
        this.sprite = sprite;
        this.setSpeed(speed);
        this.setSpeed(0);
    }

    public void move(int elapsedTime)
    {
        if (moveStartedTime < 0) {
            setMoveStartedTime(elapsedTime);
        }
        if (elapsedTime - getMoveStartedTime() < this.getStartDelay()) return;

        this.onMove(elapsedTime);
    }

    @Override
    public String toString()
    {
        return String.format("Motion{speed=%.2f, acceleration=%.2f, startDelay=%d}",
                speed, acceleration, startDelay);
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public double getAcceleration()
    {
        return acceleration;
    }

    public void setAcceleration(double acceleration)
    {
        this.acceleration = acceleration;
    }

    public int getStartDelay()
    {
        return startDelay;
    }

    public void setStartDelay(int startDelay)
    {
        this.startDelay = startDelay;
    }

    protected long getMoveStartedTime()
    {
        return moveStartedTime;
    }

    private void setMoveStartedTime(long moveStartedTime)
    {
        this.moveStartedTime = moveStartedTime;
    }
}
