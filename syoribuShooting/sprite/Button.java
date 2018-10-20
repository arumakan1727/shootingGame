package syoribuShooting.sprite;

import syoribuShooting.system.InputEventManager;

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

    public Button(BufferedImage image)
    {
        super(image.getWidth(), image.getHeight());
        setImage(image);
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

        if (!isMouseEnter && isContain) {
            actionListener.mouseEntered();
            isMouseEnter = true;
        }
        else if (isMouseEnter && !isContain) {
            actionListener.mouseExited();
            isMouseEnter = false;
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
        g2d.drawImage(getImage(), (int)getX(), (int)getY(), getWidth(), getHeight(), null);
    }

    public void setActionListener(ActionListener actionListener)
    {
        this.actionListener = actionListener;
    }
}
