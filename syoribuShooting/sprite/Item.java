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
    public int getScore(int screenX, int screenY)
    {
        return 0;
    }

    @Override
    protected void _update()
    {
    }
}
