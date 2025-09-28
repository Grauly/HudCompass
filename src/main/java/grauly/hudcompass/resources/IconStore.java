package grauly.hudcompass.resources;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class IconStore extends SimpleResourceReloader<Map<Identifier, IconStore.IconData>> {

    private Map<Identifier, IconData> iconCache = new HashMap<>();
    private List<IdentifiedIconData> orderChache = new ArrayList<>();

    @Override
    protected Map<Identifier, IconData> prepare(Store store) {
        var resourceManager = store.getResourceManager();
        Map<Identifier, List<Resource>> foundResources = resourceManager.findAllResources("icons", id -> id.getPath().split("/")[0].equals("icons"));
        //I had hoped it would not come to this
        Gson gson = new Gson();
        Map<Identifier, IconData> foundData = new HashMap<>();
        foundResources.forEach((id, resources) -> {
            var optionalResource = resourceManager.getResource(id);
            if (optionalResource.isEmpty()) return;
            try (Reader reader = optionalResource.get().getReader()) {
                foundData.put(id, gson.fromJson(reader, IconParsingData.class).toData());
            } catch (IOException | JsonIOException e) {
                throw new RuntimeException("Failed to read: " + id.toString(), e);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("Failed to parse: " + id.toString(), e);
            }
        });
        return foundData;
    }

    @Override
    protected void apply(Map<Identifier, IconData> identifierIconDataMap, Store store) {
        iconCache = identifierIconDataMap;
        orderChache = iconCache.entrySet().stream()
                .map(entry -> entry.getValue().toIdentified(entry.getKey()))
                .sorted((a,b) -> {
                    if (a.showAfter != b.showAfter) {
                        return Integer.compare(a.showAfter, b.showAfter);
                    }
                    return String.CASE_INSENSITIVE_ORDER.compare(a.identifier.toString(), b.identifier.toString());
                })
                .toList();
    }

    public List<IdentifiedIconData> getAvailableIcons() {
        return List.copyOf(orderChache);
    }

    public Identifier resolveIcon(Identifier entry) {
        return iconCache.get(entry).texture;
    }

    record IconParsingData(String texture, int showAfter) {
        public IconData toData() {
            return new IconData(Identifier.tryParse(texture), showAfter);
        }
    }

    record IconData(Identifier texture, int showAfter) {
        public static Codec<IconData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(IconData::texture),
                Codecs.NON_NEGATIVE_INT.fieldOf("showAfter").forGetter(IconData::showAfter)
        ).apply(instance, IconData::new));

        public IdentifiedIconData toIdentified(Identifier identifier) {
            return new IdentifiedIconData(identifier, texture, showAfter);
        }
    }

    public record IdentifiedIconData(Identifier identifier, Identifier texture, int showAfter) {
    }
}
