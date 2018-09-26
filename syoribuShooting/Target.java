package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Target
{
    protected static final double MAX_ZOOM_UP = 1.0;
    
    protected BufferedImage img;
    private double x, y;
    private int loopCount;
    private State state;
    
    private final int defaultWidth;
    private final int defaultHeight;
    
    private int imgWidth;
    private int imgHeight;
    private double zoom; // 何倍にするか
    
    public Target(BufferedImage img, double centerX, double centerY)
    {
        this.img = img;
        this.defaultHeight = this.img.getHeight();
        this.defaultWidth =  this.img.getWidth();
        this.setState(State.ZOOM_UP);
        this.setLoopCount(0);
        
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setZoom(0);
    }
    
    public void update(Game game)
    {
        int loopCount = this.getLoopCount() + 1;
        this.setLoopCount(loopCount);
        
        System.out.printf("zoom: %2.2f\n", this.getZoom());
        
        switch (this.getState()) {
        case ZOOM_UP:
            if (loopCount < 20) break;
            
            if (this.zoomUp(0.24) == false)
            {
                this.setState(State.FLY);
            }
            break;
            
        case FLY:
            if (loopCount > 60)
            {
                this.setLoopCount(0);
                this.setState(State.ZOOM_UP);
                this.setZoom(0);
                this.setCenterX(800);
                this.setCenterY(400);
            }
            break;
        }
    }
    
    protected boolean zoomUp(double addition)
    {
        double zoom = this.getZoom();
        double prevCX = this.getCenterX();
        double prevCY = this.getCenterY();

        if (zoom >= MAX_ZOOM_UP)
        {
            this.setZoom(MAX_ZOOM_UP);
            this.setCenterX(prevCX);
            this.setCenterY(prevCY);
            return false;
        }
        
        zoom += addition;
        this.setZoom(zoom);
        this.setCenterX(prevCX);
        this.setCenterY(prevCY);
        
        return true;
    }
    
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(this.img, (int)this.getX(), (int)this.getY(), this.getImgWidth(), this.getImgHeight(), null);
    }
    
    public double getZoom()
    {
        return zoom;
    }
    
    public void setZoom(double zoom) throws IllegalArgumentException
    {
        if (zoom < 0) throw new IllegalArgumentException();
        
        this.zoom = zoom;
        this.setImgWidth((int)(this.getDefaultWidth() * zoom));
        this.setImgHeight((int)(this.getDefaultHeight() * zoom));
    }
    
    public int getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth)
    {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight()
    {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight)
    {
        this.imgHeight = imgHeight;
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
        return this.getX() + this.getImgWidth() / 2;
    }
    
    public void setCenterX(double centerX)
    {
        this.setX(centerX - this.getImgWidth() / 2);
    }


    public double getCenterY()
    {
        return this.getY() + this.getImgHeight() / 2;
    }
    
    public void setCenterY(double centerY)
    {
        this.setY(centerY - this.getImgHeight() / 2);
    }
    
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }



    public int getLoopCount()
    {
        return loopCount;
    }

    public void setLoopCount(int loopCount)
    {
        this.loopCount = loopCount;
    }



    public enum State
    {
        ZOOM_UP,
        FLY,
        DISPOSE
    }
}
