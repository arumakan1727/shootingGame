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

public class Button extends Sprite
{
    private BufferedImage image;
    private ActionListener actionListener;
    private boolean isMouseEnter = false;
    private boolean isMousePress = false;
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
        Rectangle rect = new Rectangle((int)getX(), (int)getY(), getWidth(), getHeight());
        boolean isContain = rect.contains(eventManager.mouseX(), eventManager.mouseY());

        if (isEnable && !isMouseEnter && isContain) {
            cursorManager.changeCurrentCursor(Cursor.HAND_CURSOR);
            actionListener.mouseEntered();
            isMouseEnter = true;
        }
        else if (isMouseEnter && !isContain) {
            cursorManager.changeCurrentCursor(Cursor.DEFAULT_CURSOR);
            actionListener.mouseExited();
            isMouseEnter = false;
        }

        if (!isEnable) {
            return;
        }
        
        final int mouseCode = MouseEvent.BUTTON1;
        if (isContain)
        {
            if (eventManager.justNowMousePressed(mouseCode)) {
                actionListener.justNowPressed();
                isMousePress = true;
            } else if (isMousePress && eventManager.isMouseReleased(mouseCode)){
                isMousePress = false;
                actionListener.justNowReleased();
            }
        }
        else
        {
            if (isMousePress) {
                isMousePress = false;
                actionListener.mouseExited();
            }
        }
    }

    public void draw(Graphics2D g2d)
    {
        Composite defaultComposite = g2d.getComposite();
        if (!isEnable) {
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
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
    
}
