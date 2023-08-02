package me.gurwi.lobbybridge.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.gurwi.lobbybridge.enums.BreakAnimation;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PseudoPlayer {

    private final UUID uniqueId;
    private final int placedBlocks;
    private final XMaterial blockMaterial;
    private final BreakAnimation breakAnimation;

}
