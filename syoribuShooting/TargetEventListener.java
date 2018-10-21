package syoribuShooting;

import java.util.EventListener;

public interface TargetEventListener extends EventListener
{
    void cursorEnteredTarget();

    void cursorExitedTarget();

    void clickedTarget(TargetEvent e);
}
