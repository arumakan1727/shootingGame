package syoribuShooting.system;

import java.awt.Graphics2D;

public interface BufferedRenderer
{
    void setBuffering();
    Graphics2D getRenderer();
    void flipBuffer();
}
