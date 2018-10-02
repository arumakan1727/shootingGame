package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.StaticTarget;
import syoribuShooting.GameConfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class RandomStage1 extends GameStage
{
    private static final int NUM_TARGETS = 8;
    private static final int TIME_LIMIT = 30 * 1000;    // 30秒 (x1000でミリ指定)

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
                    if (this.targetManager.size() < 7) {
                        storeTargets((int) (Math.random() * 6) + 2);
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
        g2d.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 30));
        g2d.setColor(Color.GREEN);
        g2d.drawString("Time: " + this.stopWatch.getElapsedSec(), GameConfig.WINDOW_WIDTH - 200, 40);
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
            double centerX = Math.random() * GameConfig.WINDOW_WIDTH;
            double centerY = Math.random() * GameConfig.WINDOW_HEIGHT;
            this.targetManager.add(new StaticTarget(GameConfig.img_target, centerX, centerY));
        }
    }
}
