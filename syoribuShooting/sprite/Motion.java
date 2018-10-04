package syoribuShooting.sprite;

public abstract class Motion
{
    public abstract void move();

    private double speed;
    private double acceleration;
    protected final Sprite sprite;

    public static final Motion NO_MOVE = new Motion(null, 0)
    {
        @Override
        public void move() {}
    };

    public Motion(final Sprite sprite, double speed)
    {
        this.sprite = sprite;
        this.setSpeed(speed);
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
}
