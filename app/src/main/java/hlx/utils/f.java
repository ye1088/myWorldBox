package hlx.utils;

/* compiled from: NumerationUtils */
public class f {
    public static int R(float objFloat) {
        int tmp = (int) objFloat;
        if (((double) objFloat) >= ((double) tmp) + 0.5d) {
            return tmp + 1;
        }
        return tmp;
    }
}
