package hlx.gameoperator;

import com.huluxia.mcinterface.j;
import java.util.List;

/* compiled from: BuildOperator */
public class a {
    private static final boolean bOR = false;

    public static void b(List<j> inList, int inputX, int inputY, int inputZ, int inputId, int inputDmg, int axes, int inputLength) {
        int[] ZxesUnit = new int[]{0, 0, 0};
        if (axes <= ZxesUnit.length && axes >= 1) {
            ZxesUnit[axes - 1] = 1;
            for (int i = 0; i < inputLength; i++) {
                inList.add(new j(inputId, inputDmg, inputX + (ZxesUnit[0] * i), inputY + (ZxesUnit[1] * i), inputZ + (ZxesUnit[2] * i)));
            }
        }
    }

    public static void a(List<j> inList, int inputX, int inputY, int inputZ, int inputId, int inputDmg, int increWidth, int increLength) {
        for (int i = 0; i < increWidth; i++) {
            for (int j = 0; j < increLength; j++) {
                inList.add(new j(inputId, inputDmg, inputX + j, inputY, inputZ + i));
            }
        }
    }

    public static void a(List<j> inList, int inputX, int inputY, int inputZ, int inputId, int inputDmg, int axes, int inputLength, int inputHigh) {
        int[] ZxesUnit = new int[]{0, 0};
        if (axes <= ZxesUnit.length && axes >= 1) {
            ZxesUnit[axes - 1] = 1;
            for (int i = 0; i < inputHigh; i++) {
                for (int j = 0; j < inputLength; j++) {
                    inList.add(new j(inputId, inputDmg, inputX + (ZxesUnit[0] * j), inputY + i, inputZ + (ZxesUnit[1] * j)));
                }
            }
        }
    }

    public static void b(List<j> inList, int inputX, int inputY, int inputZ, int inputId, int inputDmg, int inputWidth, int inputLength, int inputHigh) {
        for (int increHigh = 0; increHigh < inputHigh; increHigh++) {
            for (int increWidth = 0; increWidth < inputWidth; increWidth++) {
                for (int increLength = 0; increLength < inputLength; increLength++) {
                    inList.add(new j(inputId, inputDmg, inputX + increLength, inputY + increHigh, inputZ + increWidth));
                }
            }
        }
    }

    public static void a(List<j> inList, int inputX, int inputY, int inputZ, int inputSurfaceId, int inputSurfaceDmg, int inputCoreId, int inputCoreDmg, int inputWidth, int inputLength, int inputHigh) {
        int coreWidth = inputWidth - 1;
        int coreLength = inputLength - 1;
        int coreHigh = inputHigh - 1;
        a(inList, inputX, (inputY + inputHigh) - 1, inputZ, inputSurfaceId, inputSurfaceDmg, inputWidth, inputLength);
        a(inList, inputX, inputY, inputZ, inputSurfaceId, inputSurfaceDmg, inputWidth, inputLength);
        a(inList, inputX, inputY + 1, inputZ, inputSurfaceId, inputSurfaceDmg, 2, inputWidth - 1, inputHigh - 2);
        a(inList, inputX, inputY + 1, (inputZ + inputWidth) - 1, inputSurfaceId, inputSurfaceDmg, 1, inputLength - 1, inputHigh - 2);
        a(inList, (inputX + inputLength) - 1, inputY + 1, inputZ + 1, inputSurfaceId, inputSurfaceDmg, 2, inputWidth - 1, inputHigh - 2);
        a(inList, inputX + 1, inputY + 1, inputZ, inputSurfaceId, inputSurfaceDmg, 1, inputLength - 1, inputHigh - 2);
        for (int increHigh = 1; increHigh < coreHigh; increHigh++) {
            for (int increWidth = 1; increWidth < coreWidth; increWidth++) {
                for (int increLength = 1; increLength < coreLength; increLength++) {
                    inList.add(new j(inputCoreId, inputCoreDmg, inputX + increLength, inputY + increHigh, inputZ + increWidth));
                }
            }
        }
    }

    public static void c(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int semimajor, int semiminor) {
        double sqa = (double) (semimajor * semimajor);
        double sqb = (double) (semiminor * semiminor);
        double d = sqb + ((((double) (-semiminor)) + 0.25d) * sqa);
        int x = 0;
        int y = semiminor;
        List<j> list = inList;
        list.add(new j(marginId, marginDmg, inputX + 0, inputY, inputZ + y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX + 0, inputY, inputZ - y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX - 0, inputY, inputZ + y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX - 0, inputY, inputZ - y));
        while (((double) (x + 1)) * sqb < (((double) y) - 0.5d) * sqa) {
            if (d < 0.0d) {
                d += ((double) ((x * 2) + 3)) * sqb;
            } else {
                d += (((double) ((x * 2) + 3)) * sqb) + (((double) ((y * -2) + 2)) * sqa);
                y--;
            }
            x++;
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ - y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ - y));
        }
        d = (((((double) semiminor) * (((double) x) + 0.5d)) * 2.0d) + ((double) (((y - 1) * semimajor) * 2))) - ((double) ((semimajor * semiminor) * 2));
        while (y > 0) {
            if (d < 0.0d) {
                d += (((double) ((x * 2) + 2)) * sqb) + (((double) ((y * -2) + 3)) * sqa);
                x++;
            } else {
                d += ((double) ((y * -2) + 3)) * sqa;
            }
            y--;
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ - y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ - y));
        }
    }

    public static void a(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int inputCoreId, int inputCoreDmg, int semimajor, int semiminor) {
        double sqa = (double) (semimajor * semimajor);
        double sqb = (double) (semiminor * semiminor);
        double d = sqb + ((((double) (-semiminor)) + 0.25d) * sqa);
        int x = 0;
        int y = semiminor;
        List<j> list = inList;
        list.add(new j(marginId, marginDmg, inputX + 0, inputY, inputZ + y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX + 0, inputY, inputZ - y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX - 0, inputY, inputZ + y));
        list = inList;
        list.add(new j(marginId, marginDmg, inputX - 0, inputY, inputZ - y));
        List<j> list2 = inList;
        b(list2, inputX + 0, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
        list2 = inList;
        b(list2, inputX - 0, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
        while (((double) (x + 1)) * sqb < (((double) y) - 0.5d) * sqa) {
            if (d < 0.0d) {
                d += ((double) ((x * 2) + 3)) * sqb;
            } else {
                d += (((double) ((x * 2) + 3)) * sqb) + (((double) ((y * -2) + 2)) * sqa);
                y--;
            }
            x++;
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ - y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ - y));
            list2 = inList;
            b(list2, inputX + x, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
            list2 = inList;
            b(list2, inputX - x, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
        }
        d = (((((double) semiminor) * (((double) x) + 0.5d)) * 2.0d) + ((double) (((y - 1) * semimajor) * 2))) - ((double) ((semimajor * semiminor) * 2));
        while (y > 0) {
            if (d < 0.0d) {
                d += (((double) ((x * 2) + 2)) * sqb) + (((double) ((y * -2) + 3)) * sqa);
                x++;
            } else {
                d += ((double) ((y * -2) + 3)) * sqa;
            }
            y--;
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX + x, inputY, inputZ - y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ + y));
            list = inList;
            list.add(new j(marginId, marginDmg, inputX - x, inputY, inputZ - y));
            list2 = inList;
            b(list2, inputX + x, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
            list2 = inList;
            b(list2, inputX - x, inputY, (inputZ - y) + 1, inputCoreId, inputCoreDmg, 3, (y * 2) - 1);
        }
    }

    public static void c(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int semimajor, int semiminor, int height) {
        for (int i = 0; i < height; i++) {
            c(inList, inputX, inputY + i, inputZ, marginId, marginDmg, semimajor, semiminor);
        }
    }

    public static void b(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int coreId, int coreDmg, int semimajor, int semiminor, int height) {
        a(inList, inputX, inputY, inputZ, marginId, marginDmg, marginId, marginDmg, semimajor, semiminor);
        for (int i = 1; i < height - 1; i++) {
            a(inList, inputX, inputY + i, inputZ, marginId, marginDmg, coreId, coreDmg, semimajor, semiminor);
        }
        a(inList, inputX, (inputY + height) - 1, inputZ, marginId, marginDmg, marginId, marginDmg, semimajor, semiminor);
    }

    public static void c(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int coreId, int coreDmg, int semimajor, int semiminor, int height) {
        for (int i = 0; i < height; i++) {
            a(inList, inputX, inputY + i, inputZ, marginId, marginDmg, coreId, coreDmg, semimajor, semiminor);
        }
    }

    public static void d(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int coreId, int coreDmg, int semimajor, int semiminor, int height) {
        for (int i = 0; i < height; i++) {
            a(inList, inputX, inputY + i, inputZ, marginId, marginDmg, coreId, coreDmg, semimajor, semiminor);
        }
    }

    public static void d(List<j> inList, int inputX, int inputY, int inputZ, int marginId, int marginDmg, int semimajor, int semiminor, int height) {
        a(inList, inputX, inputY, inputZ, marginId, marginDmg, marginId, marginDmg, semimajor, semiminor);
        for (int i = 1; i < height - 1; i++) {
            c(inList, inputX, inputY + i, inputZ, marginId, marginDmg, semimajor, semiminor);
        }
        a(inList, inputX, (inputY + height) - 1, inputZ, marginId, marginDmg, marginId, marginDmg, semimajor, semiminor);
    }

    public static void RH() {
    }
}
