package com.huluxia.mojang.entitystore;

import com.huluxia.mojang.entity.Minecart;
import java.util.List;
import org.spout.nbt.Tag;

public class MinecartEntityStore<T extends Minecart> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        return super.save(paramT);
    }
}
