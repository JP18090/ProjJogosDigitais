package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation; 
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion; 
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    // Tela
    SpriteBatch spriteBatch;
    FitViewport viewport;

    // Player
    Player player;
    
    // Animação
    private Animation<TextureRegion> animacaoCorrida;
    private float stateTimeCorrida; 
    private TextureRegion frameAtual;
    private Texture[] playerTexturesToDispose; 

    // Fase
    FaseBase fasePiscina;
    FaseBase faseAtual;

    @Override
    public void create() {
        // Inicializa tela
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Carregamento dos frames
        TextureRegion[] framesNado = new TextureRegion[8];
        playerTexturesToDispose = new Texture[8]; // Array para liberar memória
        
        for (int i = 0; i < 8; i++) {
            // Usando for para pegar as imagens do nadador
            Texture t = new Texture("Imagem_" + (i + 1) + "_commit.png");
            framesNado[i] = new TextureRegion(t);
            playerTexturesToDispose[i] = t;
        }
        
        animacaoCorrida = new Animation<>(0.2f, framesNado); // 0.2s de duração por frame
        
        // Estado Inicial
        stateTimeCorrida = 0f;
        frameAtual = animacaoCorrida.getKeyFrames()[0]; // Define o primeiro frame como inicial

        // Player configurado
        Texture playerTextureInitial = playerTexturesToDispose[0];
        float playerWidth = 2f; 
        float playerHeight = playerWidth * (playerTextureInitial.getHeight() / (float) playerTextureInitial.getWidth());

        player = new Player(playerTextureInitial, -10f, 0.6f, playerWidth, playerHeight, viewport);

        // Fase
        fasePiscina = new FasePiscina(this);
        faseAtual = fasePiscina;
        faseAtual.music.play();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        draw();
    }

    private void update(float dt) {
        // Atualiza O player 
        player.update(dt);

        // Lógica da Animação
        if (player.isMoving()) {
            stateTimeCorrida += dt;
            frameAtual = animacaoCorrida.getKeyFrame(stateTimeCorrida, true); 
        } else {
            // Se parado, mostra o frame inicial
            stateTimeCorrida = 0f;
            frameAtual = animacaoCorrida.getKeyFrame(stateTimeCorrida); 
        }

        // Atualiza fase
        faseAtual.update();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
    
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        
        // Desenho Paralaxe Fundo
        
        float x1 = faseAtual.backgroundOffsetX % worldWidth;
        if (x1 > 0){
            x1 -= worldWidth; // Garante a rolagem contínua (looping)

        }

        spriteBatch.draw(faseAtual.background, x1, 0, worldWidth, worldHeight); 
        spriteBatch.draw(faseAtual.background, x1 + worldWidth, 0, worldWidth, worldHeight);
        

        // Desenho animação player
        spriteBatch.draw(frameAtual, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        
        for (Texture t : playerTexturesToDispose) {
            if (t != null) {
                t.dispose();
            }
        }
        
        faseAtual.dispose();
    }
}
