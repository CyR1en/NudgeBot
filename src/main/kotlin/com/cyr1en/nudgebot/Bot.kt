@file:JvmName("Bot")
package com.cyr1en.nudgebot

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class Bot(private val waiter: EventWaiter) {

    private val client = CommandClientBuilder()

    private var JDA: JDA? = null
    private var config = ArrayList<String>()
    private var commands = ArrayList<Command>()
    private var listeners = ArrayList<ListenerAdapter>()
    private var token = ""
    private var ownerID = ""

    init {
        config = Files.readAllLines(Paths.get("config.txt")) as ArrayList<String>
        token = config[0]
        ownerID = config[1]
    }

    fun start() {
        client.setGame(Game.of(Game.GameType.WATCHING, "Cyr1en ;)"))
        client.setOwnerId(ownerID)
        client.setPrefix("!!")
        commands.forEach { cmd -> client.addCommand(cmd) }
        val builder = JDABuilder(AccountType.BOT)
            .setToken(token)
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .setGame(Game.playing("loading nudginator!"))
            .addEventListener(waiter)
        listeners.forEach { listener -> builder.addEventListener(listener) }
        JDA = builder.addEventListener(client.build()).build()
    }

    fun getOwnerID() = ownerID

    fun getJDA() = JDA

    fun getWaiter() = waiter

    fun appendListener(listener: ListenerAdapter): Bot {
        listeners.add(listener)
        return this
    }

    fun appendCommand(command: Command): Bot {
        commands.add(command)
        return this
    }

}