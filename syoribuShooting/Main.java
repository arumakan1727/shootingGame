package syoribuShooting;

import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.system.BufferedResponsivePanel;
import syoribuShooting.system.BufferedVolatilePanel;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.ScreenBuffer;

import static syoribuShooting.GameConfig.*;

public class Main {

    private static Game game;

    static InputEventManager getEventManager()
    {
        return game.getEventManager();
    }

    static CursorManager getCursorManager()
    {
        return game.getWindow().getCursorManager();
    }

    static AnimationProcessor getAnimationProcessor()
    {
        return game.getAnimationProcessor();
    }

    static GameWindow getWindow()
    {
        return game.getWindow();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ScreenBuffer buffer = null;
        for (String arg : args) {
            if (arg.equals("--buff=another")) {
                System.out.println("Using Java-BufferedImage for buffering.");
                buffer = new BufferedResponsivePanel(
                        VIRTUAL_WIDTH,
                        VIRTUAL_HEIGHT,
                        REAL_WIDTH,
                        REAL_HEIGHT);
            }
            else {
                System.err.println("Argument `" + arg + "` is undefined.");
                System.out.println("[Hint]  \'--buff=another\' - Using another buffering mode. ");
            }
        }

        if (buffer == null) {
            System.out.println("Using VolatileImage for buffering.");
            buffer = new BufferedVolatilePanel(
                        VIRTUAL_WIDTH,
                        VIRTUAL_HEIGHT,
                        REAL_WIDTH,
                        REAL_HEIGHT);
        }
        
        game = new Game(buffer);
    }

}
