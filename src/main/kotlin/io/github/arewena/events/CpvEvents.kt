package io.github.arewena.events


import io.github.arewena.*
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class CpvEvents : Listener {
    @EventHandler
    fun onFirstJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!player.hasPlayedBefore() && startXZ != null) {
            player.teleport(startXZ!!)
            player.gameMode = GameMode.SPECTATOR
        }
        else if(pause) {
            player.gameMode = GameMode.SPECTATOR
        }
    }

    @EventHandler
    fun onAdvDone(event: PlayerAdvancementDoneEvent) {
        if (advList.size + 1 == 102) {
            worldborder?.setSize(200000000000.0, 6)
            for (i in Bukkit.getOnlinePlayers()) {
                i.sendMessage(text("게임과 핵심의 이야기").color(NamedTextColor.GOLD))
            }
        }
        if (!pause) {
            if (!event.advancement.display?.isHidden!!) {
                if (!advList.contains(event.advancement.key.toString())) {
                    advList += event.advancement.key.toString()
                    worldborder?.setSize(worldborder.size + advList.size * 8, 6)
                }
            }
            else {
                if (!hiddenList.contains(event.advancement.key.toString())) {
                    hiddenList += event.advancement.key.toString()
                    worldborder?.setSize(worldborder.size + 256, 6)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (pause && !event.player.isOp) {
            event.isCancelled = true
            event.player.sendMessage(text("퍼즈가 적용되었습니다! 관리자에게 재개를 요청하세요.").color(NamedTextColor.RED))
        }
    }
}