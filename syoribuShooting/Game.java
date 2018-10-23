package syoribuShooting;

import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.sprite.HitEffect1;
import syoribuShooting.sprite.TargetFactory;
import syoribuShooting.sprite.TargetType;
import syoribuShooting.system.GifReader;
import syoribuShooting.system.ScreenBuffer;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.FPSTimer;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Cursor;

import static syoribuShooting.GameConfig.*;

public class Game extends FPSTimer
{
    private final GameWindow window;
    private final InputEventManager eventManager;
    private final AnimationProcessor animationProcessor;
    private final DrawTask drawTask = new DrawTask();
    private SceneManager sceneManager;

    private void update()
    {
        sceneManager.update(this);
        animationProcessor.update();
        eventManager.update();
    }

    private void initialize()
    {
        // カスタムカーソルの設定
        CursorManager cursorManager = this.window.getCursorManager();
        cursorManager.defineCursor(ID_SHOOTING_CURSOR_NORMAL, readImage("shooting_cursor.png"), new Point(32, 32));
        cursorManager.defineCursor(ID_SHOOTING_CURSOR_GREEN, readImage("shooting_cursor_green.png"), new Point(32, 32));
        cursorManager.defineCursor(Cursor.CROSSHAIR_CURSOR, new Cursor(Cursor.CROSSHAIR_CURSOR));
        cursorManager.defineCursor(Cursor.DEFAULT_CURSOR, new Cursor(Cursor.DEFAULT_CURSOR));
        cursorManager.defineCursor(Cursor.HAND_CURSOR, new Cursor(Cursor.HAND_CURSOR));
        {
            final BufferedImage clearImg = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = clearImg.createGraphics();
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0, 0, 16, 16);
            g.dispose();
            cursorManager.defineCursor(ID_CLEAR_CURSOR, clearImg, new Point(0,0));
        }
        System.out.println("normalCursor: " + cursorManager.validCursor(ID_SHOOTING_CURSOR_NORMAL) + "\ngreenCursor: " + cursorManager.validCursor(ID_SHOOTING_CURSOR_GREEN));

        TargetFactory.setTargetImage(TargetType.rankA.ID, img_targetA);
        TargetFactory.setTargetImage(TargetType.rankB.ID, img_targetB);
        TargetFactory.setTargetImage(TargetType.rankC.ID, img_targetC);
        TargetFactory.setTargetImage(TargetType.scoreUp.ID, img_scoreUP);
        TargetFactory.setTargetImage(TargetType.timeDecrease.ID, img_timeDecrease);

        HitEffect1.setAnimation(GifReader.readGif(getResourceAsStream(PATH_IMAGE + "hit-animation.gif")));
    }

    private void checkCloseTrigger()
    {
        if (this.eventManager.isKeyPressed(KeyEvent.VK_ESCAPE) && this.eventManager.isKeyPressed(KeyEvent.VK_SHIFT))
        {
            this.stop();
            System.exit(0);
        }
    }

    // ::::::: 変更禁止 :::::::
    // FPS毎にこの関数が呼び出される
    @Override
    public void callBack()
    {
        checkCloseTrigger();

        this.update();
        this.window.getCanvas().draw(drawTask);
    }
    
    GameWindow getWindow()
    {
        return this.window;
    }

    InputEventManager getEventManager()
    {
        return this.eventManager;
    }

    AnimationProcessor getAnimationProcessor()
    {
        return this.animationProcessor;
    }

    Game(ScreenBuffer screenBuffer)
    {
        super(GameConfig.FPS);

        // 描画に用いるインスタンスの選択
        this.window = new GameWindow(screenBuffer,GameConfig.isFullScreen);
        this.window.getEventManager().setCorrection(GameConfig.REAL_VIRTUAL_CORRECTION);
        
        this.window.setTitle("Syoribu-Shooting");
        this.eventManager = window.getEventManager();
        this.sceneManager = new SceneManager(new TitleScene());
        this.animationProcessor = new AnimationProcessor();
        this.initialize();
        this.start();
        this.window.getPane().requestFocus();

    }

    private class DrawTask implements ScreenBuffer.DrawTask
    {
        @Override
        public void draw(Graphics2D g2d)
        {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            sceneManager.draw(g2d);
            animationProcessor.draw(g2d);
            window.getCursorManager().draw(g2d, eventManager.mouseX(), eventManager.mouseY());
        }
    }
}

