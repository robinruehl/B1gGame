package gameengine;

public class GameContainer implements Runnable {
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	private boolean running = false;
	
	private final double UPDATE_CAP = 1.0 / 420;
	
	private int width = 320, height = 240;
	private float scale = 3f;
	private String title = "B1g Engine v1.0";
	
	public GameContainer(AbstractGame game) 
	{
		this.game = game;
	}
	
	public void start() 
	{
		window = new Window(this);
		thread = new Thread(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread.run();
	}
	
	public void stop() 
	{
		
	}
	
	@Override
	public void run() 
	{
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(running) 
		{
			render = true;
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= UPDATE_CAP)
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;
				

				game.update(this, (float)UPDATE_CAP);
				input.update();
				
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: "+fps);
					//System.out.println("X: " + input.getMouseX() + " Y: " + input.getMouseY());
				}
			}
			
			if(render)
			{
				renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.drawRectF(0, 0, 35, 7, 0xffffcccc);renderer.drawRectF(50, 0, 60, 7, 0xffffcccc);
				renderer.drawText("FPS: "+fps, 0, 0, 0xff00ffff);renderer.drawText("max es muy mal", 50, 0, 0xffb300ff);
				window.update();
				frames++;
			}
			else
			{
				try
				{
					thread.sleep(1);
				}
				catch (Exception e) {
				}
			}
		}
		
	}
	
	public void dispose() 
	{
		
	}

	public Input getInput() {
		return input;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Window getWindow() {
		return window;
	}
}
