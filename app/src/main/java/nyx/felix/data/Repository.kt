package nyx.felix.data

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

object Repository {

    private val client = HttpClient(CIO) {

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend fun getText(): String = client.get("pxnx-rw.ddns.net:9090/text")

    fun getTextLocally(): List<String> = listOf(
        "On a Friday evening, after a hard day at work after which I was shopping for the upcoming weekend, I open the door of my flat and enter. Just like any Thursday or Friday a few of my flat-mate's friends are here. I firstly walk straight to my room, unzip my helmet and put it in the big part of my backpack. Then I put the backpack down, open the front part, grab my phone and check it.",
        "A message by this Italian girl I adore. Makes me smile a little. Gonna respond to it real quick. Tell her that I've just been shopping at the local discounter and scroll a bit around, re-reading a few messages makes me smile again.",
        "It's been raining quite a bit while driving home, so I open up my jacket and hand it on the hangers at the door. Get rid of my shoes and socks as they tend to ferl not very comfortable, I also change me wet trousers to something more convenient (and dry).",
        "Let's see what else was going on on Telegram. Ah yes.. some people have questions about implementing certain parts of their software, others want to know some language specific corrections. Gonna do that real quick while waiting for her to come online. Answering the more important stuff for a bunch of minutes. Still no response.",
        "I feel suddenly quite hungry. It got a bit more quiet in the kitchen, so hopefully not an issue for then when I want to warm up the Gnocchis I made yesterday. I tried re-cooking them after this recipe she sent me and they turned out much better than I expected. Hopefully they taste just as good when heating them up a second time in the pan.",
        "I stand up from the bed, go to my laptop and start some update, might be done after dinner. My mouth waters, so it's definitely time for the kitchen now.",
        "Take a sip of Almdudler and as I'm infront of it anyway, why not apply some perfume. Open the door, leave my room and close it again. Just a few steps forward and just as I walk around the corner the sporty flat-mate calls my name and introduces me to some slightly longer haired guy, some smaller one with a big tattoo on his left arm that's already been here last week. This time it seems that he brought his girl. And then these two young women who I oddly think to have met somwhere else before, but no clue where exactly.",
        "The door rings, it's my flat-mate's girl. The inked guy and his girl follow my flat-mate to the door. They talk and laugh a bit and are gone.",
        "The girl with a small nose ring starts talking with me, she has some accent which I can't really sort in. Somewhere from southern Europe maybe. All this basic stuff like where I'm from and what I do, it gets a bit more relaxing over time and after a little while my stomach goes off again. I stop leaning against the table and get my Gnocchi out of the fridge, prepare a pan and pour them in with a bit of sauce and let it heat up. We continue talking until the long haired looks up from his phone, wishes us a nice evening and leaves.",
        "The taste of Gnocchi spreads through the air. I take a plate and ask if the girls wanted something and they also seem to be quite hungry. Luckily for them I often cook too much when making pasta, so there's enough for the three of us. It actually tastes better than yesterday. Interesting.",
        "The one with a bright smile mentions that it tastes almost as good as it does at home, in bella Italia. I tell her I'm very honored about this rating, having made them from ground up myself. As the other one agrees, I feel pretty honored and tell them, that I got this recipe from an Italian friend. „Ja! Was auch sonst?\" shouts the smiling one and laughs.",
        "After a bit more of talking about life here in Germany vs. Italy, how the people are etc. it's now time for then to leave aswell. They also tell me that they're only here for a few days and want to experience my region, so we set an appointment for the next day where we want to visit some of the most levely places around here: Meersburg, Pfahlbauten Unteruhldingen and Insel Mainau.",
        "While cleaning the dishes it just comes to my mind why I knew them... Holy shit!",
        "Back to my room, just as a close the door and lay on my bed, the notification bell rings, which means that it can only be one very special person as nobody else has notifications enabled.",
        "If have something spectacular to tell her, or maybe not yet. I want to see where this goes first. She tells me that she's also been at the same dicounter, but in another city. Is there a connection between us other than an emotional one? A bit of chatting after which I have to wish her a good night and after a while I fall asleep with many thoughts in my head and a big smile on my face.",
        "On the next day I'm fully prepared for the trip.After a bit more of talking about life here in Germany vs. Italy, how the people are etc. it's now time for then to leave aswell. They also tell me that they're only here for a few days and want to experience my region, so we set an appointment for the next day where we want to visit two of the most levely places around here: Meersburg and Insel Mainau.",
        "While cleaning the dishes it just comes to my mind why I knew them. Holy shit.",
        "Back to my room, just as a close the door and lay on my bed, the notification bell rings, which means that it can only be one very special person as nobody else has notifications enabled.",
        "If have something spectacular to tell her, or maybe not yet. I want to see where this goes first. She tells me that she's also been at the same dicounter, but in another city. Is there a connection between us other than an emotional one? A bit of chatting after which I have to wish her a good night and after a while I fall asleep with many thoughts in my head and a big smile on my face.",
        "On the next day I'm fully prepared for the trip, some apples and fresh bread my phone is charged, my jacket dry. It‘s gonna be a sunny day today, so hopefully I don‘t need it. Going outside and waiting for the girls to show up. I‘m 3 minutes to early, they are 7 minutes too late. Classic. We almost missed the train, butl uckily Deutsche Bahn is as unpunctual as always.",
        "We explore Meersburg castle and take a little walk through the city until our ship to Mainau arrives. Trying out Icecream and getting it rated by the Italian experts. My green apple + Malaga icecream tastes quite weill, but of course: „Also mir schmeckt‘s daheim besser....“, so I promise them even better icecream in my hometown. Suddenly the horn, we almost missed the time. Hurry up to the jetty.",
        "The Katamaran sprints across Bodensee with some speed, the wind tousling through my longer hair, I check my phone in hope of a response by her. Last seen 03:27. She seems to have had a pretty fancy party night again.",
        "As we approach Konstanz the boat is slowing down slowly. A few minutes later we‘re at the pier on Flower Island. We spend the next few hours there just walking across the island, enjoying the time, taking some funny Selfies, visiting all the Butterflies, taking a small trip though the palace and then it‘s time to go again. Been a cool day proofing that Germany can sometimes be more beautiful than Italy.",
        "Back home I‘m quite exhausted and can‘t wait to go on Telegram again. Whoa.. she wrote a lot. And excusing herself for not being around. That‘s not a problem at all, but it‘s quite cute nevertheless. So I answer all of the stuff she sent me and as she comes online I ask myself whether I should tell her or not. I‘m writing a small text explaining how all of this went and then she just comes online...",
        "Not the End...",
        "I simply wanted to tell you how much you mean to me."
    )
}