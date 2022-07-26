package grauly.hudcompass.events;

import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.launcher.SessionEventListener;
import grauly.hudcompass.HudCompass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.WorldSavePath;

public class WordIdentifierListener implements SessionEventListener {

    @Override
    public void onStartGameSession(GameSession session) {
        var worldIdentifier = "";
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (session.isRemoteServer()) worldIdentifier = client.getCurrentServerEntry().address;
            else worldIdentifier = client.getServer().getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
            HudCompass.LOGGER.info("worldID: {}",worldIdentifier);
        } catch (NullPointerException e) {
            HudCompass.LOGGER.info("null fuckery.");
        }
    }
}
