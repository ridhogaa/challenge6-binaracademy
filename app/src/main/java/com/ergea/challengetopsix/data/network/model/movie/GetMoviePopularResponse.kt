package com.ergea.challengetopsix.data.network.model.movie

import com.google.gson.annotations.SerializedName

data class GetMoviePopularResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ResultMovies>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)