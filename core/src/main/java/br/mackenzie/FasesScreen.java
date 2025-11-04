package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FasesScreen implements Screen{
    private final Main game;
    private final SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Texture menuBackground;
    private Texture fases1Texture;

    private float x_painel;
    private float y_painel;
    private float width_painel;
    private float height_painel;

    private float x_fase1;
    private float y_fase1;
    private float width_fase1;
    private float height_fase1;

    private float x_voltar;
    private float y_voltar;
    private float width_voltar;
    private float height_voltar;

    //private com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer; 

    public FasesScreen(Main game) {
        this.game = game;
        this.spriteBatch = game.spriteBatch;
    }

    @Override
    public void show() {
        menuBackground = new Texture("Menu/Fundo.png");
        fases1Texture = new Texture("Menu/OpcaoPlay/Img_1.png");

        viewport = new FitViewport(8, 5); 
        viewport.apply();

        //shapeRenderer = new com.badlogic.gdx.graphics.glutils.ShapeRenderer();

        width_painel = 8f;
        height_painel = 4f;

        x_painel = (viewport.getWorldWidth() / 2) - (width_painel / 2);
        y_painel = (viewport.getWorldHeight() / 2) - (height_painel / 2);

        x_fase1 = 1f;
        y_fase1 = 1.5f;
        width_fase1 = 1.7f;
        height_fase1 = 1.4f;

        x_voltar = 1.02f;
        y_voltar = 3.57f;
        width_voltar = 0.6f;
        height_voltar = 0.25f;

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        // Desenha o fundo ocupando toda a tela
        spriteBatch.draw(menuBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(fases1Texture, x_painel, y_painel, width_painel, height_painel);

        spriteBatch.end();

        // Lógica de cliques
        if (com.badlogic.gdx.Gdx.input.justTouched()) {
            
            // Converte as coordenadas da tela (pixels) para o mundo (float)
            Vector2 touchPoint = new Vector2(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY());
            viewport.unproject(touchPoint); 

            float inputX = touchPoint.x;
            float inputY = touchPoint.y;

            // Verificação de clique na Fase 1
            if (inputX >= x_fase1 && inputX <= x_fase1 + width_fase1 &&
                inputY >= y_fase1 && inputY <= y_fase1 + height_fase1) {
                
                // Inicia a Fase 1 
                game.setScreen(new GameScreen(game));
            }

            // Verificação de clique no botão de voltar
            if (inputX >= x_voltar && inputX <= x_voltar + width_voltar &&
                inputY >= y_voltar && inputY <= y_voltar + height_voltar) {
                
                // Volta a o Menu inicial
                game.setScreen(new MenuScreen(game));
            }
        }

        //Configurar o ShapeRenderer com a mesma câmera do SpriteBatch
        //shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        //shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);

        // Defina a cor (Ex: Verde Neon Transparente para destacar)
        //shapeRenderer.setColor(0f, 1f, 0f, 0.4f); // Cor (R, G, B, Alpha)

        // Desenha o retângulo que será sua área de clique
        //shapeRenderer.rect(1.02f, 3.57f, 0.6f, 0.25f); //COORDENADAS FASE 1 (X = 1f, Y = 1.5f, W = 1.7f, H = 1.4f)  //COORDENADAS FASE 2 (X = 3.1f, Y = 1.5f, W = 1.7f, H = 1.4f) //COORDENADAS FASE 3 (X = 5.2f, Y = 1.5f, W = 1.7f, H = 1.4f)
        //COORDENADAS Botão Voltar  (X = 1.02f, Y = 3.57f, W = 0.6f, H = 0.25f)
        //shapeRenderer.end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override
    public void dispose() {
        if (menuBackground != null) menuBackground.dispose();
        if (fases1Texture != null) fases1Texture.dispose();
    }
}
