package net.arikia.dev.drpc;

import net.arikia.dev.drpc.callbacks.*;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Object containing references to all event handlers registered. No callbacks are necessary,
 * every event handler is optional. Non-assigned handlers are being ignored.
 */
public class DiscordEventHandlers {

	/**
	 * Callback called when Discord-RPC was initialized successfully.
	 */
	public ReadyCallback ready;
	/**
	 * Callback called when the Discord connection was disconnected.
	 */
	public DisconnectedCallback disconnected;
	/**
	 * Callback called when a Discord error occurred.
	 */
	public ErroredCallback errored;
	/**
	 * Callback called when the player joins the game.
	 */
	public JoinGameCallback joinGame;
	/**
	 * Callback called when the player spectates a game.
	 */
	public SpectateGameCallback spectateGame;
	/**
	 * Callback called when a join request is received.
	 */
	public JoinRequestCallback joinRequest;
	
//    void (*ready)(const DiscordUser* request);
//    void (*disconnected)(int errorCode, const char* message);
//    void (*errored)(int errorCode, const char* message);
//    void (*joinGame)(const char* joinSecret);
//    void (*spectateGame)(const char* spectateSecret);
//    void (*joinRequest)(const DiscordUser* request);
	
	public void ready(DiscordUser request) {
		if(ready != null) {
			ready.apply(request);
		}
	}

	public void disconnected(int errorCode, String message) {
		if(disconnected != null) {
			disconnected.apply(errorCode, message);
		}
	}

	public void errored(int errorCode, String message) {
		if(errored != null) {
			errored.apply(errorCode, message);
		}
	}

	public void joinGame(String joinSecret) {
		if(joinGame != null) {
			joinGame.apply(joinSecret);
		}
	}

	public void spectateGame(String spectateSecret) {
		if(spectateGame != null) {
			spectateGame.apply(spectateSecret);
		}
	}

	public void joinRequest(DiscordUser request) {
		if(joinRequest != null) {
			joinRequest.apply(request);
		}
	}

	public static class Builder {

		DiscordEventHandlers h;

		public Builder() {
			h = new DiscordEventHandlers();
		}

		public Builder setReadyEventHandler(ReadyCallback r) {
			h.ready = r;
			return this;
		}

		public Builder setDisconnectedEventHandler(DisconnectedCallback d) {
			h.disconnected = d;
			return this;
		}

		public Builder setErroredEventHandler(ErroredCallback e) {
			h.errored = e;
			return this;
		}

		public Builder setJoinGameEventHandler(JoinGameCallback j) {
			h.joinGame = j;
			return this;
		}

		public Builder setSpectateGameEventHandler(SpectateGameCallback s) {
			h.spectateGame = s;
			return this;
		}

		public Builder setJoinRequestEventHandler(JoinRequestCallback j) {
			h.joinRequest = j;
			return this;
		}

		public DiscordEventHandlers build() {
			return h;
		}
	}
}
