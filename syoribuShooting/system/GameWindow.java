package syoribuShooting.system;

import syoribuShooting.InputEventManager;
import syoribuShooting.system.BufferedCanvas;

import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GameWindow
{
    private final JFrame window;
    private final BufferedCanvas canvas;
    private final InputEventManager eventManager;

    public GameWindow(int width, int height, boolean isFullScreen)
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.window = new JFrame(gd.getDefaultConfiguration());
        this.canvas = new BufferedCanvas(width, height);
        this.eventManager = new InputEventManager(canvas);

        this.window.getContentPane().add(this.canvas);  // canvasを追加

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
            this.window.pack();                       // canvasの大きさにあわせる
        }

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);

        this.canvas.setBuffering(2);
        this.canvas.requestFocus();
    }

    public JFrame getWindow()
    {
        return window;
    }

    public BufferedCanvas getCanvas()
    {
        return canvas;
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
