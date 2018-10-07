package syoribuShooting.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Target extends Sprite
{
    abstract public int getScore(int screenX, int screenY);
    abstract protected void _update();

    public enum State
    {
        CREATED,
        ZOOM_UP,
        FLY,
        DISAPPEAR,
        DISPOSE
    }

    protected static final int MAX_ZOOM_UP = 100;
    protected BufferedImage img;
    protected Motion motion;
    private State state;
    private int zoomDelay;

    public Target(BufferedImage img, double centerX, double centerY, final Motion motion)
    {
        super(img.getWidth(), img.getHeight());

        this.img = img;
        this.motion = motion;
        this.setState(State.CREATED);
        this.setZoom(0);
        this.setZoomDelay(0);
        this.setCenterX(centerX);
        this.setCenterY(centerY);
    }

    public Target(BufferedImage img, double centerX, double centerY)
    {
        this(img, centerX, centerY, Motion.NO_MOVE);
    }

    public void update(int elapsedTime)
    {
        switch (this.getState()) {
            case CREATED:
                if (this.getState() == Target.State.CREATED && elapsedTime >= this.getZoomDelay())
                {
                    this.setState(Target.State.ZOOM_UP);
                }
                break;

            case ZOOM_UP:
                if (this.zoomUp(20) == false)
                {
                    this.setState(State.FLY);
                }
                break;

            case FLY:
                this.motion.move(elapsedTime);
                break;

            case DISAPPEAR:
                deflationBreak();
                break;
        }
        _update();
    }

    public Bounds getBounds()
    {
        return new CircleBounds(this.getCenterX(), this.getCenterY(), this.getWidth() / 2 + 10);
    }

    public boolean isClickable()
    {
        return getState() == State.FLY || getState() == State.ZOOM_UP;
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

    protected void deflationBreak()
    {
        int prevCX = (int)this.getCenterX();
        int prevCY = (int)this.getCenterY();

        if (getWidth() < 90)
        {
            setHeight(getHeight() + 30);
            if (getWidth() < 30)
            {
                this.setState(State.DISPOSE);
            }
        }
        setWidth(getWidth() / 2);
        setCenterX(prevCX);
        setCenterY(prevCY);
        setMotion(Motion.NO_MOVE);
    }

    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.img, (int)this.getX(), (int)this.getY(), this.getWidth(), this.getHeight(), null);
    }

    @Override
    public String toString()
    {
        return String.format("Target(%d, %d) state:%s,  zoomDelay:%dms, motion: %s",
                ((int) getXdefault()),((int) getYdefault()), getState().toString(), getZoomDelay(), getMotion());
    }

    @Override
    public double getXdefault()
    {
        return getCenterX();
    }

    @Override
    public double getYdefault()
    {
        return getCenterY();
    }

    @Override
    public void setXdefault(double x)
    {
        this.setCenterX(x);
    }

    @Override
    public void setYdefault(double y)
    {
        this.setCenterY(y);
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public void setMotion(final Motion motion)
    {
        this.motion = motion;
    }

    public Motion getMotion()
    {
        return this.motion;
    }

    public int getZoomDelay()
    {
        return zoomDelay;
    }

    public void setZoomDelay(int zoomDelay)
    {
        this.zoomDelay = zoomDelay;
    }
}
