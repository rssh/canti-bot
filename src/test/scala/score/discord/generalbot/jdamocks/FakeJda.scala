package score.discord.generalbot.jdamocks

import java.util

import net.dv8tion.jda.bot.JDABot
import net.dv8tion.jda.client.JDAClient
import net.dv8tion.jda.core.entities._
import net.dv8tion.jda.core.hooks.IEventManager
import net.dv8tion.jda.core.managers.{AudioManager, Presence}
import net.dv8tion.jda.core.requests.RestAction
import net.dv8tion.jda.core.requests.restaction.GuildAction
import net.dv8tion.jda.core.utils.cache.{CacheView, SnowflakeCacheView}
import net.dv8tion.jda.core.{AccountType, JDA}

import scala.collection.JavaConverters._

class FakeJda extends JDA {
  private var guilds = Map.empty[Long, Guild]
  private var _nextId: Long = 123456789900L

  def nextId: Long = {
    _nextId += 1
    _nextId
  }

  def makeGuild(): FakeGuild = {
    val guild = new FakeGuild(this, nextId)
    guilds += guild.getIdLong -> guild
    guild
  }

  override def getStatus: JDA.Status = ???

  override def getPing: Long = ???

  override def getCloudflareRays: util.List[String] = ???

  override def getWebSocketTrace: util.List[String] = ???

  override def setEventManager(manager: IEventManager): Unit = ???

  override def addEventListener(listeners: Object*): Unit = ???

  override def removeEventListener(listeners: Object*): Unit = ???

  override def getRegisteredListeners: util.List[AnyRef] = ???

  override def createGuild(name: String): GuildAction = ???

  override def getAudioManagerCache: CacheView[AudioManager] = ???

  override def getUserCache: SnowflakeCacheView[User] = ???

  override def getMutualGuilds(users: User*): util.List[Guild] = ???

  override def getMutualGuilds(users: util.Collection[User]): util.List[Guild] = ???

  override def retrieveUserById(id: String): RestAction[User] = ???

  override def retrieveUserById(id: Long): RestAction[User] = ???

  override def getGuildCache: SnowflakeCacheView[Guild] = ???

  override def getRoleCache: SnowflakeCacheView[Role] = ???

  override def getCategoryCache: SnowflakeCacheView[Category] = ???

  override def getTextChannelCache: SnowflakeCacheView[TextChannel] =
    new ScalaSnowflakeCacheView(
      guilds.values.flatMap(_.getTextChannels.asScala).groupBy(_.getIdLong).mapValues(_.head),
      _.getName)

  override def getVoiceChannelCache: SnowflakeCacheView[VoiceChannel] = ???

  override def getPrivateChannelCache: SnowflakeCacheView[PrivateChannel] = ???

  override def getEmoteCache: SnowflakeCacheView[Emote] = ???

  override def getSelfUser: SelfUser = ???

  override def getPresence: Presence = ???

  override def getShardInfo: JDA.ShardInfo = ???

  override def getToken: String = ???

  override def getResponseTotal: Long = ???

  override def getMaxReconnectDelay: Int = ???

  override def setAutoReconnect(reconnect: Boolean): Unit = ???

  override def setRequestTimeoutRetry(retryOnTimeout: Boolean): Unit = ???

  override def isAutoReconnect: Boolean = ???

  override def isAudioEnabled: Boolean = ???

  override def isBulkDeleteSplittingEnabled: Boolean = ???

  override def shutdown(): Unit = ???

  override def shutdownNow(): Unit = ???

  override def getAccountType: AccountType = ???

  override def asClient(): JDAClient = ???

  override def asBot(): JDABot = ???
}
