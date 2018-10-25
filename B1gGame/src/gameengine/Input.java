package gameengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import sun.misc.GC;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private GameContainer gContainer;
	
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];
	
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
	
	private int mouseX, mouseY;
	private int scroll;
	
	public Input(GameContainer gContainer)
	{
		this.gContainer = gContainer;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		gContainer.getWindow().getCanvas().addKeyListener(this);
		gContainer.getWindow().getCanvas().addMouseListener(this);
		gContainer.getWindow().getCanvas().addMouseMotionListener(this);
		gContainer.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update()
	{
		for(int i = 0; i < NUM_KEYS; i++)
		{
			keysLast[i] = keys[i];
		}
		
		for(int i = 0; i < NUM_BUTTONS; i++)
		{
			buttonsLast[i] = buttons[i];
		}
	}

	public boolean isKey(int keyCode)
	{
		return keys[keyCode];
	}
	public boolean isKeyUp(int keyCode)
	{
		return !keys[keyCode] && keysLast[keyCode];
	}
	public boolean isKeyDown(int keyCode)
	{
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isButtons(int button)
	{
		return buttons[button];
	}
	public boolean isButtonsUp(int button)
	{
		return !buttons[button] && buttonsLast[button];
	}
	public boolean isButtonsDown(int button)
	{
		return buttons[button] && !buttonsLast[button];
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) (e.getX() / gContainer.getScale());
		mouseY = (int) (e.getY() / gContainer.getScale());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) (e.getX() / gContainer.getScale());
		mouseY = (int) (e.getY() / gContainer.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}
