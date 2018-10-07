package syoribuShooting.sprite;

import syoribuShooting.system.Utils;
import static java.lang.Math.abs;

public class LinearMotion extends Motion
{
    private int startX, startY, toX, toY;
    private double dist;
    private double vx, vy;

    public LinearMotion(Sprite sprite, double speed)
    {
        this(sprite, speed, 0, 0);
    }

    public LinearMotion(Sprite sprite, double speed, int tx, int ty)
    {
        super(sprite, speed);
        this.setPoints(tx, ty);
    }

    @Override
    protected void onMove(long elapsedTime)
    {
        if (! isArrivedEndX()) {
            sprite.addX(this.vx);
            if (isArrivedEndX()) sprite.addX(vx);
        }
        if (! isArrivedEndY()) {
            sprite.addY(this.vy);
            if (isArrivedEndY()) sprite.addY(vy);
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

    @Override
    public String toString()
    {
        return super.toString() + "LinearMotion{" +
                ", toX=" + toX +
                ", toY=" + toY +
                ", arrivedX=" + isArrivedEndX() +
                ", arrivedY=" + isArrivedEndY() +
                '}';
    }

    public boolean isArrivedEndX()
    {
        return abs(sprite.getXdefault() - this.getStartX()) > abs(getToX() - getStartX());
    }

    public boolean isArrivedEndY()
    {
        return abs(sprite.getYdefault() - this.getStartY()) > abs(getToY() - getStartY());
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public boolean isArrivedEndPoint()
    {
        return isArrivedEndX() && isArrivedEndY();
    }

    public int getToX()
    {
        return toX;
    }

    public int getToY()
    {
        return toY;
    }

    public void setPoints(int startX, int startY, int toX, int toY)
    {
        this.startX = startX;
        this.startY = startY;
        this.toX = toX;
        this.toY = toY;
        this.dist   = Utils.dist(startX, startY, toX, toY);
        calcVx();
        calcVy();
    }

    public void setPoints(int toX, int toY)
    {
        setPoints(((int) sprite.getXdefault()), ((int) sprite.getYdefault()), toX, toY);
    }

    /**
     * X方向のベクトルを求める
     * (speed / 斜辺) * 底辺
     * @return
     */
    private double calcVx()
    {
        if (getSpeed() == 0) return this.vx = 0;
        return this.vx = (this.getSpeed() / this.dist) * (toX - startX);
    }

    private double calcVy()
    {
        if (getSpeed() == 0) return this.vy = 0;
        return this.vy = (this.getSpeed() / this.dist) * (toY - startY);
    }
}
