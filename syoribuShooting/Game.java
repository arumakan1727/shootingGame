package syoribuShooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private final GameConfig config;
	private int x;

	void update()
	{
	    if (this.eventManager.keyPressed(KeyEvent.VK_ESCAPE))
	    {
	        this.stop();
	        System.exit(0);
	    }
	    x += 5;
	    if (x > config.WINDOW_WIDTH)
	    {
	        x = 0;
	    }
	}
	
	void draw(Graphics2D g2d)
	{
	    g2d.setColor(Color.BLACK);
	    g2d.fillRect(0, 0, config.WINDOW_WIDTH, config.WINDOW_HEIGHT);
	    g2d.setColor(Color.cyan);
	    g2d.fillRect(x, 5, 100, 20);
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
	
	public GameConfig getGameConfig()
	{
	    return this.config;
	}
	
	public Game(GameConfig config)
	{
	    super(config.FPS);
	    this.window = new GameWindow( config.WINDOW_WIDTH, config.WINDOW_HEIGHT, config.isFullScreen);
	    this.config = config;
	    this.eventManager = window.getEventManager();
        this.start();
	}
}
