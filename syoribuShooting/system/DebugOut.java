package syoribuShooting.system;

import java.io.PrintStream;

public class DebugOut
{
    private static int enableBitFlag = ~0;

    private final int ID; 
    private final int cycle;
    private int count = 0;
    
    public DebugOut(int ID, int cycle)
    {
        checkID(ID);
        if (cycle <= 0) throw new IllegalArgumentException(cycle + "`cycle` must be positiveNumber.");
        
        this.ID = ID;
        this.cycle = cycle;
    }
    
    public void print(final String str, PrintStream out)
    {
        if (!isEnable(this.ID)) return;
        
        if (count == 0) {
            out.print(str);
        }
        count = countNext(count);
    }
    
    public void println(final String str, PrintStream out)
    {
        this.print(str + "\n", out);
    }

    public void println(final String str)
    {
        println(str, System.out);
    }
    
    private int countNext(int cnt)
    {
        ++cnt;
        return (cnt >= cycle)? 0 : cnt; 
    }
    
    private static void checkID(int ID) throws IndexOutOfBoundsException
    {
        if (ID < 0 || 31 < ID)
            throw new IndexOutOfBoundsException(ID + ": ID must be 0 ~ 31");        
    }
    
    public static void setEnable(int ID, boolean isEnable)
    {
        checkID(ID);
        if (isEnable) {
            enableBitFlag |= (1 << ID);
        }
        else {
            enableBitFlag &= ~(1 << ID);
        }
    }
    
    public static boolean isEnable(int ID)
    {
        checkID(ID);
        return ((enableBitFlag >> ID) & 1) == 1;
    }
}

