package syoribuShooting.system;

public class Transition
{
    private double nowVal, targetVal;
    private int fps;
    private int time;

    public Transition(double nowVal, double targetVal, int fps)
    {
        this(nowVal, targetVal, fps, 0);
    }

    public Transition(double nowVal, double targetVal, int fps, int milliTime)
    {
        setNowVal(nowVal);
        setTargetVal(targetVal);
        setFps(fps);
        setTime(milliTime);
    }

    public void update()
    {

    }

    public double getNowVal()
    {
        return nowVal;
    }

    public void setNowVal(double nowVal)
    {
        this.nowVal = nowVal;
    }

    public double getTargetVal()
    {
        return targetVal;
    }

    public void setTargetVal(double targetVal)
    {
        this.targetVal = targetVal;
    }

    public int getFps()
    {
        return fps;
    }

    public void setFps(int fps)
    {
        this.fps = fps;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }
}
