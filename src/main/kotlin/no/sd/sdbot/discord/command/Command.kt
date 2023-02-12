package no.sd.sdbot.discord.command

import no.sd.sdbot.discord.function.SDFunctions

enum class Command: FunctionMapper {
    GetPlayersByNames {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getPlayersByNames
        }
    },
    GetPlayersByIds {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getPlayersById
        }
    },
    GetMatch {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getMatch
        }
    },
    GetLastMatch {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getLastMatch
        }
    },
    UpdateGuestScore {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::updateGuestScore
        }
    },
    UpdateGuestWin {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::updateGuestWin
        }
    },
    UpdateSDScore {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::updateSDScore
        }
    },
    UpdateSDWin {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::updateSDWin
        }
    },
    GetGuestScoreBoard {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getGuestScoreBoard
        }
    },
    GetSDScoreBoard {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::getSDScoreBoard
        }
    },
    Help {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::help
        }
    },
    SteinSaksPapir {
        override fun getFunction(sdFunctions: SDFunctions): (CommandMessage) -> CommandMessage {
            return sdFunctions::steinSaksPapir
        }
    };

    companion object {
        fun getCommand(commandString: String): Command? {
            val commands = values().filter { it.name.lowercase() == commandString.lowercase() }
            return if (commands.isNotEmpty() ) commands.first()
            else null
        }
    }
}

interface FunctionMapper {
    fun getFunction(sdFunctions: SDFunctions): ((CommandMessage) -> CommandMessage)
}