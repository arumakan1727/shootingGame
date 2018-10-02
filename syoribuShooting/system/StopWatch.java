package syoribuShooting.system;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatch
{
    private final static int TIMER_INTERVAL = 20;
    private final Timer timer;
    private final TimerTask timerTask;
    private int elapsed;
    private int limit;

    public StopWatch()
    {
        this.timer = new Timer();
        this.timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                addElapsed(TIMER_INTERVAL);
            }
        };

        this.setTimeLimitMillis(-1);
    }

    public void initTimer()
    {
        this.initTimer(-1);
    }

    public void initTimer(int limit)
    {
        this.setTimeLimitMillis(limit);
        this.setElapsed(0);
    }

    public void startTimer()
    {
        this.timer.scheduleAtFixedRate(this.timerTask, 0, TIMER_INTERVAL);
    }

    public void stopTimer()
    {
        this.timer.cancel();
    }

    public boolean isOverTimeLimit()
    {
        if (getTimeLimitMillis() < 0) throw new IllegalStateException("TimeLimit is Undefined.");
        return this.getElapsedMillis() >= this.getTimeLimitMillis();
    }

    public synchronized int getElapsedMillis()
    {
        return this.elapsed;
    }

    public int getElapsedSec()
    {
        return this.getElapsedMillis() / 1000;
    }

    public synchronized void setElapsed(int e)
    {
        this.elapsed = e;
    }

    public void addElapsed(int addition)
    {
        this.setElapsed(getElapsedMillis() + addition);
    }

    public int getRemainTime()
    {
        if(getTimeLimitMillis() < 0) throw new IllegalStateException("TimeLimit is Undefined.");
        return this.getTimeLimitMillis() - this.getElapsedMillis();
    }

    public void addRemainTime(int addition)
    {
        this.addElapsed(-addition);
    }

    public int getTimeLimitMillis()
    {
        return this.limit;
    }

    public void setTimeLimitMillis(int limit)
    {
        this.limit = limit;
    }


}
