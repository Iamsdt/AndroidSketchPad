/*
 * Created by Shudipto Trafder
 * on 6/13/18 9:38 AM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.content.SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
import android.content.SearchRecentSuggestionsProvider


class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {

        val AUTHORITY = "com.example.MySuggestionProvider"
        val MODE = DATABASE_MODE_QUERIES
    }
}