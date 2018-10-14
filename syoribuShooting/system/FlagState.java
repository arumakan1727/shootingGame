package syoribuShooting.system;

public enum FlagState
{
    TRUE,
    FALSE,
    JUST_NOW_TRUE,
    JUST_NOW_FALSE;

    public boolean isTrue()
    {
        return (this == TRUE || this == JUST_NOW_TRUE);
    }
}
