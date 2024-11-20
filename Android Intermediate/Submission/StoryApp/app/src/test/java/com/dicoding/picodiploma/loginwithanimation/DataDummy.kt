package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val stories = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-$i-.png",
                "2024-05-08T06:$i:18.598Z",
                "Name $i",
                "Description $i",
                "-0.93$i".toDouble(),
                i.toString(),
                "100.35$i".toDouble()
            )
            items.add(stories)
        }
        return items
    }
}