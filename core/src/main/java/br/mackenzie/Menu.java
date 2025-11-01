package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Menu implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;

    // Botões
    private Rectangle btnPiscina;
    private Rectangle btnRio;
    private Rectangle btnEspaco;

    // Controle da imagem
    private float imgX, imgY, imgWidth, imgHeight;
    private float scale = 0.75f; // 25% menor

    public Menu(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Carrega e redimensiona
        background = new Texture(Gdx.files.internal("Menu.png"));
        imgWidth = background.getWidth() * scale;
        imgHeight = background.getHeight() * scale;

        // Centraliza
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        imgX = (screenWidth - imgWidth) / 2f;
        imgY = (screenHeight - imgHeight) / 2f;

        // Ajuste fino com base na imagem enviada
        // Botões à esquerda, alinhados verticalmente com os textos
        btnPiscina = new Rectangle(imgX + 180, imgY + 670, 230, 90);
        btnRio     = new Rectangle(imgX + 180, imgY + 520, 230, 90);
        btnEspaco  = new Rectangle(imgX + 180, imgY + 370, 230, 90);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, imgX, imgY, imgWidth, imgHeight);
        batch.end();

        // Clique
        if (Gdx.input.justTouched()) {
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            click = game.viewport.getCamera().unproject(click);

            if (btnPiscina.contains(click.x, click.y)) {
                game.faseAtual = game.fasePiscina;
                game.setScreen(null);
            } else if (btnRio.contains(click.x, click.y)) {
                game.faseAtual = game.faseRio;
                game.setScreen(null);
            } else if (btnEspaco.contains(click.x, click.y)) {
                game.faseAtual = game.faseEspaco;
                game.setScreen(null);
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        background.dispose();
    }
}
