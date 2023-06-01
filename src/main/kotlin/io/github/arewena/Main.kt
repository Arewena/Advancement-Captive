package io.github.arewena

import io.github.arewena.commands.CpvCommands
import io.github.arewena.events.CpvEvents
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

//전역변수
val worldborder = Bukkit.getWorld("world")?.worldBorder
var advList = mutableListOf<Any?>("1")
var hiddenList = mutableListOf<Any?>()
var startXZ: Location? = null
var pause = false

class Main : JavaPlugin() {
    companion object {
        lateinit var mainInstance: Main
            private set
    }
    override fun onEnable() {
        advList = config.getList("advList")!!.toMutableList()
        worldborder?.setSize(config.getDouble("worldBorder"), 1)
        mainInstance = this
        CpvCommands.CpvCommands()
        server.pluginManager.registerEvents(CpvEvents(), this)
        logger.info("Plugin Enabled")
    }

    override fun onDisable() {
        logger.info("Plugin disabled")
        config.options().copyDefaults(true)
        config.set("advList", advList)
        config.set("worldBorder", worldborder?.size)
        saveConfig()
    }


}