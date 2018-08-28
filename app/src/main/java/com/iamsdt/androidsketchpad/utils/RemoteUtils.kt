/*
 * Created by Shudipto Trafder
 * on 6/9/18 3:29 PM
 */

package com.iamsdt.androidsketchpad.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class RemoteUtils {

    companion object {
        fun getAuthorDes(): String {

            var title = "Programmer"

            val mFirebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            mFirebaseRemoteConfig.fetch().addOnCompleteListener {
                if (it.isSuccessful) {
                    title = mFirebaseRemoteConfig.getString("About_Programmer")
                }
            }

            return title
        }

        fun getAuthorLink(string: String = ""): String {
            var link = if (string.isNotEmpty()) string
            else "https://3.bp.blogspot.com/-1KNdWN2tyMA/WyD5toWHveI/AAAAAAAAC-4/aiRbNmrHiiQ9DIaRD0vO_0f1COBAZXGcwCLcBGAs/s1600/Developer.png"

            val mFirebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            mFirebaseRemoteConfig.fetch().addOnCompleteListener {
                if (it.isSuccessful) {
                    link = mFirebaseRemoteConfig.getString("imgLink")
                }
            }

            return link
        }
    }
}