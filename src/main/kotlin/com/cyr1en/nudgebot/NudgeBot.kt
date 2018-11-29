@file:JvmName("NudgeBot")

package com.cyr1en.nudgebot

import com.cyr1en.nudgebot.commands.NudgeCmd
import com.cyr1en.nudgebot.listener.NudlgeLsntr
import com.jagrosh.jdautilities.commons.waiter.EventWaiter


fun start() {
    val bot = Bot(EventWaiter())
    bot.appendCommand(NudgeCmd(bot)).appendListener(NudlgeLsntr(bot))
    bot.start()
}

fun main(arg: Array<String>) {
    start()
}
