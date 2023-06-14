package com.production.gameplay.utils

class OlympusManager {

    fun olympusAgent(agent: String) : String {
        return agent.replace("wv", "")
    }
}