package syoribuShooting.sprite;

import java.awt.image.BufferedImage;

public class Item extends Target
{
    public Item(TargetType type, BufferedImage img, double centerX, double centerY, Motion motion)
    {
        super(type, img, centerX, centerY, motion);
    }

    public Item(TargetType type, BufferedImage img, double centerX, double centerY)
    {
        super(type, img, centerX, centerY);
    }

    @Override
    protected void deflationBreak()
    {
        final int prevCX = (int)getCenterX();
        final int prevCY = (int)getCenterY();
        int zoom = getZoom();
        if (zoom < 1) {
            this.setState(State.DISPOSE);
        }
        setZoom(zoom - 5);
        setCenterX(prevCX);
        setCenterY(prevCY);
    }

    @Override
    public int getScore(int screenX, int screenY)
    {
        return 0;
    }

    @Override
    protected void _update()
    {

    }
}
