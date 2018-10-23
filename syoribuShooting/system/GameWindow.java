package syoribuShooting.system;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GameWindow
{
    private final JFrame window;
    private final ScreenBuffer canvas;
    private final InputEventManager eventManager;
    private final CursorManager cursorManager;

    public GameWindow(ScreenBuffer renderer, boolean isFullScreen)
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.window = new JFrame(gd.getDefaultConfiguration());
        this.canvas = renderer;
        this.eventManager = new InputEventManager((JPanel)canvas);
        this.cursorManager = new CursorManager(this.getPane());

        this.window.getContentPane().add((JPanel)this.canvas);  // canvasを追加

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

    public ScreenBuffer getCanvas()
    {
        return canvas;
    }
    
    public JPanel getPane()
    {
        return (JPanel) canvas;
    }

    public InputEventManager getEventManager()
    {
        return eventManager;
    }
    
    public void setTitle(String title)
    {
        this.window.setTitle(title);
    }
    
    public CursorManager getCursorManager()
    {
        return this.cursorManager;
    }
}
