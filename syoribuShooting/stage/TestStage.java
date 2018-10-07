package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.TargetType;
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
    protected void _init()
    {
        Target target = TargetFactory.createTarget(TargetType.rankA, 600, 300);
        this.getTargetList().add(target);
    }

    @Override
    protected void _update(Game game)
    {
    }

}
