package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CreditsScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Texture optionsTexture;
    private BitmapFont font;
    private Texture buttonTex;
    private TextButtonStyle plusStyle;
    private WindowStyle windowStyle;
    private LabelStyle labelStyle;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Texture fundoTexture;

    // Dados dos jurados
    private static class Jurado {
        String name, role, description;
        Jurado(String n, String r, String d) { name = n; role = r; description = d; }
    }
    private final Jurado[] jurados = new Jurado[] {
            new Jurado("José Pedro", "Presidente do Júri",
                    "Especialista em avaliação técnica e comportamento em competições. Responsável por notas finais e observações."),
            new Jurado("Gabriel Labarca", "Jurado Técnico",
                    "Analisa precisão e execução. Possui larga experiência em julgamentos nacionais e internacionais."),
            new Jurado("Gustavo Netto", "Jurado Artístico",
                    "Focado em estética e apresentação. Dá pontos de estilo e criatividade."),
            new Jurado("Vítor Lemos", "Jurado de Segurança",
                    "Avalia postura, segurança e conformidade com as regras. Também registra penalidades técnicas.")
    };

    // posições relativas (percentuais) em relação à imagem: leftPercent, topPercent
    // left é de 0..1 (0 = esquerda da imagem, 1 = direita), top é 0..1 (0 = topo da imagem, 1 = fundo).
    // Ajuste se necessário para alinhar perfeitamente.
    private final float[][] positions = new float[][] {
            {0.37f, 0.12f},
            {0.47f, 0.12f},
            {0.57f, 0.12f},
            {0.67f, 0.12f}
    };

    public CreditsScreen(Main g) {
        this.game = g;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        viewport = stage.getViewport();

        optionsTexture = new Texture(Gdx.files.internal("Menu/OpcaoCredits/OptionsDescription.png"));
        fundoTexture = new Texture("Menu/Fundo.png");
        font = new BitmapFont(); // Fonte padrão
        font.getData().setScale(1.2f);

        // Criar textura do botão "+"
        Pixmap pm = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pm.setColor(1f, 1f, 1f, 0.95f);
        pm.fillCircle(32, 32, 30);
        buttonTex = new Texture(pm);
        pm.dispose();

        // Styles
        TextureRegionDrawable plusDrawable = new TextureRegionDrawable(new TextureRegion(buttonTex));
        plusStyle = new TextButtonStyle();
        plusStyle.up = plusDrawable;
        plusStyle.down = plusDrawable;
        plusStyle.font = font;
        plusStyle.fontColor = Color.BLACK;

        // Popup style
        Pixmap pm2 = new Pixmap(400, 200, Pixmap.Format.RGBA8888);
        pm2.setColor(0f, 0f, 0f, 0.85f);
        pm2.fillRectangle(0, 0, 400, 200);
        Texture wndTex = new Texture(pm2);
        pm2.dispose();
        windowStyle = new WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(wndTex)));

        labelStyle = new LabelStyle(font, Color.WHITE);

        // Coloca imagem no centro com escala mantida
        Image bg = new Image(new TextureRegionDrawable(new TextureRegion(optionsTexture)));
        Image fnd = new Image(new TextureRegionDrawable(new TextureRegion(fundoTexture)));
        fnd.setPosition(0, 0);
        fnd.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        // dimensionar para ocupar 90% largura ou 80% altura, mantendo proporção
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();
        float imgW = optionsTexture.getWidth();
        float imgH = optionsTexture.getHeight();
        float scale = Math.min((screenW * 0.95f) / imgW, (screenH * 0.85f) / imgH);
        float drawW = imgW * scale;
        float drawH = imgH * scale;
        bg.setSize(drawW, drawH);
        bg.setPosition((screenW - drawW) / 2f, (screenH - drawH) / 2f);
        stage.addActor(fnd);
        stage.addActor(bg);

        // Criar botões + posicionados sobre a imagem
        for (int i = 0; i < jurados.length; i++) {
            final int idx = i;
            TextButton plus = new TextButton("+", plusStyle);
            plus.getLabel().setFontScale(1.4f);
            float btnSize = Math.min(drawW, drawH) * 0.05f;
            if (btnSize < 36) btnSize = 36;
            if (btnSize > 72) btnSize = 72;
            plus.setSize(btnSize, btnSize);

            // calcular posição em pixels
            float leftPercent = positions[i][0];
            float topPercent = positions[i][1];
            float x = bg.getX() + leftPercent * drawW - plus.getWidth() / 2f;
            float y = bg.getY() + (drawH - topPercent * drawH) - plus.getHeight() / 2f;
            plus.setPosition(x, y);

            plus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float px, float py) {
                    showJuradosPopup(jurados[idx]);
                }
            });

            stage.addActor(plus);
        }
    }

    private void showJuradosPopup(Jurado j) {
        // Criar janela (Window)
        final Window w = new Window(j.name, windowStyle);
        w.setSize(Math.min(Gdx.graphics.getWidth() * 0.8f, 520), Math.min(Gdx.graphics.getHeight() * 0.5f, 320));
        w.setPosition((Gdx.graphics.getWidth() - w.getWidth()) / 2f, (Gdx.graphics.getHeight() - w.getHeight()) / 2f);

        Label role = new Label(j.role, labelStyle);
        role.setAlignment(Align.left);
        // Não precisa de setWrap(true) aqui, o Cell fará o trabalho.

        Label desc = new Label(j.description, labelStyle);
        desc.setAlignment(Align.topLeft);
        desc.setWrap(true);

        // Criar o overlay (fundo semi-transparente)
        final Image overlay = createOverlay();
        overlay.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        overlay.setPosition(0, 0);
        
        // Listener para fechar o pop-up se clicar fora
        overlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float px, float py) {
                // Se clicar fora do pop-up, ele fecha
                w.remove();
                overlay.remove(); // Remove o overlay!
            }
        });
        
        // Adicionar o overlay ANTES da janela 'w'
        stage.addActor(overlay);

        // Botão Fechar
        TextButton close = new TextButton("Fechar", plusStyle);
        close.getLabel().setFontScale(0.9f);

        // Listener do botão Fechar
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                w.remove();
                overlay.remove(); // <--- CORREÇÃO PRINCIPAL: Remover o overlay
            }
        });

        // Layout da janela:
        w.pad(12);
        
        // Linha 1: Role
        w.add(role).left().expandX().fillX();
        w.row().padTop(8);
        
        // Linha 2: Descrição (Permite quebra de linha com .wrap())
        w.add(desc).left().top().expand().fillX();
        w.row();
        
        // Linha 3: Botão Fechar
        w.add(close).right().padTop(8).padBottom(8);

        // Adicionar a janela 'w' por cima do overlay
        stage.addActor(w);
    }

    private Image createOverlay() {
        Pixmap pm = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pm.setColor(0f, 0f, 0f, 0.5f);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return new Image(new TextureRegionDrawable(new TextureRegion(t)));
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(1/30f, delta));
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.draw();
        //shapeRenderer.rect(250, 830, 150, 60); 
        
        // Lógica de cliques
        if (com.badlogic.gdx.Gdx.input.justTouched()) {
            
            // Converte as coordenadas da tela (pixels) para o mundo (float)
            Vector2 touchPoint = new Vector2(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY());
            viewport.unproject(touchPoint); 

            float inputX = touchPoint.x;
            float inputY = touchPoint.y;

            // Verificação de clique na Fase 1
            if (inputX >= 250 && inputX <= 250 + 150 &&
                inputY >= 830 && inputY <= 830 + 60) {
                
                // Inicia a Fase 1 
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        optionsTexture.dispose();
        buttonTex.dispose();
        font.dispose();
    }
}

