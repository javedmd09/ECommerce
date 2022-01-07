package com.vivatechrnd.ecommerce.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Select2AjaxSuggestionResponse {
    String id;
    String text;
}