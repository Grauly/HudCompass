package grauly.hudcompass.rendering.waypoint

import grauly.hudcompass.waypoint.location.WaypointLocation
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey

data class WaypointRenderState(
    val location: WaypointLocation,
    val iconProvider: WaypointIconProvider,
) {
    private var extraData: MutableMap<RenderStateDataKey<*>, Object>? = null

    @Suppress("UNCHECKED_CAST")
    fun <T> getData(key: RenderStateDataKey<T>): T? {
        return extraData?.get(key) as T?
    }

    fun <T> setData(key: RenderStateDataKey<T>, value: T) {
        if (extraData == null) {
            extraData = mutableMapOf()
        }
        extraData?.put(key, value as Object)
    }

    fun clearData() {
        extraData = null
    }

    companion object {
        val NAME: RenderStateDataKey<String> = RenderStateDataKey.create()
        val NAME_COLOR: RenderStateDataKey<Int> = RenderStateDataKey.create()
        val DISTANCE_COLOR: RenderStateDataKey<Int> = RenderStateDataKey.create()
    }
}