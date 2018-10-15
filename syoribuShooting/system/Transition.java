package syoribuShooting.system;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Transition
{
    private double nowVal, targetVal;
    private double addition;

    public Transition(double nowVal, double targetVal)
    {
        this.nowVal = nowVal;
        this.targetVal = targetVal;
    }

    public void update()
    {
        if (this.isDone()) return ;
        
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

    public boolean isDone()
    {
        // nowVal と targetVal を比較し、等しいならreturn
        return (Double.compare(nowVal, targetVal) == 0);
    }
    
    public double getNowVal()
    {
        return nowVal;
    }

    public double getTargetVal()
    {
        return targetVal;
    }
    
    public void setTargetVal(double tVal)
    {
        this.targetVal = tVal;
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
                '}';
    }

    public static double calcAddition(final Transition transition, int fps, int milliTime)
    {
        return calcAddition(transition.getNowVal(), transition.getTargetVal(), fps, milliTime);
    }
    
    public static double calcAddition(double nowVal, double targetVal, int fps, int milliTime)
    {
        return (targetVal - nowVal) / fps * 1000 / milliTime;
    }
}
