package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.TargetManager;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.sprite.LinearMotion;
import syoribuShooting.sprite.MoveTarget;
import syoribuShooting.system.Utils;

public class RandomStage1 extends GameStage
{
    private static final int NUM_TARGETS = 6;
    private static final int TIME_LIMIT = 15 * 1000;    // x1000でミリ指定

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
            int centerX = Utils.nextInt(1000, GameConfig.WINDOW_WIDTH - 100);
            int centerY = Utils.nextInt(300, GameConfig.WINDOW_HEIGHT - 100);
            MoveTarget target = TargetFactory.createTarget(
                    TargetFactory.TargetType.getTypeByID((Utils.nextInt(0, 2))),
                    centerX,
                    centerY);
            target.setMotion(new LinearMotion(target, 5, Utils.nextInt(20, 500), Utils.nextInt(50, GameConfig.WINDOW_HEIGHT - 50)));
            target.getMotion().setAcceleration(0.05);
            this.targetManager.add(target);
        }
    }
}
