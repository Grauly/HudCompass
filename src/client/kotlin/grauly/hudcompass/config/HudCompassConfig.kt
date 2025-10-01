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
    var compassPositioning: CompassPositioning = CompassPositioning()
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
        @Comment("Will default to hudcompass:default in case of any errors")
        var barStyle: String = Identifier.of(HudCompassClient.MODID, "default").toString()
        @Comment("What WaypointRenderer is picked for rendering")
        @Comment("Needs to be a valid Identifier")
        @Comment("Will default to hudcompass:default in case of any errors")
        var waypointStyle: String = Identifier.of(HudCompassClient.MODID, "default").toString()
    }

    class CompassPositioning: Section {
        @Comment("The screen positon from which the offset is taken")
        var anchor: AnchorPositions = AnchorPositions.TOP_CENTER
        var offsetX: Int = 0
        var offsetY: Int = 0
    }

    class WaypointSources: Section {
        @Comment("Under which conditions to show lodestone compass' locations on the compass")
        var lodestoneCompasses: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
        @Comment("Under which conditions to show marked map locations on the compass")
        var mapDecorations: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
        @Comment("Under which conditions to show the recovery compass's target on the compass")
        var recoveryCompass: ItemWaypointProviderMode = ItemWaypointProviderMode.IN_INVENTORY
    }

    class LocatorBarSettings: Section {
        @Comment("Whether to disable the locator bar")
        var disableLocatorBar: Boolean = true
        @Comment("Whether to show locator bar waypoints on the compass")
        var showOnCompass: Boolean = true
    }

    class DeathPoints: Section {
        @Comment("Whether to track death points")
        var enabled: Boolean = false
        @Comment("How many death points should be saved, before old ones get removed")
        @IntegerRange(min = 1, max = 1024)
        var amountOfDeathsSaved: Int = 5
        @Comment("Whether death points should be automatically deleted if the player gets close enough to it")
        var autoClearDeathPoints: Boolean = true
        @Comment("The Distance how close a player needs to get to get a deathpoint cleared")
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
