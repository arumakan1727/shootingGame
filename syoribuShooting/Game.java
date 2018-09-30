package syoribuShooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private int x;
    private GameStage nowStage;
    
    private TargetManager targetManager;

    private void update()
    {
        
        x += 3;
        if (x > GameConfig.WINDOW_WIDTH)
        {
            x = 0;
        }
        
        nowStage.update(this);

    }

    private void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(x, 5, 100, 20);
        
        nowStage.draw(g2d);
    }
    
    private void initialize()
    {
//        target = new Target(GameConfig.img_target, 800, 400);
//        color = Color.RED;
    }
    
    private void checkCloseTrigger()
    {
        if (this.eventManager.keyPressed(KeyEvent.VK_ESCAPE) && this.eventManager.keyPressed(KeyEvent.VK_SHIFT))
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
        this.eventManager.update();

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
