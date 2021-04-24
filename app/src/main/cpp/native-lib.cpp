//
// Created by Ferit Akcan on 18.04.2021.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ferit_kotlingithubrestapi_utils_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "d3a614eae9a9874a48d4f5e23d09907a";
    return env->NewStringUTF(api_key.c_str());
}
