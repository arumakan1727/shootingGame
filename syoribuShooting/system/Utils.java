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
        return random.nextInt(to - from + 1) + from;
    }

    public static double dist(int sx, int sy, int gx, int gy)
    {
        return Math.sqrt(distSqr(sx, sy, gx, gy));
    }

    public static long distSqr(int sx, int sy, int gx, int gy)
    {
        return sqr(gx-sx) + sqr(gy-sy);
    }

    public static long sqr(long n)
    {
        return n * n;
    }
}
