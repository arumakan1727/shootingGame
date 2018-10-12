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

    private double correction;

    public InputEventManager(Component component)
    {
        this(component, 1.0);
    }

    public InputEventManager(Component component, double correction)
    {
        this.component = component;
        this.correction = correction;
        this.component.addKeyListener(this);
        this.component.addMouseListener(this);
        this.component.addMouseMotionListener(this);
    }

    public double getCorrection()
    {
        return correction;
    }

    public void setCorrection(double correction)
    {
        this.correction = correction;
    }

    public void update()
    {
//        mousePressedState[0] = false;
//        mousePressedState[1] = false;
//        mousePressedState[2] = false;
//        mousePressedState[3] = false;
    }

    public int mouseX()
    {
        return ((int) (mouseX * correction));
    }

    public int mouseY()
    {
        return ((int) (mouseY * correction));
    }

    public int mouseReleasedX()
    {
        return ((int) (this.mouseReleasedX * correction));
    }
    
    public int mouseReleasedY()
    {
        return ((int) (this.mouseReleasedY * correction));
    }
    
    public int mousePressedX()
    {
        return ((int) (this.mousePressedX * correction));
    }
    
    public int mousePressedY()
    {
        return ((int) (this.mousePressedY * correction));
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
        return ! isMousePressed(mouseButtonCode);
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
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        final int code = e.getButton();
        mousePressedState[code] = true;
        this.mousePressedX = e.getX();
        this.mousePressedY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        final int code = e.getButton();
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
