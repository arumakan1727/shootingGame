package syoribuShooting.sprite;

import java.util.EventListener;

public interface ActionListener extends EventListener
{
    void mouseEntered();
    void mouseExited();
    void justNowPressed();
    void justNowReleased();
}
