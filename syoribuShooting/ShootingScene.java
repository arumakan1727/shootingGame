package syoribuShooting;

import syoribuShooting.sprite.Animation;
import syoribuShooting.system.GifReader;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

enum State
{
    WAIT_SHOOTING,
    SHOOTING,
    INTO_FEVER,
    TIME_OVER,
}

public class ShootingScene extends AbstractScene
{
    private static final int TIME_LIMIT = 40 * 1000;
    private final StopWatch stopWatch;
    private BaseStage nowStage;
    private State state;
    private ScoreManager scoreManager;
    private XMLStageParser xmlStageParser;
    private Thread readNextStageThread;

    private Animation fireFrameAnim = new Animation(GameConfig.readNumberedImages("flame%02d.png", 0, 3), 0, 0, true)
    {
        private static final int WAIT_CYCLE = 4;
        private int cycle = 0;
        @Override
        public void update()
        {
            if(!scoreManager.isFever()) {
                this.setDisposed(true);
                return;
            }
            if (cycle == 0) {
                addIndex(1);
            }
            ++cycle;
            if (cycle >= WAIT_CYCLE) cycle = 0;
        }

        @Override
        public void draw(Graphics2D g2d)
        {
            final Shape defaultShape = g2d.getClip();
            g2d.setClip(new Rectangle(0, 500, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT));
            super.draw(g2d);
            g2d.setClip(defaultShape);
        }
    };
    private Animation intoOverHeatAnim = new Animation(GifReader.readGif(GameConfig.getResourceAsStream(GameConfig.PATH_IMAGE + "overheat.gif")), 0, 0, false)
    {
        private static final int WAIT_CYCLE = 1;
        private int cycle = 0;
        @Override
        public void update()
        {
            if (cycle == 0) {
                addIndex(1);
            }
            if(getIndex() >= getFrameCount()-1) {
                setDisposed(true);
            }
            ++cycle;
            if (cycle >= WAIT_CYCLE) cycle = 0;
        }
    };

    private final BufferedImage back_normal = GameConfig.readImage("back02.jpg");
    private final BufferedImage back_fever  = GameConfig.readImage("back02-red.jpg");

    public ShootingScene(final String filePath)
    {
        super();
        this.setBackImage(back_normal);
        this.stopWatch = new StopWatch();
        this.scoreManager = new ScoreManager()
        {
            @Override
            public void intoFeverMode()
            {
                setState(State.INTO_FEVER);
                stop();
            }

            @Override
            public void intoNormalMode()
            {
                setBackImage(back_normal);
            }
        };
        this.xmlStageParser = new XMLStageParser(GameConfig.getResourceAsStream(filePath));

        this.xmlStageParser.parse();
        this.setNowStage(xmlStageParser.getParsedStage());
        this.readNextStage();
        this.setState(State.WAIT_SHOOTING);
    }

    @Override
    public void initialize(Game game)
    {
        this.nowStage.initialize();
        this.setState(State.WAIT_SHOOTING);
        this.stopWatch.initTimer(TIME_LIMIT);
        this.fireFrameAnim.setY(120);
    }

    @Override
    public void finish(Game game)
    {
    }

    @Override
    public void update(final Game game, SceneManager.SceneChanger sceneChanger)
    {
        if (nowStage.getState() == BaseStage.State.FINISHED)
        {
            changeStage();
        }
//        else {
//            if (game.getEventManager().isKeyPressed(KeyEvent.VK_SPACE)) {
//                stop();
//            } else {
//                restart();
//            }
//        }

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
                break;

            case INTO_FEVER:
                if (intoOverHeatAnim.isDisposed()) {
                    intoOverHeatAnim.setIndex(0);
                    intoOverHeatAnim.setDisposed(false);
                    setState(State.SHOOTING);
                    restart();
                } else {
                    intoOverHeatAnim.update();
                    if (intoOverHeatAnim.getIndex() == 16) {
                        setBackImage(back_fever);
                    }
                }
                break;
        }
        this.scoreManager.update(game, this);

        if (scoreManager.isFever()) {
            fireFrameAnim.update();
        }
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);

        if (scoreManager.isFever()) {
            fireFrameAnim.draw(g2d);
        }

        this.nowStage.draw(g2d);

        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 70));
        g2d.setColor(Color.GREEN);
        int t = this.stopWatch.getRemainTime();
        if (t  < 0) t = 0;
        g2d.drawString("Time: " + t/1000 + "." + t%1000 / 100, GameConfig.VIRTUAL_WIDTH - 500, 80);

        scoreManager.draw(g2d);
        if (getState() == State.INTO_FEVER) {
            intoOverHeatAnim.draw(g2d);
        }
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
                xmlStageParser.setInputStream(GameConfig.getResourceAsStream(filePath));
                xmlStageParser.parse();
            }
        });

        readNextStageThread.start();
    }

    private void stop()
    {
        nowStage.setState(BaseStage.State.WAITING);
        this.stopWatch.stopTimer();
    }

    private void restart()
    {
        nowStage.setState(BaseStage.State.SHOOTING);
        this.stopWatch.restartTimer();
    }
}
