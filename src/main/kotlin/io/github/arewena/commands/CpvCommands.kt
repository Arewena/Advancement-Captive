package io.github.arewena.commands

import io.github.arewena.*
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


object CpvCommands {
    private fun getInstance(): Plugin {
        return Main.instance
    }

    fun CpvCommands() {
        getInstance().kommand {
            register("captive") {
                then("start") {
                    executes {
                        if (sender.isOp && sender is Player) {
                            sender.sendMessage(text("Captive를 시작합니다. /captive stop으로 중단할수 있지만,").append(text(" 진행상황은 저장되지 않습니다.").color(NamedTextColor.RED)))
                            val playerList = Bukkit.getOnlinePlayers()
                            val player = (sender as Player)
                            startXZ = player.location

                            for (i in playerList) {
                                if (i != sender) {
                                    i.teleport(player.location)
                                    i.gameMode = GameMode.SURVIVAL
                                }
                            }

                            worldborder?.setCenter(startXZ!!.x, startXZ!!.z)
                            worldborder?.setSize(10.0, 1)
                        }
                        else { sender.sendMessage(text("이 명령어를 사용할 권한이 없습니다.").color(NamedTextColor.RED)) }
                    }
                }
                then("list") {
                    executes {
                        sender.sendMessage("총 110개의 도전과제 중 ${advList.size} 개 완료. (월드 크기: ${10 + advList.size * 100} * ${10 + advList.size * 100})")
                    }
                }
                then("stop") {
                    executes {
                        if(sender.isOp && sender is Player) {
                            advList = mutableListOf()
                            worldborder?.setSize(200000000.0, 3)
                        }
                        else { sender.sendMessage(text("이 명령어를 사용할 권한이 없습니다.").color(NamedTextColor.RED)) }
                    }
                }
            }
        }
    }


}