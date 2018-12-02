package com.cyr1en.nudgebot.commands

import com.cyr1en.nudgebot.Bot
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import java.util.*
import java.util.concurrent.TimeUnit

class NudgeCmd(private val bot: Bot) : Command() {

    init {
        this.name = "nudginator"
        this.aliases = arrayOf("poke", "poof", "doink", "nudge")
        this.arguments = "<HH:MM:SS>"
        this.help = "When cy needs a nudge, execute me ;)"
    }

    override fun execute(event: CommandEvent?) {
        val args = event?.args?.split(" ")
        if (args?.size!! > 1 || args.isEmpty()) {
            event.reply("Sadly, you didn't provide enough argument :P")
            return
        }
        val validTime = args[0].contains("^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)\$")
        val sched = args[0].split(":")
        if (!validTime && sched.size < 3) {
            event.reply("I would nudge Cyr1en, but you didn't give a proper schedule")
            return
        }
        val date = Calendar.getInstance(TimeZone.getTimeZone("MST"))
        date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(sched[0]))
        date.set(Calendar.MINUTE, Integer.valueOf(sched[1]))
        date.set(Calendar.SECOND, Integer.valueOf(sched[2]))
        date.add(Calendar.DATE, 1)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val user = bot.getJDA()?.getUserById(bot.getOwnerID())
                event.reply("${user?.asMention}! It's that time of the day! Get yo a$$ working!")
            }
        }, date.time, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))
        event.reply("Alright boss, I will nudge Cyr1en, in this channel, @ ${args[0]} every day!")
    }
}