package net.arikia.dev.drpc;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Object containing information about a Discord user.
 */
public class DiscordUser {

	/**
	 * The userId of the player asking to join.
	 */
	public String userId;
	/**
	 * The username of the player asking to join.
	 */
	public String username;
	/**
	 * The discriminator of the player asking to join.
	 */
	public String discriminator;
	/**
	 * The avatar hash of the player asking to join.
	 *
	 * @see <a href="https://discordapp.com/developers/docs/reference#image-formatting">Image Formatting</a>
	 */
	public String avatar;

}