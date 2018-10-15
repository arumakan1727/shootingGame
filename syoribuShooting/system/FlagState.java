package syoribuShooting.system;

public enum FlagState
{
    FALSE(0),
    JUST_NOW_TRUE(1),
    TRUE(2),
    JUST_NOW_FALSE(3);
    
    private static final FlagState states[] = {FALSE, JUST_NOW_TRUE, TRUE, JUST_NOW_FALSE};
    private static final int NUM_STATE = 4;
    public final int ID;

    public boolean isTrue()
    {
        return (this == TRUE || this == JUST_NOW_TRUE);
    }
    
    public FlagState nextState()
    {
        int nextID = this.ID + 1;
        if (nextID >= NUM_STATE) nextID = 0;
        return states[nextID];
    }
    
    private FlagState(int id)
    {
        this.ID = id;
    }
}
