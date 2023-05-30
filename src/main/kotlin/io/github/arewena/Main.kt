package io.github.arewena

import io.github.monun.kommand.kommand
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() { logger.info("Plugin Enabled") }

    override fun onDisable() { logger.info("Plugin disabled") }


}