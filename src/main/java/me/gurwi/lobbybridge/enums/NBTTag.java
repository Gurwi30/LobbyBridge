package me.gurwi.lobbybridge.enums;


import lombok.Getter;

public enum NBTTag {

    BLOCK_TAG("lobbybridge_block");

    // CONSTRUCTOR

    @Getter
    private final String tag;

    NBTTag(String tag) {
        this.tag = tag;
    }

}
