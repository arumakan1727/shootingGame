package syoribuShooting.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NumberImage extends Sprite
{
    private BufferedImage[] images;
    private byte[] digits;
    private int num;
    
    public NumberImage(BufferedImage[] imgs, int num)
    {
        super(imgs[0].getWidth(), imgs[0].getHeight());
        if (num < 0) throw new IllegalArgumentException(num + ": Not allowed negative-number!");

        setImages(imgs);
        setNum(num);
    }
    
    public void draw(Graphics2D g2d)
    {
        final int width = getWidth();
        final int height = getHeight();
        int x = (int)getX();
        int y = (int)getY();
        
        for (int i = 0; i < digits.length; ++i)
        {
            g2d.drawImage(images[digits[i]], x, y, width, height, null);
            x += width;
        }
    }
    
    public BufferedImage[] getImages()
    {
        return images;
    }
    public void setImages(BufferedImage[] images)
    {
        this.images = images;
    }
    public int getNum()
    {
        return num;
    }
    public int getDigitLen()
    {
        return digits.length;
    }
    public void setNum(int num)
    {
        this.num = num;
        this.digits = getDigitArray(getNum());

    }
    
    public static byte[] getDigitArray(int n)
    {
        byte[] digits = new byte[digitCount(n)];
        for (int i = digits.length-1; i >= 0; --i) {
            digits[i] = (byte)(n % 10);
            n /= 10;
        }
        
        return digits;
    }
    
    public static int digitCount(int n)
    {
        int cnt = 0;
        do {
           n /= 10;
           ++cnt;
        } while(n > 0);
        
        return cnt;
    }

}
