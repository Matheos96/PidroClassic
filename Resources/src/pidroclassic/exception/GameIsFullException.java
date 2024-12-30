package pidroclassic.exception;

public class GameIsFullException extends Exception {

    @Override
    public String toString() {
        return "GameIsFullException: This table is unfortunately full.";
    }
}
