/**
 * 
 */
package syoribuShooting.system;

/**
 * @author ei1727
 *
 */
public abstract class FPSTimer implements Runnable
{
    public abstract void callBack();

    private boolean running    = false;
    private int fps = -1;
    private long period;
    private Thread thread;

    private final DebugOut debugger = new DebugOut(0, 10);
    
    public FPSTimer(int fps)
    {
        this.setFPS(fps);
    }
    
    public void setFPS(int fps) throws IllegalArgumentException
    {
        if (fps <= 0) throw new IllegalArgumentException();
        this.fps = fps;
        this.period = (long)(1.0  / fps * 1_000_000_000);
    }
    
    public int getFPS()
    {
        return this.fps;
    }
    
    public void start()
    {
        this.running = true;
        if (this.thread == null)
        {
            this.thread = new Thread(this);
            thread.start();
            System.out.println("FPS loop start");
        }
    }
    
    public void stop()
    {
        this.running = false;
    }
    
    /**
     * 
     */
    @Override
    public void run()
    {
        long currentTime, beforeTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;

        beforeTime = System.nanoTime();

        while (this.running)
        {
            // fps毎にcallBack()を呼び出す
            this.callBack();
            
            currentTime = System.nanoTime();
            timeDiff = currentTime - beforeTime;
            // 前回のフレームの休止時間誤差も引いておく
            sleepTime = (this.period - timeDiff) - overSleepTime;

            debugger.println("sleepTime: " + sleepTime / 1_000_000 + "\t runTime: " + (period - sleepTime)/ 1_000_000, System.out);

            if (sleepTime > 0) { // 休止時間が取れる場合
                try {
                    Thread.sleep(sleepTime / 1_000_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // sleep() の誤差
                overSleepTime = (System.nanoTime() - currentTime) - sleepTime;
            } else {
                // 状態更新・レンダリングで時間を使い切ってしまい,休止時間が撮れない場合
                overSleepTime = 0L;
                if (++noDelays >= 16){
                    Thread.yield();
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
        }

    }
}
