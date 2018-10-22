package syoribuShooting;

import java.io.InputStream;

public class StageManager
{
    private int stageLaunchedTime;
    private BaseStage nowStage = null;
    private XMLStageParser xmlStageParser;
    private Thread readStageThread;
    
    public StageManager()
    {
        xmlStageParser = new XMLStageParser();
    }
    
    public void changeStage(int time, BaseStage newStage)
    {
        nowStage = newStage;
        stageLaunchedTime = time;
    }
    
    public int getStageElapsedTime(int nowTime)
    {
        return nowTime - stageLaunchedTime;
    }
    
    public BaseStage getNowStage()
    {
        return nowStage;
    }
    
    public BaseStage getReadStage()
    {
        try {
            readStageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return xmlStageParser.getParsedStage();
    }

    public void readStageWithThread(final InputStream is)
    {
        readStageThread = new Thread(new Runnable() {
            
            @Override
            public void run()
            {
                try {
                xmlStageParser.setInputStream(is);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                xmlStageParser.parse();
            }
        });
        
        readStageThread.start();
    }
}
