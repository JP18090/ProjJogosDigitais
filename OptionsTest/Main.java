// Testagem Fase 1 - Options Menu

package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ====== INSTRUÇÃO ======
// Adicione as variáveis volume e isMuted.
// Permite controle GLOBAL de volume e mute (todas as telas e fases).
// Substitua esta classe para aplicar a alteração.
public class Main extends Game {

    public SpriteBatch spriteBatch;

    // Variáveis globais para controle de volume/mute
    public static float volume = 1.0f; // de 0.0 a 1.0
    public static boolean isMuted = false;

    private static int maxFaseLiberada = 1;

    public static int getMaxFaseLiberada() {
        return maxFaseLiberada;
    }

    public static void setMaxFaseLiberada(int nivel) {
        // Só atualiza se o novo nível for maior que o atual
        if (nivel > maxFaseLiberada) {
            maxFaseLiberada = nivel;
        }
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
    }
}
