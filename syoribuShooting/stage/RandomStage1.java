package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.sprite.LinearMotion;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.Utils;

import static syoribuShooting.GameConfig.*;

public class RandomStage1 extends BaseStage
{
    private static final int NUM_TARGETS = 6;


    public RandomStage1()
    {
        super(GameConfig.img_back[1], 1);
    }

    @Override
    public void update(Game game)
    {
        super.update(game);
        if (getState() == State.SHOOTING && getTargetList().size() < NUM_TARGETS - 2)
        {
            this.storeTargets(Utils.nextInt(1, 2));
        }
    }

    @Override
    public int getTimeLimit()
    {
        return 3 * 1000;
    }

    @Override
    public boolean shouldBeFinished()
    {
        return this.stopWatch.getElapsed() >= getTimeLimit();
    }

    @Override
    public void initialize()
    {
        this.storeTargets(NUM_TARGETS);
    }

    private void storeTargets(int num)
    {
        for (int i = 0; i < num; ++i)
        {
            int centerX = randomX(Allocation.LEFT);
            int centerY = randomY(Allocation.LEFT);
            Target target = TargetFactory.createTarget(
                    TargetFactory.TargetType.getTypeByID((Utils.nextInt(0, 2))),
                    centerX,
                    centerY);
            target.setMotion(new LinearMotion(target, 2.0, randomX(Allocation.RIGHT), randomY(Allocation.RIGHT)));
            target.setZoomDelay(500 + i*150);
            target.getMotion().setAcceleration(0.08);
            this.getTargetList().add(target);
        }
    }
}
