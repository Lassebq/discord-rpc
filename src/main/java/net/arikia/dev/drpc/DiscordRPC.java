package net.arikia.dev.drpc;

import java.io.*;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Java Wrapper of the Discord-RPC Library for Discord Rich Presence.
 */

public final class DiscordRPC {

	//DLL-Version for Update Check (soon).
	private static final String DLL_VERSION = "3.4.0";
	private static final String LIB_VERSION = "1.6.2";

	static {
		loadDLL();
	}

	/**
	 * Method to initialize the Discord-RPC.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param handlers      EventHandlers
	 * @param autoRegister  AutoRegister
	 */
	public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister) {
		discordInitialize(applicationId, handlers, autoRegister, null);
	}

	/**
	 * Method to register the executable of the application/game.
	 * Only applicable when autoRegister in discordInitialize is false.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param command       Launch Command of the application/game.
	 */
	public static native void discordRegister(String applicationId, String command);

	/**
	 * Method to initialize the Discord-RPC within a Steam Application.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param handlers      EventHandlers
	 * @param autoRegister  AutoRegister
	 * @param steamId       SteamAppID
	 * @see DiscordEventHandlers
	 */
	public static native void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId);

	/**
	 * Method to register the Steam-Executable of the application/game.
	 * Only applicable when autoRegister in discordInitializeSteam is false.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param steamId       SteamID of the application/game.
	 */
	public static native void discordRegisterSteam(String applicationId, String steamId);

	/**
	 * Method to update the registered EventHandlers, after the initialization was
	 * already called.
	 *
	 * @param handlers DiscordEventHandler object with updated callbacks.
	 */
	public static native void discordUpdateEventHandlers(DiscordEventHandlers handlers);

	/**
	 * Method to shutdown the Discord-RPC from within the application.
	 */
	public static native void discordShutdown();

	/**
	 * Method to call Callbacks from within the library.
	 * Must be called periodically.
	 */
	public static native void discordRunCallbacks();

	/**
	 * Method to update the DiscordRichPresence of the client.
	 *
	 * @param presence Instance of DiscordRichPresence
	 * @see DiscordRichPresence
	 */
	public static void discordUpdatePresence(DiscordRichPresence presence) {
		discordUpdatePresence(presence.state, presence.details, presence.startTimestamp, presence.endTimestamp, presence.largeImageKey, presence.largeImageText, presence.smallImageKey, presence.smallImageText, presence.partyId, presence.partySize, presence.partyMax, presence.matchSecret, presence.spectateSecret, presence.joinSecret);
	}

	private static native void discordUpdatePresence(String state, String details, long startTimestamp, long endTimestamp, String largeImageKey, String largeImageText, String smallImageKey, String smallImageText, String partyId, int partySize, int partyMax, String matchSecret, String spectateSecret, String joinSecret);

	/**
	 * Method to clear(and therefor hide) the DiscordRichPresence until a new
	 * presence is applied.
	 */
	public static native void discordClearPresence();

	/**
	 * Method to respond to Join/Spectate Callback.
	 *
	 * @param userId UserID of the user to respond to.
	 * @param reply  DiscordReply to request.
	 * @see DiscordReply
	 */
	public static native void discordRespond(String userId, DiscordReply reply);

	//Load DLL depending on the user's architecture.
	private static void loadDLL() {
		String name = System.mapLibraryName("discord-rpc");
		OSUtil osUtil = new OSUtil();
		File homeDir;
		String finalPath;
		String tempPath;
		String dir;

		if (osUtil.isMac()) {
			homeDir = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
			dir = "darwin";
			tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
		} else if (osUtil.isWindows()) {
			homeDir = new File(System.getenv("TEMP"));
			boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
			dir = (is64bit ? "win-x64" : "win-x86");
			tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
		} else { //use createTempFile for Linux specifically, since it seems to break Windows support in *some* cases, noticed by me on my project. -Ceikry
			finalPath = "/linux/" + name;
			try {
				File f = File.createTempFile("drpc", name);
				InputStream in = DiscordRPC.class.getResourceAsStream(finalPath);
				if(in == null) throw new FileNotFoundException("Native Linux .so library missing. Please open an issue. https://github.com/Vatuu/discord-rpc");
				OutputStream out = openOutputStream(f);
				copyFile(in, out);
				in.close();
				out.close();
				f.deleteOnExit();
				System.load(f.getAbsolutePath());
				return;
			} catch (IOException e){
				e.printStackTrace();
				System.out.println("Fatal Discord RPC exception occurred. Discord RPC will be unavailable for this session.");
				return;
			}
		}

		finalPath = "/" + dir + "/" + name;

		try {
			File tempDir = new File(System.getProperty("java.io.tmpdir"), "drpc" + System.nanoTime());
			if(!tempDir.mkdir())
				throw new IOException("Cannot create temporary directory");
			tempDir.deleteOnExit();
			File f = new File(tempDir, name);
			f.deleteOnExit();
			
			InputStream in = DiscordRPC.class.getResourceAsStream(finalPath);
			OutputStream out = openOutputStream(f);
			try {
				copyFile(in, out);
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.load(f.getAbsolutePath());
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.loadLibrary("discord_rpc_jni");

	}

	private static void copyFile(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[1024 * 4];
		int n;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
	}

	private static FileOutputStream openOutputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (!file.canWrite()) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file);
	}

	//------------------------ Taken from apache commons ------------------------------//

	/**
	 * Enum containing reply codes for join request events.
	 *
	 * @see net.arikia.dev.drpc.callbacks.JoinRequestCallback
	 */
	public enum DiscordReply {
		/**
		 * Denies the join request immediately.
		 * Currently behaving the same way like DiscordReply.IGNORE.
		 */
		NO(0),
		/**
		 * Accepts the join request, requesting player received a JoinGameCallback.
		 *
		 * @see net.arikia.dev.drpc.callbacks.JoinGameCallback
		 */
		YES(1),
		/**
		 * Denies the join request by letting it time out(10s).
		 */
		IGNORE(2);

		/**
		 * Integer reply code send to Discord.
		 */
		public final int reply;

		DiscordReply(int reply) {
			this.reply = reply;
		}
	}
}