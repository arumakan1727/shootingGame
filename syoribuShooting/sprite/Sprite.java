package syoribuShooting.sprite;

public abstract class Sprite
{
    private double x, y;
    private final int defaultWidth;
    private final int defaultHeight;
    private int width;
    private int height;
    private int zoom; // 何倍にするか
    private boolean isDisposed;

    public double getXdefault() { return getX(); }
    public double getYdefault() { return getY(); }
    public void setXdefault(double x) { setX(x); }
    public void setYdefault(double y) { setY(y); }

    public Sprite(int width, int height)
    {
        this.defaultWidth =  width;
        this.defaultHeight = height;
        this.setZoom(100);
        this.setDisposed(false);
    }
    
    public Sprite(int x, int y, int width, int height)
    {
        this(width, height);
    }

    
    public final int getZoom()
    {
        return zoom;
    }
    
    public final void setZoom(int zoom) throws IllegalArgumentException
    {
        if (zoom < 0) throw new IllegalArgumentException();
        
        this.zoom = zoom;
        this.setWidth(this.getDefaultWidth() * zoom / 100);
        this.setHeight(this.getDefaultHeight() * zoom / 100);
    }
    
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int imgWidth)
    {
        this.width = imgWidth;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int imgHeight)
    {
        this.height = imgHeight;
    }

    public int getDefaultWidth()
    {
        return defaultWidth;
    }

    public int getDefaultHeight()
    {
        return defaultHeight;
    }

    
    public double getCenterX()
    {
        return this.getX() + this.getWidth() / 2;
    }
    
    public void setCenterX(double centerX)
    {
        this.setX(centerX - this.getWidth() / 2);
    }


    public double getCenterY()
    {
        return this.getY() + this.getHeight() / 2;
    }
    
    public void setCenterY(double centerY)
    {
        this.setY(centerY - this.getHeight() / 2);
    }
    
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void addX(double a)
    {
        this.x += a;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void addY(double a)
    {
        this.y += a;
    }

    public boolean isDisposed()
    {
        return isDisposed;
    }

    public void setDisposed(boolean disposed)
    {
        isDisposed = disposed;
    }
}

