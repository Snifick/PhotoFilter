package com.example.photofilter.domain.repository

import com.example.photofilter.domain.models.ImageFilter
import jp.co.cyberagent.android.gpuimage.GPUImage

interface EditImageMapper {
    fun mapToImageFilters(gpuImage: GPUImage): List<ImageFilter>
}