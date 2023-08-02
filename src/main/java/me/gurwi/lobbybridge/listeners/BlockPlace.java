package me.gurwi.lobbybridge.listeners;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.SneakyThrows;
import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.cache.PlayersManager;
import me.gurwi.lobbybridge.config.managers.LobbyBlocksManager;
import me.gurwi.lobbybridge.enums.Version;
import me.gurwi.lobbybridge.nms.BlockBreakAnimation;
import me.gurwi.lobbybridge.objects.LobbyBlock;
import me.gurwi.lobbybridge.objects.LobbyPlayer;
import me.gurwi.lobbybridge.utils.LobbyBlockUtils;
import me.gurwi.lobbybridge.utils.PluginCustomLoader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockPlace implements Listener {

    private final LobbyBridge plugin = LobbyBridge.getInstance();
    private final LobbyBlocksManager lobbyBlocksManager = PluginCustomLoader.getInstance().getConfigLoader().getLobbyBlocksManager();

    @Getter
    private static final Map<Integer, Block> placedBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        ItemStack placedBlock = event.getItemInHand();
        Block block = event.getBlock();

        if (!LobbyBlockUtils.isLobbyBlock(placedBlock)) return;

        placedBlock.setAmount(64);

        LobbyPlayer lobbyPlayer = PlayersManager.getInstance().getPlayersMap().get(event.getPlayer().getUniqueId());
        LobbyBlock lobbyBlock = lobbyBlocksManager.getBlocksMap().get(XMaterial.matchXMaterial(placedBlock.getType()).name());
        AtomicInteger blockBreakStep = new AtomicInteger();

        int entityId = BlockBreakAnimation.getNextEntityId();
        placedBlocks.put(entityId, block);
        lobbyPlayer.getPlacedBlocks().incrementAndGet();

        new BukkitRunnable() {
            @Override
            public void run() {
                BlockBreakAnimation.breakBlock(entityId, block, blockBreakStep.getAndIncrement());
                if (blockBreakStep.get() <= 9) return;

                cancel();

                switch (lobbyPlayer.getBreakAnimation()) {

                    case FALL:
                        spawnFallingBlock(block);
                        break;

                    case EXPLODE:
                        new ParticleBuilder(ParticleEffect.EXPLOSION_LARGE).setLocation(block.getLocation()).display();
                        break;

                }

                block.setType(Material.AIR);

                placedBlocks.remove(entityId);
                BlockBreakAnimation.breakBlock(entityId, block, -1);

            }

        }.runTaskTimer(plugin, 0, Math.round((float) (lobbyBlock.getBreakDelay() * 20L) / 9));

    }

    @SneakyThrows
    @SuppressWarnings("deprecation")
    private void spawnFallingBlock(Block block) {

        FallingBlock fallingBlock;

        if (Version.getVersion(plugin).isNewerThan(Version.V1_8)) {
            fallingBlock = (FallingBlock) block.getWorld().getClass().getDeclaredMethod("spawnFallingBlock", Location.class, MaterialData.class).invoke(block.getWorld(), block.getLocation(), new MaterialData(block.getType()));
        } else {
            fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
        }

        fallingBlock.setCustomName("LobbyBridge_Block");
        fallingBlock.setDropItem(false);

    }

}
