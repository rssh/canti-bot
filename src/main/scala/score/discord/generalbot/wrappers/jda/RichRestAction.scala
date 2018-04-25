package score.discord.generalbot.wrappers.jda

import net.dv8tion.jda.core.requests.RestAction

import scala.concurrent.{Future, Promise}

class RichRestAction[T](val orig: RestAction[T]) extends AnyVal {
  def queueFuture(): Future[T] = {
    val promise = Promise[T]()
    orig.queue({ result => promise success result }, { result => promise failure result })
    promise.future
  }
}
