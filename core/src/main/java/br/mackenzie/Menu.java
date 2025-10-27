package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Menu implements Screen {

    private final Main game;
    private SpriteBatch batch;        // agora pertence ao Menu
    private ShapeRenderer shapeRenderer;
    private BitmapFont fontTitle;
    private BitmapFont fontOptions;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private final String[] fases = {"Piscina", "Rio", "Espaço"};
    private final float boxWidth = 200;
    private final float boxHeight = 50;
    private final float startY = 250;
    private final float spacing = 80;

    public Menu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        // debug: log e criação de recursos
        Gdx.app.log("MENU", "show() chamado");
        batch = new SpriteBatch();       // se estiver usando game.batch e ele for nulo, evite NPE
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        camera.position.set(400, 240, 0);
        camera.update();

        fontTitle = new BitmapFont();
        fontOptions = new BitmapFont();
        fontTitle.setColor(Color.SKY);
        fontOptions.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        // debug: log do render (cuidado em logs muito verbosos)
        Gdx.app.log("MENU", "render() delta=" + delta);

        // Limpa com uma cor visível (teste)
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.16f, 1f); // cinza-azulado para ver contraste
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Desenha retângulos preenchidos para garantir visualização
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < fases.length; i++) {
            float x = (viewport.getWorldWidth() - boxWidth) / 2f;
            float y = startY - i * spacing;
            // retângulos semi-translúcidos
            shapeRenderer.setColor(1f, 1f, 1f, 0.15f);
            shapeRenderer.rect(x, y - boxHeight, boxWidth, boxHeight);
        }
        shapeRenderer.end();

        // Texto
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        GlyphLayout titleLayout = new GlyphLayout(fontTitle, "BLUE HORIZON");
        float titleX = (viewport.getWorldWidth() - titleLayout.width) / 2f;
        fontTitle.draw(batch, titleLayout, titleX, 400);

        for (int i = 0; i < fases.length; i++) {
            String fase = fases[i];
            GlyphLayout layout = new GlyphLayout(fontOptions, fase);
            float x = (viewport.getWorldWidth() - layout.width) / 2f;
            float y = startY - i * spacing - (boxHeight / 2f) + (layout.height / 2f);
            fontOptions.draw(batch, fase, x, y);
        }
        batch.end();

        handleClick();
    }


    private void handleClick() {
        if (Gdx.input.justTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            for (int i = 0; i < fases.length; i++) {
                float x = (viewport.getWorldWidth() - boxWidth) / 2f;
                float y = startY - i * spacing - boxHeight;

                if (touch.x >= x && touch.x <= x + boxWidth && touch.y >= y && touch.y <= y + boxHeight) {
                    System.out.println("Fase escolhida: " + fases[i]);

                    switch (fases[i]) {
                        case "Piscina":
                            game.setScreen(new FasePiscina(game));
                            break;
                        case "Rio":
                            game.setScreen(new FaseRio(game));
                            break;
                        case "Espaço":
                            game.setScreen(new FaseEspaco(game));
                            break;
                    }
                }
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        fontTitle.dispose();
        fontOptions.dispose();
        shapeRenderer.dispose();
    }
}
