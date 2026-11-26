package exceptions;

public class WithdrawalLimitException extends AccountException {

    public WithdrawalLimitException(String message) {
        super(message);
    }
}