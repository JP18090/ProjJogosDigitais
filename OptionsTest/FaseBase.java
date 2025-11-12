// Testagem Fase 1 - Options Menu

package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

// ====== INSTRUÇÃO ======
// Adicione método atualizaVolume() e use sempre o valor global Main.volume / Main.isMuted.
// Substitua esta classe na sua base.
public abstract class FaseBase implements Screen {
    public Texture background;
    public float backgroundOffsetX = 0f;
    public float parallaxVelocity = 0.75f;

    public Music music;
    protected FaseBaseScreen game;
    protected Player player;

    public FaseBase(String backgroundFile, String musicFile, FaseBaseScreen game) {
        background = new Texture(backgroundFile);
        music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        atualizaVolume(); // aplica volume global ao iniciar música
        this.game = game;
    }

    // NOVO MÉTODO: atualiza volume da música pelo valor global.
    public void atualizaVolume() {
        music.setVolume(Main.isMuted ? 0f : Main.volume);
    }

    @Override
    public void show() {
        atualizaVolume(); // toda vez que mostrar a fase, garantir volume certo
        music.play();
    }

    @Override
    public void render(float delta) {
        // Seu código normal...
        // Se quiser garantir atualização de volume em tempo real:
        atualizaVolume();
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
    public void dispose() {
        background.dispose();
        music.dispose();
    }
}
