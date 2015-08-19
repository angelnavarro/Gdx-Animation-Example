package com.pixnbgames.gdx_animation_example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Ejemplo de animacion frame-by-frame (cuadro por cuadro) con LibGDX:
 * 
 * http://www.pixnbgames.com/blog/libgdx/animaciones-cuadro-por-cuadro-en-libgdx
 * 
 * @author angel
 */
public class GdxAnimationExample extends ApplicationAdapter {
	
	// Tiempo que permanece visible cada cuadro de la animacion
	private static float FRAME_DURATION = .05f;
	
	// Para dibujar en pantalla
	private SpriteBatch batch;
	
	// Atlas con todos los cuadros definidos en "charset.atlas"
	private TextureAtlas charset;
	
	// Cuadro que debe ser dibujado en cada momento
	private TextureRegion currentFrame;
	
	// Objeto que representa la animacion del personaje corriendo
	private Animation runningAnimation;
	
	// Acumulador del tiempo que lleva representada una animacion
	private float elapsed_time = 0f;
	
	// Variables auxiliares para saber donde pintar la imagen para que quede en el centro
	private float origin_x, origin_y;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		// Carga de los cuadros definidos en "charset.atlas"
		charset = new TextureAtlas( Gdx.files.internal("charset.atlas") );
		
		// Se obtienen todas las imagenes para la animacion del personaje corriendo. Para hacer esto
		// lo mas facil posible, hemos definido todos los cuadros con el mismo nombre, "running", y
		// hemos asignado un indice (propiedad "index") a cada cuadro (ver "charset.atlas")
		Array<AtlasRegion> runningFrames = charset.findRegions("running");
		
		// Construcción de la animacion
		runningAnimation = new Animation(FRAME_DURATION, runningFrames, PlayMode.LOOP);
		
		// Calculo de la posicion en la que dibujar para que quede centrado en pantalla
		TextureRegion firstTexture = runningFrames.first();
		origin_x = (Gdx.graphics.getWidth()  - firstTexture.getRegionWidth())  / 2;
		origin_y = (Gdx.graphics.getHeight() - firstTexture.getRegionHeight()) / 2;
	}
	

	@Override
	public void render () {
		// Se limpia la pantalla aplicando un color naranja
		Gdx.gl.glClearColor(1.0f, .8f, .667f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Se acumula el tiempo que ha pasado desde la ultima vez que dibujamos. Con el tiempo total
		// transcurrido es como el objeto Animation sabe el cuadro que toca dibujar ahora
		elapsed_time += Gdx.graphics.getDeltaTime();
		
		// Obtencion del cuadro que debe ser dibujado
		currentFrame = runningAnimation.getKeyFrame(elapsed_time);
		
		// Dibujo del cuadro
		batch.begin();
		batch.draw(currentFrame, origin_x, origin_y);
		batch.end();
	}
	
	
	@Override
	public void dispose() {
		// Liberación de recursos
		charset.dispose();
		super.dispose();
	}
}
