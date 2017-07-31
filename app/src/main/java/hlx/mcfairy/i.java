package hlx.mcfairy;

/* compiled from: InterceptMess */
public class i {
    private String bTX;
    private int bTY;
    private int bTZ;

    public i(String mainBody, int paraInt1, int paraInt2) {
        this.bTX = mainBody;
        this.bTY = paraInt1;
        this.bTZ = paraInt2;
    }

    public i(String mainBody, int paraInt1) {
        this.bTX = mainBody;
        this.bTY = paraInt1;
        this.bTZ = 0;
    }

    public i(String mainBody) {
        this.bTX = mainBody;
        this.bTY = 0;
        this.bTZ = 0;
    }

    public String Tf() {
        return this.bTX;
    }

    public void hg(String messMainBody) {
        this.bTX = messMainBody;
    }

    public int Tg() {
        return this.bTY;
    }

    public void mV(int paraInt1) {
        this.bTY = paraInt1;
    }

    public int Th() {
        return this.bTZ;
    }

    public void mW(int paraInt2) {
        this.bTZ = paraInt2;
    }
}
