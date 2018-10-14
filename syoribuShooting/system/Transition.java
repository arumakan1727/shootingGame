package syoribuShooting.system;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Transition
{
    private double nowVal, targetVal;
    private double addition;
    private int fps;
    private int time;

    public Transition(double nowVal, double targetVal, int fps)
    {
        this(nowVal, targetVal, fps, 0);
    }

    /**
     * ` milliTime`秒で `nowVal`から`targetVal`へ変化する
     */
    public Transition(double nowVal, double targetVal, int fps, int milliTime)
    {
        this.nowVal = nowVal;
        this.targetVal = targetVal;
        this.fps = fps;
        this.time = milliTime;

        calcAddition();
    }

    public void update()
    {
        // nowVal と targetVal を比較し、等しいならreturn
        if (Double.compare(nowVal, targetVal) == 0)
        {
            return;
        }

        nowVal += addition;

        // 増加量が正のとき、targetValueを上回っているかチェック
        if (addition > 0) {
            nowVal = min(nowVal, targetVal);
        }
        // 増加量が負のとき、targetValueを下回っているかチェック
        else {
            nowVal = max(nowVal, targetVal);
        }
    }

    // 変化量を決める
    private void calcAddition()
    {
        // 変化量 / (秒 * fps)
        // 変化量 / (ミリ秒/1000 * fps)
        // 変化量 / fps * 1000 / ミリ秒
        this.addition = (targetVal - nowVal) / fps * 1000 / time;
    }

    public void setNextValue(double nextVal, int milliTime)
    {
        this.targetVal = nextVal;
        this.time = milliTime;
        calcAddition();
    }

    public double getNowVal()
    {
        return nowVal;
    }

    public double getTargetVal()
    {
        return targetVal;
    }

    public int getFps()
    {
        return fps;
    }

    public void setFps(int fps)
    {
        this.fps = fps;
        calcAddition();
    }

    public int getTime()
    {
        return time;
    }

    public double getAddition()
    {
        return this.addition;
    }

    public void setAddition(double addition)
    {
        this.addition = addition;
    }

    @Override
    public String toString()
    {
        return "Transition{" +
                "nowVal=" + nowVal +
                ", targetVal=" + targetVal +
                ", addition=" + addition +
                ", fps=" + fps +
                ", time=" + time +
                '}';
    }
}
