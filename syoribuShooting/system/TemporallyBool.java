package syoribuShooting.system;

public class TemporallyBool
{
    private boolean before = false;
    private boolean now = false;

    public TemporallyBool() {}

    public TemporallyBool(boolean nowBool)
    {
        update(nowBool);
    }

    public void update(boolean nextBool)
    {
        before = now;
        now = nextBool;
    }

    public boolean now()
    {
        return now;
    }

    public boolean before()
    {
        return before;
    }

    public boolean isUp()
    {
        return !before && now;
    }

    public boolean isDown()
    {
        return before && !now;
    }
}
