package syoribuShooting;

import syoribuShooting.stage.AbstractScene;
import syoribuShooting.stage.RandomStage1;
import syoribuShooting.stage.ShootingScene;
import syoribuShooting.system.FPSTimer;
import syoribuShooting.system.GameWindow;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Cursor;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private final EffectManager effectManager;
    private AbstractScene nowScene;

    private void update()
    {
        nowScene.update(this);
        effectManager.update(this);
    }

    private void draw(Graphics2D g2d)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        nowScene.draw(g2d);
        effectManager.draw(g2d);
    }

    private void initialize()
    {
    }

    private void checkCloseTrigger()
    {
        if (this.eventManager.isKeyPressed(KeyEvent.VK_ESCAPE) && this.eventManager.isKeyPressed(KeyEvent.VK_SHIFT))
        {
            this.stop();
            System.exit(0);
        }
    }

    // ::::::: 変更禁止 :::::::
    // FPS毎にこの関数が呼び出される
    @Override
    public void callBack()
    {
        checkCloseTrigger();

        this.update();
        eventManager.update();

        Graphics2D g2d = this.window.getCanvas().getRenderer();
        this.draw(g2d);
        g2d.dispose();
        this.window.getCanvas().showBuffer();
    }

    public InputEventManager getEventManager()
    {
        return this.eventManager;
    }

    public void setNowScene(final AbstractScene scene)
    {
        this.nowScene = scene;
    }

    public AbstractScene getNowScene()
    {
        return this.nowScene;
    }

    public EffectManager getEffectManager()
    {
        return this.effectManager;
    }

    public void setCursor(final Cursor cursor)
    {
        this.window.getCanvas().setCursor(cursor);
    }

    public Game()
    {
        super(GameConfig.FPS);
        this.window = new GameWindow( GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, GameConfig.isFullScreen);
        this.window.setTitle("Syoribu-Shooting");
        this.eventManager = window.getEventManager();
//        this.nowScene = new ShootingScene(new RandomStage1());
        this.nowScene = new ShootingScene(new XMLStageParser(Game.class.getResourceAsStream(GameConfig.getStageDataFileName(0))).getParsedStage());
        this.effectManager = new EffectManager();

        this.initialize();
        this.start();
    }
}
