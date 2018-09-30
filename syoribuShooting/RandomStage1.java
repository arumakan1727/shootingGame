package syoribuShooting;

import java.awt.Graphics2D;

public class RandomStage1 extends GameStage
{
    private static final int NUM_TARGETS = 4;

    public RandomStage1(final TargetManager manager)
    {
        super(manager, GameConfig.img_back01);
        this.storeTargets();
    }

    @Override
    public void update(final Game game)
    {
        this.targetManager.update(game);
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        this.targetManager.draw(g2d);
    }

    private void storeTargets()
    {
        for (int i = 0; i < NUM_TARGETS; ++i)
        {
            double centerX = Math.random() * GameConfig.WINDOW_WIDTH;
            double centerY = Math.random() * GameConfig.WINDOW_HEIGHT;
            this.targetManager.add(new StaticTarget(GameConfig.img_target, centerX, centerY));
        }
    }
}
