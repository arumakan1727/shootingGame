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
    private Target target;

    void update()
    {
        if (this.eventManager.keyPressed(KeyEvent.VK_ESCAPE))
        {
            this.stop();
            System.exit(0);
        }
        x += 3;
        if (x > GameConfig.WINDOW_WIDTH)
        {
            x = 0;
        }
        
        target.update(this);

        if (eventManager.mousePressed(MouseEvent.BUTTON1)) {
            if (target.getBounds().isContain(eventManager.mouseX(), eventManager.mouseY()))
            {
                x = 0;
            }
        }
    }

    void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        g2d.setColor(Color.cyan);
        g2d.fillRect(x, 5, 100, 20);
        
        target.draw(g2d);
    }
    
    void initialize()
    {
        target = new Target(GameConfig.img_target, 800, 400);
    }

    // ::::::: 変更禁止 :::::::
    // FPS毎にこの関数が呼び出される
    @Override
        public void callBack()
        {
            this.update();
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
        this.initialize();
        this.start();
    }
}
