package pidro.exception;

public class TeamIsFullException extends Exception {

    @Override
    public String toString() {
        return "TeamIsFullException: The current team already has two members!";
    }
}
