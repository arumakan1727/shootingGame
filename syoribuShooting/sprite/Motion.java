package syoribuShooting.sprite;

import java.awt.geom.Point2D;

public abstract class Motion
{
    abstract protected void onMove(long elapsedTime);

    protected Sprite sprite;
    private double  speed;
    private double  acceleration;
    private int     startDelay;
    private long    moveStartedTime = -1;

    public static final Motion NO_MOVE = new Motion()
    {
        @Override
        protected void onMove(long elapsedTime) {}
    };

    public Motion()
    {
        this(null, 0);
    }

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

    public final void move(int elapsedTime)
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

    public Point2D.Double getStartPosition()
    {
        return new Point2D.Double(sprite.getXdefault(), sprite.getYdefault());
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
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
