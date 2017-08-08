package com.MCWorld.mojang.entity;

import android.content.Context;
import android.content.res.Resources;
import com.MCWorld.framework.R;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public class EntityTypeLocalization {
    public static Map<EntityType, Integer> namesMap = new EnumMap(EntityType.class);

    static {
        namesMap.put(EntityType.CHICKEN, Integer.valueOf(R.color.bg_gray));
        namesMap.put(EntityType.COW, Integer.valueOf(R.color.bg_home_gray));
        namesMap.put(EntityType.PIG, Integer.valueOf(R.color.bg_item_popup));
        namesMap.put(EntityType.SHEEP, Integer.valueOf(R.color.bg_item_resource_pressed));
        namesMap.put(EntityType.ZOMBIE, Integer.valueOf(R.color.bg_game_cate_split));
        namesMap.put(EntityType.CREEPER, Integer.valueOf(R.color.bg_item_trasnparent));
        namesMap.put(EntityType.SKELETON, Integer.valueOf(R.color.bg_night));
        namesMap.put(EntityType.SPIDER, Integer.valueOf(R.color.bg_recorder_tips_cancel));
        namesMap.put(EntityType.PIG_ZOMBIE, Integer.valueOf(R.color.black));
        namesMap.put(EntityType.UNKNOWN, Integer.valueOf(R.color.black_88));
        namesMap.put(EntityType.ITEM, Integer.valueOf(R.color.black_dd));
        namesMap.put(EntityType.PRIMED_TNT, Integer.valueOf(R.color.blue));
        namesMap.put(EntityType.FALLING_BLOCK, Integer.valueOf(R.color.black_bb));
        namesMap.put(EntityType.ARROW, Integer.valueOf(R.color.blue_text_color));
        namesMap.put(EntityType.SNOWBALL, Integer.valueOf(R.color.bluish_white));
        namesMap.put(EntityType.EGG, Integer.valueOf(R.color.black_cc));
        namesMap.put(EntityType.PAINTING, Integer.valueOf(R.color.border));
        namesMap.put(EntityType.PLAYER, Integer.valueOf(R.color.border_grey));
        namesMap.put(EntityType.WOLF, Integer.valueOf(R.color.brief_text_color));
        namesMap.put(EntityType.VILLAGER, Integer.valueOf(R.color.brown));
        namesMap.put(EntityType.MUSHROOM_COW, Integer.valueOf(R.color.button_pressed_day));
        namesMap.put(EntityType.SLIME, Integer.valueOf(R.color.calendar_header));
        namesMap.put(EntityType.ENDERMAN, Integer.valueOf(R.color.category_gray));
        namesMap.put(EntityType.SILVERFISH, Integer.valueOf(R.color.category_header));
    }

    public static EntityType lookupFromString(CharSequence paramCharSequence, Context paramContext) {
        Resources localResources = paramContext.getResources();
        EntityType localEntityType = EntityType.UNKNOWN;
        for (Entry localEntry : namesMap.entrySet()) {
            if (localResources.getText(((Integer) localEntry.getValue()).intValue()).equals(paramCharSequence)) {
                localEntityType = (EntityType) localEntry.getKey();
            }
        }
        return localEntityType;
    }
}
