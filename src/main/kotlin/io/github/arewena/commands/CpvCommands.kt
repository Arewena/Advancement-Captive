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
    private fun Instance(): Plugin { return Main.mainInstance }

    fun CpvCommands() {
        Instance().kommand {
            register("captive") {
                then("start") {
                    executes {
                        if (sender.isOp && sender is Player) {
                            sender.sendMessage(text("Captive를 시작합니다. /captive stop으로 취소할 수 있지만,").append(text("진행상황은 저장되지 않습니다.").color(NamedTextColor.RED)))
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
                        sender.sendMessage("총 102개의 도전과제 중 ${advList.size} 개 완료. (월드 크기: ${10 + advList.size * 8 + hiddenList.size * 256} * ${10 + advList.size * 8 + hiddenList.size * 256})")
                    }
                }
                then("pause") {
                    executes {
                        if (sender.isOp && !pause) {
                            worldborder?.setSize(200000000.0, 3)
                            (sender as Player).sendMessage("Captive를 일시 중지 했습니다. /captive resume으로 재개할 수 있습니다.")
                            for (i in Bukkit.getOnlinePlayers()) {
                                i.gameMode = GameMode.SPECTATOR
                            }
                        }
                        else if (!sender.isOp) {
                            sender.sendMessage(text("이 명령어를 사용할 권한이 없습니다.").color(NamedTextColor.RED))
                        }
                        else if (pause) {
                            sender.sendMessage(text("퍼즈가 이미 걸려있습니다.").color(NamedTextColor.RED))
                        }

                    }
                }
                then("resume") {
                    executes {
                        if (sender.isOp && pause) {
                            for (i in Bukkit.getOnlinePlayers()) {
                                i.gameMode = GameMode.SURVIVAL
                                (sender as Player).sendMessage("Captive를 재개합니다.")
                                pause = false
                            }
                        }
                        else if (!sender.isOp) {
                            sender.sendMessage(text("이 명령어를 사용할 권한이 없습니다.").color(NamedTextColor.RED))
                        }
                        else if (!pause) {
                            sender.sendMessage(text("퍼즈가 걸린 상태가 아닙니다.").color(NamedTextColor.RED))
                        }
                    }
                }
                then("stop") {
                    executes {
                        if(sender.isOp && sender is Player) {
                            sender.sendMessage("Captive를 취소했습니다. 이미 도전과제를 깬 전적이 있으시다면 다시 시작할때 서버를 초기화 하는걸 권장드립니다.")
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