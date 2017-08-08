package com.MCWorld.mojang.entity;

import android.graphics.Color;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;

public class DataConstants {

    public enum AnimalDataItem {
        COW("CHICKEN", 0, "牛", EntityType.COW),
        SHEEP("ZOMBIE", 1, "绵羊", EntityType.SHEEP),
        CHICKEN("CHICKEN", 2, "鸡", EntityType.CHICKEN),
        ZOMBIE("ZOMBIE", 3, "僵尸", EntityType.ZOMBIE),
        CREEPER("CREEPER", 4, "爬行者", EntityType.CREEPER),
        SKELETON("SKELETON", 5, "骷髅", EntityType.SKELETON),
        SPIDER("SPIDER", 6, "蜘蛛", EntityType.SPIDER),
        PIG_ZOMBIE("PIG_ZOMBIE", 7, "僵尸猎人", EntityType.PIG_ZOMBIE),
        PIG("PIG", 8, "猪", EntityType.PIG);
        
        private EntityType entityType;
        private String name;

        static {
            AnimalDataItem[] arrayOfAnimalDataItem = new AnimalDataItem[]{COW, SHEEP, CHICKEN, ZOMBIE, CREEPER, SKELETON, SPIDER, PIG_ZOMBIE, PIG};
        }

        private AnimalDataItem(String paramString, EntityType paramEntityType) {
            this.name = paramString;
            this.entityType = paramEntityType;
        }

        private AnimalDataItem(String chicken, int i, String j, EntityType type) {
            this.name = chicken;
            this.entityType = type;
        }

        public EntityType getEntityType() {
            return this.entityType;
        }

        public String getName() {
            return this.name;
        }

        public void setEntityType(EntityType paramEntityType) {
            this.entityType = paramEntityType;
        }

        public void setName(String paramString) {
            this.name = paramString;
        }
    }

    public enum ColorDataItem {
        ORANGE("Orange", 0, "橙色", Integer.valueOf(1), Integer.valueOf(Color.rgb(ErrorCode.RENAME_EXCEPTION, 125, 62))),
        Magenta("Magenta", r4, "品红", Integer.valueOf(2), Integer.valueOf(Color.rgb(179, 80, 188))),
        LIGHTBLUE("LIGHTBLUE", r4, "淡蓝色", Integer.valueOf(3), Integer.valueOf(Color.rgb(107, 138, 201))),
        YELLOW("YELLOW", r4, "黄色", Integer.valueOf(4), Integer.valueOf(Color.rgb(177, 166, 39))),
        LIME("LIME", r4, "绿黄色", Integer.valueOf(5), Integer.valueOf(Color.rgb(65, 174, 56))),
        PINK("PINK", r4, "粉红色", Integer.valueOf(6), Integer.valueOf(Color.rgb(208, 132, 153))),
        GRAY("GRAY", 6, "灰色", Integer.valueOf(7), Integer.valueOf(Color.rgb(64, 64, 64))),
        LIGHT_GREY("LIGHT_GREY", 7, "浅灰色", Integer.valueOf(8), Integer.valueOf(Color.rgb(154, 161, 161))),
        CYAN("CYAN", 8, "青色", Integer.valueOf(9), Integer.valueOf(Color.rgb(46, 110, 137))),
        Purple("Purple", 9, "紫色", Integer.valueOf(10), Integer.valueOf(Color.rgb(126, 61, 181))),
        BLUE("BLUE", 10, "蓝色", Integer.valueOf(11), Integer.valueOf(Color.rgb(46, 56, 141))),
        BROWN("BROWN", 11, "棕色", Integer.valueOf(12), Integer.valueOf(Color.rgb(79, 50, 31))),
        GREED("GREED", 12, "绿色", Integer.valueOf(13), Integer.valueOf(Color.rgb(53, 70, 27))),
        RED("RED", 13, "红色", Integer.valueOf(14), Integer.valueOf(Color.rgb(150, 52, 48))),
        BLACK("BLACK", 14, "黑色", Integer.valueOf(15), Integer.valueOf(Color.rgb(25, 22, 22)));
        
        final Integer colorId;
        final String colorName;
        final Integer id;

        static {
            ColorDataItem[] arrayOfColorDataItem = new ColorDataItem[]{ORANGE, Magenta, LIGHTBLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GREY, CYAN, Purple, BLUE, BROWN, GREED, RED, BLACK};
        }

        private ColorDataItem(String paramString, Integer paramInteger1, Integer paramInteger2) {
            this.colorName = paramString;
            this.id = paramInteger1;
            this.colorId = paramInteger2;
        }

        private ColorDataItem(String desc, int i, String chnDesc, Integer j, Integer k) {
            this.colorName = desc;
            this.id = j;
            this.colorId = k;
        }

        public Integer getColorId() {
            return this.colorId;
        }

        public String getColorName() {
            return this.colorName;
        }

        public Integer getId() {
            return this.id;
        }
    }
}
