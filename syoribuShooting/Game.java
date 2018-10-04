package syoribuShooting;

import syoribuShooting.stage.GameStage;
import syoribuShooting.stage.RandomStage1;
import syoribuShooting.stage.TestStage;
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
    private final Player player;
    private final FeverManager feverManager;
    private final TargetManager targetManager;
    private final EffectManager effectManager;
    private GameStage nowStage;

    private void update()
    {
        nowStage.update(this);
        player.update(this);
        feverManager.update(this);
        effectManager.update(this);
    }

    private void draw(Graphics2D g2d)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        nowStage.draw(g2d);
        effectManager.draw(g2d);
        player.draw(g2d);
        feverManager.draw(g2d);
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
    public Player getPlayer()
    {
        return this.player;
    }
    public void setNowStage(final GameStage stage)
    {
        this.nowStage = stage;
    }
    public GameStage getNowStage()
    {
        return this.nowStage;
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
        this.targetManager = new TargetManager(this.eventManager);
        this.nowStage = new TestStage(this.targetManager);
        this.player = Player.getInstance();
        this.feverManager = new FeverManager();
        this.effectManager = new EffectManager();

        this.initialize();
        this.start();
    }
}
