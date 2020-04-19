package pidro.exception;

public class GameNotReadyException extends Exception {

    @Override
    public String toString() {
        return "GameNotReadyException: The game is not ready yet. Teams may not be complete.";
    }
}
