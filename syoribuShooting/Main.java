package syoribuShooting;

public class Main {

    private static Game game;

    public static Game getGame()
    {
        return game;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        game = new Game();
    }

}
