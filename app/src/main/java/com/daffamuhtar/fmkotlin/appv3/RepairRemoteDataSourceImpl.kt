//package com.daffamuhtar.fmkotlin.appv3
//
//const val NETWORK_PAGE_SIZE = 25
//
//internal class RepairRemoteDataSourceImpl(
//    private val movieService: MovieService
//) : RepairRemoteDataSource {
//
//    override fun getMovies(): Flow<PagingData<MovieResponse>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                MoviesPagingSource(service = movieService)
//            }
//        ).flow
//    }
//}