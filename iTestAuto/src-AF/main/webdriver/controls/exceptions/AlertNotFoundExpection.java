

package controls.exceptions;

public class AlertNotFoundExpection extends RuntimeException {

    public AlertNotFoundExpection() {
        super();
    }

    public AlertNotFoundExpection(String s) {
        super(s);
    }
}
