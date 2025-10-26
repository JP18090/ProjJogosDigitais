package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class FaseBase {
    // Definindo texturas de cada fase
    public Texture background;

    // Definindo Velocidade por trás do fundo paralax padrao para todas fases
    public float backgroundOffsetX = 0f;
    public float parallaxVelocity = .75f;

    // Definindo musica de cada fase
    public Music music;

    // No construtor dessa classe (SUPERCLASS) vamos chamar tanto o arquivo de fundo de cada fase
    // Quanto o arquivo de musica, pensei em colocarmos uma musica especifica para cada fase
    public FaseBase(String backgroundFile, String musicFile) {
        background = new Texture(backgroundFile);
        music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
    }

    public void render(SpriteBatch batch, FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float x1 = backgroundOffsetX % worldWidth;
        if (x1 > 0) x1 -= worldWidth;

        batch.draw(background, x1, 0, worldWidth, worldHeight);
        batch.draw(background, x1 + worldWidth, 0, worldWidth, worldHeight);
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        backgroundOffsetX -= delta * parallaxVelocity;
    }

    public void dispose() {
        background.dispose();
    }
}

