package br.mackenzie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

/* Blue-Horizon -> Jogo de natação inclusivo
 * Criadores do Projeto:
 * Gabriel Labarca Del Bianco - 10443681
 * Gustavo Netto de Carvalho - 10437996
 * José Pedro Bitetti - 10427372
 * Vitor Costa Lemos - 10438932
 */

public class Main implements ApplicationListener {

    // Chamando cada classe para usar seus metodos posteriormente
    FasePiscina fasePiscina;
    FaseRio faseRio;
    FaseEspaco faseEspaco;

    // Definindo uma fase atual para testes
    FaseBase faseAtual;

    // Sprite Batch
    SpriteBatch spriteBatch;

    // Viewport
    FitViewport viewport;

    @Override
    public void create() {
        // Instanciando fundos
        fasePiscina = new FasePiscina();
        faseRio = new FaseRio();
        faseEspaco = new FaseEspaco();

        // Começa teste com a fase da piscina
        faseAtual = fasePiscina;

        // Criando instância do spriteBatch
        spriteBatch = new SpriteBatch();

        // Aplicando o viewport de acordo com a tela
        viewport = new FitViewport(8,5);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Renderizando todas sprites nesse bloco
        spriteBatch.begin();
        faseAtual.render(spriteBatch, viewport);
        spriteBatch.end();
    }


    public void input() {
        // Atualiza a fase atual
        faseAtual.update();
        faseAtual.music.play();

        // Logica criada para teste para mudar as fases atuais de acordo com teclas (TESTEI AQUI TANTO O FUNDO QUANTO UMA MUSICA PARA CADA FASE)
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_1)) {
            faseAtual.music.stop();
            faseAtual = fasePiscina;
            faseAtual.music.setLooping(true);
            faseAtual.music.setVolume(0.5f);
            faseAtual.music.play();
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_2)) {
            faseAtual.music.stop();
            faseAtual = faseRio;
            faseAtual.music.setLooping(true);
            faseAtual.music.setVolume(0.5f);
            faseAtual.music.play();
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_3)) {
            faseAtual.music.stop();
            faseAtual = faseEspaco;
            faseAtual.music.setLooping(true);
            faseAtual.music.setVolume(0.5f);
            faseAtual.music.play();
        }
    }


    public void logic() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        fasePiscina.dispose();
        faseRio.dispose();
        faseEspaco.dispose();
    }
}
