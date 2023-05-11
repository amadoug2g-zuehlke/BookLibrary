package com.example.booklibrary.ui.home.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.domain.model.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock

/*
@RunWith(AndroidJUnit4::class)
class BookVolumeAdapterTest {

    @Test
    fun testEmptyList() {
        val mockAction = mock(BookAction::class.java)

        val adapter = BookVolumeAdapter(mockAction)
        adapter.setData(emptyList()) // Initialize the adapter with an empty list
        // Then the adapter should have no items
        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun testNonEmptyList() {
        val mockAction = mock(BookAction::class.java)
        val mockContext = mock(Context::class.java)

        val adapter = BookVolumeAdapter(mockAction)

        val authors = mutableListOf("author 1", "author 2")
        val industryId = IndustryIdentifier("type", "identifier")
        val listIndustryId = mutableListOf(industryId, industryId)
        val modes = ReadingModes(text = false, image = false)
        val summary = PanelizationSummary(false, containsImageBubbles = false)

        // Given a non-empty list of volumes
        val info = VolumeInfo(
            "title",
            authors,
            "2023",
            "Yes",
            listIndustryId,
            modes,
            100,
            "Yes",
            authors,
            5.0,
            1,
            "",
            false,
            "Yes", summary,
        )

        val volumes = listOf(
            Volume("Kind 1", "Id 1", "Etag 1", "1", info),
            Volume("Kind 2", "Id 2", "Etag 2", "2", info),
            Volume("Kind 3", "Id 3", "Etag 3", "3", info),
        )

        // When the adapter sets the new list
        adapter.setData(volumes)

        // Then the adapter should have the same number of items as the list
        assertEquals(volumes.size, adapter.itemCount)

        val parent = Mockito.mock(ViewGroup::class.java)

        val context = ApplicationProvider.getApplicationContext<Context>()

        // And each item in the adapter should have the correct data
        for (i in volumes.indices) {
            val volume = volumes[i]
            val viewHolder = adapter.createViewHolder(
                parent,
                100
            ) as BookVolumeViewHolder
            adapter.bindViewHolder(viewHolder, i)

            assertEquals(volume.id, "Id 1")
            //assertEquals(volume.title, viewHolder.title.text.toString())
            //assertEquals(volume.author, viewHolder.author.text.toString())
        }
    }
}
*/
