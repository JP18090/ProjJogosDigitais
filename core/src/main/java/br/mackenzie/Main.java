package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    public SpriteBatch spriteBatch;

    @Override
    public void create() {
        // Inicializa o SpriteBatch
        spriteBatch = new SpriteBatch(); 

        // Define a primeira tela: O Menu!
        setScreen(new MenuScreen(this)); 
    }
    
    @Override
    public void dispose() {
        super.dispose();
        // Dispor o SpriteBatch que é usado em todas as telas
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
    }
}
