package syoribuShooting;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputEventManager
    implements KeyListener, MouseListener, MouseMotionListener
{
    private final Component component;
    private int mouseX, mouseY;
    private final boolean[] keyState    = new boolean[525];
    private final boolean[] mouseState  = new boolean[4];
    private final boolean[] mouseClickedState = new boolean[4];
    
    public void update()
    {
        this.mouseClickedState[0] =
                mouseClickedState[1] =
                mouseClickedState[2] =
                mouseClickedState[3] = false;
    }
    
    public InputEventManager(Component component)
    {
        this.component = component;
        this.component.addKeyListener(this);
        this.component.addMouseListener(this);
        this.component.addMouseMotionListener(this);
    }

    public int mouseX()
    {
        return mouseX;
    }



    public int mouseY()
    {
        return mouseY;
    }



    public boolean keyPressed(int keyCode)
    {
        return keyState[keyCode];
    }



    public boolean mousePressed(int mouseButtonCode)
    {
        return mouseState[mouseButtonCode];
    }


    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keyState[e.getKeyCode()] = true;
//        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyState[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        this.mouseClickedState[e.getButton()] = true;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseState[e.getButton()] = true;
//        System.out.println(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseState[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    public boolean mouseClicked(int mouseButtonCode)
    {
        return this.mouseClickedState[mouseButtonCode];
    }
}
