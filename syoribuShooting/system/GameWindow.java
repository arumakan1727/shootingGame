package syoribuShooting.system;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GameWindow
{
    private final JFrame window;
    private final BufferedRenderer canvas;
    private final InputEventManager eventManager;

    public GameWindow(BufferedRenderer renderer, boolean isFullScreen)
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.window = new JFrame(gd.getDefaultConfiguration());
        this.canvas = renderer;
        this.eventManager = new InputEventManager((Component)canvas);

        this.window.getContentPane().add((Component)this.canvas);  // canvasを追加

        if (isFullScreen)
        {
            try {
                this.window.setUndecorated(true);
                gd.setFullScreenWindow(this.window);
            } catch (Exception e) {
                e.printStackTrace();
                gd.setFullScreenWindow(null);
                System.exit(1);
            }
        }
        else
        {
            this.window.pack();   // canvasの大きさにあわせる
        }

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);

        this.canvas.setBuffering();
    }

    public JFrame getWindow()
    {
        return window;
    }

    public BufferedRenderer getCanvas()
    {
        return canvas;
    }
    
    public Component getPane()
    {
        return (Component)canvas;
    }

    public InputEventManager getEventManager()
    {
        return eventManager;
    }
    
    public void setTitle(String title)
    {
        this.window.setTitle(title);
    }
}
