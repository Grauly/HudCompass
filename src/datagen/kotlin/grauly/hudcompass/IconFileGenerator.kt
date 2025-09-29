package grauly.hudcompass

import net.minecraft.util.Identifier

object IconFileGenerator {
    val MAP_ICONS: ArrayList<Identifier?> = ArrayList()

    private fun addIcon(id: String?) {
        MAP_ICONS.add(Identifier.ofVanilla("textures/map/decorations/$id.png"))
    }

    fun init() {
        addIcon("player")
        addIcon("frame")
        addIcon("red_marker")
        addIcon("blue_marker")
        addIcon("target_x")
        addIcon("target_point")
        addIcon("player_off_map")
        addIcon("player_off_limits")
        addIcon("woodland_mansion")
        addIcon("ocean_monument")
        addIcon("white_banner")
        addIcon("orange_banner")
        addIcon("magenta_banner")
        addIcon("light_blue_banner")
        addIcon("yellow_banner")
        addIcon("lime_banner")
        addIcon("pink_banner")
        addIcon("gray_banner")
        addIcon("light_gray_banner")
        addIcon("cyan_banner")
        addIcon("purple_banner")
        addIcon("blue_banner")
        addIcon("brown_banner")
        addIcon("green_banner")
        addIcon("red_banner")
        addIcon("black_banner")
        addIcon("red_x")
        addIcon("desert_village")
        addIcon("plains_village")
        addIcon("savanna_village")
        addIcon("snowy_village")
        addIcon("taiga_village")
        addIcon("jungle_temple")
        addIcon("swamp_hut")
        addIcon("trial_chambers")
    }
}