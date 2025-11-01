package br.mackenzie;

public class FaseEspaco extends FaseBase {
    private final Main game;

    public FaseEspaco(Main game){
        super("Background-ParalaxFaseEspaco.png","GameMusic.mp3");
        this.game = game;
    }

    private void voltarAoMenu() {
        if (game != null) {
            game.setScreen(new Menu(game));
        }
    }
}
