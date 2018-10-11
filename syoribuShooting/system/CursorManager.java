package syoribuShooting.system;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class CursorManager
{
    private static final int NUM_CURSOR = 8;
    private final Component component;
    private CustomCursor cursorList[];
    private CustomCursor currentCursor = new CustomCursor(new Cursor(Cursor.DEFAULT_CURSOR), null);

    public CursorManager(final Component component)
    {
        this.component = component;
        this.cursorList = new CustomCursor[NUM_CURSOR];
    }

    public void defineCursor(int id, final Image img, final Point hotSpot)
    {
        if (cursorList[id] != null) throw new IllegalStateException("cursorList[" + id + "] : Already exists");
        if (img == null) throw new NullPointerException();
        cursorList[id] = new CustomCursor(img, hotSpot);
    }

    public Cursor getCursor(int id)
    {
        return cursorList[id].cursor;
    }

    public Image getCursorImage(int id)
    {
        return cursorList[id].img;
    }

    public boolean validCursor(int id)
    {
        return cursorList[id].usableCursor();
    }

    public void changeCurrentCursor(int id, boolean systemCursor)
    {
        if (systemCursor) {
            currentCursor = new CustomCursor(new Cursor(id), null);
        }
        else {
            currentCursor = cursorList[id];
        }
    }

    public void draw(Graphics2D g2d, int mouseX, int mouseY)
    {
        if (currentCursor.usableCursor())
        {
            component.setCursor(currentCursor.cursor);
        }
        else
        {
            g2d.drawImage(
                    currentCursor.img,
                     mouseX + currentCursor.hotSpot.x,
                    mouseY + currentCursor.hotSpot.y,
                    null );
        }
    }

    private static class CustomCursor
    {
        Cursor cursor   = null;
        Image img       = null;
        Point hotSpot   = null;

        CustomCursor(final Image img, final Point hotSpot)
        {
            this.hotSpot = hotSpot;
            try {
                cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, "CustomCursor");
            } catch (IndexOutOfBoundsException e) {
                cursor = null;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        CustomCursor(final Cursor cursor, final Point hotSpot)
        {
            this.cursor = cursor;
            this.hotSpot = hotSpot;
        }

        public boolean usableCursor()
        {
            return cursor != null;
        }
    }
}
