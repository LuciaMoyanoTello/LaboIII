package ar.edu.utn.frc.tup.lciii;

/**
 * Esta clase representa un jugador
 *
 * Puede instanciarde como el usuario que jugará y como la app.
 */
public class Player {

    /**
     * Name of the player
     */
    private String playerName;

    /**
     * Accumulated score of the match
     */
    private Integer score;

    /**
     * Accumulated games won
     */
    private Integer gamesWon;

    private Integer barcosQueHundio;
    // TODO: more attributes if necessary

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getbarcosQueHundio() {
        return barcosQueHundio;
    }

    public void setbarcosQueHundio(Integer barcosQueHundio) {
        this.barcosQueHundio = barcosQueHundio;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }


    // TODO: getters & setters...

    // TODO: constructors if necessary...


    public Player() {
        this.barcosQueHundio = 0;
    }

    public Player(String playerName, Integer score, Integer gamesWon, Integer barcosQueHundio) {
        this.playerName = playerName;
        this.score = score;
        this.gamesWon = gamesWon;
        this.barcosQueHundio = barcosQueHundio;
    }
}
