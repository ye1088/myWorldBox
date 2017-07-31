package com.huluxia.mojang.options;

import java.io.Serializable;

public class Options implements Serializable {
    protected Integer game_thirdperson;
    protected Integer old_game_version_beta;
    protected Integer old_game_version_major;
    protected Integer old_game_version_minor;
    protected Integer old_game_version_patch;

    public Integer getGame_thirdperson() {
        return this.game_thirdperson;
    }

    public void setGame_thirdperson(Integer game_thirdperson) {
        this.game_thirdperson = game_thirdperson;
    }

    public Integer getOld_game_version_beta() {
        return this.old_game_version_beta;
    }

    public Integer getOld_game_version_major() {
        return this.old_game_version_major;
    }

    public Integer getOld_game_version_minor() {
        return this.old_game_version_minor;
    }

    public Integer getOld_game_version_patch() {
        return this.old_game_version_patch;
    }

    public void setOld_game_version_beta(Integer paramInteger) {
        this.old_game_version_beta = paramInteger;
    }

    public void setOld_game_version_major(Integer paramInteger) {
        this.old_game_version_major = paramInteger;
    }

    public void setOld_game_version_minor(Integer paramInteger) {
        this.old_game_version_minor = paramInteger;
    }

    public void setOld_game_version_patch(Integer paramInteger) {
        this.old_game_version_patch = paramInteger;
    }
}
