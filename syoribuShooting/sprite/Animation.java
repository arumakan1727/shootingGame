package syoribuShooting.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 画像リストを順に描画してアニメーションを表現するクラス。
 */
public abstract class Animation extends Sprite
{
    // 描画前の更新毎に呼び出される。
    abstract public void update();

    final List<BufferedImage> anim;
    private int index;
    private final boolean isLoop;

    public Animation(List<BufferedImage> anim, int x, int y, boolean isLoop)
    {
        this(anim, x, y, anim.get(0).getWidth(), anim.get(0).getHeight(), isLoop);
    }

    public Animation(List<BufferedImage> anim, int x, int y, int width, int height, boolean isLoop)
    {
        super(width, height);
        this.setDisposed(false);
        this.setIndex(0);
        this.anim = anim;
        this.isLoop = isLoop;
    }

    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(this.anim.get(getIndex()), (int)this.getX(), (int)this.getY(),
                this.getWidth(), this.getHeight(), null);
    }

    public int getFrameCount()
    {
        return anim.size();
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * 表示する画像を指す添字を{@code plus}だけ進める。
     * 画像リストの終端を超えた場合は添字は画像リストの末尾を指す。
     * @param plus 表示する画像を指す添字の増分
     */
    public void addIndex(int plus)
    {
        int idx = this.getIndex();
        idx += plus;

        if (idx >= anim.size()) {
            if (isLoop()) {
               idx = 0;
            } else {
                idx = anim.size() - 1;
            }
        }

        this.setIndex(idx);
    }

    public boolean isLoop()
    {
        return this.isLoop;
    }
}
