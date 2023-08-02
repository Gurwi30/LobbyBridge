package me.gurwi.lobbybridge.cache;

import lombok.Getter;
import me.gurwi.lobbybridge.objects.LobbyPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersManager {

    @Getter(lazy = true)
    private static final PlayersManager instance = new PlayersManager();

    @Getter
    private final Map<UUID, LobbyPlayer> playersMap = new HashMap<>();

}
