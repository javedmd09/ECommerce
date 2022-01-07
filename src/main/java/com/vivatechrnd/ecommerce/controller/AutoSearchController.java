package com.vivatechrnd.ecommerce.controller;

import com.vivatechrnd.ecommerce.Dto.ProductDto;
import com.vivatechrnd.ecommerce.Repository.ProductSummaryRepository;
import com.vivatechrnd.ecommerce.Utility.Select2AjaxSuggestionResponse;
import com.vivatechrnd.ecommerce.Utility.Select2Response;
import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by Limon on 4/6/2018.
 */

@RestController
@RequestMapping("/admin/auto-search")
public class AutoSearchController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ProductSummaryRepository repository;

    private Select2Response getResponseToSelect2Response(Select2AjaxSuggestionResponse[] responses) {
        Select2Response select2Response = new Select2Response();
        select2Response.setResults(responses);
        return select2Response;
    }

    @RequestMapping(value = "/get-products-by-id", method = RequestMethod.POST)
    public Object getAllProductById(ProductDto productDto){
        Select2AjaxSuggestionResponse[] responses =
                restTemplate.postForObject("http://localhost:8087/get-products-by-ids",productDto,Select2AjaxSuggestionResponse[].class);
        return getResponseToSelect2Response(responses);
    }

    @RequestMapping(value = "/get-products-by-name", method = RequestMethod.POST)
    public Object getAllProductByName(ProductDto productDto){
        String url = "http://localhost:8087/get-products-by-names";

        Select2AjaxSuggestionResponse[] responses =
                restTemplate.postForObject(url,productDto,Select2AjaxSuggestionResponse[].class);
        return getResponseToSelect2Response(responses);
    }


}
