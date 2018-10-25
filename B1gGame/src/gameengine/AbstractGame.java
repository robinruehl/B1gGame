package gameengine;

public abstract class AbstractGame 
{
	public abstract void update(GameContainer gContainer, float dt);
	public abstract void render(GameContainer gContainer, Renderer r);
}
