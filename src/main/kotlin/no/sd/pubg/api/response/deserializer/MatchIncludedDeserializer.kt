package no.sd.pubg.api.response.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.module.kotlin.KotlinModule
import no.sd.pubg.api.response.AssetResponse
import no.sd.pubg.api.response.IncludedResponse
import no.sd.pubg.api.response.ParticipantResponse
import no.sd.pubg.api.response.RosterResponse

class MatchIncludedDeserializer : StdDeserializer<IncludedResponse>(IncludedResponse::class.java) {

    override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): IncludedResponse {
        val assets: MutableList<AssetResponse> = arrayListOf()
        val participants: MutableList<ParticipantResponse> = arrayListOf()
        val rosters: MutableList<RosterResponse> = arrayListOf()

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule.Builder().build())
        val arrayNode: ArrayNode = jp!!.codec.readTree(jp)

        arrayNode.forEach {
            when(it.get("type").textValue()){
                "participant" -> participants.add(objectMapper.treeToValue(it, ParticipantResponse::class.java))
                "roster" -> rosters.add(objectMapper.treeToValue(it, RosterResponse::class.java))
                "asset" -> assets.add(objectMapper.treeToValue(it, AssetResponse::class.java))
                else -> throw RuntimeException("No such type")
            }
        }
        return IncludedResponse(assets, participants, rosters)
    }
}