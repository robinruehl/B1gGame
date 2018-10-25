package gameengine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class Image 
{
	private boolean alpha = false;
	private int w,h;
	private int[] p;
	public Image(String path)
	{
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(ImageInputStream.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		
		image.flush();
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int[] getP() {
		return p;
	}
	public void setP(int[] p) {
		this.p = p;
	}
	public boolean isAlpha() {
		return alpha;
	}
	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}
}
