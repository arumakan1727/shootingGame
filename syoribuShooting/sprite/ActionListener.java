package syoribuShooting.sprite;

import java.util.EventListener;

/**
 * {@link Button}用のイベントリスナ。
 */
public interface ActionListener extends EventListener
{
    /**
     * マウスが対象のボタンの境界に入った瞬間に呼び出される。
     */
    void mouseEntered();

    /**
     * マウスが対象のボタンの境界から出た瞬間に呼び出される。
     */
    void mouseExited();

    /**
     * マウスがボタンを押した瞬間に呼び出される。
     */
    void justNowPressed();

    /**
     * マウスがボタンを話した瞬間に呼び出される。
     */
    void justNowReleased();
}
