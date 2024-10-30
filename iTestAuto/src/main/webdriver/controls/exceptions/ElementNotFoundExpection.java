

package controls.exceptions;

public class ElementNotFoundExpection extends RuntimeException {

    public ElementNotFoundExpection() {
        super();
    }

    public ElementNotFoundExpection(String s) {
        super(s);
    }
}
