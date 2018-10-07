package syoribuShooting.sprite;

public abstract class Motion
{
    public abstract void move(int elapsedTime);

    private double speed;
    private double acceleration;
    private int startDelay;
    protected final Sprite sprite;

    public static final Motion NO_MOVE = new Motion(null)
    {
        @Override
        public void move(int elapsedTime) {}
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
}
