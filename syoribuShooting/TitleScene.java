package syoribuShooting;

import syoribuShooting.sprite.ActionListener;
import syoribuShooting.sprite.Button;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static syoribuShooting.GameConfig.readImage;

public class TitleScene extends AbstractScene implements ActionListener
{
    private static final int BTN_Y = 750;
    private boolean changeSceneFlag = false;
    private Button btn_startPlay;
    private BufferedImage img_normalBtn = readImage("play_button00.png");
    private BufferedImage img_hoverBtn = readImage("play_button01.png");
    private AbstractScene nextScene = null;

    private Thread nextSceneBuiltThread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            nextScene = new ShootingScene(GameConfig.FIRST_STAGE_FILE_PATH);
        }
    });

    public TitleScene()
    {
        super();
        btn_startPlay = new Button(img_normalBtn);
        btn_startPlay.setCenterX(GameConfig.VIRTUAL_WIDTH / 2);
        btn_startPlay.setY(BTN_Y);
        btn_startPlay.setActionListener(this);
        setBackImage(GameConfig.readImage("title_logo.jpg"));
    }

    @Override
    public void initialize(Game game)
    {
        game.getWindow().getCursorManager().changeCurrentCursor(Cursor.DEFAULT_CURSOR);
        nextSceneBuiltThread.start();
    }

    @Override
    public void finish(Game game)
    {

    }

    @Override
    public void update(Game game, SceneManager.SceneChanger sceneChanger)
    {
        if (changeSceneFlag) {
            sceneChanger.changeScene(nextScene);
        }
        btn_startPlay.update(game.getEventManager());
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);
        btn_startPlay.draw(g2d);
    }

    @Override
    public void mouseEntered()
    {
        btn_startPlay.setImage(img_hoverBtn);
        Main.getCursorManager().changeCurrentCursor(Cursor.HAND_CURSOR);
    }

    @Override
    public void mouseExited()
    {
        btn_startPlay.setImage(img_normalBtn);
        Main.getCursorManager().changeCurrentCursor(Cursor.DEFAULT_CURSOR);
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
