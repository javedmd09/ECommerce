package com.vivatechrnd.ecommerce.query

import com.vivatechrnd.ecommerce.read_modal.OrderSummary
import com.vivatechrnd.ecommerce.read_modal.ProductSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

class GetOrderQuery
class GetProductQuery

enum class SearchCriteriaFieldType {
    TEXT, DATE, DATE_RANGE, DATE_TIME, NUMERIC, ENUM, SELECT2_SEARCH, HIDDEN
}



