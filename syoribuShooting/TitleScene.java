package syoribuShooting;

import syoribuShooting.system.InputEventManager;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class TitleScene extends AbstractScene
{
    public TitleScene()
    {
        super();
        setBackImage(GameConfig.readImage("back01.jpg"));
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void finish()
    {

    }

    @Override
    public void update(Game game, SceneManager.SceneChanger sceneChanger)
    {
        InputEventManager eventManager = game.getEventManager();
        if (eventManager.isMousePressed(MouseEvent.BUTTON1))
        {
            sceneChanger.changeScene(new ShootingScene(GameConfig.FIRST_STAGE_FILE_PATH));
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(getBackImage(), 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, null);
    }
}
