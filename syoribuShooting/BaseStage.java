package syoribuShooting;

public abstract class BaseStage
{
    abstract public int getTimeLimit();

    private TargetList localTargetList, globalTargetList;
    private String nextStageFilePath;

    public BaseStage(String nextStageFilePath)
    {
        this.nextStageFilePath = nextStageFilePath;
        localTargetList = new TargetList();
        globalTargetList = new TargetList();
    }

    public TargetList getLocalTargetList()
    {
        return localTargetList;
    }

    public TargetList getGlobalTargetList()
    {
        return globalTargetList;
    }
    
    public String getNextStageFilePath()
    {
        return this.nextStageFilePath;
    }
}
