package syoribuShooting.system;

public class StopWatch
{
    private int elapsed;
    private int addition;
    private int timeLimit;
    private long now, before;
    private boolean running;

    public StopWatch()
    {
        this.setTimeLimitMillis(-1);
        this.running = false;
    }

    public void initTimer()
    {
        this.initTimer(-1);
    }

    public void initTimer(int limit)
    {
        this.setTimeLimitMillis(limit);
        this.setElapsed(0);
        now = before = System.currentTimeMillis();
        this.addition = 0;
        this.running = false;
    }

    public void startTimer()
    {
        // もし initされていなければ initTimer() を呼ぶ
        if (now != before) {
            initTimer();
        }
        this.running = true;
    }

    public void restartTimer()
    {
        if (isRunning()) return;
        now = before = System.currentTimeMillis();
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

    private void updateElapsed()
    {
        if (this.running) {
            this.before = this.now;
            now = System.currentTimeMillis();
            elapsed += now - before;
        }
    }

    private void setElapsed(int e)
    {
        this.elapsed = e;
    }

    public boolean isRunning()
    {
        return this.running;
    }


}
