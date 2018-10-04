package syoribuShooting.system;

import java.util.Random;

public class Utils
{
    private Utils() {}

    private static final Random random = new Random();

    public static int nextInt(int from, int to)
    {
        if (from == to) return to;
        if (to < from) return nextInt(to, from);
        return random.nextInt(to - from) + from;
    }
}
