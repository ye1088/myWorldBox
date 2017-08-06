package io.netty.handler.codec.compression;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;

final class Bzip2DivSufSort {
    private static final int BUCKET_A_SIZE = 256;
    private static final int BUCKET_B_SIZE = 65536;
    private static final int INSERTIONSORT_THRESHOLD = 8;
    private static final int[] LOG_2_TABLE = new int[]{-1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
    private static final int SS_BLOCKSIZE = 1024;
    private static final int STACK_SIZE = 64;
    private final int[] SA;
    private final byte[] T;
    private final int n;

    private static class PartitionResult {
        final int first;
        final int last;

        PartitionResult(int first, int last) {
            this.first = first;
            this.last = last;
        }
    }

    private static class StackEntry {
        final int a;
        final int b;
        final int c;
        final int d;

        StackEntry(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    private static class TRBudget {
        int budget;
        int chance;

        TRBudget(int budget, int chance) {
            this.budget = budget;
            this.chance = chance;
        }

        boolean update(int size, int n) {
            this.budget -= n;
            if (this.budget <= 0) {
                int i = this.chance - 1;
                this.chance = i;
                if (i == 0) {
                    return false;
                }
                this.budget += size;
            }
            return true;
        }
    }

    Bzip2DivSufSort(byte[] block, int[] bwtBlock, int blockLength) {
        this.T = block;
        this.SA = bwtBlock;
        this.n = blockLength;
    }

    private static void swapElements(int[] array1, int idx1, int[] array2, int idx2) {
        int temp = array1[idx1];
        array1[idx1] = array2[idx2];
        array2[idx2] = temp;
    }

    private int ssCompare(int p1, int p2, int depth) {
        int[] SA = this.SA;
        byte[] T = this.T;
        int U1n = SA[p1 + 1] + 2;
        int U2n = SA[p2 + 1] + 2;
        int U1 = depth + SA[p1];
        int U2 = depth + SA[p2];
        while (U1 < U1n && U2 < U2n && T[U1] == T[U2]) {
            U1++;
            U2++;
        }
        return U1 < U1n ? U2 < U2n ? (T[U1] & 255) - (T[U2] & 255) : 1 : U2 < U2n ? -1 : 0;
    }

    private int ssCompareLast(int pa, int p1, int p2, int depth, int size) {
        int[] SA = this.SA;
        byte[] T = this.T;
        int U1 = depth + SA[p1];
        int U2 = depth + SA[p2];
        int U1n = size;
        int U2n = SA[p2 + 1] + 2;
        while (U1 < U1n && U2 < U2n && T[U1] == T[U2]) {
            U1++;
            U2++;
        }
        if (U1 < U1n) {
            if (U2 < U2n) {
                return (T[U1] & 255) - (T[U2] & 255);
            }
            return 1;
        } else if (U2 == U2n) {
            return 1;
        } else {
            U1 %= size;
            U1n = SA[pa] + 2;
            while (U1 < U1n && U2 < U2n && T[U1] == T[U2]) {
                U1++;
                U2++;
            }
            if (U1 >= U1n) {
                return U2 < U2n ? -1 : 0;
            } else {
                if (U2 < U2n) {
                    return (T[U1] & 255) - (T[U2] & 255);
                }
                return 1;
            }
        }
    }

    private void ssInsertionSort(int pa, int first, int last, int depth) {
        int[] SA = this.SA;
        for (int i = last - 2; first <= i; i--) {
            int t = SA[i];
            int j = i + 1;
            do {
                int r = ssCompare(pa + t, SA[j] + pa, depth);
                if (r <= 0) {
                    break;
                }
                do {
                    SA[j - 1] = SA[j];
                    j++;
                    if (j >= last) {
                        break;
                    }
                } while (SA[j] < 0);
                continue;
            } while (last > j);
            if (r == 0) {
                SA[j] = SA[j] ^ -1;
            }
            SA[j - 1] = t;
        }
    }

    private void ssFixdown(int td, int pa, int sa, int i, int size) {
        int i2;
        int j;
        int[] SA = this.SA;
        byte[] T = this.T;
        int v = SA[sa + i];
        int c = T[SA[pa + v] + td] & 255;
        while (true) {
            i2 = (i * 2) + 1;
            if (i2 >= size) {
                break;
            }
            j = i2 + 1;
            int k = i2;
            int d = T[SA[SA[sa + i2] + pa] + td] & 255;
            int e = T[SA[SA[sa + j] + pa] + td] & 255;
            if (d < e) {
                k = j;
                d = e;
            }
            if (d <= c) {
                break;
            }
            SA[sa + i] = SA[sa + k];
            i = k;
        }
        i2 = j;
        SA[sa + i] = v;
    }

    private void ssHeapSort(int td, int pa, int sa, int size) {
        int i;
        int[] SA = this.SA;
        byte[] T = this.T;
        int m = size;
        if (size % 2 == 0) {
            m--;
            if ((T[SA[SA[(m / 2) + sa] + pa] + td] & 255) < (T[SA[SA[sa + m] + pa] + td] & 255)) {
                swapElements(SA, sa + m, SA, (m / 2) + sa);
            }
        }
        for (i = (m / 2) - 1; i >= 0; i--) {
            ssFixdown(td, pa, sa, i, m);
        }
        if (size % 2 == 0) {
            swapElements(SA, sa, SA, sa + m);
            ssFixdown(td, pa, sa, 0, m);
        }
        for (i = m - 1; i > 0; i--) {
            int t = SA[sa];
            SA[sa] = SA[sa + i];
            ssFixdown(td, pa, sa, 0, i);
            SA[sa + i] = t;
        }
    }

    private int ssMedian3(int td, int pa, int v1, int v2, int v3) {
        int[] SA = this.SA;
        byte[] T = this.T;
        int T_v1 = T[SA[SA[v1] + pa] + td] & 255;
        int T_v2 = T[SA[SA[v2] + pa] + td] & 255;
        int T_v3 = T[SA[SA[v3] + pa] + td] & 255;
        if (T_v1 > T_v2) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
            int T_vtemp = T_v1;
            T_v1 = T_v2;
            T_v2 = T_vtemp;
        }
        if (T_v2 > T_v3) {
            return T_v1 > T_v3 ? v1 : v3;
        } else {
            return v2;
        }
    }

    private int ssMedian5(int td, int pa, int v1, int v2, int v3, int v4, int v5) {
        int[] SA = this.SA;
        byte[] T = this.T;
        int T_v1 = T[SA[SA[v1] + pa] + td] & 255;
        int T_v2 = T[SA[SA[v2] + pa] + td] & 255;
        int T_v3 = T[SA[SA[v3] + pa] + td] & 255;
        int T_v4 = T[SA[SA[v4] + pa] + td] & 255;
        int T_v5 = T[SA[SA[v5] + pa] + td] & 255;
        if (T_v2 > T_v3) {
            int temp = v2;
            v2 = v3;
            v3 = temp;
            int T_vtemp = T_v2;
            T_v2 = T_v3;
            T_v3 = T_vtemp;
        }
        if (T_v4 > T_v5) {
            temp = v4;
            v4 = v5;
            v5 = temp;
            T_vtemp = T_v4;
            T_v4 = T_v5;
            T_v5 = T_vtemp;
        }
        if (T_v2 > T_v4) {
            v4 = v2;
            T_v4 = T_v2;
            temp = v3;
            v3 = v5;
            v5 = temp;
            T_vtemp = T_v3;
            T_v3 = T_v5;
            T_v5 = T_vtemp;
        }
        if (T_v1 > T_v3) {
            temp = v1;
            v1 = v3;
            v3 = temp;
            T_vtemp = T_v1;
            T_v1 = T_v3;
            T_v3 = T_vtemp;
        }
        if (T_v1 > T_v4) {
            v4 = v1;
            T_v4 = T_v1;
            v3 = v5;
            T_v3 = T_v5;
        }
        return T_v3 > T_v4 ? v4 : v3;
    }

    private int ssPivot(int td, int pa, int first, int last) {
        int t = last - first;
        int middle = first + (t / 2);
        if (t > 512) {
            t >>= 3;
            return ssMedian3(td, pa, ssMedian3(td, pa, first, first + t, first + (t << 1)), ssMedian3(td, pa, middle - t, middle, middle + t), ssMedian3(td, pa, (last - 1) - (t << 1), (last - 1) - t, last - 1));
        } else if (t <= 32) {
            return ssMedian3(td, pa, first, middle, last - 1);
        } else {
            t >>= 2;
            return ssMedian5(td, pa, first, first + t, middle, (last - 1) - t, last - 1);
        }
    }

    private static int ssLog(int n) {
        return (MotionEventCompat.ACTION_POINTER_INDEX_MASK & n) != 0 ? LOG_2_TABLE[(n >> 8) & 255] + 8 : LOG_2_TABLE[n & 255];
    }

    private int ssSubstringPartition(int pa, int first, int last, int depth) {
        int[] SA = this.SA;
        int a = first - 1;
        int b = last;
        while (true) {
            a++;
            if (a >= b || SA[SA[a] + pa] + depth < SA[(SA[a] + pa) + 1] + 1) {
                b--;
                while (a < b && SA[SA[b] + pa] + depth < SA[(SA[b] + pa) + 1] + 1) {
                    b--;
                }
                if (b <= a) {
                    break;
                }
                int t = SA[b] ^ -1;
                SA[b] = SA[a];
                SA[a] = t;
            } else {
                SA[a] = SA[a] ^ -1;
            }
        }
        if (first < a) {
            SA[first] = SA[first] ^ -1;
        }
        return a;
    }

    private void ssMultiKeyIntroSort(int pa, int first, int last, int depth) {
        int[] SA = this.SA;
        byte[] T = this.T;
        StackEntry[] stack = new StackEntry[64];
        int x = 0;
        int limit = ssLog(last - first);
        int ssize = 0;
        while (true) {
            if (last - first <= 8) {
                if (1 < last - first) {
                    ssInsertionSort(pa, first, last, depth);
                }
                if (ssize != 0) {
                    int ssize2 = ssize - 1;
                    StackEntry entry = stack[ssize2];
                    first = entry.a;
                    last = entry.b;
                    depth = entry.c;
                    limit = entry.d;
                    ssize = ssize2;
                } else {
                    return;
                }
            }
            int Td = depth;
            int limit2 = limit - 1;
            if (limit == 0) {
                ssHeapSort(Td, pa, first, last - first);
            }
            int a;
            int v;
            if (limit2 < 0) {
                a = first + 1;
                v = T[SA[SA[first] + pa] + Td] & 255;
                while (a < last) {
                    x = T[SA[SA[a] + pa] + Td] & 255;
                    if (x != v) {
                        if (1 < a - first) {
                            break;
                        }
                        v = x;
                        first = a;
                    }
                    a++;
                }
                if ((T[(SA[SA[first] + pa] + Td) - 1] & 255) < v) {
                    first = ssSubstringPartition(pa, first, a, depth);
                }
                if (a - first <= last - a) {
                    if (1 < a - first) {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(a, last, depth, -1);
                        last = a;
                        depth++;
                        limit = ssLog(a - first);
                        ssize = ssize2;
                    } else {
                        first = a;
                        limit = -1;
                    }
                } else if (1 < last - a) {
                    ssize2 = ssize + 1;
                    stack[ssize] = new StackEntry(first, a, depth + 1, ssLog(a - first));
                    first = a;
                    limit = -1;
                    ssize = ssize2;
                } else {
                    last = a;
                    depth++;
                    limit = ssLog(a - first);
                }
            } else {
                a = ssPivot(Td, pa, first, last);
                v = T[SA[SA[a] + pa] + Td] & 255;
                swapElements(SA, first, SA, a);
                int b = first + 1;
                while (b < last) {
                    x = T[SA[SA[b] + pa] + Td] & 255;
                    if (x != v) {
                        break;
                    }
                    b++;
                }
                a = b;
                if (b < last && x < v) {
                    while (true) {
                        b++;
                        if (b >= last) {
                            break;
                        }
                        x = T[SA[SA[b] + pa] + Td] & 255;
                        if (x > v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, b, SA, a);
                            a++;
                        }
                    }
                }
                int c = last - 1;
                while (b < c) {
                    x = T[SA[SA[c] + pa] + Td] & 255;
                    if (x != v) {
                        break;
                    }
                    c--;
                }
                int d = c;
                if (b < c && x > v) {
                    while (true) {
                        c--;
                        if (b >= c) {
                            break;
                        }
                        x = T[SA[SA[c] + pa] + Td] & 255;
                        if (x < v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, c, SA, d);
                            d--;
                        }
                    }
                }
                while (b < c) {
                    swapElements(SA, b, SA, c);
                    while (true) {
                        b++;
                        if (b >= c) {
                            break;
                        }
                        x = T[SA[SA[b] + pa] + Td] & 255;
                        if (x > v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, b, SA, a);
                            a++;
                        }
                    }
                    while (true) {
                        c--;
                        if (b >= c) {
                            break;
                        }
                        x = T[SA[SA[c] + pa] + Td] & 255;
                        if (x < v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, c, SA, d);
                            d--;
                        }
                    }
                }
                if (a <= d) {
                    c = b - 1;
                    int s = a - first;
                    int t = b - a;
                    if (s > t) {
                        s = t;
                    }
                    int e = first;
                    int f = b - s;
                    while (s > 0) {
                        swapElements(SA, e, SA, f);
                        s--;
                        e++;
                        f++;
                    }
                    s = d - c;
                    t = (last - d) - 1;
                    if (s > t) {
                        s = t;
                    }
                    e = b;
                    f = last - s;
                    while (s > 0) {
                        swapElements(SA, e, SA, f);
                        s--;
                        e++;
                        f++;
                    }
                    a = first + (b - a);
                    c = last - (d - c);
                    b = v <= (T[(SA[SA[a] + pa] + Td) + -1] & 255) ? a : ssSubstringPartition(pa, a, c, depth);
                    if (a - first <= last - c) {
                        if (last - c <= c - b) {
                            ssize2 = ssize + 1;
                            stack[ssize] = new StackEntry(b, c, depth + 1, ssLog(c - b));
                            ssize = ssize2 + 1;
                            stack[ssize2] = new StackEntry(c, last, depth, limit2);
                            last = a;
                            limit = limit2;
                        } else if (a - first <= c - b) {
                            ssize2 = ssize + 1;
                            stack[ssize] = new StackEntry(c, last, depth, limit2);
                            ssize = ssize2 + 1;
                            stack[ssize2] = new StackEntry(b, c, depth + 1, ssLog(c - b));
                            last = a;
                            limit = limit2;
                        } else {
                            ssize2 = ssize + 1;
                            stack[ssize] = new StackEntry(c, last, depth, limit2);
                            ssize = ssize2 + 1;
                            stack[ssize2] = new StackEntry(first, a, depth, limit2);
                            first = b;
                            last = c;
                            depth++;
                            limit = ssLog(c - b);
                        }
                    } else if (a - first <= c - b) {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(b, c, depth + 1, ssLog(c - b));
                        ssize = ssize2 + 1;
                        stack[ssize2] = new StackEntry(first, a, depth, limit2);
                        first = c;
                        limit = limit2;
                    } else if (last - c <= c - b) {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(first, a, depth, limit2);
                        ssize = ssize2 + 1;
                        stack[ssize2] = new StackEntry(b, c, depth + 1, ssLog(c - b));
                        first = c;
                        limit = limit2;
                    } else {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(first, a, depth, limit2);
                        ssize = ssize2 + 1;
                        stack[ssize2] = new StackEntry(c, last, depth, limit2);
                        first = b;
                        last = c;
                        depth++;
                        limit = ssLog(c - b);
                    }
                } else {
                    limit2++;
                    if ((T[(SA[SA[first] + pa] + Td) - 1] & 255) < v) {
                        first = ssSubstringPartition(pa, first, last, depth);
                        limit2 = ssLog(last - first);
                    }
                    depth++;
                    limit = limit2;
                }
            }
        }
    }

    private static void ssBlockSwap(int[] array1, int first1, int[] array2, int first2, int size) {
        int i = size;
        int a = first1;
        int b = first2;
        while (i > 0) {
            swapElements(array1, a, array2, b);
            i--;
            a++;
            b++;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void ssMergeForward(int r16, int[] r17, int r18, int r19, int r20, int r21, int r22) {
        /*
        r15 = this;
        r3 = r15.SA;
        r13 = r20 - r19;
        r13 = r13 + r18;
        r4 = r13 + -1;
        r13 = r20 - r19;
        r0 = r17;
        r1 = r18;
        r2 = r19;
        ssBlockSwap(r0, r1, r3, r2, r13);
        r12 = r3[r19];
        r5 = r19;
        r7 = r18;
        r9 = r20;
    L_0x001b:
        r13 = r17[r7];
        r13 = r13 + r16;
        r14 = r3[r9];
        r14 = r14 + r16;
        r0 = r22;
        r11 = r15.ssCompare(r13, r14, r0);
        if (r11 >= 0) goto L_0x0044;
    L_0x002b:
        r6 = r5 + 1;
        r13 = r17[r7];
        r3[r5] = r13;
        if (r4 > r7) goto L_0x0037;
    L_0x0033:
        r17[r7] = r12;
        r5 = r6;
    L_0x0036:
        return;
    L_0x0037:
        r8 = r7 + 1;
        r13 = r3[r6];
        r17[r7] = r13;
        r13 = r17[r8];
        if (r13 < 0) goto L_0x00d5;
    L_0x0041:
        r7 = r8;
        r5 = r6;
        goto L_0x001b;
    L_0x0044:
        if (r11 <= 0) goto L_0x0079;
    L_0x0046:
        r6 = r5 + 1;
        r13 = r3[r9];
        r3[r5] = r13;
        r10 = r9 + 1;
        r13 = r3[r6];
        r3[r9] = r13;
        r0 = r21;
        if (r0 > r10) goto L_0x0072;
    L_0x0056:
        r8 = r7;
    L_0x0057:
        if (r8 >= r4) goto L_0x0068;
    L_0x0059:
        r5 = r6 + 1;
        r13 = r17[r8];
        r3[r6] = r13;
        r7 = r8 + 1;
        r13 = r3[r5];
        r17[r8] = r13;
        r8 = r7;
        r6 = r5;
        goto L_0x0057;
    L_0x0068:
        r13 = r17[r8];
        r3[r6] = r13;
        r17[r8] = r12;
        r9 = r10;
        r7 = r8;
        r5 = r6;
        goto L_0x0036;
    L_0x0072:
        r13 = r3[r10];
        if (r13 < 0) goto L_0x00d1;
    L_0x0076:
        r9 = r10;
        r5 = r6;
        goto L_0x001b;
    L_0x0079:
        r13 = r3[r9];
        r13 = r13 ^ -1;
        r3[r9] = r13;
    L_0x007f:
        r6 = r5 + 1;
        r13 = r17[r7];
        r3[r5] = r13;
        if (r4 > r7) goto L_0x008b;
    L_0x0087:
        r17[r7] = r12;
        r5 = r6;
        goto L_0x0036;
    L_0x008b:
        r8 = r7 + 1;
        r13 = r3[r6];
        r17[r7] = r13;
        r13 = r17[r8];
        if (r13 < 0) goto L_0x00ce;
    L_0x0095:
        r5 = r6;
    L_0x0096:
        r6 = r5 + 1;
        r13 = r3[r9];
        r3[r5] = r13;
        r10 = r9 + 1;
        r13 = r3[r6];
        r3[r9] = r13;
        r0 = r21;
        if (r0 > r10) goto L_0x00c2;
    L_0x00a6:
        if (r8 >= r4) goto L_0x00b7;
    L_0x00a8:
        r5 = r6 + 1;
        r13 = r17[r8];
        r3[r6] = r13;
        r7 = r8 + 1;
        r13 = r3[r5];
        r17[r8] = r13;
        r8 = r7;
        r6 = r5;
        goto L_0x00a6;
    L_0x00b7:
        r13 = r17[r8];
        r3[r6] = r13;
        r17[r8] = r12;
        r9 = r10;
        r7 = r8;
        r5 = r6;
        goto L_0x0036;
    L_0x00c2:
        r13 = r3[r10];
        if (r13 < 0) goto L_0x00cb;
    L_0x00c6:
        r9 = r10;
        r7 = r8;
        r5 = r6;
        goto L_0x001b;
    L_0x00cb:
        r9 = r10;
        r5 = r6;
        goto L_0x0096;
    L_0x00ce:
        r7 = r8;
        r5 = r6;
        goto L_0x007f;
    L_0x00d1:
        r9 = r10;
        r5 = r6;
        goto L_0x0046;
    L_0x00d5:
        r7 = r8;
        r5 = r6;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2DivSufSort.ssMergeForward(int, int[], int, int, int, int, int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void ssMergeBackward(int r19, int[] r20, int r21, int r22, int r23, int r24, int r25) {
        /*
        r18 = this;
        r0 = r18;
        r4 = r0.SA;
        r17 = r24 - r23;
        r5 = r21 + r17;
        r17 = r24 - r23;
        r0 = r20;
        r1 = r21;
        r2 = r23;
        r3 = r17;
        ssBlockSwap(r0, r1, r4, r2, r3);
        r16 = 0;
        r17 = r5 + -1;
        r17 = r20[r17];
        if (r17 >= 0) goto L_0x0071;
    L_0x001d:
        r16 = r16 | 1;
        r17 = r5 + -1;
        r17 = r20[r17];
        r17 = r17 ^ -1;
        r12 = r19 + r17;
    L_0x0027:
        r17 = r23 + -1;
        r17 = r4[r17];
        if (r17 >= 0) goto L_0x0078;
    L_0x002d:
        r16 = r16 | 2;
        r17 = r23 + -1;
        r17 = r4[r17];
        r17 = r17 ^ -1;
        r13 = r19 + r17;
    L_0x0037:
        r17 = r24 + -1;
        r15 = r4[r17];
        r6 = r24 + -1;
        r8 = r5 + -1;
        r10 = r23 + -1;
    L_0x0041:
        r0 = r18;
        r1 = r25;
        r14 = r0.ssCompare(r12, r13, r1);
        if (r14 <= 0) goto L_0x009b;
    L_0x004b:
        r17 = r16 & 1;
        if (r17 == 0) goto L_0x0063;
    L_0x004f:
        r7 = r6 + -1;
        r17 = r20[r8];
        r4[r6] = r17;
        r9 = r8 + -1;
        r17 = r4[r7];
        r20[r8] = r17;
        r17 = r20[r9];
        if (r17 < 0) goto L_0x01a9;
    L_0x005f:
        r16 = r16 ^ 1;
        r8 = r9;
        r6 = r7;
    L_0x0063:
        r7 = r6 + -1;
        r17 = r20[r8];
        r4[r6] = r17;
        r0 = r21;
        if (r8 > r0) goto L_0x007f;
    L_0x006d:
        r20[r8] = r15;
        r6 = r7;
    L_0x0070:
        return;
    L_0x0071:
        r17 = r5 + -1;
        r17 = r20[r17];
        r12 = r19 + r17;
        goto L_0x0027;
    L_0x0078:
        r17 = r23 + -1;
        r17 = r4[r17];
        r13 = r19 + r17;
        goto L_0x0037;
    L_0x007f:
        r9 = r8 + -1;
        r17 = r4[r7];
        r20[r8] = r17;
        r17 = r20[r9];
        if (r17 >= 0) goto L_0x0094;
    L_0x0089:
        r16 = r16 | 1;
        r17 = r20[r9];
        r17 = r17 ^ -1;
        r12 = r19 + r17;
        r8 = r9;
        r6 = r7;
        goto L_0x0041;
    L_0x0094:
        r17 = r20[r9];
        r12 = r19 + r17;
        r8 = r9;
        r6 = r7;
        goto L_0x0041;
    L_0x009b:
        if (r14 >= 0) goto L_0x00fb;
    L_0x009d:
        r17 = r16 & 2;
        if (r17 == 0) goto L_0x00b5;
    L_0x00a1:
        r7 = r6 + -1;
        r17 = r4[r10];
        r4[r6] = r17;
        r11 = r10 + -1;
        r17 = r4[r7];
        r4[r10] = r17;
        r17 = r4[r11];
        if (r17 < 0) goto L_0x01a5;
    L_0x00b1:
        r16 = r16 ^ 2;
        r10 = r11;
        r6 = r7;
    L_0x00b5:
        r7 = r6 + -1;
        r17 = r4[r10];
        r4[r6] = r17;
        r11 = r10 + -1;
        r17 = r4[r7];
        r4[r10] = r17;
        r0 = r22;
        if (r11 >= r0) goto L_0x00e3;
    L_0x00c5:
        r9 = r8;
    L_0x00c6:
        r0 = r21;
        if (r0 >= r9) goto L_0x00d9;
    L_0x00ca:
        r6 = r7 + -1;
        r17 = r20[r9];
        r4[r7] = r17;
        r8 = r9 + -1;
        r17 = r4[r6];
        r20[r9] = r17;
        r9 = r8;
        r7 = r6;
        goto L_0x00c6;
    L_0x00d9:
        r17 = r20[r9];
        r4[r7] = r17;
        r20[r9] = r15;
        r10 = r11;
        r8 = r9;
        r6 = r7;
        goto L_0x0070;
    L_0x00e3:
        r17 = r4[r11];
        if (r17 >= 0) goto L_0x00f3;
    L_0x00e7:
        r16 = r16 | 2;
        r17 = r4[r11];
        r17 = r17 ^ -1;
        r13 = r19 + r17;
        r10 = r11;
        r6 = r7;
        goto L_0x0041;
    L_0x00f3:
        r17 = r4[r11];
        r13 = r19 + r17;
        r10 = r11;
        r6 = r7;
        goto L_0x0041;
    L_0x00fb:
        r17 = r16 & 1;
        if (r17 == 0) goto L_0x0113;
    L_0x00ff:
        r7 = r6 + -1;
        r17 = r20[r8];
        r4[r6] = r17;
        r9 = r8 + -1;
        r17 = r4[r7];
        r20[r8] = r17;
        r17 = r20[r9];
        if (r17 < 0) goto L_0x01a1;
    L_0x010f:
        r16 = r16 ^ 1;
        r8 = r9;
        r6 = r7;
    L_0x0113:
        r7 = r6 + -1;
        r17 = r20[r8];
        r17 = r17 ^ -1;
        r4[r6] = r17;
        r0 = r21;
        if (r8 > r0) goto L_0x0124;
    L_0x011f:
        r20[r8] = r15;
        r6 = r7;
        goto L_0x0070;
    L_0x0124:
        r9 = r8 + -1;
        r17 = r4[r7];
        r20[r8] = r17;
        r17 = r16 & 2;
        if (r17 == 0) goto L_0x019f;
    L_0x012e:
        r6 = r7;
    L_0x012f:
        r7 = r6 + -1;
        r17 = r4[r10];
        r4[r6] = r17;
        r11 = r10 + -1;
        r17 = r4[r7];
        r4[r10] = r17;
        r17 = r4[r11];
        if (r17 < 0) goto L_0x019c;
    L_0x013f:
        r16 = r16 ^ 2;
        r10 = r11;
        r6 = r7;
    L_0x0143:
        r7 = r6 + -1;
        r17 = r4[r10];
        r4[r6] = r17;
        r11 = r10 + -1;
        r17 = r4[r7];
        r4[r10] = r17;
        r0 = r22;
        if (r11 >= r0) goto L_0x0171;
    L_0x0153:
        r0 = r21;
        if (r0 >= r9) goto L_0x0166;
    L_0x0157:
        r6 = r7 + -1;
        r17 = r20[r9];
        r4[r7] = r17;
        r8 = r9 + -1;
        r17 = r4[r6];
        r20[r9] = r17;
        r9 = r8;
        r7 = r6;
        goto L_0x0153;
    L_0x0166:
        r17 = r20[r9];
        r4[r7] = r17;
        r20[r9] = r15;
        r10 = r11;
        r8 = r9;
        r6 = r7;
        goto L_0x0070;
    L_0x0171:
        r17 = r20[r9];
        if (r17 >= 0) goto L_0x018e;
    L_0x0175:
        r16 = r16 | 1;
        r17 = r20[r9];
        r17 = r17 ^ -1;
        r12 = r19 + r17;
    L_0x017d:
        r17 = r4[r11];
        if (r17 >= 0) goto L_0x0193;
    L_0x0181:
        r16 = r16 | 2;
        r17 = r4[r11];
        r17 = r17 ^ -1;
        r13 = r19 + r17;
        r10 = r11;
        r8 = r9;
        r6 = r7;
        goto L_0x0041;
    L_0x018e:
        r17 = r20[r9];
        r12 = r19 + r17;
        goto L_0x017d;
    L_0x0193:
        r17 = r4[r11];
        r13 = r19 + r17;
        r10 = r11;
        r8 = r9;
        r6 = r7;
        goto L_0x0041;
    L_0x019c:
        r10 = r11;
        r6 = r7;
        goto L_0x012f;
    L_0x019f:
        r6 = r7;
        goto L_0x0143;
    L_0x01a1:
        r8 = r9;
        r6 = r7;
        goto L_0x00ff;
    L_0x01a5:
        r10 = r11;
        r6 = r7;
        goto L_0x00a1;
    L_0x01a9:
        r8 = r9;
        r6 = r7;
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2DivSufSort.ssMergeBackward(int, int[], int, int, int, int, int):void");
    }

    private static int getIDX(int a) {
        return a >= 0 ? a : a ^ -1;
    }

    private void ssMergeCheckEqual(int pa, int depth, int a) {
        int[] SA = this.SA;
        if (SA[a] >= 0 && ssCompare(getIDX(SA[a - 1]) + pa, SA[a] + pa, depth) == 0) {
            SA[a] = SA[a] ^ -1;
        }
    }

    private void ssMerge(int pa, int first, int middle, int last, int[] buf, int bufoffset, int bufsize, int depth) {
        int[] SA = this.SA;
        StackEntry[] stack = new StackEntry[64];
        int check = 0;
        int ssize = 0;
        while (true) {
            int ssize2;
            StackEntry entry;
            if (last - middle <= bufsize) {
                if (first < middle && middle < last) {
                    ssMergeBackward(pa, buf, bufoffset, first, middle, last, depth);
                }
                if ((check & 1) != 0) {
                    ssMergeCheckEqual(pa, depth, first);
                }
                if ((check & 2) != 0) {
                    ssMergeCheckEqual(pa, depth, last);
                }
                if (ssize != 0) {
                    ssize2 = ssize - 1;
                    entry = stack[ssize2];
                    first = entry.a;
                    middle = entry.b;
                    last = entry.c;
                    check = entry.d;
                    ssize = ssize2;
                } else {
                    return;
                }
            } else if (middle - first <= bufsize) {
                if (first < middle) {
                    ssMergeForward(pa, buf, bufoffset, first, middle, last, depth);
                }
                if ((check & 1) != 0) {
                    ssMergeCheckEqual(pa, depth, first);
                }
                if ((check & 2) != 0) {
                    ssMergeCheckEqual(pa, depth, last);
                }
                if (ssize != 0) {
                    ssize2 = ssize - 1;
                    entry = stack[ssize2];
                    first = entry.a;
                    middle = entry.b;
                    last = entry.c;
                    check = entry.d;
                    ssize = ssize2;
                } else {
                    return;
                }
            } else {
                int m = 0;
                int len = Math.min(middle - first, last - middle);
                int half = len >> 1;
                while (len > 0) {
                    if (ssCompare(getIDX(SA[(middle + m) + half]) + pa, getIDX(SA[((middle - m) - half) - 1]) + pa, depth) < 0) {
                        m += half + 1;
                        half -= (len & 1) ^ 1;
                    }
                    len = half;
                    half >>= 1;
                }
                if (m > 0) {
                    ssBlockSwap(SA, middle - m, SA, middle, m);
                    int j = middle;
                    int i = middle;
                    int next = 0;
                    if (middle + m < last) {
                        if (SA[middle + m] < 0) {
                            while (SA[i - 1] < 0) {
                                i--;
                            }
                            SA[middle + m] = SA[middle + m] ^ -1;
                        }
                        j = middle;
                        while (SA[j] < 0) {
                            j++;
                        }
                        next = 1;
                    }
                    if (i - first <= last - j) {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(j, middle + m, last, (check & 2) | (next & 1));
                        middle -= m;
                        last = i;
                        check &= 1;
                        ssize = ssize2;
                    } else {
                        if (i == middle && middle == j) {
                            next <<= 1;
                        }
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(first, middle - m, i, (check & 1) | (next & 2));
                        first = j;
                        middle += m;
                        check = (check & 2) | (next & 1);
                        ssize = ssize2;
                    }
                } else {
                    if ((check & 1) != 0) {
                        ssMergeCheckEqual(pa, depth, first);
                    }
                    ssMergeCheckEqual(pa, depth, middle);
                    if ((check & 2) != 0) {
                        ssMergeCheckEqual(pa, depth, last);
                    }
                    if (ssize != 0) {
                        ssize2 = ssize - 1;
                        entry = stack[ssize2];
                        first = entry.a;
                        middle = entry.b;
                        last = entry.c;
                        check = entry.d;
                        ssize = ssize2;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void subStringSort(int pa, int first, int last, int[] buf, int bufoffset, int bufsize, int depth, boolean lastsuffix, int size) {
        int k;
        int[] SA = this.SA;
        if (lastsuffix) {
            first++;
        }
        int a = first;
        int i = 0;
        while (a + 1024 < last) {
            ssMultiKeyIntroSort(pa, a, a + 1024, depth);
            int[] curbuf = SA;
            int curbufoffset = a + 1024;
            int curbufsize = last - (a + 1024);
            if (curbufsize <= bufsize) {
                curbufsize = bufsize;
                curbuf = buf;
                curbufoffset = bufoffset;
            }
            int b = a;
            k = 1024;
            for (int j = i; (j & 1) != 0; j >>>= 1) {
                ssMerge(pa, b - k, b, b + k, curbuf, curbufoffset, curbufsize, depth);
                b -= k;
                k <<= 1;
            }
            a += 1024;
            i++;
        }
        ssMultiKeyIntroSort(pa, a, last, depth);
        k = 1024;
        while (i != 0) {
            if ((i & 1) != 0) {
                ssMerge(pa, a - k, a, last, buf, bufoffset, bufsize, depth);
                a -= k;
            }
            k <<= 1;
            i >>= 1;
        }
        if (lastsuffix) {
            a = first;
            i = SA[first - 1];
            int r = 1;
            while (a < last) {
                if (SA[a] >= 0) {
                    r = ssCompareLast(pa, pa + i, pa + SA[a], depth, size);
                    if (r <= 0) {
                        break;
                    }
                }
                SA[a - 1] = SA[a];
                a++;
            }
            if (r == 0) {
                SA[a] = SA[a] ^ -1;
            }
            SA[a - 1] = i;
        }
    }

    private int trGetC(int isa, int isaD, int isaN, int p) {
        return isaD + p < isaN ? this.SA[isaD + p] : this.SA[(((isaD - isa) + p) % (isaN - isa)) + isa];
    }

    private void trFixdown(int isa, int isaD, int isaN, int sa, int i, int size) {
        int j;
        int j2;
        int[] SA = this.SA;
        int v = SA[sa + i];
        int c = trGetC(isa, isaD, isaN, v);
        while (true) {
            j = (i * 2) + 1;
            if (j >= size) {
                break;
            }
            j2 = j + 1;
            int k = j;
            int d = trGetC(isa, isaD, isaN, SA[sa + k]);
            int e = trGetC(isa, isaD, isaN, SA[sa + j2]);
            if (d < e) {
                k = j2;
                d = e;
            }
            if (d <= c) {
                break;
            }
            SA[sa + i] = SA[sa + k];
            i = k;
        }
        j = j2;
        SA[sa + i] = v;
    }

    private void trHeapSort(int isa, int isaD, int isaN, int sa, int size) {
        int i;
        int[] SA = this.SA;
        int m = size;
        if (size % 2 == 0) {
            m--;
            if (trGetC(isa, isaD, isaN, SA[(m / 2) + sa]) < trGetC(isa, isaD, isaN, SA[sa + m])) {
                swapElements(SA, sa + m, SA, (m / 2) + sa);
            }
        }
        for (i = (m / 2) - 1; i >= 0; i--) {
            trFixdown(isa, isaD, isaN, sa, i, m);
        }
        if (size % 2 == 0) {
            swapElements(SA, sa, SA, sa + m);
            trFixdown(isa, isaD, isaN, sa, 0, m);
        }
        for (i = m - 1; i > 0; i--) {
            int t = SA[sa];
            SA[sa] = SA[sa + i];
            trFixdown(isa, isaD, isaN, sa, 0, i);
            SA[sa + i] = t;
        }
    }

    private void trInsertionSort(int isa, int isaD, int isaN, int first, int last) {
        int[] SA = this.SA;
        for (int a = first + 1; a < last; a++) {
            int t = SA[a];
            int b = a - 1;
            do {
                int r = trGetC(isa, isaD, isaN, t) - trGetC(isa, isaD, isaN, SA[b]);
                if (r >= 0) {
                    break;
                }
                do {
                    SA[b + 1] = SA[b];
                    b--;
                    if (first > b) {
                        break;
                    }
                } while (SA[b] < 0);
                continue;
            } while (b >= first);
            if (r == 0) {
                SA[b] = SA[b] ^ -1;
            }
            SA[b + 1] = t;
        }
    }

    private static int trLog(int n) {
        return (SupportMenu.CATEGORY_MASK & n) != 0 ? (-16777216 & n) != 0 ? LOG_2_TABLE[(n >> 24) & 255] + 24 : LOG_2_TABLE[(n >> 16) & 271] : (MotionEventCompat.ACTION_POINTER_INDEX_MASK & n) != 0 ? LOG_2_TABLE[(n >> 8) & 255] + 8 : LOG_2_TABLE[n & 255];
    }

    private int trMedian3(int isa, int isaD, int isaN, int v1, int v2, int v3) {
        int[] SA = this.SA;
        int SA_v1 = trGetC(isa, isaD, isaN, SA[v1]);
        int SA_v2 = trGetC(isa, isaD, isaN, SA[v2]);
        int SA_v3 = trGetC(isa, isaD, isaN, SA[v3]);
        if (SA_v1 > SA_v2) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
            int SA_vtemp = SA_v1;
            SA_v1 = SA_v2;
            SA_v2 = SA_vtemp;
        }
        if (SA_v2 > SA_v3) {
            return SA_v1 > SA_v3 ? v1 : v3;
        } else {
            return v2;
        }
    }

    private int trMedian5(int isa, int isaD, int isaN, int v1, int v2, int v3, int v4, int v5) {
        int[] SA = this.SA;
        int SA_v1 = trGetC(isa, isaD, isaN, SA[v1]);
        int SA_v2 = trGetC(isa, isaD, isaN, SA[v2]);
        int SA_v3 = trGetC(isa, isaD, isaN, SA[v3]);
        int SA_v4 = trGetC(isa, isaD, isaN, SA[v4]);
        int SA_v5 = trGetC(isa, isaD, isaN, SA[v5]);
        if (SA_v2 > SA_v3) {
            int temp = v2;
            v2 = v3;
            v3 = temp;
            int SA_vtemp = SA_v2;
            SA_v2 = SA_v3;
            SA_v3 = SA_vtemp;
        }
        if (SA_v4 > SA_v5) {
            temp = v4;
            v4 = v5;
            v5 = temp;
            SA_vtemp = SA_v4;
            SA_v4 = SA_v5;
            SA_v5 = SA_vtemp;
        }
        if (SA_v2 > SA_v4) {
            v4 = v2;
            SA_v4 = SA_v2;
            temp = v3;
            v3 = v5;
            v5 = temp;
            SA_vtemp = SA_v3;
            SA_v3 = SA_v5;
            SA_v5 = SA_vtemp;
        }
        if (SA_v1 > SA_v3) {
            temp = v1;
            v1 = v3;
            v3 = temp;
            SA_vtemp = SA_v1;
            SA_v1 = SA_v3;
            SA_v3 = SA_vtemp;
        }
        if (SA_v1 > SA_v4) {
            v4 = v1;
            SA_v4 = SA_v1;
            v3 = v5;
            SA_v3 = SA_v5;
        }
        return SA_v3 > SA_v4 ? v4 : v3;
    }

    private int trPivot(int isa, int isaD, int isaN, int first, int last) {
        int t = last - first;
        int middle = first + (t / 2);
        if (t > 512) {
            t >>= 3;
            return trMedian3(isa, isaD, isaN, trMedian3(isa, isaD, isaN, first, first + t, first + (t << 1)), trMedian3(isa, isaD, isaN, middle - t, middle, middle + t), trMedian3(isa, isaD, isaN, (last - 1) - (t << 1), (last - 1) - t, last - 1));
        } else if (t <= 32) {
            return trMedian3(isa, isaD, isaN, first, middle, last - 1);
        } else {
            t >>= 2;
            return trMedian5(isa, isaD, isaN, first, first + t, middle, (last - 1) - t, last - 1);
        }
    }

    private void lsUpdateGroup(int isa, int first, int last) {
        int[] SA = this.SA;
        int a = first;
        while (a < last) {
            int b;
            if (SA[a] >= 0) {
                b = a;
                do {
                    SA[SA[a] + isa] = a;
                    a++;
                    if (a >= last) {
                        break;
                    }
                } while (SA[a] >= 0);
                SA[b] = b - a;
                if (last <= a) {
                    return;
                }
            }
            b = a;
            do {
                SA[a] = SA[a] ^ -1;
                a++;
            } while (SA[a] < 0);
            int t = a;
            do {
                SA[SA[b] + isa] = t;
                b++;
            } while (b <= a);
            a++;
        }
    }

    private void lsIntroSort(int isa, int isaD, int isaN, int first, int last) {
        int[] SA = this.SA;
        StackEntry[] stack = new StackEntry[64];
        int x = 0;
        int ssize = 0;
        int limit = trLog(last - first);
        while (true) {
            int i;
            int ssize2;
            StackEntry entry;
            if (last - first <= 8) {
                if (1 < last - first) {
                    trInsertionSort(isa, isaD, isaN, first, last);
                    lsUpdateGroup(isa, first, last);
                } else if (last - first == 1) {
                    SA[first] = -1;
                }
                if (ssize == 0) {
                    i = limit;
                    return;
                }
                ssize2 = ssize - 1;
                entry = stack[ssize2];
                first = entry.a;
                last = entry.b;
                ssize = ssize2;
                limit = entry.c;
            } else {
                i = limit - 1;
                int a;
                int b;
                if (limit == 0) {
                    trHeapSort(isa, isaD, isaN, first, last - first);
                    for (a = last - 1; first < a; a = b) {
                        x = trGetC(isa, isaD, isaN, SA[a]);
                        b = a - 1;
                        while (first <= b) {
                            if (trGetC(isa, isaD, isaN, SA[b]) != x) {
                                break;
                            }
                            SA[b] = SA[b] ^ -1;
                            b--;
                        }
                    }
                    lsUpdateGroup(isa, first, last);
                    if (ssize != 0) {
                        ssize2 = ssize - 1;
                        entry = stack[ssize2];
                        first = entry.a;
                        last = entry.b;
                        ssize = ssize2;
                        limit = entry.c;
                    } else {
                        return;
                    }
                }
                swapElements(SA, first, SA, trPivot(isa, isaD, isaN, first, last));
                int v = trGetC(isa, isaD, isaN, SA[first]);
                b = first + 1;
                while (b < last) {
                    x = trGetC(isa, isaD, isaN, SA[b]);
                    if (x != v) {
                        break;
                    }
                    b++;
                }
                a = b;
                if (b < last && x < v) {
                    while (true) {
                        b++;
                        if (b >= last) {
                            break;
                        }
                        x = trGetC(isa, isaD, isaN, SA[b]);
                        if (x > v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, b, SA, a);
                            a++;
                        }
                    }
                }
                int c = last - 1;
                while (b < c) {
                    x = trGetC(isa, isaD, isaN, SA[c]);
                    if (x != v) {
                        break;
                    }
                    c--;
                }
                int d = c;
                if (b < c && x > v) {
                    while (true) {
                        c--;
                        if (b >= c) {
                            break;
                        }
                        x = trGetC(isa, isaD, isaN, SA[c]);
                        if (x < v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, c, SA, d);
                            d--;
                        }
                    }
                }
                while (b < c) {
                    swapElements(SA, b, SA, c);
                    while (true) {
                        b++;
                        if (b >= c) {
                            break;
                        }
                        x = trGetC(isa, isaD, isaN, SA[b]);
                        if (x > v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, b, SA, a);
                            a++;
                        }
                    }
                    while (true) {
                        c--;
                        if (b >= c) {
                            break;
                        }
                        x = trGetC(isa, isaD, isaN, SA[c]);
                        if (x < v) {
                            break;
                        } else if (x == v) {
                            swapElements(SA, c, SA, d);
                            d--;
                        }
                    }
                }
                if (a <= d) {
                    c = b - 1;
                    int s = a - first;
                    int t = b - a;
                    if (s > t) {
                        s = t;
                    }
                    int e = first;
                    int f = b - s;
                    while (s > 0) {
                        swapElements(SA, e, SA, f);
                        s--;
                        e++;
                        f++;
                    }
                    s = d - c;
                    t = (last - d) - 1;
                    if (s > t) {
                        s = t;
                    }
                    e = b;
                    f = last - s;
                    while (s > 0) {
                        swapElements(SA, e, SA, f);
                        s--;
                        e++;
                        f++;
                    }
                    a = first + (b - a);
                    b = last - (d - c);
                    v = a - 1;
                    for (c = first; c < a; c++) {
                        SA[SA[c] + isa] = v;
                    }
                    if (b < last) {
                        v = b - 1;
                        for (c = a; c < b; c++) {
                            SA[SA[c] + isa] = v;
                        }
                    }
                    if (b - a == 1) {
                        SA[a] = -1;
                    }
                    if (a - first <= last - b) {
                        if (first < a) {
                            ssize2 = ssize + 1;
                            stack[ssize] = new StackEntry(b, last, i, 0);
                            last = a;
                            ssize = ssize2;
                            limit = i;
                        } else {
                            first = b;
                            limit = i;
                        }
                    } else if (b < last) {
                        ssize2 = ssize + 1;
                        stack[ssize] = new StackEntry(first, a, i, 0);
                        first = b;
                        ssize = ssize2;
                        limit = i;
                    } else {
                        last = a;
                        limit = i;
                    }
                } else if (ssize != 0) {
                    ssize2 = ssize - 1;
                    entry = stack[ssize2];
                    first = entry.a;
                    last = entry.b;
                    ssize = ssize2;
                    limit = entry.c;
                } else {
                    return;
                }
            }
        }
    }

    private void lsSort(int isa, int n, int depth) {
        int[] SA = this.SA;
        int isaD = isa + depth;
        while ((-n) < SA[0]) {
            int first = 0;
            int skip = 0;
            do {
                int t = SA[first];
                if (t < 0) {
                    first -= t;
                    skip += t;
                    continue;
                } else {
                    if (skip != 0) {
                        SA[first + skip] = skip;
                        skip = 0;
                    }
                    int last = SA[isa + t] + 1;
                    lsIntroSort(isa, isaD, isa + n, first, last);
                    first = last;
                    continue;
                }
            } while (first < n);
            if (skip != 0) {
                SA[first + skip] = skip;
            }
            if (n < isaD - isa) {
                first = 0;
                do {
                    t = SA[first];
                    if (t < 0) {
                        first -= t;
                        continue;
                    } else {
                        last = SA[isa + t] + 1;
                        for (int i = first; i < last; i++) {
                            SA[SA[i] + isa] = i;
                        }
                        first = last;
                        continue;
                    }
                } while (first < n);
                return;
            }
            isaD += isaD - isa;
        }
    }

    private PartitionResult trPartition(int isa, int isaD, int isaN, int first, int last, int v) {
        int[] SA = this.SA;
        int x = 0;
        int b = first;
        while (b < last) {
            x = trGetC(isa, isaD, isaN, SA[b]);
            if (x != v) {
                break;
            }
            b++;
        }
        int a = b;
        if (b < last && x < v) {
            while (true) {
                b++;
                if (b >= last) {
                    break;
                }
                x = trGetC(isa, isaD, isaN, SA[b]);
                if (x > v) {
                    break;
                } else if (x == v) {
                    swapElements(SA, b, SA, a);
                    a++;
                }
            }
        }
        int c = last - 1;
        while (b < c) {
            x = trGetC(isa, isaD, isaN, SA[c]);
            if (x != v) {
                break;
            }
            c--;
        }
        int d = c;
        if (b < c && x > v) {
            while (true) {
                c--;
                if (b >= c) {
                    break;
                }
                x = trGetC(isa, isaD, isaN, SA[c]);
                if (x < v) {
                    break;
                } else if (x == v) {
                    swapElements(SA, c, SA, d);
                    d--;
                }
            }
        }
        while (b < c) {
            swapElements(SA, b, SA, c);
            while (true) {
                b++;
                if (b >= c) {
                    break;
                }
                x = trGetC(isa, isaD, isaN, SA[b]);
                if (x > v) {
                    break;
                } else if (x == v) {
                    swapElements(SA, b, SA, a);
                    a++;
                }
            }
            while (true) {
                c--;
                if (b >= c) {
                    break;
                }
                x = trGetC(isa, isaD, isaN, SA[c]);
                if (x < v) {
                    break;
                } else if (x == v) {
                    swapElements(SA, c, SA, d);
                    d--;
                }
            }
        }
        if (a <= d) {
            c = b - 1;
            int s = a - first;
            int t = b - a;
            if (s > t) {
                s = t;
            }
            int e = first;
            int f = b - s;
            while (s > 0) {
                swapElements(SA, e, SA, f);
                s--;
                e++;
                f++;
            }
            s = d - c;
            t = (last - d) - 1;
            if (s > t) {
                s = t;
            }
            e = b;
            f = last - s;
            while (s > 0) {
                swapElements(SA, e, SA, f);
                s--;
                e++;
                f++;
            }
            first += b - a;
            last -= d - c;
        }
        return new PartitionResult(first, last);
    }

    private void trCopy(int isa, int isaN, int first, int a, int b, int last, int depth) {
        int c;
        int[] SA = this.SA;
        int v = b - 1;
        int d = a - 1;
        for (c = first; c <= d; c++) {
            int s = SA[c] - depth;
            if (s < 0) {
                s += isaN - isa;
            }
            if (SA[isa + s] == v) {
                d++;
                SA[d] = s;
                SA[isa + s] = d;
            }
        }
        c = last - 1;
        int e = d + 1;
        d = b;
        while (e < d) {
            s = SA[c] - depth;
            if (s < 0) {
                s += isaN - isa;
            }
            if (SA[isa + s] == v) {
                d--;
                SA[d] = s;
                SA[isa + s] = d;
            }
            c--;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trIntroSort(int r34, int r35, int r36, int r37, int r38, io.netty.handler.codec.compression.Bzip2DivSufSort.TRBudget r39, int r40) {
        /*
        r33 = this;
        r0 = r33;
        r0 = r0.SA;
        r16 = r0;
        r4 = 64;
        r0 = new io.netty.handler.codec.compression.Bzip2DivSufSort.StackEntry[r4];
        r29 = r0;
        r32 = 0;
        r27 = 0;
        r4 = r38 - r37;
        r22 = trLog(r4);
        r28 = r27;
        r23 = r22;
    L_0x001a:
        if (r23 >= 0) goto L_0x02b6;
    L_0x001c:
        r4 = -1;
        r0 = r23;
        if (r0 != r4) goto L_0x01a4;
    L_0x0021:
        r4 = r38 - r37;
        r0 = r39;
        r1 = r40;
        r4 = r0.update(r1, r4);
        if (r4 != 0) goto L_0x0050;
    L_0x002d:
        r22 = r23;
    L_0x002f:
        r26 = 0;
    L_0x0031:
        r0 = r26;
        r1 = r28;
        if (r0 >= r1) goto L_0x072a;
    L_0x0037:
        r4 = r29[r26];
        r4 = r4.d;
        r5 = -3;
        if (r4 != r5) goto L_0x004d;
    L_0x003e:
        r4 = r29[r26];
        r4 = r4.b;
        r5 = r29[r26];
        r5 = r5.c;
        r0 = r33;
        r1 = r34;
        r0.lsUpdateGroup(r1, r4, r5);
    L_0x004d:
        r26 = r26 + 1;
        goto L_0x0031;
    L_0x0050:
        r6 = r35 + -1;
        r10 = r38 + -1;
        r4 = r33;
        r5 = r34;
        r7 = r36;
        r8 = r37;
        r9 = r38;
        r25 = r4.trPartition(r5, r6, r7, r8, r9, r10);
        r0 = r25;
        r8 = r0.first;
        r0 = r25;
        r9 = r0.last;
        r0 = r37;
        if (r0 < r8) goto L_0x0072;
    L_0x006e:
        r0 = r38;
        if (r9 >= r0) goto L_0x016d;
    L_0x0072:
        r0 = r38;
        if (r8 >= r0) goto L_0x0087;
    L_0x0076:
        r17 = r37;
        r31 = r8 + -1;
    L_0x007a:
        r0 = r17;
        if (r0 >= r8) goto L_0x0087;
    L_0x007e:
        r4 = r16[r17];
        r4 = r4 + r34;
        r16[r4] = r31;
        r17 = r17 + 1;
        goto L_0x007a;
    L_0x0087:
        r0 = r38;
        if (r9 >= r0) goto L_0x009c;
    L_0x008b:
        r17 = r8;
        r31 = r9 + -1;
    L_0x008f:
        r0 = r17;
        if (r0 >= r9) goto L_0x009c;
    L_0x0093:
        r4 = r16[r17];
        r4 = r4 + r34;
        r16[r4] = r31;
        r17 = r17 + 1;
        goto L_0x008f;
    L_0x009c:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = 0;
        r6 = 0;
        r4.<init>(r5, r8, r9, r6);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + -1;
        r6 = -2;
        r0 = r37;
        r1 = r38;
        r4.<init>(r5, r0, r1, r6);
        r29[r27] = r4;
        r4 = r8 - r37;
        r5 = r38 - r9;
        if (r4 > r5) goto L_0x0117;
    L_0x00bd:
        r4 = 1;
        r5 = r8 - r37;
        if (r4 >= r5) goto L_0x00e3;
    L_0x00c2:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r38 - r9;
        r5 = trLog(r5);
        r0 = r35;
        r1 = r38;
        r4.<init>(r0, r9, r1, r5);
        r29[r28] = r4;
        r38 = r8;
        r4 = r8 - r37;
        r22 = trLog(r4);
    L_0x00dd:
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x00e3:
        r4 = 1;
        r5 = r38 - r9;
        if (r4 >= r5) goto L_0x00f3;
    L_0x00e8:
        r37 = r9;
        r4 = r38 - r9;
        r22 = trLog(r4);
        r27 = r28;
        goto L_0x00dd;
    L_0x00f3:
        if (r28 != 0) goto L_0x00fa;
    L_0x00f5:
        r27 = r28;
        r22 = r23;
    L_0x00f9:
        return;
    L_0x00fa:
        r27 = r28 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        goto L_0x00dd;
    L_0x0117:
        r4 = 1;
        r5 = r38 - r9;
        if (r4 >= r5) goto L_0x0138;
    L_0x011c:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r8 - r37;
        r5 = trLog(r5);
        r0 = r35;
        r1 = r37;
        r4.<init>(r0, r1, r8, r5);
        r29[r28] = r4;
        r37 = r9;
        r4 = r38 - r9;
        r22 = trLog(r4);
        goto L_0x00dd;
    L_0x0138:
        r4 = 1;
        r5 = r8 - r37;
        if (r4 >= r5) goto L_0x0148;
    L_0x013d:
        r38 = r8;
        r4 = r8 - r37;
        r22 = trLog(r4);
        r27 = r28;
        goto L_0x00dd;
    L_0x0148:
        if (r28 != 0) goto L_0x014f;
    L_0x014a:
        r27 = r28;
        r22 = r23;
        goto L_0x00f9;
    L_0x014f:
        r27 = r28 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        goto L_0x00dd;
    L_0x016d:
        r17 = r37;
    L_0x016f:
        r0 = r17;
        r1 = r38;
        if (r0 >= r1) goto L_0x017e;
    L_0x0175:
        r4 = r16[r17];
        r4 = r4 + r34;
        r16[r4] = r17;
        r17 = r17 + 1;
        goto L_0x016f;
    L_0x017e:
        if (r28 != 0) goto L_0x0186;
    L_0x0180:
        r27 = r28;
        r22 = r23;
        goto L_0x00f9;
    L_0x0186:
        r27 = r28 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        goto L_0x00dd;
    L_0x01a4:
        r4 = -2;
        r0 = r23;
        if (r0 != r4) goto L_0x01ea;
    L_0x01a9:
        r27 = r28 + -1;
        r4 = r29[r27];
        r8 = r4.b;
        r4 = r29[r27];
        r9 = r4.c;
        r11 = r35 - r34;
        r4 = r33;
        r5 = r34;
        r6 = r36;
        r7 = r37;
        r10 = r38;
        r4.trCopy(r5, r6, r7, r8, r9, r10, r11);
        if (r27 != 0) goto L_0x01c8;
    L_0x01c4:
        r22 = r23;
        goto L_0x00f9;
    L_0x01c8:
        r27 = r27 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x01ea:
        r4 = r16[r37];
        if (r4 < 0) goto L_0x0202;
    L_0x01ee:
        r8 = r37;
    L_0x01f0:
        r4 = r16[r8];
        r4 = r4 + r34;
        r16[r4] = r8;
        r8 = r8 + 1;
        r0 = r38;
        if (r8 >= r0) goto L_0x0200;
    L_0x01fc:
        r4 = r16[r8];
        if (r4 >= 0) goto L_0x01f0;
    L_0x0200:
        r37 = r8;
    L_0x0202:
        r0 = r37;
        r1 = r38;
        if (r0 >= r1) goto L_0x028c;
    L_0x0208:
        r8 = r37;
    L_0x020a:
        r4 = r16[r8];
        r4 = r4 ^ -1;
        r16[r8] = r4;
        r8 = r8 + 1;
        r4 = r16[r8];
        if (r4 < 0) goto L_0x020a;
    L_0x0216:
        r4 = r16[r8];
        r4 = r4 + r34;
        r4 = r16[r4];
        r5 = r16[r8];
        r5 = r5 + r35;
        r5 = r16[r5];
        if (r4 == r5) goto L_0x0241;
    L_0x0224:
        r4 = r8 - r37;
        r4 = r4 + 1;
        r24 = trLog(r4);
    L_0x022c:
        r8 = r8 + 1;
        r0 = r38;
        if (r8 >= r0) goto L_0x0244;
    L_0x0232:
        r9 = r37;
        r31 = r8 + -1;
    L_0x0236:
        if (r9 >= r8) goto L_0x0244;
    L_0x0238:
        r4 = r16[r9];
        r4 = r4 + r34;
        r16[r4] = r31;
        r9 = r9 + 1;
        goto L_0x0236;
    L_0x0241:
        r24 = -1;
        goto L_0x022c;
    L_0x0244:
        r4 = r8 - r37;
        r5 = r38 - r8;
        if (r4 > r5) goto L_0x0264;
    L_0x024a:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = -3;
        r0 = r35;
        r1 = r38;
        r4.<init>(r0, r8, r1, r5);
        r29[r28] = r4;
        r35 = r35 + 1;
        r38 = r8;
        r22 = r24;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0264:
        r4 = 1;
        r5 = r38 - r8;
        if (r4 >= r5) goto L_0x0282;
    L_0x0269:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r37;
        r1 = r24;
        r4.<init>(r5, r0, r8, r1);
        r29[r28] = r4;
        r37 = r8;
        r22 = -3;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0282:
        r35 = r35 + 1;
        r38 = r8;
        r22 = r24;
        r23 = r22;
        goto L_0x001a;
    L_0x028c:
        if (r28 != 0) goto L_0x0294;
    L_0x028e:
        r27 = r28;
        r22 = r23;
        goto L_0x00f9;
    L_0x0294:
        r27 = r28 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x02b6:
        r4 = r38 - r37;
        r5 = 8;
        if (r4 > r5) goto L_0x02d5;
    L_0x02bc:
        r4 = r38 - r37;
        r0 = r39;
        r1 = r40;
        r4 = r0.update(r1, r4);
        if (r4 != 0) goto L_0x02cc;
    L_0x02c8:
        r22 = r23;
        goto L_0x002f;
    L_0x02cc:
        r33.trInsertionSort(r34, r35, r36, r37, r38);
        r22 = -3;
        r23 = r22;
        goto L_0x001a;
    L_0x02d5:
        r22 = r23 + -1;
        if (r23 != 0) goto L_0x0331;
    L_0x02d9:
        r4 = r38 - r37;
        r0 = r39;
        r1 = r40;
        r4 = r0.update(r1, r4);
        if (r4 == 0) goto L_0x002f;
    L_0x02e5:
        r15 = r38 - r37;
        r10 = r33;
        r11 = r34;
        r12 = r35;
        r13 = r36;
        r14 = r37;
        r10.trHeapSort(r11, r12, r13, r14, r15);
        r8 = r38 + -1;
    L_0x02f6:
        r0 = r37;
        if (r0 >= r8) goto L_0x032b;
    L_0x02fa:
        r4 = r16[r8];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r9 = r8 + -1;
    L_0x030a:
        r0 = r37;
        if (r0 > r9) goto L_0x0329;
    L_0x030e:
        r4 = r16[r9];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r4 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        if (r4 != r0) goto L_0x0329;
    L_0x0320:
        r4 = r16[r9];
        r4 = r4 ^ -1;
        r16[r9] = r4;
        r9 = r9 + -1;
        goto L_0x030a;
    L_0x0329:
        r8 = r9;
        goto L_0x02f6;
    L_0x032b:
        r22 = -3;
        r23 = r22;
        goto L_0x001a;
    L_0x0331:
        r8 = r33.trPivot(r34, r35, r36, r37, r38);
        r0 = r16;
        r1 = r37;
        r2 = r16;
        swapElements(r0, r1, r2, r8);
        r4 = r16[r37];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r31 = r0.trGetC(r1, r2, r3, r4);
        r9 = r37 + 1;
    L_0x034e:
        r0 = r38;
        if (r9 >= r0) goto L_0x0369;
    L_0x0352:
        r4 = r16[r9];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x0369;
    L_0x0366:
        r9 = r9 + 1;
        goto L_0x034e;
    L_0x0369:
        r8 = r9;
        r0 = r38;
        if (r9 >= r0) goto L_0x039e;
    L_0x036e:
        r0 = r32;
        r1 = r31;
        if (r0 >= r1) goto L_0x039e;
    L_0x0374:
        r9 = r9 + 1;
        r0 = r38;
        if (r9 >= r0) goto L_0x039e;
    L_0x037a:
        r4 = r16[r9];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 > r1) goto L_0x039e;
    L_0x038e:
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x0374;
    L_0x0394:
        r0 = r16;
        r1 = r16;
        swapElements(r0, r9, r1, r8);
        r8 = r8 + 1;
        goto L_0x0374;
    L_0x039e:
        r17 = r38 + -1;
    L_0x03a0:
        r0 = r17;
        if (r9 >= r0) goto L_0x03bb;
    L_0x03a4:
        r4 = r16[r17];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x03bb;
    L_0x03b8:
        r17 = r17 + -1;
        goto L_0x03a0;
    L_0x03bb:
        r18 = r17;
        r0 = r17;
        if (r9 >= r0) goto L_0x03f5;
    L_0x03c1:
        r0 = r32;
        r1 = r31;
        if (r0 <= r1) goto L_0x03f5;
    L_0x03c7:
        r17 = r17 + -1;
        r0 = r17;
        if (r9 >= r0) goto L_0x03f5;
    L_0x03cd:
        r4 = r16[r17];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 < r1) goto L_0x03f5;
    L_0x03e1:
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x03c7;
    L_0x03e7:
        r0 = r16;
        r1 = r17;
        r2 = r16;
        r3 = r18;
        swapElements(r0, r1, r2, r3);
        r18 = r18 + -1;
        goto L_0x03c7;
    L_0x03f5:
        r0 = r17;
        if (r9 >= r0) goto L_0x045a;
    L_0x03f9:
        r0 = r16;
        r1 = r16;
        r2 = r17;
        swapElements(r0, r9, r1, r2);
    L_0x0402:
        r9 = r9 + 1;
        r0 = r17;
        if (r9 >= r0) goto L_0x042c;
    L_0x0408:
        r4 = r16[r9];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 > r1) goto L_0x042c;
    L_0x041c:
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x0402;
    L_0x0422:
        r0 = r16;
        r1 = r16;
        swapElements(r0, r9, r1, r8);
        r8 = r8 + 1;
        goto L_0x0402;
    L_0x042c:
        r17 = r17 + -1;
        r0 = r17;
        if (r9 >= r0) goto L_0x03f5;
    L_0x0432:
        r4 = r16[r17];
        r0 = r33;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r32 = r0.trGetC(r1, r2, r3, r4);
        r0 = r32;
        r1 = r31;
        if (r0 < r1) goto L_0x03f5;
    L_0x0446:
        r0 = r32;
        r1 = r31;
        if (r0 != r1) goto L_0x042c;
    L_0x044c:
        r0 = r16;
        r1 = r17;
        r2 = r16;
        r3 = r18;
        swapElements(r0, r1, r2, r3);
        r18 = r18 + -1;
        goto L_0x042c;
    L_0x045a:
        r0 = r18;
        if (r8 > r0) goto L_0x0716;
    L_0x045e:
        r17 = r9 + -1;
        r26 = r8 - r37;
        r30 = r9 - r8;
        r0 = r26;
        r1 = r30;
        if (r0 <= r1) goto L_0x046c;
    L_0x046a:
        r26 = r30;
    L_0x046c:
        r19 = r37;
        r21 = r9 - r26;
    L_0x0470:
        if (r26 <= 0) goto L_0x0484;
    L_0x0472:
        r0 = r16;
        r1 = r19;
        r2 = r16;
        r3 = r21;
        swapElements(r0, r1, r2, r3);
        r26 = r26 + -1;
        r19 = r19 + 1;
        r21 = r21 + 1;
        goto L_0x0470;
    L_0x0484:
        r26 = r18 - r17;
        r4 = r38 - r18;
        r30 = r4 + -1;
        r0 = r26;
        r1 = r30;
        if (r0 <= r1) goto L_0x0492;
    L_0x0490:
        r26 = r30;
    L_0x0492:
        r19 = r9;
        r21 = r38 - r26;
    L_0x0496:
        if (r26 <= 0) goto L_0x04aa;
    L_0x0498:
        r0 = r16;
        r1 = r19;
        r2 = r16;
        r3 = r21;
        swapElements(r0, r1, r2, r3);
        r26 = r26 + -1;
        r19 = r19 + 1;
        r21 = r21 + 1;
        goto L_0x0496;
    L_0x04aa:
        r4 = r9 - r8;
        r8 = r37 + r4;
        r4 = r18 - r17;
        r9 = r38 - r4;
        r4 = r16[r8];
        r4 = r4 + r34;
        r4 = r16[r4];
        r0 = r31;
        if (r4 == r0) goto L_0x04d3;
    L_0x04bc:
        r4 = r9 - r8;
        r24 = trLog(r4);
    L_0x04c2:
        r17 = r37;
        r31 = r8 + -1;
    L_0x04c6:
        r0 = r17;
        if (r0 >= r8) goto L_0x04d6;
    L_0x04ca:
        r4 = r16[r17];
        r4 = r4 + r34;
        r16[r4] = r31;
        r17 = r17 + 1;
        goto L_0x04c6;
    L_0x04d3:
        r24 = -1;
        goto L_0x04c2;
    L_0x04d6:
        r0 = r38;
        if (r9 >= r0) goto L_0x04eb;
    L_0x04da:
        r17 = r8;
        r31 = r9 + -1;
    L_0x04de:
        r0 = r17;
        if (r0 >= r9) goto L_0x04eb;
    L_0x04e2:
        r4 = r16[r17];
        r4 = r4 + r34;
        r16[r4] = r31;
        r17 = r17 + 1;
        goto L_0x04de;
    L_0x04eb:
        r4 = r8 - r37;
        r5 = r38 - r9;
        if (r4 > r5) goto L_0x060c;
    L_0x04f1:
        r4 = r38 - r9;
        r5 = r9 - r8;
        if (r4 > r5) goto L_0x0571;
    L_0x04f7:
        r4 = 1;
        r5 = r8 - r37;
        if (r4 >= r5) goto L_0x051e;
    L_0x04fc:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r27] = r4;
        r38 = r8;
        r23 = r22;
        goto L_0x001a;
    L_0x051e:
        r4 = 1;
        r5 = r38 - r9;
        if (r4 >= r5) goto L_0x0538;
    L_0x0523:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r28] = r4;
        r37 = r9;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0538:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x0549;
    L_0x053d:
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r23 = r22;
        goto L_0x001a;
    L_0x0549:
        if (r28 != 0) goto L_0x054f;
    L_0x054b:
        r27 = r28;
        goto L_0x00f9;
    L_0x054f:
        r27 = r28 + -1;
        r20 = r29[r27];
        r0 = r20;
        r0 = r0.a_isRightVersion;
        r35 = r0;
        r0 = r20;
        r0 = r0.b;
        r37 = r0;
        r0 = r20;
        r0 = r0.c;
        r38 = r0;
        r0 = r20;
        r0 = r0.d;
        r22 = r0;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0571:
        r4 = r8 - r37;
        r5 = r9 - r8;
        if (r4 > r5) goto L_0x05c6;
    L_0x0577:
        r4 = 1;
        r5 = r8 - r37;
        if (r4 >= r5) goto L_0x059e;
    L_0x057c:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r27] = r4;
        r38 = r8;
        r23 = r22;
        goto L_0x001a;
    L_0x059e:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x05c0;
    L_0x05a3:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r28] = r4;
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x05c0:
        r37 = r9;
        r23 = r22;
        goto L_0x001a;
    L_0x05c6:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x05f5;
    L_0x05cb:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r27] = r4;
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r23 = r22;
        goto L_0x001a;
    L_0x05f5:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r28] = r4;
        r38 = r8;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x060c:
        r4 = r8 - r37;
        r5 = r9 - r8;
        if (r4 > r5) goto L_0x067b;
    L_0x0612:
        r4 = 1;
        r5 = r38 - r9;
        if (r4 >= r5) goto L_0x0639;
    L_0x0617:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r27] = r4;
        r37 = r9;
        r23 = r22;
        goto L_0x001a;
    L_0x0639:
        r4 = 1;
        r5 = r8 - r37;
        if (r4 >= r5) goto L_0x0653;
    L_0x063e:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r28] = r4;
        r38 = r8;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0653:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x0664;
    L_0x0658:
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r23 = r22;
        goto L_0x001a;
    L_0x0664:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r38;
        r3 = r22;
        r4.<init>(r0, r1, r2, r3);
        r29[r28] = r4;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x067b:
        r4 = r38 - r9;
        r5 = r9 - r8;
        if (r4 > r5) goto L_0x06d0;
    L_0x0681:
        r4 = 1;
        r5 = r38 - r9;
        if (r4 >= r5) goto L_0x06a8;
    L_0x0686:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r5 = r35 + 1;
        r0 = r24;
        r4.<init>(r5, r8, r9, r0);
        r29[r27] = r4;
        r37 = r9;
        r23 = r22;
        goto L_0x001a;
    L_0x06a8:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x06ca;
    L_0x06ad:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r28] = r4;
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x06ca:
        r38 = r8;
        r23 = r22;
        goto L_0x001a;
    L_0x06d0:
        r4 = 1;
        r5 = r9 - r8;
        if (r4 >= r5) goto L_0x06ff;
    L_0x06d5:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r28] = r4;
        r28 = r27 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r38;
        r2 = r22;
        r4.<init>(r0, r9, r1, r2);
        r29[r27] = r4;
        r35 = r35 + 1;
        r37 = r8;
        r38 = r9;
        r22 = r24;
        r23 = r22;
        goto L_0x001a;
    L_0x06ff:
        r27 = r28 + 1;
        r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry;
        r0 = r35;
        r1 = r37;
        r2 = r22;
        r4.<init>(r0, r1, r8, r2);
        r29[r28] = r4;
        r37 = r9;
        r28 = r27;
        r23 = r22;
        goto L_0x001a;
    L_0x0716:
        r4 = r38 - r37;
        r0 = r39;
        r1 = r40;
        r4 = r0.update(r1, r4);
        if (r4 == 0) goto L_0x002f;
    L_0x0722:
        r22 = r22 + 1;
        r35 = r35 + 1;
        r23 = r22;
        goto L_0x001a;
    L_0x072a:
        r27 = r28;
        goto L_0x00f9;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2DivSufSort.trIntroSort(int, int, int, int, int, io.netty.handler.codec.compression.Bzip2DivSufSort$TRBudget, int):void");
    }

    private void trSort(int isa, int n, int depth) {
        int[] SA = this.SA;
        int first = 0;
        if ((-n) < SA[0]) {
            TRBudget budget = new TRBudget(n, ((trLog(n) * 2) / 3) + 1);
            do {
                int t = SA[first];
                if (t < 0) {
                    first -= t;
                    continue;
                } else {
                    int last = SA[isa + t] + 1;
                    if (1 < last - first) {
                        trIntroSort(isa, isa + depth, isa + n, first, last, budget, n);
                        if (budget.chance == 0) {
                            if (first > 0) {
                                SA[0] = -first;
                            }
                            lsSort(isa, n, depth);
                            return;
                        }
                    }
                    first = last;
                    continue;
                }
            } while (first < n);
        }
    }

    private static int BUCKET_B(int c0, int c1) {
        return (c1 << 8) | c0;
    }

    private static int BUCKET_BSTAR(int c0, int c1) {
        return (c0 << 8) | c1;
    }

    private int sortTypeBstar(int[] bucketA, int[] bucketB) {
        int i;
        int m;
        int ti;
        int t0;
        int BUCKET_BSTAR;
        int j;
        int c0;
        int t;
        int c1;
        int ISAb;
        byte[] T = this.T;
        int[] SA = this.SA;
        int n = this.n;
        int[] tempbuf = new int[256];
        int flag = 1;
        for (i = 1; i < n; i++) {
            int ti1;
            int PAb;
            int i2;
            int[] buf;
            int bufoffset;
            int bufsize;
            int k;
            if (T[i - 1] != T[i]) {
                if ((T[i - 1] & 255) > (T[i] & 255)) {
                    flag = 0;
                }
                i = n - 1;
                m = n;
                ti = T[i] & 255;
                t0 = T[0] & 255;
                if (ti < t0 || (T[i] == T[0] && flag != 0)) {
                    if (flag != 0) {
                        BUCKET_BSTAR = BUCKET_BSTAR(ti, t0);
                        bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                        m--;
                        SA[m] = i;
                    } else {
                        BUCKET_BSTAR = BUCKET_B(ti, t0);
                        bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                    }
                    i--;
                    while (i >= 0) {
                        ti = T[i] & 255;
                        ti1 = T[i + 1] & 255;
                        if (ti <= ti1) {
                            break;
                        }
                        BUCKET_BSTAR = BUCKET_B(ti, ti1);
                        bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                        i--;
                    }
                }
                while (i >= 0) {
                    do {
                        BUCKET_BSTAR = T[i] & 255;
                        bucketA[BUCKET_BSTAR] = bucketA[BUCKET_BSTAR] + 1;
                        i--;
                        if (i >= 0) {
                            break;
                        }
                    } while ((T[i] & 255) >= (T[i + 1] & 255));
                    if (i >= 0) {
                        BUCKET_BSTAR = BUCKET_BSTAR(T[i] & 255, T[i + 1] & 255);
                        bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                        m--;
                        SA[m] = i;
                        i--;
                        while (i >= 0) {
                            ti = T[i] & 255;
                            ti1 = T[i + 1] & 255;
                            if (ti <= ti1) {
                                break;
                            }
                            BUCKET_BSTAR = BUCKET_B(ti, ti1);
                            bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                            i--;
                        }
                    }
                }
                m = n - m;
                if (m != 0) {
                    for (i = 0; i < n; i++) {
                        SA[i] = i;
                    }
                    return 0;
                }
                i = -1;
                j = 0;
                for (c0 = 0; c0 < 256; c0++) {
                    t = i + bucketA[c0];
                    bucketA[c0] = i + j;
                    i = t + bucketB[BUCKET_B(c0, c0)];
                    for (c1 = c0 + 1; c1 < 256; c1++) {
                        j += bucketB[BUCKET_BSTAR(c0, c1)];
                        bucketB[(c0 << 8) | c1] = j;
                        i += bucketB[BUCKET_B(c0, c1)];
                    }
                }
                PAb = n - m;
                ISAb = m;
                for (i = m - 2; i >= 0; i--) {
                    t = SA[PAb + i];
                    BUCKET_BSTAR = BUCKET_BSTAR(T[t] & 255, T[t + 1] & 255);
                    i2 = bucketB[BUCKET_BSTAR] - 1;
                    bucketB[BUCKET_BSTAR] = i2;
                    SA[i2] = i;
                }
                t = SA[(PAb + m) - 1];
                BUCKET_BSTAR = BUCKET_BSTAR(T[t] & 255, T[t + 1] & 255);
                i2 = bucketB[BUCKET_BSTAR] - 1;
                bucketB[BUCKET_BSTAR] = i2;
                SA[i2] = m - 1;
                buf = SA;
                bufoffset = m;
                bufsize = n - (m * 2);
                if (bufsize <= 256) {
                    buf = tempbuf;
                    bufoffset = 0;
                    bufsize = 256;
                }
                c0 = 255;
                j = m;
                while (j > 0) {
                    for (c1 = 255; c0 < c1; c1--) {
                        i = bucketB[BUCKET_BSTAR(c0, c1)];
                        if (1 >= j - i) {
                            subStringSort(PAb, i, j, buf, bufoffset, bufsize, 2, SA[i] != m + -1, n);
                        }
                        j = i;
                    }
                    c0--;
                }
                i = m - 1;
                while (i >= 0) {
                    if (SA[i] >= 0) {
                        j = i;
                        do {
                            SA[SA[i] + ISAb] = i;
                            i--;
                            if (i >= 0) {
                                break;
                            }
                        } while (SA[i] >= 0);
                        SA[i + 1] = i - j;
                        if (i <= 0) {
                            break;
                        }
                    }
                    j = i;
                    do {
                        BUCKET_BSTAR = SA[i] ^ -1;
                        SA[i] = BUCKET_BSTAR;
                        SA[BUCKET_BSTAR + ISAb] = j;
                        i--;
                    } while (SA[i] < 0);
                    SA[SA[i] + ISAb] = j;
                    i--;
                }
                trSort(ISAb, m, 1);
                i = n - 1;
                j = m;
                if ((T[i] & 255) < (T[0] & 255) || (T[i] == T[0] && flag != 0)) {
                    if (flag == 0) {
                        j--;
                        SA[SA[ISAb + j]] = i;
                    }
                    i--;
                    while (i >= 0 && (T[i] & 255) <= (T[i + 1] & 255)) {
                        i--;
                    }
                }
                while (i >= 0) {
                    i--;
                    while (i >= 0 && (T[i] & 255) >= (T[i + 1] & 255)) {
                        i--;
                    }
                    if (i >= 0) {
                        j--;
                        SA[SA[ISAb + j]] = i;
                        i--;
                        while (i >= 0 && (T[i] & 255) <= (T[i + 1] & 255)) {
                            i--;
                        }
                    }
                }
                i = n - 1;
                k = m - 1;
                for (c0 = 255; c0 >= 0; c0--) {
                    for (c1 = 255; c0 < c1; c1--) {
                        t = i - bucketB[BUCKET_B(c0, c1)];
                        bucketB[BUCKET_B(c0, c1)] = i + 1;
                        i = t;
                        j = bucketB[BUCKET_BSTAR(c0, c1)];
                        while (j <= k) {
                            SA[i] = SA[k];
                            i--;
                            k--;
                        }
                    }
                    t = i - bucketB[BUCKET_B(c0, c0)];
                    bucketB[BUCKET_B(c0, c0)] = i + 1;
                    if (c0 < 255) {
                        bucketB[BUCKET_BSTAR(c0, c0 + 1)] = t + 1;
                    }
                    i = bucketA[c0];
                }
                return m;
            }
        }
        i = n - 1;
        m = n;
        ti = T[i] & 255;
        t0 = T[0] & 255;
        if (flag != 0) {
            BUCKET_BSTAR = BUCKET_B(ti, t0);
            bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
        } else {
            BUCKET_BSTAR = BUCKET_BSTAR(ti, t0);
            bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
            m--;
            SA[m] = i;
        }
        i--;
        while (i >= 0) {
            ti = T[i] & 255;
            ti1 = T[i + 1] & 255;
            if (ti <= ti1) {
                break;
            }
            BUCKET_BSTAR = BUCKET_B(ti, ti1);
            bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
            i--;
        }
        while (i >= 0) {
            do {
                BUCKET_BSTAR = T[i] & 255;
                bucketA[BUCKET_BSTAR] = bucketA[BUCKET_BSTAR] + 1;
                i--;
                if (i >= 0) {
                    break;
                }
            } while ((T[i] & 255) >= (T[i + 1] & 255));
            if (i >= 0) {
                BUCKET_BSTAR = BUCKET_BSTAR(T[i] & 255, T[i + 1] & 255);
                bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                m--;
                SA[m] = i;
                i--;
                while (i >= 0) {
                    ti = T[i] & 255;
                    ti1 = T[i + 1] & 255;
                    if (ti <= ti1) {
                        break;
                    }
                    BUCKET_BSTAR = BUCKET_B(ti, ti1);
                    bucketB[BUCKET_BSTAR] = bucketB[BUCKET_BSTAR] + 1;
                    i--;
                }
            }
        }
        m = n - m;
        if (m != 0) {
            i = -1;
            j = 0;
            for (c0 = 0; c0 < 256; c0++) {
                t = i + bucketA[c0];
                bucketA[c0] = i + j;
                i = t + bucketB[BUCKET_B(c0, c0)];
                for (c1 = c0 + 1; c1 < 256; c1++) {
                    j += bucketB[BUCKET_BSTAR(c0, c1)];
                    bucketB[(c0 << 8) | c1] = j;
                    i += bucketB[BUCKET_B(c0, c1)];
                }
            }
            PAb = n - m;
            ISAb = m;
            for (i = m - 2; i >= 0; i--) {
                t = SA[PAb + i];
                BUCKET_BSTAR = BUCKET_BSTAR(T[t] & 255, T[t + 1] & 255);
                i2 = bucketB[BUCKET_BSTAR] - 1;
                bucketB[BUCKET_BSTAR] = i2;
                SA[i2] = i;
            }
            t = SA[(PAb + m) - 1];
            BUCKET_BSTAR = BUCKET_BSTAR(T[t] & 255, T[t + 1] & 255);
            i2 = bucketB[BUCKET_BSTAR] - 1;
            bucketB[BUCKET_BSTAR] = i2;
            SA[i2] = m - 1;
            buf = SA;
            bufoffset = m;
            bufsize = n - (m * 2);
            if (bufsize <= 256) {
                buf = tempbuf;
                bufoffset = 0;
                bufsize = 256;
            }
            c0 = 255;
            j = m;
            while (j > 0) {
                for (c1 = 255; c0 < c1; c1--) {
                    i = bucketB[BUCKET_BSTAR(c0, c1)];
                    if (1 >= j - i) {
                        if (SA[i] != m + -1) {
                        }
                        subStringSort(PAb, i, j, buf, bufoffset, bufsize, 2, SA[i] != m + -1, n);
                    }
                    j = i;
                }
                c0--;
            }
            i = m - 1;
            while (i >= 0) {
                if (SA[i] >= 0) {
                    j = i;
                    do {
                        SA[SA[i] + ISAb] = i;
                        i--;
                        if (i >= 0) {
                            break;
                        }
                    } while (SA[i] >= 0);
                    SA[i + 1] = i - j;
                    if (i <= 0) {
                        break;
                    }
                }
                j = i;
                do {
                    BUCKET_BSTAR = SA[i] ^ -1;
                    SA[i] = BUCKET_BSTAR;
                    SA[BUCKET_BSTAR + ISAb] = j;
                    i--;
                } while (SA[i] < 0);
                SA[SA[i] + ISAb] = j;
                i--;
            }
            trSort(ISAb, m, 1);
            i = n - 1;
            j = m;
            if (flag == 0) {
                j--;
                SA[SA[ISAb + j]] = i;
            }
            i--;
            while (i >= 0) {
                i--;
            }
            while (i >= 0) {
                i--;
                while (i >= 0) {
                    i--;
                }
                if (i >= 0) {
                    j--;
                    SA[SA[ISAb + j]] = i;
                    i--;
                    while (i >= 0) {
                        i--;
                    }
                }
            }
            i = n - 1;
            k = m - 1;
            for (c0 = 255; c0 >= 0; c0--) {
                for (c1 = 255; c0 < c1; c1--) {
                    t = i - bucketB[BUCKET_B(c0, c1)];
                    bucketB[BUCKET_B(c0, c1)] = i + 1;
                    i = t;
                    j = bucketB[BUCKET_BSTAR(c0, c1)];
                    while (j <= k) {
                        SA[i] = SA[k];
                        i--;
                        k--;
                    }
                }
                t = i - bucketB[BUCKET_B(c0, c0)];
                bucketB[BUCKET_B(c0, c0)] = i + 1;
                if (c0 < 255) {
                    bucketB[BUCKET_BSTAR(c0, c0 + 1)] = t + 1;
                }
                i = bucketA[c0];
            }
            return m;
        }
        for (i = 0; i < n; i++) {
            SA[i] = i;
        }
        return 0;
    }

    private int constructBWT(int[] bucketA, int[] bucketB) {
        int c0;
        byte[] T = this.T;
        int[] SA = this.SA;
        int n = this.n;
        int t = 0;
        int c2 = 0;
        int orig = -1;
        for (int c1 = 254; c1 >= 0; c1--) {
            int i = bucketB[BUCKET_BSTAR(c1, c1 + 1)];
            t = 0;
            c2 = -1;
            for (int j = bucketA[c1 + 1]; i <= j; j--) {
                int s = SA[j];
                int s1 = s;
                if (s >= 0) {
                    s--;
                    if (s < 0) {
                        s = n - 1;
                    }
                    c0 = T[s] & 255;
                    if (c0 <= c1) {
                        SA[j] = s1 ^ -1;
                        if (s > 0 && (T[s - 1] & 255) > c0) {
                            s ^= -1;
                        }
                        if (c2 == c0) {
                            t--;
                            SA[t] = s;
                        } else {
                            if (c2 >= 0) {
                                bucketB[BUCKET_B(c2, c1)] = t;
                            }
                            c2 = c0;
                            t = bucketB[BUCKET_B(c0, c1)] - 1;
                            SA[t] = s;
                        }
                    }
                } else {
                    SA[j] = s ^ -1;
                }
            }
        }
        for (i = 0; i < n; i++) {
            s = SA[i];
            s1 = s;
            if (s >= 0) {
                s--;
                if (s < 0) {
                    s = n - 1;
                }
                c0 = T[s] & 255;
                if (c0 >= (T[s + 1] & 255)) {
                    if (s > 0 && (T[s - 1] & 255) < c0) {
                        s ^= -1;
                    }
                    if (c0 == c2) {
                        t++;
                        SA[t] = s;
                    } else {
                        if (c2 != -1) {
                            bucketA[c2] = t;
                        }
                        c2 = c0;
                        t = bucketA[c0] + 1;
                        SA[t] = s;
                    }
                }
            } else {
                s1 ^= -1;
            }
            if (s1 == 0) {
                SA[i] = T[n - 1];
                orig = i;
            } else {
                SA[i] = T[s1 - 1];
            }
        }
        return orig;
    }

    public int bwt() {
        int[] SA = this.SA;
        byte[] T = this.T;
        int n = this.n;
        int[] bucketA = new int[256];
        int[] bucketB = new int[65536];
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            SA[0] = T[0];
            return 0;
        } else if (sortTypeBstar(bucketA, bucketB) > 0) {
            return constructBWT(bucketA, bucketB);
        } else {
            return 0;
        }
    }
}
