package com.cyr1en.nudgebot.listener

import com.cyr1en.nudgebot.Bot
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter


public class NudlgeLsntr(private val bot: Bot) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        val content = event?.message!!.contentRaw
        print("captured event!")
        val determinants = arrayOf("Nudger", "NudgeBot", "Nudginator")
        determinants.forEach { determinant ->
            print("determined a nudge request!")
            if (content.startsWith("Hey $determinant", true)) {
                processCall(content, event)
            }
        }
    }

    private fun processCall(statement: String, event: MessageReceivedEvent?) {
        print("checking nudge request")
        val determinants = arrayOf("nudge", "poke", "disturb")
        determinants.forEach { determinant ->
            print("confirmed nudge")
            if (statement.contains(determinant, true)) {
                event?.textChannel?.sendMessage(
                    "${bot.getJDA()!!.
                        getUserById(bot.getOwnerID()).asMention} can you get your a$$ working! :postal_horn:")!!.queue()
                return
            }
        }
    }
}