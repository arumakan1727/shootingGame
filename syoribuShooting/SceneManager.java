package syoribuShooting;

import java.awt.Graphics2D;

public class SceneManager
{
    private AbstractScene nowScene;
    private AbstractScene nextScene = null;

    private SceneChanger sceneChanger =
            new SceneChanger()
    {
        @Override
        public void changeScene(AbstractScene nextScene)
        {
            SceneManager.this.nextScene = nextScene;
        }
    };

    public SceneManager() {}

    public SceneManager(AbstractScene scene)
    {
        this.nextScene = scene;
    }

    public void update(Game game)
    {
        if (nextScene != null)
        {
            if (nowScene != null) {
                nowScene.finish(game);
            }
            nextScene.initialize(game);

            nowScene = nextScene;
            nextScene = null;
        }
        nowScene.update(game, this.sceneChanger);
    }

    public void draw(Graphics2D g2d)
    {
        nowScene.draw(g2d);
    }

    public interface SceneChanger {
        void changeScene(AbstractScene nextScene);
    }
}
