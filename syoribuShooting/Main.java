package syoribuShooting;

import org.jetbrains.annotations.NotNull;
import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.system.BufferedResponsivePanel;
import syoribuShooting.system.BufferedVolatilePanel;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.MP3Player;
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
     * Creates instance of {@link Game} with command line argument.
     * 画面描画バッファリングにはデフォルトで{@link java.awt.image.VolatileImage}を使用する。これはコマンドライン引数で設定可能。
     * @param args コマンドライン引数。以下のパラメータを渡すことができます。
     *             <ul>
     *             <li>--buff=another - 画面描画のバッファリングに{@link java.awt.image.BufferedImage}を使用する。この指定をすると描画が遅くなることが多い。</li>
     *             <li>--nosounds - 音声の再生を無効化する。</li>
     *             <li>--frame - ウィンドウのフレームを有効にする。</li>
     *             <li>--noframe - ウィンドウのフレームを無効化し、フルスクリーンで実行する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        ScreenBuffer buffer = null;
        for (int i = 0; i < args.length; ++i)
        {
            final String arg = args[i];
            switch (arg) {
                case "--buff=another":
                    System.out.println("Using Java-BufferedImage for buffering.");
                    buffer = new BufferedResponsivePanel(
                            VIRTUAL_WIDTH,
                            VIRTUAL_HEIGHT,
                            REAL_WIDTH,
                            REAL_HEIGHT);
                    break;
                    
                case "--nosounds":
                    MP3Player.setEnable(false);
                    System.out.println("Sound off");
                    break;

                case "--frame":
                    GameConfig.isFullScreen = false;
                    System.out.println("Frame enable");
                    break;

                case "--noframe":
                    GameConfig.isFullScreen = true;
                    System.out.println("Frame enable");
                    break;

                default:
                    System.out.println();
                    System.err.println("Argument `" + arg + "` is undefined.");
                    System.out.println("[Hint]  \'--buff=another\' - Using another buffering mode. ");
                    System.out.println("[Hint]  \'--nosounds\' - Sound off.");
                    System.out.println("[Hint]  \'--frame\' - Window frame enable.");
                    System.exit(0);
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
