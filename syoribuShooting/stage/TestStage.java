package syoribuShooting.stage;

import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.sprite.Target;

public class TestStage extends BaseStage
{
    public TestStage()
    {
        super(GameConfig.img_back[1], 0);
    }

    @Override
    public int getTimeLimit()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean shouldBeFinished()
    {
        return false;
    }

    @Override
    public void initialize()
    {
        Target target = TargetFactory.createTarget(TargetFactory.TargetType.rankA, 600, 300);
        this.getTargetList().add(target);
    }

}
