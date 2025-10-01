package grauly.hudcompass.config

import folk.sisby.kaleido.api.WrappedConfig
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange
import grauly.hudcompass.HudCompassClient
import net.minecraft.util.Identifier

class HudCompassConfig: WrappedConfig() {

    @Comment("Whether to show the compass")
    var compassVisibility: CompassVisibilityOptions = CompassVisibilityOptions.ALWAYS

    var compassSpecifics: CompassSpecifics = CompassSpecifics()
    var compassStyling: CompassStyling = CompassStyling()
    var waypointSources: WaypointSources = WaypointSources()
    var locatorBarSettings: LocatorBarSettings = LocatorBarSettings()
    var deathPoints: DeathPoints = DeathPoints()
    var teleportConfiguration: TeleportConfiguration = TeleportConfiguration()

    class CompassSpecifics: Section {
        var cardinalDirections: CardinalDirectionMode = CardinalDirectionMode.BASIC
        @IntegerRange(min = 1, max = 360)
        var degreesOnCompass: Int = 360
        var showDegreeNumbers: Boolean = false
        @IntegerRange(min = 0, max = 360)
        var degreeNumbersShown: Int = 15
        @IntegerRange(min = 0, max = 360)
        var degreeTicks: Int = 5
    }

    class CompassStyling: Section {
        @Comment("What CompassRenderer is picked for rendering")
        @Comment("Needs to be a valid Identifier")
        @Comment("Will default to the default renderer in case of any errors")
        var barStyle: String = Identifier.of(HudCompassClient.MODID, "default").toString()
        @Comment("What WaypointRenderer is picked for rendering")
        @Comment("Needs to be a valid Identifier")
        @Comment("Will default to the default renderer in case of any errors")
        var waypointStyle: String = Identifier.of(HudCompassClient.MODID, "default").toString()
    }

    class WaypointSources: Section {
        var lodestoneCompasses: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
        var mapDecorations: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
        var recoveryCompass: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
    }

    class LocatorBarSettings: Section {
        var disableLocatorBar: Boolean = true
        var showOnCompass: Boolean = true
    }

    class DeathPoints: Section {
        var enabled: Boolean = false
        var amountOfDeathsSaved: Int = 5
        var autoClearDeathPoints: Boolean = true
        var autoClearRadius: Int = 5
    }

    class TeleportConfiguration: Section {
        @Comment("Command for teleporting in the same dimension")
        @Comment("Allows for data insertion")
        @Comment("\$x - The x coordinate")
        @Comment("\$y - The y coordinate")
        @Comment("\$z - The z coordinate")
        var sameDimensionTeleport: String = "/tp \$x \$y \$z"
        @Comment("Command for teleporting to another dimension")
        @Comment("Allows for data insertion")
        @Comment("\$d - The Dimensions identifier")
        @Comment("\$x - The x coordinate")
        @Comment("\$y - The y coordinate")
        @Comment("\$z - The z coordinate")
        var crossDimensionTeleport: String = "/execute in \$d run tp \$x \$y \$z"
    }
}
