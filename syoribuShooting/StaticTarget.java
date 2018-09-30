package syoribuShooting;

import java.awt.image.BufferedImage;

public class StaticTarget extends Target
{
    private static final int SCORE = 100;

    @Override
    int getScore()
    {
        return SCORE;
    }

    public StaticTarget(BufferedImage img, double centerX, double centerY)
    {
        super(img, centerX, centerY);
    }

    @Override
    public void update(final Game game)
    {
        super.update(game);
        switch (this.getState()) {
        case FLY:
            break;

        default:
            break;
        }
    }
}
