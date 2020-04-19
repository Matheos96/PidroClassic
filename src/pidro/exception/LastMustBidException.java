package pidro.exception;

public class LastMustBidException extends Exception{
    @Override
    public String toString() {
        return "LastMustBidException: The last player must bid a minimum of 6 if nothing has been bid yet!";
    }
}
