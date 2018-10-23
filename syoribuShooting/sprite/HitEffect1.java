package syoribuShooting.sprite;

import syoribuShooting.GameConfig;

import java.awt.image.BufferedImage;
import java.util.List;

public class HitEffect1 extends Animation
{
    private static List<BufferedImage> animation = null;

    public static void setAnimation(List<BufferedImage> anim)
    {
        animation = anim;
    }

    public HitEffect1(int x, int y, boolean isLoop)
    {
        super(animation, x, y, isLoop);
        setZoom(10);
        setCenterX(x);
        setCenterY(y);
    }

    @Override
    public void update()
    {
        this.addIndex(3);
        this.setZoom(getZoom() + 2);
        this.addX(-9);
        this.addY(-9);
        if (this.getIndex() >= this.anim.size() - 1)
        {
            this.setIndex(this.anim.size() - 1);
            this.setDisposed(true);
        }
    }
}
