package syoribuShooting.stage;

import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.sprite.Target;

public class TestStage extends AbstractStage
{
    public TestStage()
    {
        super(GameConfig.img_back01);
    }

    @Override
    public void initialize()
    {
        Target target = TargetFactory.createTarget(TargetFactory.TargetType.rankA, 600, 300);
        //target.setMotion(new LinearMotion(target, 10, 600, 300, 605, 302));
        this.getTargets().add(target);
    }

}
