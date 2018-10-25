package gameengine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import gameengine.gfx.Font;
import gameengine.gfx.Image;
import gameengine.gfx.ImageTile;
import gameengine.gfx.ImageRequest;

public class Renderer 
{
	private Font font = Font.STANDARD;
	private ArrayList<ImageRequest> ImageRequest = new ArrayList<ImageRequest>();
	
	private int pW, pH;
	private int[] p;
	private int[] zb;
	
	private int zDepth = 0;
	private boolean processing = false;
	
	public Renderer(GameContainer gContainer)
	{
		pW = gContainer.getWidth();
		pH = gContainer.getHeight();
		p = ((DataBufferInt)gContainer.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb = new int[p.length];
	}
	
	public void clear()
	{
		for(int i = 0; i < p.length; i++)
		{
			p[i] = 0;
			zb[i] = 0;
		}
	}
	
	public void process()
	{
		processing = true;
		for(int i = 0; i < ImageRequest.size(); i++)
		{
			ImageRequest iR = ImageRequest.get(i);
			setzDepth(iR.zDepth);
			drawImage(iR.image, iR.offX, iR.offY);
		}
		
		ImageRequest.clear();
		processing = false;
	}
	
	public void setpixel(int x, int y, int value)
	{
		
		int alpha = ((value >> 24) & 0xff);
		
		if((x<0||x>=pW||y<0||y>=pH)||alpha == 0)
		{
			return;
		}
		
		if(zb[x + y * pW] > zDepth)return;
			
		if(alpha == 255)
		{
			p[x + y * pW] = value;
		}
		else
		{
			int pColor = p[x + y * pW];
			
			int newRed = ((pColor >> 16) & 0xff) - (int)(((pColor >> 16) & 0xff) - ((value >> 16) & 0xff) * (alpha/255f));
			int newGreen = ((pColor >> 8) & 0xff) - (int)(((pColor >> 8) & 0xff) - ((value >> 8) & 0xff) * (alpha/255f));
			int newBlue = (pColor & 0xff) -(int)(((pColor & 0xff) - (value & 0xff)) * (alpha/255f));
			
			p[x + y * pW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}
	
	public void drawText(String senttext, int offX, int offY, int color)
	{
		String text = senttext.toUpperCase();
		Image fontImage = font.getFontImage();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++)
		{
			int unicode = text.codePointAt(i) - 32;
			
			
			
			for(int y = 0; y<fontImage.getH(); y++)
			{
				for(int x = 0; x < font.getWidths()[unicode]; x++)
				{
					if(fontImage.getP()[(x + font.getOffsets()[unicode]) + y * fontImage.getW()] == 0xffffffff)
					{
						setpixel(x + offX + offset, y + offY, color);
					}
				}
			}
			
			offset += font.getWidths()[unicode];
		}
	}
	
	public void drawImage(Image image, int offX, int offY)
	{
		
		if(image.isAlpha() && !processing)
		{
			ImageRequest.add(new ImageRequest(image,zDepth, offX, offY));
			return;
		}
		
		if (offX < - image.getW())
		{return;}
		if (offY < - image.getH())
		{return;}
		if (offX >= pW)
		{return;}
		if (offY >=pH)
		{return;}
		
		int newX = 0; int newY = 0;
		int newW = image.getW();
		int newH = image.getH();
		
		if (newW + offX > pW)
		{newW -= newW + offX - pW;}
		if (newH + offY > pH)
		{newH -= newH + offY - pH;}
		if (offX<0)
		{newX-=offX;}
		if (offY<0)
		{newY-=offY;}
		
		
		for(int y = 0; y < image.getH(); y++)
		{
			for(int x = 0; x < image.getW(); x++)
			{
			setpixel(x+ offX, y + offY, image.getP()[x+y*image.getW()]);
			}
		}
		
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		if (offX < - image.getTileW())
		{return;}
		if (offY < - image.getTileH())
		{return;}
		if (offX >= pW)
		{return;}
		if (offY >=pH)
		{return;}
		
		int newX = 0; int newY = 0;
		int newW = image.getTileW();
		int newH = image.getTileH();
		
		if (newW + offX > pW)
		{newW -= newW + offX - pW;}
		if (newH + offY > pH)
		{newH -= newH + offY - pH;}
		if (offX<0)
		{newX-=offX;}
		if (offY<0)
		{newY-=offY;}
		
		
		for(int y = 0; y < newH; y++)
		{
			for(int x = 0; x < newW; x++)
			{
			setpixel( x + offX, y + offY, image.getP()[ ( x + tileX * image.getTileW() ) + ( y + tileY * image.getTileH() ) * image.getW() ]);
			}
		}
	}
	
	public void drawRectH(int offX, int offY, int width, int height, int color)
	{
		for (int y = 0; y <= height; y++)
		{
			setpixel(offX, y + offY, color);
			setpixel(offX + width, y + offY, color);
		}
		
		for (int x = 0; x <= width; x++)
		{
			setpixel(x + offX,offY, color);
			setpixel(x + offX,offY + height, color);
		}
	}
	
	public void drawRectF(int offX, int offY, int width, int height, int color)
	{
		
		if (offX < - width)
		{return;}
		if (offY < - height)
		{return;}
		if (offX >= pW)
		{return;}
		if (offY >=pH)
		{return;}
		
		int newX = 0; int newY = 0;
		int newW = width;
		int newH = height;
		
		if (newW + offX > pW)
		{newW -= newW + offX - pW;}
		if (newH + offY > pH)
		{newH -= newH + offY - pH;}
		if (offX<0)
		{newX-=offX;}
		if (offY<0)
		{newY-=offY;}
		
		//System.out.println(newX+" "+newY+" "+newW+" "+newH);
		
		for (int y = newY; y <= newH; y++)
		{
			for (int x = newX; x <= newW; x++)
			{
				setpixel(x+ offX, y + offY, color);
			}
		}
		
		
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
