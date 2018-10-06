package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.BaseScene;
import syoribuShooting.sprite.Target;

public class TestStage extends AbstractStage
{
    public TestStage(BaseScene manager)
    {
        super(manager, GameConfig.img_back01);
}

    @Override
    public void initialize()
    {
        this.baseScene.initialize();
        this.setState(State.INITIAL_WAITING);
        this.stopWatch.initTimer(getTimeLimitMillis());

        Target target = TargetFactory.createTarget(TargetFactory.TargetType.rankA, 600, 300);
        //target.setMotion(new LinearMotion(target, 10, 600, 300, 605, 302));
        this.baseScene.add(target);
    }

    @Override
    public void update(Game game)
    {
        switch (this.getState())
        {
            case INITIAL_WAITING:
                this.stopWatch.startTimer();
                this.setState(State.SHOOTING);
                break;
            case SHOOTING:
                this.baseScene.update(game);
                break;
        }

    }

    @Override
    public int getTimeLimitMillis()
    {
        return 30000;
    }
}
