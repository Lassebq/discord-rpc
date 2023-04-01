cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_SYSTEM_NAME Linux)
set(CMAKE_SYSTEM_PROCESSOR arm)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
include_directories(include)

add_library(discord_rpc_jni

			SHARED

			net_arikia_dev_drpc_DiscordRPC.c)

add_library(discord-rpc SHARED IMPORTED)
set_target_properties(discord-rpc
	PROPERTIES
	IMPORTED_IMPLIB
	${CMAKE_SOURCE_DIR}/lib/libdiscord-rpc.a)
target_link_libraries(discord_rpc_jni discord-rpc)
set_target_properties(discord_rpc_jni PROPERTIES PREFIX "")
