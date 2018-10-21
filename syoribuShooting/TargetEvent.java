package syoribuShooting;

import syoribuShooting.sprite.Target;

public class TargetEvent
{
    private Target target;
    private int mouseX, mouseY;

    public TargetEvent(Target target, int mouseX, int mouseY)
    {
        this.target =target;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public Target getTarget()
    {
        return target;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }
}
