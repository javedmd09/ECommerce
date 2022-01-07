package com.vivatechrnd.ecommerce.controller;

import com.vivatechrnd.ecommerce.Dto.ProductDto;
import com.vivatechrnd.ecommerce.Utility.SearchCriteria;
import com.vivatechrnd.ecommerce.query.SearchCriteriaFieldType;
import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private static Map<String, Object> products;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String home(Model model){
        String productUrl = "http://localhost:8087/products";

        ProductSummary[] objects = restTemplate.getForObject(productUrl, ProductSummary[].class);

        List<Object> allProducts = Arrays.asList(objects);
        model.addAttribute("products", allProducts);
        return "index";
    }

    @RequestMapping(value = "/all-products", method = RequestMethod.GET)
    public String listAllProduct(@RequestParam(name = "productId", required = false) String productId,
                                 @RequestParam(name = "productName", required = false) String productName,
                                 Model model, HttpServletRequest request){
        String productUrl = "http://localhost:8087/products";

        ProductDto productDto = new ProductDto();
        productDto.setProductId(productId);
        productDto.setDescription(productName);

        String queryString = request.getQueryString();
        HashMap<String, String> parameterKeyValuePair = getUrlParameterToKeyValuePair(queryString);
        List<SearchCriteria> searchCriteriaList = getActiveOutletSearchCriteria(parameterKeyValuePair);
        model.addAttribute("searchCriteriaList", searchCriteriaList);

        ProductSummary[] objects = restTemplate.postForObject(productUrl,productDto,ProductSummary[].class);

        List<Object> allProducts = Arrays.asList(objects);
        model.addAttribute("products", allProducts);
        return "index";
    }


    public HashMap<String, String> getUrlParameterToKeyValuePair(String urlParameter) {

        List<String> withoutParameters = new ArrayList<>();
        withoutParameters.add("page");
        withoutParameters.add("size");
        HashMap<String, String> hashMap = new HashMap<>();

        if (urlParameter != null) {
            try {
                urlParameter = URLDecoder.decode(urlParameter, java.nio.charset.StandardCharsets.UTF_8.toString());
                String[] parameters = urlParameter.split("&");

                for (String parameter : parameters) {
                    String[] keyValue = parameter.split("=");
                    String key = keyValue[0];
                    String value = null;

                    if (keyValue.length > 1) {
                        value = keyValue[1];
                    }
                    if(withoutParameters != null && withoutParameters.isEmpty() == false && !withoutParameters.contains(key)) {
                        hashMap.put(key, value);
                    }
                }
            } catch (Exception ex) {

            }
        }
        return hashMap;
    }

    private List<SearchCriteria> getActiveOutletSearchCriteria(HashMap<String, String> prevSearchedValues) {

        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        SearchCriteria searchCriteria1 = new SearchCriteria(
                "productId", "Product ID", SearchCriteriaFieldType.SELECT2_SEARCH, ":", prevSearchedValues.get("productId")
        );

        searchCriteriaList.add(searchCriteria1);

        SearchCriteria searchCriteria2 = new SearchCriteria(
                "productName", "Product Name", SearchCriteriaFieldType.SELECT2_SEARCH, ":", prevSearchedValues.get("productName")
        );
        searchCriteriaList.add(searchCriteria2);

        return searchCriteriaList;
    }
}