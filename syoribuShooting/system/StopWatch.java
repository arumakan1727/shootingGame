package syoribuShooting.system;

public class StopWatch
{
    private final static int TIMER_INTERVAL = 20;
    private long startTime;
    private int elapsed;
    private int addition;
    private int timeLimit;
    private boolean running;

    public StopWatch()
    {
        this.setTimeLimitMillis(-1);
        this.running = false;
    }

    private void updateElapsed()
    {
        if (this.running) {
            elapsed = (int)(System.currentTimeMillis() - this.startTime);
        }
    }

    private void setElapsed(int e)
    {
        this.elapsed = e;
    }

    public void initTimer()
    {
        this.initTimer(-1);
    }

    public void initTimer(int limit)
    {
        this.setTimeLimitMillis(limit);
        this.setElapsed(0);
        this.startTime = -1;
        this.addition = 0;
        this.running = false;
    }

    public void startTimer()
    {
        // もし initされていなければ initTimer() を呼ぶ
        if (this.startTime >= 0) {
            initTimer();
        }
        this.running = true;
        this.startTime = System.currentTimeMillis();
    }

    public void restartTimer()
    {
        this.running = true;
    }

    public void stopTimer()
    {
        this.running = false;
    }

    public int getElapsedSec()
    {
        return this.getElapsed() / 1000;
    }

    public int getElapsed()
    {
        this.updateElapsed();
        return this.elapsed + addition;
    }

    public boolean isOverTimeLimit()
    {
        if (getTimeLimit() < 0) throw new IllegalStateException("TimeLimit is Undefined.");
        return this.getElapsed() >= this.getTimeLimit();
    }


    public void addElapsed(int addition)
    {
        this.addition += addition;
    }

    public int getRemainTime()
    {
        if(getTimeLimit() < 0) throw new IllegalStateException("TimeLimit is Undefined.");
        return this.getTimeLimit() - this.getElapsed();
    }

    public void addRemainTime(int addition)
    {
        if(getTimeLimit() < 0) throw new IllegalStateException("TimeLimit is Undefined.");
        this.addElapsed(-addition);
    }

    public int getTimeLimit()
    {
        return this.timeLimit;
    }

    public void setTimeLimitMillis(int limit)
    {
        this.timeLimit = limit;
    }

}
