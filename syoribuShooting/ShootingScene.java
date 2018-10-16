package syoribuShooting;

import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ShootingScene extends AbstractScene
{
    private static final int TIME_LIMIT = 40 * 1000;
    private final StopWatch stopWatch;
    private BaseStage nowStage;
    private State state;
    private ScoreManager scoreManager;
    private XMLStageParser xmlStageParser;
    private Class aClass;
    private Thread readNextStageThread;

    private final BufferedImage back_normal = GameConfig.readImage("back02.jpg");
    private final BufferedImage back_fever  = GameConfig.readImage("back02-red.jpg");

    public ShootingScene(final String filePath, Class c)
    {
        super();
        this.setBackImage(back_normal);
        this.stopWatch = new StopWatch();
        this.scoreManager = new ScoreManager()
        {
            @Override
            public void intoFeverMode()
            {
                setBackImage(back_fever);
            }

            @Override
            public void intoNormalMode()
            {
                setBackImage(back_normal);
            }
        };
        this.xmlStageParser = new XMLStageParser(filePath, c);
        this.aClass = c;

        this.xmlStageParser.parse();
        this.setNowStage(xmlStageParser.getParsedStage());
        this.readNextStage();
        this.setState(State.WAIT_SHOOTING);

        this.initialize();
    }

    @Override
    public void initialize()
    {
        this.nowStage.initialize();
        this.setState(State.WAIT_SHOOTING);
        this.stopWatch.initTimer(TIME_LIMIT);
    }

    @Override
    public void update(final Game game)
    {
        if (nowStage.getState() == BaseStage.State.FINISHED)
        {
            changeStage();
        }
        else {
            if (game.getEventManager().isKeyPressed(KeyEvent.VK_SPACE)) {
                nowStage.setState(BaseStage.State.WAITING);
                this.stopWatch.stopTimer();
            } else {
                nowStage.setState(BaseStage.State.SHOOTING);
                this.stopWatch.restartTimer();
            }
        }

        switch (this.getState()) {
            case WAIT_SHOOTING:
                this.stopWatch.startTimer();
                this.setState(State.SHOOTING);
                this.nowStage.setState(BaseStage.State.SHOOTING);
                break;
            case SHOOTING:
                if (this.stopWatch.isOverTimeLimit())
                {
                    if (this.nowStage.noTargets()) {
                        this.setState(State.TIME_OVER);
                    } else {
                        nowStage.makeAllDisappear();
                    }
                }
                this.nowStage.update(game);
                this.scoreManager.update(game, this.getNowStage());
                break;
        }
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);
        this.nowStage.draw(g2d);

        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 70));
        g2d.setColor(Color.GREEN);
        int t = this.stopWatch.getRemainTime();
        if (t  < 0) t = 0;
        g2d.drawString("Time: " + t/1000 + "." + t%1000 / 100, GameConfig.VIRTUAL_WIDTH - 500, 80);

        scoreManager.draw(g2d);
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public BaseStage getNowStage()
    {
        return nowStage;
    }

    public void setNowStage(BaseStage nowStage)
    {
        this.nowStage = nowStage;
    }


    private void changeStage()
    {
        try {
            readNextStageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BaseStage nextStage = xmlStageParser.getParsedStage();
        nextStage.initialize();
        nextStage.setState(BaseStage.State.SHOOTING);
        setNowStage(nextStage);

        this.readNextStage();
    }

    private void readNextStage()
    {
        readNextStageThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                final String filePath = getNowStage().getNextStageFilePath();
                xmlStageParser.setInputStream(aClass.getResourceAsStream(filePath));
                xmlStageParser.parse();
            }
        });

        readNextStageThread.start();
    }
}
