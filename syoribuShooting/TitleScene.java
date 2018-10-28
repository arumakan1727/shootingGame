package syoribuShooting;

import syoribuShooting.sprite.ActionListener;
import syoribuShooting.sprite.Button;
import syoribuShooting.system.MP3Player;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.FIRST_STAGE_FILE_PATH;
import static syoribuShooting.GameConfig.readImage;
import static syoribuShooting.GameConfig.se_buzzer;

public class TitleScene extends AbstractScene implements ActionListener
{
    private static final BufferedImage backImage = GameConfig.readImage("title_logo.jpg");
    private static final int BTN_Y = 750;
    private static final BufferedImage img_normalBtn = readImage("play_button00.png");
    private static final BufferedImage img_hoverBtn = readImage("play_button01.png");

    private boolean changeSceneFlag = false;
    private Button btn_startPlay;
    private boolean visibleBtn = false;
    private long startTime;

    public TitleScene()
    {
        super();
        btn_startPlay = new Button(img_normalBtn);
        btn_startPlay.setCenterX(GameConfig.VIRTUAL_WIDTH / 2);
        btn_startPlay.setY(BTN_Y);
        btn_startPlay.setActionListener(this);
        setBackImage(backImage);
    }

    @Override
    public void initialize(Game game)
    {
        game.getWindow().getCursorManager().changeCurrentCursor(Cursor.DEFAULT_CURSOR);
        btn_startPlay.init(game.getWindow().getCursorManager());
        Runtime.getRuntime().gc();
        game.setFPS(25);
        startTime = System.currentTimeMillis();
//        btn_startPlay.setEnable(false);
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                try {
//                    Thread.sleep(10 * 1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Runtime.getRuntime().gc();
//                System.out.println("Garbage Collected");
//            }
//        }).start();
    }

    @Override
    public void finish(Game game)
    {

    }

    @Override
    public void update(Game game, SceneManager.SceneChanger sceneChanger)
    {
        if (changeSceneFlag) {
            sceneChanger.changeScene(new ShootingScene(FIRST_STAGE_FILE_PATH));
        }
        btn_startPlay.update(game.getEventManager());

        long elapsed = System.currentTimeMillis() - startTime;
        if (visibleBtn) {
            if (elapsed > 2000) {
                visibleBtn = false;
                startTime = System.currentTimeMillis();
            }
        } else {
            if (elapsed > 500) {
                visibleBtn = true;
                startTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);
        if (btn_startPlay.isMouseEnter() || visibleBtn) {
            btn_startPlay.draw(g2d);
        }
    }

    @Override
    public void mouseEntered()
    {
        btn_startPlay.setImage(img_hoverBtn);
    }

    @Override
    public void mouseExited()
    {
        btn_startPlay.setImage(img_normalBtn);
        setUnPushed();
    }

    @Override
    public void justNowPressed()
    {
        setPushed();
    }

    @Override
    public void justNowReleased()
    {
        setUnPushed();
        changeSceneFlag = true;
        new MP3Player(se_buzzer, false);
        btn_startPlay.setEnable(false);
    }

    private void setPushed()
    {
        int prevCX = (int)btn_startPlay.getCenterX();
        int prevCY = (int)btn_startPlay.getCenterY();
        btn_startPlay.setZoom(90);
        btn_startPlay.setCenterX(prevCX);
        btn_startPlay.setCenterY(prevCY);
    }

    private void setUnPushed()
    {
        int prevCX = (int)btn_startPlay.getCenterX();
        int prevCY = (int)btn_startPlay.getCenterY();
        btn_startPlay.setZoom(100);
        btn_startPlay.setCenterX(prevCX);
        btn_startPlay.setCenterY(prevCY);
    }
}
