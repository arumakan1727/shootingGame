package syoribuShooting.sprite;

public abstract class Motion
{
    public abstract void move(final Sprite sprite);

    private int speed;

    public static final Motion NO_MOVE = new Motion(0)
    {
        @Override
        public void move(final Sprite sprite)
        {
        }
    };

    public Motion(int speed)
    {
        this.setSpeed(speed);
    }


    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
