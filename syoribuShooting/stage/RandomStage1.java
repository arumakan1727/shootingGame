package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.StaticTarget;
import syoribuShooting.GameConfig;
import syoribuShooting.sprite.TargetFactory;
import syoribuShooting.system.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class RandomStage1 extends GameStage
{
    private static final int NUM_TARGETS = 6;
    private static final int TIME_LIMIT = 10 * 1000;    // x1000でミリ指定

    public RandomStage1(final TargetManager manager)
    {
        super(manager, GameConfig.img_back01);
    }

    @Override
    public void update(final Game game)
    {
        switch (this.getState())
        {
            case INITIAL_WAITING:
                this.stopWatch.startTimer();
                this.setState(State.SHOOTING);
                break;
            case SHOOTING:
                if (this.stopWatch.isOverTimeLimit())
                {
                    this.targetManager.getTargets().clear();
                    this.stopWatch.stopTimer();
                    this.setState(State.TIME_OVER);
                }
                else
                {
                    if (this.targetManager.size() < 5) {
                        storeTargets((int) (Math.random() * 3) + 1);
                    }
                    this.targetManager.update(game);
                }
                break;
        }
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 70));
        g2d.setColor(Color.GREEN);
        g2d.drawString("Time: " + this.stopWatch.getRemainTimeSec(), GameConfig.WINDOW_WIDTH - 500, 80);
        this.targetManager.draw(g2d);
    }

    @Override
    public int getTimeLimitMillis()
    {
        return TIME_LIMIT;
    }

    @Override
    public void initialize()
    {
        this.storeTargets(NUM_TARGETS);
        this.setState(State.INITIAL_WAITING);
        this.stopWatch.initTimer(getTimeLimitMillis());
    }

    private void storeTargets(int num)
    {
        for (int i = 0; i < num; ++i)
        {
            int centerX = Utils.nextInt(100, GameConfig.WINDOW_WIDTH - 100);
            int centerY = Utils.nextInt(100, GameConfig.WINDOW_HEIGHT - 100);
//            this.targetManager.add(new StaticTarget(GameConfig.img_targetA, centerX, centerY));
            this.targetManager.add(TargetFactory.createTarget(
                    TargetFactory.TargetType.getTypeByID((Utils.nextInt(0, 3))),
                    centerX,
                    centerY));
        }
    }
}
