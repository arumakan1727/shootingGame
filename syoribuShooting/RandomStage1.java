package syoribuShooting;

import java.awt.Graphics2D;

public class RandomStage1 extends GameStage
{
    private static final int NUM_TARGETS = 8;

    public RandomStage1(final TargetManager manager)
    {
        super(manager, GameConfig.img_back01);
        this.storeTargets(NUM_TARGETS);
    }

    @Override
    public void update(final Game game)
    {
        if (this.targetManager.size() < 7) {
            storeTargets((int)(Math.random() * 6) + 2);
        }
        this.targetManager.update(game);
    }

    @Override
    public void draw(final Graphics2D g2d)
    {
        g2d.drawImage(this.getBackImage(), 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
        this.targetManager.draw(g2d);
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
