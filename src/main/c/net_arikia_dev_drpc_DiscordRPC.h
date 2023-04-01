#include <jni.h>

#ifndef _Included_net_arikia_dev_drpc_DiscordRPC
#define _Included_net_arikia_dev_drpc_DiscordRPC
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordInitialize(JNIEnv *env, jclass clazz, jstring applicationId, jobject handlers, jboolean autoRegister, jstring steamId);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRegister(JNIEnv *env, jclass clazz, jstring applicationId, jstring command);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRegisterSteam(JNIEnv *env, jclass clazz, jstring applicationId, jstring steamId);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordUpdateEventHandlers(JNIEnv *env, jclass clazz, jobject handlers);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordShutdown(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRunCallbacks(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordUpdatePresence(JNIEnv *env, jclass clazz, 
jstring jstate, jstring jdetails, jlong startTimestamp, jlong endTimestamp, jstring jlargeImageKey, jstring jlargeImageText, jstring jsmallImageKey, jstring jsmallImageText,
jstring jpartyId, jint partySize, jint partyMax, jstring jmatchSecret, jstring jspectateSecret, jstring jjoinSecret);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordClearPresence(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL Java_net_arikia_dev_drpc_DiscordRPC_discordRespond(JNIEnv *env, jclass clazz, jstring userId, jobject jreply);

#ifdef __cplusplus
}
#endif
#endif