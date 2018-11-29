package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.model.service.GeoSearcher

class SearchLocation(private val searcher: GeoSearcher) {
    fun findAddresses(names: List<String>) = searcher.findAll(names)
}