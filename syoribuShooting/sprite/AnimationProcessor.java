package syoribuShooting.sprite;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link Animation}を管理するクラス。内部では連結リストを使用して管理する。
 */
public class AnimationProcessor
{
    private final List<Animation> animations;

    public AnimationProcessor()
    {
        animations = new LinkedList<>();
    }

    /**
     * 内部に保持している全ての{@link Animation}インスタンスに対して{@link Animation#update()}を実行する。
     *
     * インスタンスで{@link Animation#isDisposed()}が{@code true}になった時はそのインスタンスをリストから削除する。
     */
    public void update()
    {
        ListIterator<Animation> itr = animations.listIterator();
        while (itr.hasNext())
        {
            Animation elem = itr.next();
            if (elem.isDisposed())
            {
                itr.remove();
                continue;
            }

            elem.update();
        }
    }

    /**
     * 内部に保持している全ての{@link Animation}インスタンスに対して{@link Animation#draw(Graphics2D)}を実行する。
     */
    public void draw(Graphics2D g2d)
    {
        for (int i = 0; i < animations.size(); ++i)
        {
            final Animation elem = animations.get(i);
            elem.draw(g2d);
        }
    }

    /**
     * 内部の{@link Animation}インスタンスを管理するリストに引数のインスタンスを追加する。
     * @param animation 追加するAnimationインスタンス
     */
    public void add(Animation animation)
    {
        this.animations.add(animation);
    }

}
