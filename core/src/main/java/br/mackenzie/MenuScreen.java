package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen{
    private final Main game;
    private final SpriteBatch spriteBatch;
    private FitViewport viewport;
    
    // Recursos do Menu
    private Texture menuBackground;
    private Texture startButtonTexture;
    private Texture optionsButtonTexture;
    private Texture creditsButtonTexture;
    private Texture exitButtonTexture;
    private Texture tituloTexture;

    // Dimensões e Posição do Botão
    private final float buttonWidth = 2.0f;
    private final float buttonHeight = 1.0f;

    private float buttonX;
    private float buttonY;

    public MenuScreen(Main game) {
        this.game = game;
        this.spriteBatch = game.spriteBatch;
    }

    @Override
    public void show() {
        // Inicialização das Imagens
        menuBackground = new Texture("Menu/Fundo.png");
        tituloTexture = new Texture("Menu/Titulo.png");
        startButtonTexture = new Texture("Menu/Play.png"); 
        optionsButtonTexture = new Texture("Menu/Options.png");
        creditsButtonTexture = new Texture("Menu/Credits.png");
        exitButtonTexture = new Texture("Menu/Exit.png");

        // Configuração do Viewport
        viewport = new FitViewport(8, 5); 
        viewport.apply();
        
        // Posição dos botões
        buttonX = (viewport.getWorldWidth() / 2) - (buttonWidth / 2);
        buttonY = (viewport.getWorldHeight() / 2) - (buttonHeight / 2);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        // Desenha o fundo ocupando toda a tela
        spriteBatch.draw(menuBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        
        // Desenha os botões

        spriteBatch.draw(tituloTexture, buttonX, buttonY + 1.4f, buttonWidth, buttonHeight);
        spriteBatch.draw(startButtonTexture, buttonX, buttonY + 0.7f, buttonWidth, buttonHeight);
        spriteBatch.draw(optionsButtonTexture, buttonX, buttonY , buttonWidth, buttonHeight);
        spriteBatch.draw(creditsButtonTexture, buttonX, buttonY - 0.7f, buttonWidth, buttonHeight);
        spriteBatch.draw(exitButtonTexture, buttonX, buttonY - 1.4f, buttonWidth, buttonHeight);
        
        spriteBatch.end();
        
        // Lógica de troca de tela
        if (Gdx.input.justTouched()) {
            Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPoint); // O objeto 'touchPoint' agora tem as coordenadas do mundo

            float inputX = touchPoint.x;
            float inputY = touchPoint.y;

            // Lógica Botão "PLAY"
            if (inputX >= buttonX && inputX <= buttonX + buttonWidth &&
                inputY >= (buttonY + 0.7) && inputY <= (buttonY + 0.7) + buttonHeight) {
                
                // Sai do Menu e entra no Jogo 
                game.setScreen(new FasesScreen(game));
                
            }

            // Lógica Botão "OPTIONS"
            if (inputX >= buttonX && inputX <= buttonX + buttonWidth &&
                inputY >= (buttonY + 0.7) && inputY <= (buttonY + 0.7) + buttonHeight) {
                
                
                
            }

            // Lógica Botão "CREDITS"
            if (inputX >= buttonX && inputX <= buttonX + buttonWidth &&
                inputY >= (buttonY + 0.7) && inputY <= (buttonY + 0.7) + buttonHeight) {
                
                
                
            }

            // Lógica Botão "EXIT"
            if (inputX >= buttonX && inputX <= buttonX + buttonWidth &&
                inputY >= (buttonY - 1.4) && inputY <= (buttonY - 1.4) + buttonHeight) {
                
                // Encerra o Jogo
                com.badlogic.gdx.Gdx.app.exit();
                
            }
            
        }
    }

    @Override
    public void dispose() {
        if (menuBackground != null) menuBackground.dispose();
        if (startButtonTexture != null) startButtonTexture.dispose();
        if (optionsButtonTexture != null) optionsButtonTexture.dispose();
        if (creditsButtonTexture != null) creditsButtonTexture.dispose();
        if (exitButtonTexture != null) exitButtonTexture.dispose();

    }
    
    @Override public void resize(int width, int height) { 
        viewport.update(width, height, true); 
    }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}
