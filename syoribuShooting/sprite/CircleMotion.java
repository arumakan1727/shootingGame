package syoribuShooting.sprite;

import java.awt.geom.Point2D;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class CircleMotion extends Motion
{
    private double radius;

    private double theta;
    private int centerX, centerY;

    public CircleMotion(Sprite sprite, double radius, double speed, int centerX, int centerY)
    {
        super(sprite, speed);
        setRadius(radius);
        this.theta = 0;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    protected void onMove(long elapsedTime)
    {
        sprite.setXdefault(centerX + radius * cos(theta));
        sprite.setYdefault(centerY + radius * sin(theta));

        this.theta += this.getSpeed();
//        this.theta = (int)theta % 360;
        this.setSpeed(getSpeed() + getAcceleration());
    }

    @Override
    public Point2D.Double getStartPosition()
    {
        sprite.setXdefault(centerX + radius * cos(theta));
        sprite.setYdefault(centerY + radius * sin(theta));
        return new Point2D.Double(sprite.getXdefault(), sprite.getYdefault());
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public void setDeg(double degree)
    {
        this.theta = degree / 180 * PI;
    }
}
