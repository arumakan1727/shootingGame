package syoribuShooting;

import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.system.BufferedJPanel;
import syoribuShooting.system.BufferedRenderer;
import syoribuShooting.system.BufferedResponsivePanel;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.FPSTimer;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import static syoribuShooting.GameConfig.*;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private final AnimationProcessor animationProcessor;
    private AbstractScene nowScene;

    private void update()
    {
        nowScene.update(this);
        animationProcessor.update();
    }

    private void draw(Graphics2D g2d)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        nowScene.draw(g2d);
        animationProcessor.draw(g2d);
        window.getCursorManager().draw(g2d, eventManager.mouseX(), eventManager.mouseY());
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
        this.window.getCanvas().flipBuffer();
    }
    
    public GameWindow getWindow()
    {
        return this.window;
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

    public AnimationProcessor getAnimationProcessor()
    {
        return this.animationProcessor;
    }

    public Game()
    {
        super(GameConfig.FPS);

        // 描画に用いるインスタンスの選択
        BufferedRenderer bufferedRenderer;
        bufferedRenderer = new BufferedResponsivePanel(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, REAL_WIDTH, REAL_HEIGHT);
//        bufferedRenderer = new BufferedJPanel(VIRTUAL_WIDTH, VIRTUAL_WIDTH);
        this.window = new GameWindow(bufferedRenderer ,GameConfig.isFullScreen);
        this.window.getEventManager().setCorrection(GameConfig.REAL_VIRTUAL_CORRECTION);
        
        this.window.setTitle("Syoribu-Shooting");
        this.eventManager = window.getEventManager();
        this.nowScene = new ShootingScene(GameConfig.FIRST_STAGE_FILE_PATH, Game.class);
        this.animationProcessor = new AnimationProcessor();
        
        // カスタムカーソルの設定
        CursorManager cursorManager = this.window.getCursorManager();
        cursorManager.defineCursor(ID_SHOOTING_CURSOR_NORMAL, readImage("shooting_cursor.png"), new Point(32, 32));
        cursorManager.defineCursor(ID_SHOOTING_CURSOR_GREEN, readImage("shooting_cursor_green.png"), new Point(32, 32));
        cursorManager.defineCursor(Cursor.CROSSHAIR_CURSOR, new Cursor(Cursor.CROSSHAIR_CURSOR));
        cursorManager.changeCurrentCursor(Cursor.CROSSHAIR_CURSOR);
        
        System.out.println("normalCursor: " + cursorManager.validCursor(ID_SHOOTING_CURSOR_NORMAL) + "\ngreenCursor: " + cursorManager.validCursor(ID_SHOOTING_CURSOR_GREEN));
        this.initialize();
        this.start();
        this.window.getPane().requestFocus();
    }
}
