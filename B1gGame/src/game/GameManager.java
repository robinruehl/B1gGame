package game;

import java.awt.event.MouseEvent;
import java.util.Random;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.ui.Window;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI.WindowsComboBoxEditor;
import com.sun.java.swing.plaf.windows.resources.windows;

import gameengine.AbstractGame;
import gameengine.GameContainer;
import gameengine.Renderer;
import gameengine.audio.soundClip;
import gameengine.gfx.Image;
import gameengine.gfx.ImageTile;

public class GameManager extends AbstractGame{
	

	private ImageTile Image;
	private Image Image1;
	private Image Image2;
	private Image Shit;
	private soundClip clip;
	private float xshit, yshit;
	private float xvel, yvel;
	Random rand = new Random();
	public GameManager() 
	{
		Image = new ImageTile("/ImageTile.png", 16, 16);
		clip = new soundClip("/audio/drop.wav");
		Image1 = new Image("/test.png");
		Image2 = new Image("/test2.png");
		Image2.setAlpha(true);
		Shit = new Image("/shit.png");
		
		xshit = 100;
		yshit = 100;
		
		xvel = rand.nextFloat();
		yvel = rand.nextFloat();
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		// TODO Auto-generated method stub
		if(gc.getInput().isKey(KeyEvent.VK_A))
		{
			System.out.println("a pressed");
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_D))
		{
			System.out.println("d pressed");
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_SPACE))
		{
			System.out.println("space pressed");
		}
		
		if (gc.getInput().isButtonsDown(MouseEvent.BUTTON1))
		{
			System.out.println("mouseclick");
			clip.setVolume(-20);
			clip.play();
		}
		temp +=dt * 4;
		if(temp > 3)
		{
			temp=0;
		}
	}
	
	float temp = 0;
	
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		xshit+=xvel;
		yshit+=yvel;
		System.out.println(xshit+" "+yshit);
		if(xshit+Shit.getW() > gc.getWindow().getFrame().getWidth()/gc.getScale())
		{
			xvel*=-1;
			xshit+=xvel;
		}
		if(yshit+Shit.getH() > gc.getWindow().getFrame().getHeight()/gc.getScale()-11)
		{
			yvel*=-1;
			yshit+=yvel;
		}
		if(xshit<0)
		{
			xvel*=-1;
			xshit+=xvel;
		}
		if(yshit<0)
		{
			yvel*=-1;
			yshit+=yvel;
		}
		
		r.drawImage(Shit, (int)xshit, (int)yshit);
		
		r.drawImageTile(Image, 10, 100, (int)temp, 0);
		r.drawImage(Image2, 120, 20);
		r.drawImage(Image1, 50, 20);
		r.drawRectH(10, 20, 32, 32, 0xffffccff);
		r.drawRectF(10, 60, 32, 32, 0xffffcccc);
	}
	
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
	
}
