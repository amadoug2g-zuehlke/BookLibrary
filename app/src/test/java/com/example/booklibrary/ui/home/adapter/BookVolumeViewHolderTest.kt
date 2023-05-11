package com.example.booklibrary.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.booklibrary.databinding.VolumeCardBinding
import com.example.core.domain.model.Volume
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//@RunWith(AndroidJUnit4::class)
//@RunWith(MockitoJUnitRunner::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "/Users/amci/AndroidStudioProjects/BookLibrary/app/src/main/AndroidManifest.xml")
class BookVolumeViewHolderTest {
    @Test
    fun testBind() {
        // Given
        val parent = Mockito.mock(ViewGroup::class.java)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val inflater = LayoutInflater.from(context)
        val binding = VolumeCardBinding.inflate(inflater, parent, false)

        val viewHolder: BookVolumeViewHolder = BookVolumeViewHolder(binding)

        val volume = Mockito.mock(Volume::class.java)
        val action = Mockito.mock(BookAction::class.java)

        // When
        viewHolder.bind(volume, action)

        // Then
        Mockito.verify(binding).volume = volume
    }
}