package syoribuShooting.system;

import java.awt.Graphics2D;

public interface ScreenBuffer
{
    void setBuffering();

    void draw(final DrawTask drawTask);

    interface DrawTask
    {
        void draw(Graphics2D g2d);
    }
}
