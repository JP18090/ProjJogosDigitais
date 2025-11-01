package br.mackenzie;

public class FaseRio extends FaseBase {

    private final Main game;

    public FaseRio(Main game){
        super("Background-ParalaxFaseRio.png","GameMusic.mp3");
        this.game = game;
    }

    private void voltarAoMenu() {
        if (game != null) {
            game.setScreen(new Menu(game));
        }
    }
}
