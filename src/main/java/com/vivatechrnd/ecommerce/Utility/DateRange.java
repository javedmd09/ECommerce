package com.vivatechrnd.ecommerce.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DateRange {
    private Date startDate;
    private Date endDate;
}
