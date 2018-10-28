package syoribuShooting.sprite;

/**
 * 当たり判定の境界を表す抽象クラス。
 */
public abstract class Bounds
{
    /**
     * 座標(px, py)が当たり判定の境界に内包されているかどうか判定する。
     * @param px 内包を判定する点のx座標
     * @param py 内包を判定する点のy座標
     * @return (px, py)を内包している場合はtrue, そうでない場合はfalse。
     */
    public abstract boolean isContain(int px, int py);
}
