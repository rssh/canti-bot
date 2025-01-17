package score.discord.canti.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.{Category, Guild, Role}
import score.discord.canti.wrappers.NullWrappers.*
import score.discord.canti.wrappers.jda.RichRole.mention
import score.discord.canti.wrappers.jda.RichSnowflake.rawId

import scala.jdk.CollectionConverters.*

object ParseUtils:
  private def searchGeneric[T](
    term: String,
    findOne: Long => T | Null,
    findMany: String => java.util.List[T]
  ): Seq[T] =
    if term.isEmpty then Nil
    else
      val fromId =
        for
          id <- term.toLongOption
          result <- findOne(id).?
        yield List(result)
      fromId.getOrElse(findMany(term).asScala.toSeq)

  /** Searches the given guild for a role by the given name/ID. If the provided string is a valid
    * Long, it will match by ID, otherwise it will match by name. Not case sensitive.
    *
    * @param guild
    *   guild to search
    * @param roleName
    *   exact name of role
    * @return
    *   all roles that match
    */
  def searchRoles(guild: Guild, roleName: String): Seq[Role] =
    searchGeneric(roleName, guild.getRoleById, guild.getRolesByName(_, true))

  /** Searches the given guild for a single role by the given name/ID. If there are multiple matches
    * or no matches, a human-readable error message will be returned in a Left. Not case sensitive.
    *
    * @param guild
    *   guild to search
    * @param roleName
    *   exact name of role
    * @return
    *   either a human readable error, or the desired role
    */
  def findRole(guild: Guild, roleName: String): Either[String, Role] =
    searchRoles(guild, roleName) match
      case Nil =>
        Left("Could not find a role by that name.")

      case Seq(role) =>
        Right(role)

      case Seq(matchingRoles*) =>
        val msg = "Too many roles by that name."
        val roles = for role <- matchingRoles yield s"`${role.rawId}`: ${role.mention}"

        Left((msg +: roles).mkString("\n"))

  /** Searches the given guild for a category by the given name/ID. If the provided string is a
    * valid Long, it will match by ID, otherwise it will match by name. Not case sensitive.
    *
    * @param guild
    *   guild to search
    * @param categoryName
    *   exact name of category
    * @return
    *   all categories that match
    */
  def searchCategories(guild: Guild, categoryName: String): Seq[Category] =
    searchGeneric(categoryName, guild.getCategoryById, guild.getCategoriesByName(_, true))

  /** Searches the given guild for a single category by the given name/ID. If there are multiple
    * matches or no matches, a human-readable error message will be returned in a Left. Not case
    * sensitive.
    *
    * @param guild
    *   guild to search
    * @param categoryName
    *   exact name of category
    * @return
    *   either a human readable error, or the desired category
    */
  def findCategory(guild: Guild, categoryName: String): Either[String, Category] =
    searchCategories(guild, categoryName) match
      case Nil =>
        Left("Could not find a category by that name.")

      case Seq(category) =>
        Right(category)

      case Seq(matchingCategories*) =>
        val msg = "Too many categories by that name."
        val roles = for category <- matchingCategories yield s"`${category.rawId}`: <#$category>"

        Left((msg +: roles).mkString("\n"))
end ParseUtils
