
package controls.exceptions;

public class PageNotLoadedException extends RuntimeException {

    public PageNotLoadedException() {
        super();
    }

    public PageNotLoadedException(String s) {
        super(s);
    }
}
