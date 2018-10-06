package syoribuShooting.stage;

import syoribuShooting.Game;
import syoribuShooting.GameConfig;
import syoribuShooting.TargetFactory;
import syoribuShooting.sprite.LinearMotion;
import syoribuShooting.sprite.Target;
import syoribuShooting.system.Utils;

import java.awt.image.BufferedImage;

public class RandomStage1 extends AbstractStage
{
    private static final int NUM_TARGETS = 6;


    public RandomStage1()
    {
        super(GameConfig.img_back01);
    }

    @Override
    public void update(Game game)
    {
        super.update(game);
        if (getTargets().size() < NUM_TARGETS - 2)
        {
            this.storeTargets(Utils.nextInt(1, 2));
        }
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
            int centerX = Utils.nextInt(1000, GameConfig.WINDOW_WIDTH - 100);
            int centerY = Utils.nextInt(300, GameConfig.WINDOW_HEIGHT - 100);
            Target target = TargetFactory.createTarget(
                    TargetFactory.TargetType.getTypeByID((Utils.nextInt(0, 2))),
                    centerX,
                    centerY);
            target.setMotion(new LinearMotion(target, 5, Utils.nextInt(20, 500), Utils.nextInt(50, GameConfig.WINDOW_HEIGHT - 50)));
            target.getMotion().setAcceleration(0.05);
            this.getTargets().add(target);
        }
    }
}
