package syoribuShooting.system;

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
    private int mouseReleasedX, mouseReleasedY;
    private int mousePressedX, mousePressedY;
    private final boolean[] keyState    = new boolean[525];
    private final boolean[] mousePressedState = new boolean[4];
    private final boolean[] mouseReleasedState = new boolean[4];

    public InputEventManager(Component component)
    {
        this.component = component;
        this.component.addKeyListener(this);
        this.component.addMouseListener(this);
        this.component.addMouseMotionListener(this);
    }

    public void update()
    {
        mouseReleasedState[0] = false;
        mouseReleasedState[1] = false;
        mouseReleasedState[2] = false;
        mouseReleasedState[3] = false;
        
        mousePressedState[0] = false;
        mousePressedState[1] = false;
        mousePressedState[2] = false;
        mousePressedState[3] = false;

    }

    public int mouseX()
    {
        return mouseX;
    }



    public int mouseY()
    {
        return mouseY;
    }

    public int mouseReleasedX()
    {
        return this.mouseReleasedX;
    }
    
    public int mouseReleasedY()
    {
        return this.mouseReleasedY;
    }
    
    public int mousePressedX()
    {
        return this.mousePressedX;
    }
    
    public int mousePressedY()
    {
        return this.mousePressedY;
    }

    public boolean isKeyPressed(int keyCode)
    {
        return keyState[keyCode];
    }


    public boolean isMousePressed(int mouseButtonCode)
    {
        return mousePressedState[mouseButtonCode];
    }

    public boolean isMouseReleased(int mouseButtonCode)
    {
        return this.mouseReleasedState[mouseButtonCode];
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keyState[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyState[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        this.mouseReleasedState[e.getButton()] = true;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        final int code = e.getButton();
        mousePressedState[code] = true;
        mouseReleasedState[code] = false;
        this.mousePressedX = e.getX();
        this.mousePressedY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        final int code = e.getButton();
        mouseReleasedState[code] = true;
        mousePressedState[code] = false;
        this.mouseReleasedX = e.getX();
        this.mouseReleasedY = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e)
    {
       this.mouseX = e.getX();
       this.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

}
