package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.ScoreManager;
import syoribuShooting.XMLStageParser;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.StopWatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class ShootingScene extends AbstractScene
{
    private static final int TIME_LIMIT = 40 * 1000;
    private final StopWatch stopWatch;
    private BaseStage stage;
    private State state;
    private ScoreManager scoreManager;

    public ShootingScene(BaseStage stage)
    {
        super(stage.getBackImage());
        this.stopWatch = new StopWatch();
        this.scoreManager = new ScoreManager();
        this.setStage(stage);
        this.setState(State.WAIT_SHOOTING);

        this.initialize();
    }

    @Override
    public void initialize()
    {
        this.stage.initialize();
        this.setState(State.WAIT_SHOOTING);
        this.stopWatch.initTimer(TIME_LIMIT);
    }

    @Override
    public void update(final Game game)
    {
        if (stage.getState() == BaseStage.State.FINISHED)
        {
            changeStage();
        }
//        else {
//            if (game.getEventManager().isKeyPressed(KeyEvent.VK_SPACE)) {
//                stage.setState(BaseStage.State.WAITING);
//                this.stopWatch.stopTimer();
//            } else {
//                stage.setState(BaseStage.State.SHOOTING);
//                this.stopWatch.restartTimer();
//            }
//        }
        System.out.println("Scene: " + getState()
                + ", stage: " + stage.getState()
                + ", elem=" + stage.getTargetList().size()
                + ", run=" + stage.getStopWatch().isRunning());
        switch (this.getState()) {
            case WAIT_SHOOTING:
                this.stopWatch.startTimer();
                this.setState(State.SHOOTING);
                this.stage.setState(BaseStage.State.SHOOTING);
                break;
            case SHOOTING:
                if (this.stopWatch.isOverTimeLimit())
                {
                    if (this.stage.noTargets()) {
                        this.setState(State.TIME_OVER);
                    } else {
                        stage.makeAllDisappear();
                    }
                }
                this.stage.update(game);
                break;
        }
        scoreManager.update(game, this.getStage());
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
        this.stage.draw(g2d);

        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 70));
        g2d.setColor(Color.GREEN);
        int t = this.stopWatch.getRemainTime();
        if (t  < 0) t = 0;
        g2d.drawString("Time: " + t/1000 + "." + t%1000 / 100, GameConfig.WINDOW_WIDTH - 500, 80);

        scoreManager.draw(g2d);
    }

    public void changeStage()
    {
        String fileName = GameConfig.getStageDataFileName((this.stage.STATE_ID + 2) % 2 + 1);
        System.out.println(fileName);
        XMLStageParser stageParser = new XMLStageParser(Game.class.getResourceAsStream(fileName));

        BaseStage nextStage = stageParser.getParsedStage();
        nextStage.initialize();
        nextStage.setState(BaseStage.State.SHOOTING);
        setStage(nextStage);
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public Target getHitTarget()
    {
        return this.stage.getHitTarget();
    }

    public BaseStage getStage()
    {
        return stage;
    }

    public void setStage(BaseStage stage)
    {
        this.stage = stage;
    }

}
