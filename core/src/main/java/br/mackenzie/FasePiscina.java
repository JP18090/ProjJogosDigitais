package br.mackenzie;

public class FasePiscina extends FaseBase {

    private final Main game;

    public FasePiscina(Main game) {
        super("Background-ParalaxFasePiscina.jpeg", "GameMusic.mp3");
        this.game = game;
    }

    private void voltarAoMenu() {
        if (game != null) {
            game.setScreen(new Menu(game));
        }
    }

}
