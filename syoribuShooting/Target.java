package syoribuShooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Target extends Sprite
{
    protected static final int MAX_ZOOM_UP = 100;
    
    protected BufferedImage img;
    private int loopCount;
    private State state;
        
    public Target(BufferedImage img, double centerX, double centerY)
    {
        super(img.getWidth(), img.getHeight());

        this.img = img;
        this.setState(State.ZOOM_UP);
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setLoopCount(0);
    }
    
    public void update(Game game)
    {
        int loopCount = this.getLoopCount() + 1;
        this.setLoopCount(loopCount);
        
        System.out.printf("zoom: %d\n", this.getZoom());
        
        switch (this.getState()) {
        case ZOOM_UP:
            if (loopCount < 20) break;
            
            if (this.zoomUp(15) == false)
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
    
    protected boolean zoomUp(int addition)
    {
        int zoom = this.getZoom();
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
        g2d.drawImage(this.img, (int)this.getX(), (int)this.getY(), this.getWidth(), this.getHeight(), null);
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
