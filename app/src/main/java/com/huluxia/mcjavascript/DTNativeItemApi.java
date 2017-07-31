package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManagerEx;
import hlx.mcstorymode.c;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeItemApi extends ScriptableObject {
    private static final boolean DEBUG = false;

    @JSStaticFunction
    public static void addCraftRecipe(int id, int count, int damage, Scriptable ingredientsScriptable) {
        int i;
        int[] expanded = DTSDKManager.expandShapelessRecipe(ingredientsScriptable);
        char nextchar = 'a';
        int[] ingredients = new int[expanded.length];
        StringBuilder temprow = new StringBuilder();
        for (i = 0; i < expanded.length; i += 3) {
            int inputid = expanded[i];
            int inputcount = expanded[i + 1];
            int inputdamage = expanded[i + 2];
            char mychar = (char) (nextchar + 1);
            char c3 = nextchar;
            for (int a = 0; a < inputcount; a++) {
                temprow.append(c3);
            }
            ingredients[i] = c3;
            ingredients[i + 1] = inputid;
            ingredients[i + 2] = inputdamage;
            nextchar = mychar;
        }
        int temprowLength = temprow.length();
        if (temprowLength > 9) {
            temprow.delete(9, temprow.length());
            temprowLength = temprow.length();
        }
        int width = temprowLength <= 4 ? 2 : 3;
        String[] shape = new String[(temprowLength % width != 0 ? 1 : (temprowLength / width) + 0)];
        for (i = 0; i < shape.length; i++) {
            int begin = i * width;
            int end = begin + width;
            if (end > temprowLength) {
                end = temprowLength;
            }
            shape[i] = temprow.substring(begin, end);
        }
        DTSDKManagerEx.dtNativeAddShapedRecipe(id, count, damage, shape, ingredients);
    }

    @JSStaticFunction
    public static void addFurnaceRecipe(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.L(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void addShapedRecipe(int id, int count, int damage, Scriptable shape, Scriptable ingredients) {
        int i;
        int shapeArrayLength = ((Number) ScriptableObject.getProperty(shape, "length")).intValue();
        String[] shapeArray = new String[shapeArrayLength];
        for (i = 0; i < shapeArrayLength; i++) {
            shapeArray[i] = ScriptableObject.getProperty(shape, i).toString();
        }
        int ingredientsArrayLength = ((Number) ScriptableObject.getProperty(ingredients, "length")).intValue();
        if (ingredientsArrayLength % 3 != 0) {
            throw new RuntimeException("Ingredients array must be [\"?\", id, damage, ...]");
        }
        int[] ingredientsArray = new int[ingredientsArrayLength];
        for (i = 0; i < ingredientsArrayLength; i++) {
            Object str = ScriptableObject.getProperty(ingredients, i);
            if (i % 3 == 0) {
                ingredientsArray[i] = str.toString().charAt(0);
            } else {
                ingredientsArray[i] = ((Number) str).intValue();
            }
        }
        DTSDKManagerEx.dtNativeAddShapedRecipe(id, count, damage, shapeArray, ingredientsArray);
    }

    @JSStaticFunction
    public static void defineArmor(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, int paramInt3, int paramInt4, int paramInt5) {
        if (paramInt5 < 0 || paramInt5 > 3) {
            throw new RuntimeException("Invalid armor type: use ArmorType.helmet, ArmorType.chestplate,ArmorType.leggings, or ArmorType.boots");
        } else if (paramInt1 < 0 || paramInt1 >= ScriptManager.ITEM_ID_COUNT) {
            throw new IllegalArgumentException("Item IDs must be >= 0 and < " + ScriptManager.ITEM_ID_COUNT);
        } else if (DTSDKManagerEx.anO == null || DTSDKManagerEx.anO.A(paramString1, paramInt2)) {
            ScriptManager.nativeDefineArmor(paramInt1, paramString1, paramInt2, paramString2, paramString3, paramInt3, paramInt4, paramInt5);
        } else {
            throw new IllegalArgumentException("The item icon " + paramString1 + ":" + paramInt2 + " does not exist");
        }
    }

    @JSStaticFunction
    public static String getName(int paramInt1, int paramInt2, boolean paramBoolean) {
        return DTSDKManagerEx.e(paramInt1, paramInt2, paramBoolean);
    }

    @JSStaticFunction
    public static void setCategory(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.M(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void setHandEquipped(int paramInt, boolean paramBoolean) {
        k.f(paramInt, paramBoolean);
    }

    @JSStaticFunction
    public static void setMaxDamage(int paramInt1, int paramInt2) {
        DTSDKManagerEx.ae(paramInt1, paramInt2);
    }

    public String getClassName() {
        return c.bVI;
    }
}
