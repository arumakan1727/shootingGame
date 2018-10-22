package syoribuShooting;

import syoribuShooting.sprite.Animation;
import syoribuShooting.sprite.HitEffect1;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.Function;
import syoribuShooting.system.GifReader;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.*;

enum State
{
    WAIT_SHOOTING,
    SHOOTING,
    INTO_FEVER,
    TIME_OVER,
}

public class ShootingScene extends AbstractScene implements TargetEventListener
{
    private static final int TIME_LIMIT = 10 * 1000;
    private final StopWatch stopWatch;
    private State state;
    private ScoreManager scoreManager;
    private TargetManager targetManager;
    private StageManager stageManager;

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

    ShootingScene(final String filePath)
    {
        super();
        this.setBackImage(back_normal);
        this.stopWatch = new StopWatch();
        this.targetManager = new TargetManager(this);
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
        this.stageManager = new StageManager();
        stageManager.readStageWithThread(getResourceAsStream(FIRST_STAGE_FILE_PATH));

        this.setState(State.WAIT_SHOOTING);
    }

    @Override
    public void initialize(Game game)
    {
        this.setState(State.WAIT_SHOOTING);
        this.stopWatch.initTimer(TIME_LIMIT);
        this.fireFrameAnim.setY(120);
        Main.getCursorManager().changeCurrentCursor(ID_SHOOTING_CURSOR_NORMAL);
    }

    @Override
    public void finish(Game game)
    {
    }

    @Override
    public void update(final Game game, SceneManager.SceneChanger sceneChanger)
    {
        switch (this.getState()) {
            case WAIT_SHOOTING:
                this.setState(State.SHOOTING);
                this.stopWatch.startTimer();
                changeStage();
                break;

            case SHOOTING:
//                if (targetManager.isEmpty(TargetManager.LOCAL_LIST) ||
//                        stageManager.getStageElapsedTime(stopWatch.getElapsed()) > stageManager.getNowStage().getTimeLimit())
//                {
//                    changeStage();
//                }
                updateShooting();
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
        this.scoreManager.update();

        if (scoreManager.isFever()) {
            fireFrameAnim.update();
        }
    }

    private void updateShooting()
    {
        if (this.stopWatch.isOverTimeLimit())
        {
            if (targetManager.isEmpty()) {
                this.setState(State.TIME_OVER);
            } else {
                targetManager.setAllState(Target.State.DISAPPEAR);
            }
        }
        if (mustFinishStage()) {
            targetManager.forEach(TargetManager.LOCAL_LIST, new Function<Target>()
            {
                @Override
                public void task(Target obj) { obj.setState(Target.State.DISAPPEAR); }
            });
            if (targetManager.isEmpty(TargetManager.LOCAL_LIST)) {
                changeStage();
            }
        }
        targetManager.update(stageManager.getStageElapsedTime(stopWatch.getElapsed()));
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);

        if (scoreManager.isFever()) {
            fireFrameAnim.draw(g2d);
        }

        targetManager.draw(g2d);
        
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

    private void changeStage()
    {
        System.err.println("change Stage");
        final BaseStage stage = stageManager.getReadStage();

        stageManager.changeStage(stopWatch.getElapsed(), stage);
        targetManager.setLocalList(stage.getLocalTargetList());
        targetManager.getGlobalList().addAll(stage.getGlobalTargetList());
        stageManager.readStageWithThread(
                getResourceAsStream(stage.getNextStageFilePath())
        );
    }

    private void stop()
    {
        this.stopWatch.stopTimer();
    }

    private void restart()
    {
        this.stopWatch.restartTimer();
    }

    private boolean mustFinishStage()
    {
        return targetManager.isEmpty(TargetManager.LOCAL_LIST) ||
                getStageElapsedTime() >= stageManager.getNowStage().getTimeLimit();
    }

    private int getStageElapsedTime()
    {
        return stageManager.getStageElapsedTime(stopWatch.getElapsed());
    }

    @Override
    public void cursorEnteredTarget()
    {
        Main.getCursorManager().changeCurrentCursor(ID_SHOOTING_CURSOR_GREEN);
    }

    @Override
    public void cursorExitedTarget()
    {
        Main.getCursorManager().changeCurrentCursor(ID_SHOOTING_CURSOR_NORMAL);
    }

    @Override
    public void clickedTarget(TargetEvent e)
    {
        scoreManager.notifyHitTarget(e);
        final Target target = e.getTarget();
        if (target != null)
        {
            target.setState(Target.State.DISAPPEAR);
            Main.getAnimationProcessor().add(
                    new HitEffect1(e.getMouseX(), e.getMouseY(), false) );
        }
    }
}
