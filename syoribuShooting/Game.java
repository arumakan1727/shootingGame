package syoribuShooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private GameStage nowStage;
    
    private TargetManager targetManager;

    private void update()
    {
        nowStage.update(this);
    }

    private void draw(Graphics2D g2d)
    {
        nowStage.draw(g2d);

        final boolean mousePressed = this.eventManager.isMousePressed(MouseEvent.BUTTON1);
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("isMousePressed: " + (mousePressed? "ON" : "OFF"), 30, 30);
    }
    
    private void initialize()
    {
        //this.window.getCanvas().setCursor(GameConfig.shootingCursor);
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


    public Game()
    {
        super(GameConfig.FPS);
        this.window = new GameWindow( GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, GameConfig.isFullScreen);
        this.window.setTitle("SyoribuShooting");
        this.eventManager = window.getEventManager();
        this.targetManager = new TargetManager(this.eventManager);
        this.nowStage = new RandomStage1(targetManager);
        this.initialize();
        this.start();
    }
}
