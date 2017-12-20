package tech.miliya.kotlin_get_number_of_wiki.api

/**
 * Created by t-ninomiya on 2017/12/19.
 */

object Model {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
}