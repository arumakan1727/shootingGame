package syoribuShooting.sprite;

public class XYMotion extends Motion
{
    private double vx, vy, accelX, accelY;

    public XYMotion(double vx, double vy, double accelX, double accelY)
    {
        this(null, vx, vy, accelX, accelY);
    }

    public XYMotion(Sprite sprite, double accelX, double accelY)
    {
        this(sprite, 0, 0, accelX, accelY);
        this.setAccelX(accelX);
        this.setAccelY(accelY);
    }

    public XYMotion(Sprite sprite, double vx, double vy, double accelX, double accelY)
    {
        super(sprite);
        this.setVx(vx);
        this.setVy(vy);
        this.setAccelX(accelX);
        this.setAccelY(accelY);
    }

    @Override
    protected void onMove(long elapsedTime)
    {
        vx += getAccelX();
        vy += getAccelY();
        sprite.addX(vx);
        sprite.addY(vy);
    }

    public double getVx()
    {
        return vx;
    }

    public void setVx(double vx)
    {
        this.vx = vx;
    }

    public double getVy()
    {
        return vy;
    }

    public void setVy(double vy)
    {
        this.vy = vy;
    }

    public double getAccelX()
    {
        return accelX;
    }

    public void setAccelX(double accelX)
    {
        this.accelX = accelX;
    }

    public double getAccelY()
    {
        return accelY;
    }

    public void setAccelY(double accelY)
    {
        this.accelY = accelY;
    }

}
