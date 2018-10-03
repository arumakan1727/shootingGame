package syoribuShooting.sprite;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.InputEventManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Target extends Sprite
{
    protected static final int MAX_ZOOM_UP = 100;
    
    protected BufferedImage img;
    private int loopCount;
    private State state;

    abstract public int getScore();

    public Target(BufferedImage img, double centerX, double centerY)
    {
        super(img.getWidth(), img.getHeight());

        this.img = img;
        this.setState(State.ZOOM_UP);
        this.setZoom(0);
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setLoopCount(0);
    }
    
    public void update(final Game game)
    {
        final InputEventManager eventManager = game.getEventManager();
        int loopCount = this.getLoopCount() + 1;
        this.setLoopCount(loopCount);


        if (this.getBounds().isContain(eventManager.mouseX(), eventManager.mouseY()))
        {
            game.getPlayer().setTouchingTarget(true);
        }

        switch (this.getState()) {
        case ZOOM_UP:
            if (loopCount < 20) break;
            
            if (this.zoomUp(20) == false)
            {
                this.setState(State.FLY);
            }
            break;
        }
    }

    public Bounds getBounds()
    {
        return new CircleBounds(this.getCenterX(), this.getCenterY(), this.getWidth() / 2);
    }
    
    protected boolean zoomUp(int addition)
    {
        int zoom = this.getZoom();
        double prevCX = this.getCenterX();
        double prevCY = this.getCenterY();
        boolean canZoom = true;

        zoom += addition;

        if (zoom >= MAX_ZOOM_UP)
        {
            zoom = MAX_ZOOM_UP;
            canZoom = false;
        }
        this.setZoom(zoom);
        this.setCenterX(prevCX);
        this.setCenterY(prevCY);

        return canZoom;
    }
    
    public void draw(final Graphics2D g2d)
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
        BREAK,
        DISPOSE
    }
}
