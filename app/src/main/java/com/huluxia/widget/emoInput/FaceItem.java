package com.huluxia.widget.emoInput;

public class FaceItem {
    public FACE_TYPE bym;
    public int byn = 0;
    public String byo = "";
    public String byp = "";
    public String name = "";
    public String text = "";

    public enum FACE_TYPE {
        TYPE_EMOJI,
        TYPE_GIF
    }

    public static FaceItem Ot() {
        FaceItem item = new FaceItem();
        item.bym = FACE_TYPE.TYPE_EMOJI;
        String code = c.byf;
        item.byn = d.Ou().gO(code);
        item.text = code;
        return item;
    }
}
