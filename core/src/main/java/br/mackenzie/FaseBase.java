package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public abstract class FaseBase implements Screen {
    public Texture background;
    public float backgroundOffsetX = 0f;
    public float parallaxVelocity = 0.75f;
    public Music music;
    protected Main game;
    protected Player player; 

    public FaseBase(String backgroundFile, String musicFile, Main game) {
        background = new Texture(backgroundFile);
        music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        this.game = game;
    }

    @Override
    public void render(float delta) {
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        if (game.player.isMoving()) {
            backgroundOffsetX -= delta * parallaxVelocity;
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void show(){}

    @Override
    public void dispose() {
        background.dispose();
        music.dispose();
    }
}
