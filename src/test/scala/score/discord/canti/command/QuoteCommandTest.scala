package score.discord.canti.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import score.discord.canti.TestFixtures

class QuoteCommandTest extends AnyFlatSpec with should.Matchers:
  private val fixture = TestFixtures.default

  import fixture.{*, given}

  private val quoterChannel = botChannel
  private val quoteeChannel = exampleChannel

  commands.register(QuoteCommand(messageCache))

  def quoteCommandTest(invocation: String, expected: String): Unit =
    testCommand(invocation).getEmbeds.get(0).nn.getDescription.nn `should` include(expected)

  "The &quote command" `should` "understand id + channel mention" in {
    quoteCommandTest(
      s"&quote ${quoteeMessage.getIdLong} ${quoteeChannel.getAsMention}",
      quoteeMessageData
    )
  }

  it `should` "understand long-style message quotes" in {
    quoteCommandTest(
      s"&quote ${quoteeChannel.getIdLong}-${quoteeMessage.getIdLong}",
      quoteeMessageData
    )
  }

  it `should` "understand URL message quotes" in {
    quoteCommandTest(
      s"&quote https://canary.discord.com/channels/${guild.getId}/${quoteeChannel.getIdLong}/${quoteeMessage.getIdLong}",
      quoteeMessageData
    )
  }

  it `should` "find cached messages" in {
    // ensure message cache is populated with the message to find
    messageCache.onEvent(MessageReceivedEvent(jda, 0, quotee2Message))

    quoteCommandTest(s"&quote ${quotee2Message.getIdLong}", quotee2MessageData)
  }

  it `should` "find messages in the same channel" in {
    quoteCommandTest(s"&quote ${quotee3Message.getIdLong}", quotee3MessageData)
  }
