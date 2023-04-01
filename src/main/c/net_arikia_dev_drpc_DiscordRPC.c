#include <stdlib.h>
#include <discord_rpc.h>
#include <discord_register.h>
#include <jni.h>

#include "net_arikia_dev_drpc_DiscordRPC.h"

const char* get_chars(JNIEnv *env, jstring s) {
    if(s == NULL) {
        return NULL;
    }
    return (*env)->GetStringUTFChars(env, s, 0);
}

void release_chars(JNIEnv *env, jstring s, const char *chars) {
    if(s == NULL) {
        return;
    }
    (*env)->ReleaseStringUTFChars(env, s, chars);
}

//public static native void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId);
//fix DiscordEventHandlers
JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordInitialize(JNIEnv *env, jclass clazz, jstring applicationId, jobject handlers, jboolean autoRegister, jstring steamId)
{
    const char *appId = get_chars(env, applicationId);
    const char *steam = get_chars(env, steamId);
    Discord_Initialize(appId, NULL, autoRegister, steam);
    release_chars(env, applicationId, appId);
    release_chars(env, steamId, steam);
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRegister(JNIEnv *env, jclass clazz, jstring applicationId, jstring command)
{
    const char *appId = get_chars(env, applicationId);
    const char *cmd = get_chars(env, command);
    Discord_Register(appId, cmd);
    release_chars(env, applicationId, appId);
    release_chars(env, command, cmd);
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRegisterSteam(JNIEnv *env, jclass clazz, jstring applicationId, jstring steamId)
{
    const char *appId = get_chars(env, applicationId);
    const char *steam = get_chars(env, steamId);
    Discord_RegisterSteamGame(appId, steam);
    release_chars(env, applicationId, appId);
    release_chars(env, steamId, steam);
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordUpdateEventHandlers(JNIEnv *env, jclass clazz, jobject handlers)
{
    //TODO
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordShutdown(JNIEnv *env, jclass clazz)
{
    Discord_Shutdown();
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRunCallbacks(JNIEnv *env, jclass clazz)
{
    Discord_RunCallbacks();
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordUpdatePresence(JNIEnv *env, jclass clazz, 
jstring jstate, jstring jdetails, jlong startTimestamp, jlong endTimestamp, jstring jlargeImageKey, jstring jlargeImageText, jstring jsmallImageKey, jstring jsmallImageText,
jstring jpartyId, jint partySize, jint partyMax, jstring jmatchSecret, jstring jspectateSecret, jstring jjoinSecret)
{
    struct DiscordRichPresence *rp = malloc(sizeof(struct DiscordRichPresence));
    const char *state = get_chars(env, jstate);
    const char *details = get_chars(env, jdetails);
    const char *largeImageKey = get_chars(env, jlargeImageKey);
    const char *largeImageText = get_chars(env, jlargeImageText);
    const char *smallImageKey = get_chars(env, jsmallImageKey);
    const char *smallImageText = get_chars(env, jsmallImageText);
    const char *partyId = get_chars(env, jpartyId);
    const char *matchSecret = get_chars(env, jmatchSecret);
    const char *joinSecret = get_chars(env, jjoinSecret);
    const char *spectateSecret = get_chars(env, jspectateSecret);
    rp->state = state;
    rp->details = details;
    rp->startTimestamp = startTimestamp;
    rp->endTimestamp = endTimestamp;
    rp->largeImageKey = largeImageKey;
    rp->largeImageText = largeImageText;
    rp->smallImageKey = smallImageKey;
    rp->smallImageText = smallImageText;
    rp->partyId = partyId;
    rp->partySize = partySize;
    rp->partyMax = partyMax;
    rp->matchSecret = matchSecret;
    rp->joinSecret = joinSecret;
    rp->spectateSecret = spectateSecret;
    Discord_UpdatePresence(rp);
    release_chars(env, jstate, state);
    release_chars(env, jdetails, details);
    release_chars(env, jlargeImageKey, largeImageKey);
    release_chars(env, jlargeImageText, largeImageText);
    release_chars(env, jsmallImageKey, smallImageKey);
    release_chars(env, jsmallImageText, smallImageText);
    release_chars(env, jpartyId, partyId);
    release_chars(env, jmatchSecret, matchSecret);
    release_chars(env, jjoinSecret, joinSecret);
    release_chars(env, jspectateSecret, spectateSecret);
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordClearPresence(JNIEnv *env, jclass clazz)
{
    Discord_ClearPresence();
}

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRespond(JNIEnv *env, jclass clazz, jstring userId, jobject jreply)
{
	jclass reply_class = (*env)->FindClass(env, "net/arikia/dev/drpc/DiscordRPC$DiscordReply");
	jfieldID reply_field = (*env)->GetFieldID(env, reply_class, "reply", "I");
	int reply = (*env)->GetIntField(env, jreply, reply_field);
    const char *usrId = get_chars(env, userId);
    Discord_Respond(usrId, reply);
    release_chars(env, userId, usrId);
}