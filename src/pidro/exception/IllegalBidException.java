package pidro.exception;

public class IllegalBidException extends Exception {

    @Override
    public String toString() {
        return "IllegalBidException: The given bid is not acceptable in any case.";
    }
}
