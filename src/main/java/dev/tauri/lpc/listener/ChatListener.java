package dev.tauri.lpc.listener;

import dev.tauri.lpc.LPC;
import dev.tauri.lpc.util.ComponentUtils;
import dev.tauri.lpc.util.LPUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LPC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ChatListener {
    @SubscribeEvent
    public static void onPlayerDisplayNameGet(PlayerEvent.NameFormat event) {
        if (event.getEntity() instanceof ServerPlayer sp)
            event.setDisplayname(ComponentUtils.toMCComponent(ComponentUtils.colorFormat(LPUtils.getPlayerDisplayName(sp))));
    }
}
