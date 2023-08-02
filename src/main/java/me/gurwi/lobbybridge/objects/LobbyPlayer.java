package me.gurwi.lobbybridge.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.gurwi.lobbybridge.enums.BreakAnimation;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@AllArgsConstructor
public class LobbyPlayer {

    private final UUID uniqueId;
    private final AtomicInteger placedBlocks;

    @Setter
    private LobbyBlock selectedBlock;
    @Setter
    private BreakAnimation breakAnimation;

}
