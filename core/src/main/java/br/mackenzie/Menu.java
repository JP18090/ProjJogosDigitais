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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Menu implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont fontTitle;
    private BitmapFont fontOptions;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // Atualizado: “Lago” -> “Rio”
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
        batch = game.batch;
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        camera.position.set(400, 240, 0);
        camera.update();

        // Carrega fonte TTF para o título e opções
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Título
        param.size = 64;
        param.color = Color.SKY;
        fontTitle = generator.generateFont(param);

        // Opções
        param.size = 36;
        param.color = Color.WHITE;
        fontOptions = generator.generateFont(param);

        generator.dispose();
    }

    @Override
    public void render(float delta) {
        // Limpa fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Desenha retângulos das opções
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        for (int i = 0; i < fases.length; i++) {
            float x = (viewport.getWorldWidth() - boxWidth) / 2;
            float y = startY - i * spacing;
            shapeRenderer.rect(x, y - boxHeight, boxWidth, boxHeight);
        }
        shapeRenderer.end();

        // Desenha texto
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Título
        GlyphLayout titleLayout = new GlyphLayout(fontTitle, "BLUE HORIZON");
        float titleX = (viewport.getWorldWidth() - titleLayout.width) / 2;
        fontTitle.draw(batch, titleLayout, titleX, 400);

        // Opções de fases
        for (int i = 0; i < fases.length; i++) {
            String fase = fases[i];
            GlyphLayout layout = new GlyphLayout(fontOptions, fase);
            float x = (viewport.getWorldWidth() - layout.width) / 2;
            float y = startY - i * spacing - (boxHeight / 2) + (layout.height / 2);
            fontOptions.draw(batch, fase, x, y);
        }

        batch.end();

        handleClick();
    }

    private void handleClick() {
        if (Gdx.input.justTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            for (int i = 0; i < fases.length; i++) {
                float x = (viewport.getWorldWidth() - boxWidth) / 2;
                float y = startY - i * spacing - boxHeight;

                if (touch.x >= x && touch.x <= x + boxWidth && touch.y >= y && touch.y <= y + boxHeight) {
                    System.out.println("Fase escolhida: " + fases[i]);

                    // Troca para a tela correspondente
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
        fontTitle.dispose();
        fontOptions.dispose();
        shapeRenderer.dispose();
    }
}
