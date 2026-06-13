package dev.tauri.lpc.mixins;

import dev.tauri.lpc.util.ComponentUtils;
import dev.tauri.lpc.util.LPUtils;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Predicate;

@Mixin(value = PlayerList.class, priority = 1300)
public class MixinPlayerChat {
    private static final ThreadLocal<Boolean> IS_BROADCASTING = ThreadLocal.withInitial(() -> false);

    @Inject(
            method = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void onBroadcastChatMessage(PlayerChatMessage originalMessage, Predicate<ServerPlayer> pShouldFilterMessageTo, ServerPlayer player, ChatType.Bound bound, CallbackInfo ci) {
        if (IS_BROADCASTING.get()) {
            return;
        }
        try {
            IS_BROADCASTING.set(true);
            var originalText = originalMessage.decoratedContent().getString();
            if (player == null) return;

            var format = LPUtils.buildFormat(player);
            var processedMessage = LPUtils.processMessage(player, originalText);
            var newMessage = ComponentUtils.toMCComponent(ComponentUtils.colorFormat(format.replace("{message}", processedMessage)));

            //var newMessageComplete = PlayerChatMessage.unsigned(originalMessage.sender(), newMessage.getString()).withUnsignedContent(newMessage);
            Objects.requireNonNull(player.getServer()).getPlayerList().broadcastSystemMessage(newMessage, false);
            player.getServer().logChatMessage(newMessage, ChatType.bind(ChatType.CHAT, player), null);

            ci.cancel();
        } finally {
            IS_BROADCASTING.set(false);
        }
    }
}
