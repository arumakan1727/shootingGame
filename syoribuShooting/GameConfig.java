package syoribuShooting;

import java.awt.Toolkit;

public final class GameConfig
{
    
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;
    public final int FPS;
    public final boolean isFullScreen;

    public static final GameConfig getInstance()
    {
        return singleton;
    }

    private GameConfig()
    {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        WINDOW_WIDTH = (int)(toolkit.getScreenSize().getWidth());
        WINDOW_HEIGHT = (int)(toolkit.getScreenSize().getHeight());
        FPS = 30;
        isFullScreen = true;
    }

    private static final GameConfig singleton = new GameConfig();
}
