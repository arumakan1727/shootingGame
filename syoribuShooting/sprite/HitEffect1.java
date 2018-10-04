package syoribuShooting.sprite;

import syoribuShooting.GameConfig;

public class HitEffect1 extends Effect
{
    public HitEffect1(int x, int y, boolean isLoop)
    {
        super(GameConfig.anim_hit, x, y, isLoop);
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
