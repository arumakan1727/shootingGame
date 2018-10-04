package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.TargetManager;
import syoribuShooting.sprite.LinearMotion;
import syoribuShooting.sprite.MoveTarget;

public class TestStage extends GameStage
{
    public TestStage(TargetManager manager)
    {
        super(manager, GameConfig.img_back01);
}

    @Override
    public void initialize()
    {
        this.targetManager.initialize();
        this.setState(State.INITIAL_WAITING);
        this.stopWatch.initTimer(getTimeLimitMillis());

        MoveTarget target = TargetFactory.createTarget(TargetFactory.TargetType.rankA, 600, 300);
        //target.setMotion(new LinearMotion(target, 10, 600, 300, 605, 302));
        this.targetManager.add(target);
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
                this.targetManager.update(game);
                break;
        }

    }

    @Override
    public int getTimeLimitMillis()
    {
        return 30000;
    }
}
