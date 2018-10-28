package syoribuShooting.sprite;

import syoribuShooting.system.CursorManager;
import syoribuShooting.system.InputEventManager;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * マウスでクリック可能なボタンを表すクラス。
 * 表示されるボタンの見た目は設定された{@link BufferedImage}となる。
 *
 * 利用者はインスタンスを生成したあと、{@link Button#init(CursorManager)}を呼び出す必要がある。
 */
public class Button extends Sprite
{
    private BufferedImage image;
    private ActionListener actionListener;
    private boolean beforeMouseEnter = false;
    private boolean beforeMousePress = false;
    private CursorManager cursorManager;
    private boolean isEnable = true;

    public Button(BufferedImage image)
    {
        super(image.getWidth(), image.getHeight());
        setImage(image);
    }
    
    public void init(CursorManager cursorManager)
    {
        this.cursorManager = cursorManager;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public void update(InputEventManager eventManager)
    {
        if (!isEnable) {
            return;
        }

        // このボタンの境界を取得し、マウスが触れているか判定する
        Rectangle rect = new Rectangle((int)getX(), (int)getY(), getWidth(), getHeight());
        boolean isContain = rect.contains(eventManager.mouseX(), eventManager.mouseY());

        // ちょうど今containになったときにマウスカーソルの画像を指の画像に変更してイベントを発火する
        if (!beforeMouseEnter && isContain) {
            cursorManager.changeCurrentCursor(Cursor.HAND_CURSOR);
            actionListener.mouseEntered();
            beforeMouseEnter = true;
        }
        // ちょうど今!containになったときにマウスカーソルの画像を指の画像に変更してイベントを発火する
        else if (beforeMouseEnter && !isContain) {
            cursorManager.changeCurrentCursor(Cursor.DEFAULT_CURSOR);
            actionListener.mouseExited();
            beforeMouseEnter = false;
        }

        // ちょうど今マウスが押された or ちょうど今マウスが離されたらイベントを発火する
        final int mouseCode = MouseEvent.BUTTON1;
        if (isContain)
        {
            if (eventManager.justNowMousePressed(mouseCode)) {
                actionListener.justNowPressed();
                beforeMousePress = true;
            } else if (beforeMousePress && eventManager.isMouseReleased(mouseCode)){
                beforeMousePress = false;
                actionListener.justNowReleased();
            }
        }
        else
        {
            if (beforeMousePress) {
                beforeMousePress = false;
                actionListener.mouseExited();
            }
        }
    }

    // ボタンのイメージを描画する
    // !isEnableのときは半透明に描画する
    public void draw(Graphics2D g2d)
    {
        Composite defaultComposite = g2d.getComposite();
        if (!isEnable) {
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2d.setComposite(alpha);
        }
        
        g2d.drawImage(getImage(), (int)getX(), (int)getY(), getWidth(), getHeight(), null);
        g2d.setComposite(defaultComposite);
    }

    public void setActionListener(ActionListener actionListener)
    {
        this.actionListener = actionListener;
    }

    public boolean isEnable()
    {
        return isEnable;
    }

    public void setEnable(boolean isEnable)
    {
        this.isEnable = isEnable;
    }

    public boolean isMouseEnter()
    {
        return this.beforeMouseEnter;
    }
    
}
