package syoribuShooting.sprite;

import syoribuShooting.system.Utils;
import static java.lang.Math.abs;

public class LinearMotion extends Motion
{
    private final int startX, startY, endX, endY;
    private final double dist;
    private double vx, vy;

    public LinearMotion(Sprite sprite, double speed, int ex, int ey)
    {
        this(sprite, speed, (int)sprite.getXdefault(), (int)sprite.getYdefault(), ex, ey);
    }

    public LinearMotion(Sprite sprite, double speed, int sx, int sy, int ex, int ey)
    {
        super(sprite, speed);
        this.startX = sx;
        this.startY = sy;
        this.endX   = ex;
        this.endY   = ey;
        this.dist   = Utils.dist(sx, sy, ex, ey);
        calcVx();
        calcVy();
    }

    @Override
    public void move()
    {
        if (! isArrivedEndX()) {
            sprite.addX(this.vx);
        }
        if (! isArrivedEndY()) {
            sprite.addY(this.vy);
        }

        // 加速度をスピードに加算
        this.setSpeed(getSpeed() + getAcceleration());
    }

    @Override
    public void setSpeed(double speed)
    {
        super.setSpeed(speed);
        calcVx();
        calcVy();
    }

    public boolean isArrivedEndX()
    {
        return abs(sprite.getXdefault() - this.getStartX()) >= abs(getEndX() - getStartX());
    }

    public boolean isArrivedEndY()
    {
        return abs(sprite.getYdefault() - this.getStartY()) >= abs(getEndY() - getStartY());
    }

    public boolean isArrivedEndPoint()
    {
        return isArrivedEndX() && isArrivedEndY();
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public int getEndX()
    {
        return endX;
    }

    public int getEndY()
    {
        return endY;
    }

    /**
     * X方向のベクトルを求める
     * (speed / 斜辺) * 底辺
     * @return
     */
    private double calcVx()
    {
        if (getSpeed() == 0) return this.vx = 0;
        return this.vx = (this.getSpeed() / this.dist) * (endX - startX);
    }

    private double calcVy()
    {
        if (getSpeed() == 0) return this.vy = 0;
        return this.vy = (this.getSpeed() / this.dist) * (endY - startY);
    }
}
