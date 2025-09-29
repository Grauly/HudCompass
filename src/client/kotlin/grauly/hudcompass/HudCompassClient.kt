package grauly.hudcompass

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object HudCompassClient : ClientModInitializer {
	const val MODID = "hudcompass"
	val logger = LoggerFactory.getLogger(MODID)
	override fun onInitializeClient() {
		KeyBindings.init()
	}
}