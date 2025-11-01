package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    // Fases
    public FasePiscina fasePiscina;
    public FaseRio faseRio;
    public FaseEspaco faseEspaco;

    // Fase atual
    public FaseBase faseAtual;

    // Menu clicável
    public Menu menu;

    // Sprite/batch/viewport
    SpriteBatch spriteBatch;
    public FitViewport viewport; // público para o Menu (conforme o segundo Main)

    // Player
    Player player;

    // Animação
    private Animation<TextureRegion> animacaoCorrida;
    private float stateTimeCorrida;
    private TextureRegion frameAtual;
    private Texture[] playerTexturesToDispose;

    @Override
    public void create() {
        // Inicializa tela
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 600); // mantive 800x600 do segundo Main (menu)

        // Instancia fases
        fasePiscina = new FasePiscina(this);
        faseRio     = new FaseRio(this);
        faseEspaco  = new FaseEspaco(this);

        // Menu clicável
        menu = new Menu(this);

        // Carregamento dos frames do player (8 frames como no primeiro Main)
        TextureRegion[] framesNado = new TextureRegion[8];
        playerTexturesToDispose = new Texture[8];

        for (int i = 0; i < 8; i++) {
            Texture t = new Texture("Imagem_" + (i + 1) + "_commit.png");
            framesNado[i] = new TextureRegion(t);
            playerTexturesToDispose[i] = t;
        }

        animacaoCorrida = new Animation<>(0.2f, framesNado);
        stateTimeCorrida = 0f;
        frameAtual = animacaoCorrida.getKeyFrames()[0];

        // Player configurado (usa o primeiro texture como inicial)
        Texture playerTextureInitial = playerTexturesToDispose[0];
        float playerWidth = 64f; // valor base em pixels; ajuste se quiser coordenadas em unidade mundo
        // calcula altura com proporção da textura
        float playerHeight = playerWidth * (playerTextureInitial.getHeight() / (float) playerTextureInitial.getWidth());
        // posição inicial arbitrária (se preferir, altere)
        player = new Player(playerTextureInitial, 100f, 50f, playerWidth, playerHeight, viewport);

        // Começa pelo menu (usa setScreen do Game)
        setScreen(menu);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        // Renderiza a tela atual (menu ou outro Screen)
        Screen current = getScreen();
        if (current != null) {
            current.render(dt);
        }

        // Se uma fase foi selecionada (faseAtual != null), atualiza e renderiza a fase
        if (faseAtual != null) {
            update(dt);        // atualiza player, animação e lógica da fase
            renderFase(dt);    // desenha background, fase e player
        }
    }

    private void update(float dt) {
        // Atualiza o player (input, física, etc.)
        if (player != null) {
            player.update(dt);
        }

        // Lógica da Animação
        if (player != null && player.isMoving()) {
            stateTimeCorrida += dt;
            frameAtual = animacaoCorrida.getKeyFrame(stateTimeCorrida, true);
        } else {
            stateTimeCorrida = 0f;
            frameAtual = animacaoCorrida.getKeyFrame(stateTimeCorrida);
        }

        // Atualiza fase (logica interna da fase)
        if (faseAtual != null) {
            faseAtual.update();
        }
    }

    private void renderFase(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        // Desenha a própria fase (assume que FaseBase tem render(SpriteBatch, FitViewport))
        faseAtual.render(spriteBatch, viewport);

        // Desenho da animação do player (caso a fase não desenhe o player internamente)
        if (frameAtual != null && player != null) {
            spriteBatch.draw(frameAtual, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }
        spriteBatch.end();

        // Entrada e música da fase
        inputFase();
    }

    private void inputFase() {
        if (faseAtual == null) return;

        if (!faseAtual.music.isPlaying()) {
            faseAtual.music.setLooping(true);
            faseAtual.music.setVolume(0.5f);
            faseAtual.music.play();
        }
    }

    /**
     * Helper para trocar de tela usando API do Game (substitui comportamento custom do segundo Main).
     * Além de setar a Screen, seta faseAtual se o screen for uma Fase (caso queira selecionar fase via menu).
     */
    @Override
    public void setScreen(Screen screen) {
        // chama Game.setScreen para gerenciar show/hide corretamente
        super.setScreen(screen);

        // Se o screen passado for uma tela que representa uma fase (por exemplo Menu chama main.setScreen(fasePiscina))
        // atualiza referencia faseAtual para tocar a fase correspondente.
        // Aqui fazemos uma checagem simples por instância; se seus Screens de fase tiverem classes próprias, ajuste conforme necessário.
        if (screen instanceof FaseBase) {
            faseAtual = (FaseBase) screen;
        } else {
            // Se for menu ou outra tela, não mantemos faseAtual (até que o menu peça para iniciar uma fase)
            // OBS: Se você quer que a fase continue em background ao voltar do menu, comente a linha abaixo.
            // faseAtual = null;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        Screen current = getScreen();
        if (current != null) current.resize(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();

        if (playerTexturesToDispose != null) {
            for (Texture t : playerTexturesToDispose) {
                if (t != null) t.dispose();
            }
        }

        if (fasePiscina != null) fasePiscina.dispose();
        if (faseRio != null) faseRio.dispose();
        if (faseEspaco != null) faseEspaco.dispose();
        if (menu != null) menu.dispose();
    }
}
