package io.github.arewena.events


import io.github.arewena.advList
import io.github.arewena.startXZ
import io.github.arewena.worldborder
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerJoinEvent

class CpvEvents : Listener {
    @EventHandler
    fun onFirstJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!player.hasPlayedBefore() && startXZ != null) {
            player.teleport(startXZ!!)
            player.gameMode = GameMode.SPECTATOR
        }
    }

    @EventHandler
    fun onAdvDone(event: PlayerAdvancementDoneEvent) {
        if (!advList.contains(event.advancement.toString())) {
            advList += event.advancement.key.toString()
            worldborder?.setSize(10.0 + (advList.size * 50).toDouble(), 6)

        }

    }
}