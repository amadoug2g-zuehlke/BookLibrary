package com.example.booklibrary.ui.home.adapter

import com.example.core.domain.model.Volume
import com.example.core.domain.model.VolumeInfo

interface BookAction {
    fun onClick(volumeInfo: VolumeInfo)
}
