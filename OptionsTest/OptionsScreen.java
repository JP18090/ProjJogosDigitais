// Testagem Fase 1 - Options Menu

package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ====== INSTRUÇÃO ======
// Nova classe de menu de opções!
// Permite mutar/desmutar música de TODAS as telas/fases (controle global)
// Possui botão de retorno ao Menu Principal
// Basta adicionar no projeto e referenciar onde necessário.
public class OptionsScreen implements Screen {
    private final Main game;
    private final SpriteBatch spriteBatch;

    private Texture background;
    private Texture muteButtonTexture;
    private Texture unmuteButtonTexture;
    private Texture backButtonTexture;
    private boolean isMuted;

    public OptionsScreen(Main game) {
        this.game = game;
        this.spriteBatch = game.spriteBatch;
        background = new Texture("Menu/Fundo.png"); // ou outro fundo desejado
        muteButtonTexture = new Texture("Menu/mute.png");      // Crie icones/botões apropriados
        unmuteButtonTexture = new Texture("Menu/unmute.png");
        backButtonTexture = new Texture("Menu/back.png");
        isMuted = Main.isMuted; // sincroniza com o global
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);

        // Exibe botão mute ou unmute conforme status
        if (isMuted) {
            spriteBatch.draw(unmuteButtonTexture, 300, 300, 120, 50); // ajustar posições/ícones
        } else {
            spriteBatch.draw(muteButtonTexture, 300, 300, 120, 50);
        }
        spriteBatch.draw(backButtonTexture, 300, 200, 120, 50);
        spriteBatch.end();

        // Detecta clique para mutar/desmutar
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 300 && x <= 420 && y >= 300 && y <= 350) {
                // Mute/unmute de todas as fases/telas
                isMuted = !isMuted;
                Main.isMuted = isMuted;
                Main.volume = isMuted ? 0f : 1f; // Volume global
            }
            if (x >= 300 && x <= 420 && y >= 200 && y <= 250) {
                // Voltar ao menu principal
                game.setScreen(new MenuScreen(game));
            }
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
    public void dispose() {
        background.dispose();
        muteButtonTexture.dispose();
        unmuteButtonTexture.dispose();
        backButtonTexture.dispose();
    }
}
