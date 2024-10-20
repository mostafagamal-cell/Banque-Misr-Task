package banquemisr.challenge05.mostafa.dummydata

import banquemisr.challenge05.mostafa.pojos.MovieResponse
import banquemisr.challenge05.mostafa.pojos.Results

fun generateDummyResultsList(i: Int): List<Results> {
    return List(10) { index ->
        Results(
            adult = false,
            backdropPath = "/backdrop_path_$index.jpg",
            genreIds = arrayListOf(28L, 12L, 16L), // Action, Adventure, Animation genres
            id = index.toLong()+i,
            originalLanguage = "en",
            originalTitle = "Dummy Movie Title $index",
            overview = "This is the overview for Dummy Movie $index.",
            popularity = (100.0 + index),
            posterPath = "/poster_path_$index.jpg",
            releaseDate = "2024-10-${index + 1}",
            title = "Dummy Movie $index",
            video = false,
            voteAverage = (7.5 + index % 3),
            voteCount = (1000L + index * 100)
        )
    }
}
fun generateDummyResults(): List<MovieResponse>{
    return List(10) {
        MovieResponse(
            page = it.toLong(),
            results = generateDummyResultsList(it) as ArrayList<Results>,
            totalPages = 5,
            totalResults = 50
        )
    }
}