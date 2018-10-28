package syoribuShooting.sprite;

import syoribuShooting.system.Utils;
import static java.lang.Math.abs;

/**
 * 設定した始点から終点までの直線移動を処理をするクラス。
 */
public class LinearMotion extends Motion
{
    private int startX, startY, toX, toY;
    private double dist;
    private double vx, vy;

    public LinearMotion()
    {
        this(null, 0);
    }

    public LinearMotion(Sprite sprite, double speed)
    {
        this(sprite, speed, 0, 0);
    }

    public LinearMotion(Sprite sprite, double speed, int tx, int ty)
    {
        super(sprite, speed);
        setToPoint(tx, ty);
    }

    @Override
    public void setSprite(Sprite sprite)
    {
        super.setSprite(sprite);
        if (sprite != null) {
            setStartPoint((int) sprite.getXdefault(), (int) sprite.getYdefault());
        }
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

    // x座標が終点に到達しているならtrueを返す
    public boolean isArrivedEndX()
    {
        return abs(sprite.getXdefault() - this.getStartX()) > abs(getToX() - getStartX());
    }

    // y座標が終点に到達しているならtrueを返す
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

    public void setStartPoint(int startX, int startY)
    {
        this.startX = startX;
        this.startY = startY;
        this.dist   = Utils.dist(startX, startY, toX, toY);
        calcVx();
        calcVy();
    }

    public void setToPoint(int toX, int toY)
    {
        this.toX = toX;
        this.toY = toY;
        this.dist   = Utils.dist(startX, startY, toX, toY);
        calcVx();
        calcVy();
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

    /**
     * X方向のベクトルを求める
     * (speed / 斜辺) * 底辺
     * @return x方向のベクトル成分
     */
    private double calcVx()
    {
        if (getSpeed() == 0) return this.vx = 0;
        return this.vx = (this.getSpeed() / this.dist) * (toX - startX);
    }

    /**
     * Y方向のベクトルを求める
     * (speed / 斜辺) * 底辺
     * @return y方向のベクトル成分
     */
    private double calcVy()
    {
        if (getSpeed() == 0) return this.vy = 0;
        return this.vy = (this.getSpeed() / this.dist) * (toY - startY);
    }
}
