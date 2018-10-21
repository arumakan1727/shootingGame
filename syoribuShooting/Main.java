package syoribuShooting;

import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;

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
        game = new Game();
    }

}
