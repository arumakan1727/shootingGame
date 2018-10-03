package syoribuShooting.system;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 * バッファリングされた描画用Canvasクラスを提供します。
 * @author ei1727
 *
 */
@SuppressWarnings("serial")
public class BufferedCanvas extends Canvas
{
	/**
	 * キャンバスを生成します。
	 * この時点ではバッファリングされていません。
	 * @param w
	 * @param h
	 */
	public BufferedCanvas(int w, int h)
	{
		Dimension dimension = new Dimension(w, h);
		this.setPreferredSize(dimension);
		this.setFocusable(true);
	}
	
	/**
	 * キャンバスをバッファリングします。
	 * ただし、これを持っているJFrameはsetVisibe(true)した状態
	 * でないと例外が発生します。(なぜなのかは不明)
	 * @param numBuff バッファの数 : 2 を推奨
	 * 
	 */
	public void setBuffering(int numBuff)
	{
		try {
			this.createBufferStrategy(numBuff);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			System.err.println("Error: window must be visble.");
			System.exit(1);
		}
	}
	
	/**
	 *
	 * @return
	 */
	public Graphics2D getRenderer()
	{
		final BufferStrategy strategy = this.getBufferStrategy();
		
		if (strategy.contentsLost()) {
			System.err.println("BufferStrategy lost");
			return null;
		} else {
			return (Graphics2D)(strategy.getDrawGraphics());
		}
	}
	
	public void showBuffer()
	{
		this.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
	}
}
