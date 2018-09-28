package syoribuShooting;

public class CircleBounds extends Bounds
{
    private int centerX;
    private int centerY;
    private int radius;

    public CircleBounds(int centerX, int centerY, int r)
    {
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(r);
    }

    public CircleBounds(double centerX, double centerY, double r)
    {
        this((int)centerX, (int)centerY, (int)r);
    }

    @Override
    public boolean isContain(int px, int py)
    {
        // 三平方の定理 x^2 + y^2 < r^2
        return sqr(px - getCenterX()) + sqr(py - getCenterY()) < sqr(getRadius());
    }

    public int getCenterX()
    {
        return centerX;
    }

    public void setCenterX(int centerX)
    {
        this.centerX = centerX;
    }

    public int getCenterY()
    {
        return centerY;
    }

    public void setCenterY(int centerY)
    {
        this.centerY = centerY;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    private static int sqr(int n) throws ArithmeticException
    {
        if (n > 45000) throw new ArithmeticException(n + "^2 is too large for int");
        return n * n;
    }
}
