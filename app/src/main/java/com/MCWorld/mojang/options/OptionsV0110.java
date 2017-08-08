package com.MCWorld.mojang.options;

import java.io.Serializable;

public class OptionsV0110 extends Options implements Serializable {
    private static final long serialVersionUID = -7249156821462100507L;
    private Float audio_sound;
    private Integer ctrl_invertmouse;
    private Integer ctrl_islefthanded;
    private Float ctrl_sensitivity;
    private Integer ctrl_usetouchjoypad;
    private Integer ctrl_usetouchscreen;
    private String dev_autoloadlevel;
    private String dev_disablefilesystem;
    private String dev_showchunkmap;
    private Integer feedback_vibration;
    private Integer game_difficulty;
    private String game_flatworldlayers;
    private String game_language;
    private Integer game_lastcustomskin;
    private String game_limitworldsize;
    private Integer game_skintype;
    private Integer game_thirdperson;
    private Integer gfx_animatetextures;
    private Float gfx_dpadscale;
    private Integer gfx_fancygraphics;
    private Integer gfx_fancyskies;
    private String gfx_gamma;
    private Integer gfx_hidegui;
    private String gfx_renderdistance_new;
    private Integer mp_server_visible;
    private String mp_username;
    private Integer old_game_version_beta;
    private Integer old_game_version_major;
    private Integer old_game_version_minor;
    private Integer old_game_version_patch;

    public Integer getOld_game_version_beta() {
        return this.old_game_version_beta;
    }

    public void setOld_game_version_beta(Integer old_game_version_beta) {
        this.old_game_version_beta = old_game_version_beta;
    }

    public Integer getOld_game_version_major() {
        return this.old_game_version_major;
    }

    public void setOld_game_version_major(Integer old_game_version_major) {
        this.old_game_version_major = old_game_version_major;
    }

    public Integer getOld_game_version_minor() {
        return this.old_game_version_minor;
    }

    public void setOld_game_version_minor(Integer old_game_version_minor) {
        this.old_game_version_minor = old_game_version_minor;
    }

    public Integer getOld_game_version_patch() {
        return this.old_game_version_patch;
    }

    public void setOld_game_version_patch(Integer old_game_version_patch) {
        this.old_game_version_patch = old_game_version_patch;
    }

    public Integer getGame_thirdperson() {
        return this.game_thirdperson;
    }

    public void setGame_thirdperson(Integer game_thirdperson) {
        this.game_thirdperson = game_thirdperson;
    }

    public Float getGfx_dpadscale() {
        return this.gfx_dpadscale;
    }

    public void setGfx_dpadscale(Float gfx_dpadscale) {
        this.gfx_dpadscale = gfx_dpadscale;
    }

    public String getGame_language() {
        return this.game_language;
    }

    public void setGame_language(String game_language) {
        this.game_language = game_language;
    }

    public Integer getGame_skintype() {
        return this.game_skintype;
    }

    public void setGame_skintype(Integer game_skintype) {
        this.game_skintype = game_skintype;
    }

    public Integer getGame_lastcustomskin() {
        return this.game_lastcustomskin;
    }

    public void setGame_lastcustomskin(Integer game_lastcustomskin) {
        this.game_lastcustomskin = game_lastcustomskin;
    }

    public Float getAudio_sound() {
        return this.audio_sound;
    }

    public Integer getCtrl_invertmouse() {
        return this.ctrl_invertmouse;
    }

    public Integer getCtrl_islefthanded() {
        return this.ctrl_islefthanded;
    }

    public Float getCtrl_sensitivity() {
        return this.ctrl_sensitivity;
    }

    public Integer getCtrl_usetouchjoypad() {
        return this.ctrl_usetouchjoypad;
    }

    public Integer getCtrl_usetouchscreen() {
        return this.ctrl_usetouchscreen;
    }

    public String getDev_autoloadlevel() {
        return this.dev_autoloadlevel;
    }

    public String getDev_disablefilesystem() {
        return this.dev_disablefilesystem;
    }

    public String getDev_showchunkmap() {
        return this.dev_showchunkmap;
    }

    public Integer getFeedback_vibration() {
        return this.feedback_vibration;
    }

    public Integer getGame_difficulty() {
        return this.game_difficulty;
    }

    public String getGame_flatworldlayers() {
        return this.game_flatworldlayers;
    }

    public String getGame_limitworldsize() {
        return this.game_limitworldsize;
    }

    public Integer getGfx_animatetextures() {
        return this.gfx_animatetextures;
    }

    public Integer getGfx_fancygraphics() {
        return this.gfx_fancygraphics;
    }

    public Integer getGfx_fancyskies() {
        return this.gfx_fancyskies;
    }

    public String getGfx_gamma() {
        return this.gfx_gamma;
    }

    public Integer getGfx_hidegui() {
        return this.gfx_hidegui;
    }

    public String getGfx_renderdistance_new() {
        return this.gfx_renderdistance_new;
    }

    public Integer getMp_server_visible() {
        return this.mp_server_visible;
    }

    public String getMp_username() {
        return this.mp_username;
    }

    public void setAudio_sound(Float paramInteger) {
        this.audio_sound = paramInteger;
    }

    public void setCtrl_invertmouse(Integer paramInteger) {
        this.ctrl_invertmouse = paramInteger;
    }

    public void setCtrl_islefthanded(Integer paramInteger) {
        this.ctrl_islefthanded = paramInteger;
    }

    public void setCtrl_sensitivity(Float paramFloat) {
        this.ctrl_sensitivity = paramFloat;
    }

    public void setCtrl_usetouchjoypad(Integer paramInteger) {
        this.ctrl_usetouchjoypad = paramInteger;
    }

    public void setCtrl_usetouchscreen(Integer paramInteger) {
        this.ctrl_usetouchscreen = paramInteger;
    }

    public void setDev_autoloadlevel(String paramString) {
        this.dev_autoloadlevel = paramString;
    }

    public void setDev_disablefilesystem(String paramString) {
        this.dev_disablefilesystem = paramString;
    }

    public void setDev_showchunkmap(String paramString) {
        this.dev_showchunkmap = paramString;
    }

    public void setFeedback_vibration(Integer paramInteger) {
        this.feedback_vibration = paramInteger;
    }

    public void setGame_difficulty(Integer paramInteger) {
        this.game_difficulty = paramInteger;
    }

    public void setGame_flatworldlayers(String paramString) {
        this.game_flatworldlayers = paramString;
    }

    public void setGame_limitworldsize(String paramString) {
        this.game_limitworldsize = paramString;
    }

    public void setGfx_animatetextures(Integer paramInteger) {
        this.gfx_animatetextures = paramInteger;
    }

    public void setGfx_fancygraphics(Integer paramInteger) {
        this.gfx_fancygraphics = paramInteger;
    }

    public void setGfx_fancyskies(Integer paramInteger) {
        this.gfx_fancyskies = paramInteger;
    }

    public void setGfx_gamma(String paramString) {
        this.gfx_gamma = paramString;
    }

    public void setGfx_hidegui(Integer paramInteger) {
        this.gfx_hidegui = paramInteger;
    }

    public void setGfx_renderdistance_new(String paramString) {
        this.gfx_renderdistance_new = paramString;
    }

    public void setMp_server_visible(Integer paramInteger) {
        this.mp_server_visible = paramInteger;
    }

    public void setMp_username(String paramString) {
        this.mp_username = paramString;
    }
}
