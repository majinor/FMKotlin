import androidx.paging.PagingSource
import com.daffamuhtar.fmkotlin.services.RepairServices

//class RepairItemDataSource(private val service: RepairServices) :
//    PagingSource<Int, Character>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
//        val pageNumber = params.key ?: 1
//        return try {
//            val response = service.getRepairOngoingNew(pageNumber)
//            val pagedResponse = response.body()
//            val data = pagedResponse?.results
//
//            var nextPageNumber: Int? = null
//            if (pagedResponse?.pageInfo?.next != null) {
//                val uri = Uri.parse(pagedResponse.pageInfo.next)
//                val nextPageQuery = uri.getQueryParameter("page")
//                nextPageNumber = nextPageQuery?.toInt()
//            }
//
//            LoadResult.Page(
//                data = data.orEmpty(),
//                prevKey = null,
//                nextKey = nextPageNumber
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}