package com.vivatechrnd.ecommerce.Utility;

import com.vivatechrnd.ecommerce.query.SearchCriteriaFieldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Limon on 10/20/2017.
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String viewKey;
    private SearchCriteriaFieldType fieldType;
    private String operation;
    private Object value;
    private String viewValue;
    private DateRange dateRange;
    private String valueDateRangeStartDate;
    private String valueDateRangeEndDate;
    private Map<String, String> options;

    public SearchCriteria(String key, String viewKey, SearchCriteriaFieldType fieldType, String operation, Object value, String viewValue) {
        this.key = key;
        this.viewKey = viewKey;
        this.fieldType = fieldType;
        this.operation = operation;
        this.value = value;
        this.viewValue = viewValue;
    }

    public SearchCriteria(String key, String viewKey, SearchCriteriaFieldType fieldType, String operation, Object value) {
        this.key = key;
        this.viewKey = viewKey;
        this.fieldType = fieldType;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, String viewKey, SearchCriteriaFieldType fieldType, String operation, Object value, Map<String, String> options) {
        this(key, viewKey, fieldType, operation, value);
        this.options = options;
    }

    public SearchCriteria(String key, String viewKey, SearchCriteriaFieldType fieldType, String operation, Object value, String viewValue, Map<String, String> options) {
        this(key, viewKey, fieldType, operation, value, viewValue);
        this.options = options;
    }

}
