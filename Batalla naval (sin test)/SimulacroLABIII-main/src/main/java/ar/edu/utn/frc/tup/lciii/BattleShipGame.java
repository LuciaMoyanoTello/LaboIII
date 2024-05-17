package ar.edu.utn.frc.tup.lciii;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static ar.edu.utn.frc.tup.lciii.Utils.print;

/**
 * Esta clase mantiene el estado de un juego.
 *
 * Un juego es una partida de las muchas que puede jugar el Player
 * en una misma corrida del programa.
 *
 */
public class BattleShipGame {

    /**
     * Expresion regular para validar entradas de posiciones
     */
    private static final String POSITION_INPUT_REGEX = "[0-9]{1} [0-9]{1}";

    /**
     * Numero de barcos requeridos para jugar
     */
    private static final Integer FLEET_SIZE = 20;

    /**
     * Scanner para capturar las entradas del usuario
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Jugador asignado al usuario
     */
    private Player player;

    /**
     * Jugador asignado a la app
     */
    private Player appPlayer;

    /**
     * Tablero de la flota del jugador
     */
    private Board playerFleetBoard;

    /**
     * Tablero de marcación de la flota enemiga del jugador
     */
    private Board playerEnemyFleetBoard;

    /**
     * Tablero de la flota de la app
     */
    private Board appFleetBoard;

    /**
     * Tablero de marcación de la flota enemiga de la app
     */
    private Board appEnemyFleetBoard;

    /**
     * Lista de los disparos efectuados por el jugador
     */
    private List<Position> playerShots;

    /**
     * Lista de los disparos efectuados por la app
     */
    private List<Position> appShots;

    /**
     * Lista de los barcos del jugador
     */
    private List<Ship> playerShips;

    /**
     * Lista de los barcos de la app
     */
    private List<Ship> appShips;

    /**
     * Jugador que gano el juego
     */
    public Player winner;
    // TODO: more attributes if necessary

    // TODO: getters & setters...

    // TODO: constructors if necessary...
    public BattleShipGame(Player player, Player appPlayer) {
        this.player = player;
        this.appPlayer = appPlayer;
        this.playerFleetBoard = new Board();
        this.playerEnemyFleetBoard = new Board();
        this.appFleetBoard = new Board();
        this.appEnemyFleetBoard = new Board();
        this.playerShots = new ArrayList<>();
        this.appShots = new ArrayList<>();
        this.playerShips = new ArrayList<>();
        this.appShips = new ArrayList<>();
        this.winner = null;
        this.playerFleetBoard.initBoardFleet();
        this.playerEnemyFleetBoard.initBoardEnemyFleet();
        this.appFleetBoard.initBoardFleet();
        this.appEnemyFleetBoard.initBoardEnemyFleet();
    }

    public BattleShipGame() {
    }

    /**
     * Este metodo genera una lista de posiciones aleatoria para
     * la flota de barcos con la que jugará la App.
     *
     * Este metodo valida que las posiciones de la cada barco de la flota es unica
     * y que se encuentra dentro de los margenes del tablero.
     *
     * Por cada barco de la flota debe agregarlo en la lista "appShips"
     *
     * @see #getPlayerFleetPositions()
     * @see #generateAppShot()
     * @see #getRandomPosition()
     *
     */
    public void generateAppFleetPositions() {
        do {
            playerEnemyFleetBoard.initBoardEnemyFleet();
            Position position = getRandomPosition();
            if(isAvailablePosition(appShips, position)) {
                this.appShips.add(new Ship(position, ShipStatus.AFLOAT));
            } else {
                // TODO: Mostrar un mensaje de error comentando que ya estableció esa posicion.
            }
            /*for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 2; j++) {
                    this.appShips.add(new Ship(new Position(i, j), ShipStatus.AFLOAT));
                }
            }*/
        } while (this.appShips.size() < 2);
        appFleetBoard.setShipPositions(appShips);
        // TODO: Hacer un bucle para generar las posiciones hasta alcanzar el limite
        // TODO: Validar si la posicion esta disponible en la lista de barcos.
        // TODO: Crear el barco y agregarlo a la lista de barcos.
        // TODO: Setear en el board la posicion del barco.

    }

    /**
     * Este metodo gestiona el pedido de posiciones de cada barco al jugador,
     * y los agrega en la lista "playerShips".
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero y que NO haya colocado ya
     * otro barco en dicha posicion.
     *
     * Cuando el usuario ha colocado todos los barcos (20 en total),
     * el metodo los posiciona en el tablero del usuario.
     *
     * @see #generateAppFleetPositions()
     * @see #getPlayerShot()
     *
     */
    public void getPlayerFleetPositions() {
        appEnemyFleetBoard.initBoardEnemyFleet();
        do {
            System.out.println("Donde quiere posicionar su barco?");
                Position position = this.getPosition();
                if(isAvailablePosition(playerShips, position)) {
                    this.playerShips.add(new Ship(position, ShipStatus.AFLOAT));
                } else {
                    // TODO: Mostrar un mensaje de error comentando que ya estableció esa posicion.
                    print("Ya hay un barco en esa posición!");
                }
        } while (this.playerShips.size() < 20);
        // TODO: Setear en el board la posicion del barco.
        playerFleetBoard.setShipPositions(playerShips);
    }

    /**
     * Este metodo gestiona la acción de disparar por parte del usuario.
     * Cuando el usuario estableció el disparo, debe agregarlo a la lista de
     * disparos realizados "playerShots" y cargarlo en su board de la flota enemiga "playerEnemyFleetBoard"
     * según haya derribado un barco o encontrado agua.
     *
     * Si el disparo alcanza un barco enemigo, se debe cambiar el barco de dicha posicion a ShipStatus.SUNKEN
     * mediante el metodo de Ship "sinkShip()"
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero.
     *
     * @see #getPlayerFleetPositions()
     * @see #impactEnemyShip(List, Position)
     *
     */
    public void getPlayerShot() {
        Position position = null;
        do {
            System.out.println("Donde quiere disparar?");
            position = this.getPosition();
            if(isAvailableShot(playerShots, position)) {
                this.playerShots.add(position);
                if (impactEnemyShip(appShips, position) == true) {
                    print("Barco Hundido!");
                    playerEnemyFleetBoard.setShipOnBoard(position);
                    player.setbarcosQueHundio(player.getbarcosQueHundio()+1);
                } else {
                    playerEnemyFleetBoard.setWaterOnBoard(position);
                }
            } else {
                System.out.println("Ya disparó a esa posición.!" +
                        System.lineSeparator() + "Elija otra posicion...");
            }
        } while (position == null);
        // TODO: Preguntar si la posicion del disparo impacto un barco enemigo.
        // TODO: Setear segun hubo un impacto o no, agua o un barco, en el tablero de marcacion de la flota enemiga
    }

    /**
     * Este metodo genera de manera aleatoria un disparo por parte de la app.
     * El metodo genera dos enteros entre 0 y 9 para definir las coordenadas
     * donde efectuará el disparo.
     *
     * El metodo valida que el disparo no se haya hecho antes de cargarlo en
     * la lista de disparos de la app.
     *
     * Cuando la app estableció el disparo, debe agregarlo a la lista de
     * disparos realizados "appShots" y cargarlo en su board de la flota enemiga "appEnemyFleetBoard"
     * según haya derribado un barco o encontrado agua.
     *
     * Si el disparo alcanza un barco enemigo, se debe cambiar el barco de dicha posicion a ShipStatus.SUNKEN
     * mediante el metodo de "ship.sinkShip()"
     *
     * @see #generateAppFleetPositions()
     * @see #getRandomPosition()
     */
    public void generateAppShot() {
        /*Position randomShot;
        if (appShots.size() <1) {
             randomShot = new Position(0, 1);
        } else {
            randomShot = new Position(0, 2);
        }*/
        Position randomShot = new Position();
        do {
            randomShot = getRandomPosition();
        } while (!this.isAvailableShot(appShots, randomShot));
        this.appShots.add(randomShot);
        if(this.impactEnemyShip(playerShips, randomShot)) {
            appEnemyFleetBoard.setShipOnBoard(randomShot);
            playerFleetBoard.setRedX(randomShot);
            appPlayer.setbarcosQueHundio(appPlayer.getbarcosQueHundio()+1);
            print("El enemigo te hundió un barco!");
        } else {
            appEnemyFleetBoard.setWaterOnBoard(randomShot);
            print("El enemigo falló!");
        }
        drawPlayerBoards();
    }

    /**
     * Este metodo imprime por pantalla el estado del juego, que incluye
     * cuantos barcos tiene cada jugar a flote y cuantos hundidos
     *
     * @see Player
     * @see #playerShips
     * @see #appShips
     * @see #player
     * @see #appPlayer
     *
     */
    public void printGameStatus() {
        // TODO: Imprimir por pantalla el status del juego
        // TODO: Incluir barcos flotando y hundidos de cada jugador
        int cantiShipsPlayer = playerShips.size();
        int cantiShipsApp = appShips.size();
        int cantiSunkenShipsPlayer = 0;
        int cantiSunkenShipsApp = 0;

        for (Ship s : playerShips) {
            if (s.getShipStatus() == ShipStatus.SUNKEN) {
                cantiSunkenShipsPlayer++;
            }
        }

        for (Ship s : appShips) {
            if (s.getShipStatus() == ShipStatus.SUNKEN) {
                cantiSunkenShipsApp++;
            }
        }
        print("Tenés "+cantiShipsPlayer+" naves en total y "+cantiSunkenShipsPlayer+" está/n hundida/s.\n");
        print("El enemigo tiene "+cantiShipsApp+" naves en total y "+cantiSunkenShipsApp+" está/n hundida/s.\n");
    }

    /**
     * Este metodo dibuja los tableros del Player junto al titulo de cada uno.
     *
     * @see Board#drawBoard()
     *
     */
    public void drawPlayerBoards() {
        System.out.println("TU FLOTA" + System.lineSeparator());
        // TODO: Dibujar el tablero del usuario
        playerFleetBoard.drawBoard();
        System.out.println("FLOTA ENEMIGA" + System.lineSeparator());
        // TODO: Dibujar el tablero de marcacion de la flota enemiga del usuario
        playerEnemyFleetBoard.drawBoard();
    }

    /**
     * Este metodo muestra un mensaje de finalizacion de la partida,
     * muestra el nombre del ganador, el puntaje obtenido en esta partida
     * y los puntajes acumulados a traves de las partidas jugadas.
     *
     * @see System#out
     */
    public void goodbyeMessage() {
        // TODO: Imprimir por pantalla un mensaje de despedida
    }

    /**
     * Este metodo calcula los puntos obtenidos por cada jugador en esta partida
     * y se los suma a los que ya traia de otras partidas.
     *
     * @see Player
     * @see #playerShips
     * @see #appShips
     * @see #player
     * @see #appPlayer
     *
     */
    public void calculateScores() {
        // TODO: Se debe completar este metodo
        // TODO: Calcular los puntos obtenidos por cada jugador en este juego
        // TODO: Sumar los puntos al score de cada jugador done
        // TODO: Sumar la partida ganada al jugador que ganó done
        if (winner == player) {
            print("El jugador '"+ player.getPlayerName()+"' ganó "+player.getGamesWon()+" juego/s," +
                    " consiguió "+player.getbarcosQueHundio()+" puntos en esta partida  y obtuvo "+player.getScore()+" puntos en total!");
            if (player.getScore()> appPlayer.getScore()) {
                int dif = player.getScore()-appPlayer.getScore();
                print("El jugador '"+player.getPlayerName()+"' ganó con "+dif+" más puntos que la APP!");
            } else {
                int dif = appPlayer.getScore()-player.getScore();
                print("La APP perdió con "+dif+" más puntos que el jugador '"+player.getPlayerName()+"'!");
            }
        } else if (winner == appPlayer) {
            print("La app ganó "+appPlayer.getGamesWon()+" juego/s," +
                    " consiguió "+appPlayer.getbarcosQueHundio()+" puntos en esta partida  y obtuvo "+appPlayer.getScore()+" puntos en total!");
            if (player.getScore()> appPlayer.getScore()) {
                int dif = player.getScore()-appPlayer.getScore();
                print("El jugador '"+player.getPlayerName()+"' perdió con "+dif+" más puntos que la APP!");
            } else {
                int dif = appPlayer.getScore()-player.getScore();
                print("La APP ganó con "+dif+" más puntos que el jugador '"+player.getPlayerName()+"'!");
            }
        }
    }

    /**
     * Este metodo verifica si hubo un impacto con el disparo,
     * si el disparo impacto, hunde el barco y retorna true.
     * Si el disparo no impacto retorna false
     *
     * @param fleetEnemyShips Lista de barcos de la flota enemiga
     * @param shot
     *
     * @see Position#equals(Object)
     * @see Ship#sinkShip()
     *
     * @return true si el disparo impacta, false si no lo hace.
     */
    private Boolean impactEnemyShip(List<Ship> fleetEnemyShips, Position shot) {
        for(Ship ship : fleetEnemyShips) {
            if(ship.getPosition().equals(shot)) {
                ship.sinkShip();
                return true;
            }
        }
        return false;
    }

    /**
     * Este metodo define si el juego terminó.
     * El juego termina cuando uno de los dos jugadores (El player o la app)
     * a hundido todos los barcos del contrario.
     *
     * Cuando el juego termina, este metodo setea en el atributo winner quien ganó.
     *
     * @see #validateSunkenFleet(List)
     * @see #winner
     *
     * @return true si el juego terminó, false si aun no hay un ganador
     */
    public Boolean isFinish() {
        // TODO: Validar si todos lo barcos de algun jugador fueron undidos
        // TODO: Si algun jugador ya perdio todos sus barcos, setear el ganador en winner
        // TODO: Retornar true si hubo un ganador, o false si no lo hubo
        int cantShipsPlayer = playerShips.size();
        int cantShipsApp = appShips.size();
        int cantSunkenShipsPlayer = 0;
        int cantSunkenShipsApp = 0;

        /*if (appPlayer.getGamesWon() == null) {
            appPlayer.setGamesWon(0);
        }
        if (appPlayer.getScore() == null) {
            appPlayer.setScore(0);
        }
        if (player.getGamesWon() == null) {
            player.setGamesWon(0);
        }
        if (player.getScore() == null) {
            player.setScore(0);
        }*/
        for (Ship s : appShips) {
            if (s.getShipStatus() == ShipStatus.SUNKEN) {
                cantSunkenShipsPlayer++;
            }
        }
        if (cantSunkenShipsPlayer == cantShipsApp) {
            winner = player;
            player.setGamesWon(player.getGamesWon()+1);
            player.setScore(player.getScore()+20);
            appPlayer.setScore(appPlayer.getScore()+appPlayer.getbarcosQueHundio());
            print("El jugador '"+player.getPlayerName()+"' ganó!");
            return true;
        }

        for (Ship s : playerShips) {
            if (s.getShipStatus() == ShipStatus.SUNKEN) {
                cantSunkenShipsApp++;
            }
        }
        if (cantSunkenShipsApp == cantShipsPlayer) {
            winner = appPlayer;
            appPlayer.setGamesWon(appPlayer.getGamesWon()+1);
            appPlayer.setScore(appPlayer.getScore()+20);
            player.setScore(player.getScore()+player.getbarcosQueHundio());
            print("La 'APP' ganó!");
            return true;
        }


        // TODO: Remember to replace the return statement with the correct object
        return false;
    }

    /**
     * Este metodo valida si aun queda algun barco a flote en la flota,
     * para determinar si toda la flota fue hundida.
     *
     * @param fleet
     *
     * @see Ship#getShipStatus()
     * @see ShipStatus
     *
     * @return true si toda la flota fue hundida, flase si al menos queda un barco a flote.
     */
    private Boolean validateSunkenFleet(List<Ship> fleet) {
        // TODO: Recorrer la lista de barcos y validar si todo fueron undidos
        // TODO: Retornar true si todos fueron undidos o false si al menos queda un barco a flote

        // TODO: Remember to replace the return statement with the correct object
        return null;
    }

    /**
     * Este metodo gestiona la acción de pedir coordenads al usuario.
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero.
     *
     * @see #isValidPositionInput(String)
     * @see Position#Position()
     * @see String#split(String)
     *
     * @return La posicion elegida por el usuario.
     */
    private Position getPosition() {
        Position position = null;
        do {
            System.out.println("Ingrese una coordenada en un formato de dos numeros " +
                    "enteros entre 0 y 9 separados por un espacio en blanco.");

            String input = scanner.nextLine();

            if (isValidPositionInput(input)) {
                //TODO: Separar los enteros de input en dos Integers y crear Position
                String[] coordinates = input.split(" ");
                int row = Integer.parseInt(coordinates[0]);
                int column = Integer.parseInt(coordinates[1]);

                // Create a new Position object
                position = new Position(row, column);
            } else {
                // TODO: Mostrar un mensaje de error sobre como ingreso los datos
                print("*********************************************************************************" +
                      "\nIngresó las coordenadas en un formato distinto al solicitado, intente nuevamente." +
                      "\n*********************************************************************************\n");
            }
        } while(position == null);
        return position;
    }

    /**
     * Este metodo valida que la entrada del usuario como String este en el formato establecido
     * de dos numeros enteros entre 0 y 9 separados por un espacio en blanco.
     *
     * @see BattleShipMatch#getYesNoAnswer(String)
     * @see Pattern#compile(String)
     * @see Pattern#matcher(CharSequence)
     * @see Matcher#matches()
     *
     * @param input La entrada que se capturo del usuario
     * @return true si el formato es valido, false si no lo es
     */
    private Boolean isValidPositionInput(String input) {
        Pattern p = Pattern.compile(POSITION_INPUT_REGEX);
        Matcher m = p.matcher(input);
        return m.matches();
        // TODO: Crear el objeto Pattern a partir de la expresion regular provista
        // TODO: Validar si input hace match con la expresion regular
        // TODO: Retornar true si hace match, o false si no lo hace.

        // TODO: Remember to replace the return statement with the correct object
        //return null;
    }

    /**
     * Este metodo retorna una posición random que puede ser usada
     * para representar una posicion de un barco o un disparo random de la app.
     *
     * @see Random
     * @see Random#nextInt(int)
     * @see Position
     *
     * @return la posición del disparo random.
     */
    private Position getRandomPosition() {
        // TODO: Generar 2 numeros random entre 0 y 9 para establecer la row y column
        Random r = new Random();
        int rowRandom = r.nextInt(9);
        int colRandom = r.nextInt(9);
        // TODO: Crear la Posicion a partir de la row y column
        Position newPosition = new Position(rowRandom, colRandom);
        // TODO: Retornar la Position
        // TODO: Remember to replace the return statement with the correct object
        return newPosition;
    }

    /**
     * Este metodo valida que la posición nueva no exista en la lista de posiciones que ya fueron cargadas
     * para eso recibe por parametro la lista donde hará la busqueda y la posicion a buscar.
     *
     * El metodo otorga un mecanismo de validacion de que un objeto del tipo Position
     * no existe en una lista del tipo List<Position>
     *
     * @see List#contains(Object)
     * @see Position#equals(Object)
     *
     * @param listShots la lista donde se hará la busqueda
     * @param position La nueva posicion a buscar
     * @return true si la posición no existe en la lista, false si ya existe esa posicion.
     */
    private Boolean isAvailableShot(List<Position> listShots, Position position) {
        // TODO: Validar que la lista de posiciones NO tenga la posision indicada
        return listShots.stream().noneMatch(posit -> posit.equals(position));
        // TODO: Remember to replace the return statement with the correct object
    }

    /**
     * Este metodo valida que la posición pasada por parametro no exista dentro de las
     * posiciones de los barcos de la lista "listToCheck".
     *
     * @param listToCheck la lista donde se hará la busqueda
     * @param position La nueva posicion a buscar
     *
     * @see List#contains(Object)
     * @see Ship#equals(Object)
     *
     * @return true si la posición no existe en la lista, false si ya existe esa posicion.
     */
    private Boolean isAvailablePosition(List<Ship> listToCheck, Position position) {
        // TODO: Validar que la lista de Ship NO tenga un Ship con la posision indicada
        return listToCheck.stream().noneMatch(ship -> ship.getPosition().equals(position));
        /*int check = 0;
        for (Ship ship : listToCheck) {
            if (ship.getPosition().equals(position)) {
                check++;
            }
        }
        return check == 0;*/
        // TODO: Remember to replace the return statement with the correct object
    }
}
