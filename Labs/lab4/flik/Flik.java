package flik;

/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
        // the origin code use `==` rather than equals
        // `==` check if a and b are pointing at the same object
        // Integer constant pool: java will cache [-128, 127]
        // so `a == b` will fail when they are 128
        return a.equals(b);
    }
}
