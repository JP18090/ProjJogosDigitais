package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject{
    private final Viewport viewport;
    private boolean isMoving = false; 

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
    }

   @Override
    public void update(float dt){
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float playerWidth = sprite.getWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerHeight = sprite.getHeight();
        
        isMoving = false; 

        // NADADOR
        // Para frente e para trás
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            isMoving = true; 
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            isMoving = true; 
        }
        // Para cima e para baixo
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)){
            sprite.translateY(speed * dt);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)){
            sprite.translateY(-speed * dt);
        }

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - playerWidth));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - playerHeight));

    }

    public boolean isMoving() { // Novo método para Main verificar se está movendo
        return isMoving;
    }
    
    public float getX(){
        return sprite.getX();
    }
    public float getY(){
        return sprite.getY();
    }
    public float getWidth(){
        return sprite.getWidth();
    }
    public float getHeight(){
        return sprite.getHeight();
    }
}
