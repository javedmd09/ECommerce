package com.vivatechrnd.ecommerce.controller;

import com.vivatechrnd.ecommerce.Dto.OrderDto;
import com.vivatechrnd.ecommerce.Dto.ProductDto;
import com.vivatechrnd.ecommerce.Utility.SearchCriteria;
import com.vivatechrnd.ecommerce.query.SearchCriteriaFieldType;
import com.vivatechrnd.ecommerce.read_modal.OrderSummary;
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
                                 @RequestParam(name = "priceMin", required = false) Double priceMin,
                                 @RequestParam(name = "priceMax", required = false) Double priceMax,
                                 Model model, HttpServletRequest request){
        String productUrl = "http://localhost:8087/products";

        ProductDto productDto = ProductDto.builder()
                .productId(productId)
                .description(productName)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .build();

        String queryString = request.getQueryString();
        HashMap<String, String> parameterKeyValuePair = getUrlParameterToKeyValuePair(queryString);
        List<SearchCriteria> searchCriteriaList = getActiveProductSearchCriteria(parameterKeyValuePair);
        model.addAttribute("searchCriteriaList", searchCriteriaList);

        try {
            ProductSummary[] objects = restTemplate.postForObject(productUrl,productDto,ProductSummary[].class);

            List<Object> allProducts = Arrays.asList(objects);
            model.addAttribute("products", allProducts);
        } catch (Exception ex){
            model.addAttribute("message","Please enter Maximum value also");
        }
        return "index";
    }

    @RequestMapping(value = "/order-list", method = RequestMethod.GET)
    public String listAllOrders(@RequestParam(name = "orderId", required = false) String orderId,
                                 @RequestParam(name = "productId", required = false) String productId,
                                 @RequestParam(name = "fromDate", required = false) Date fromDate,
                                 @RequestParam(name = "toDate", required = false) Date toDate,
                                 Model model, HttpServletRequest request){
        String productUrl = "http://localhost:8087/all-orders";

        OrderDto orderDto = OrderDto.builder()
                .orderId(orderId)
                .productId(productId)
                .fromDate(fromDate)
                .toDate(toDate).build();


        String queryString = request.getQueryString();
        HashMap<String, String> parameterKeyValuePair = getUrlParameterToKeyValuePair(queryString);
        List<SearchCriteria> searchCriteriaList = getActiveOrderSearchCriteria(parameterKeyValuePair);
        model.addAttribute("searchCriteriaList", searchCriteriaList);

        try {
            OrderSummary[] objects = restTemplate.postForObject(productUrl,orderDto,OrderSummary[].class);

            List<Object> allOrders = Arrays.asList(objects);
            model.addAttribute("orders", allOrders);
        } catch (Exception ex){
            model.addAttribute("message","Error");
        }
        return "orderlist";
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

    private List<SearchCriteria> getActiveProductSearchCriteria(HashMap<String, String> prevSearchedValues) {

        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        SearchCriteria searchCriteria1 = new SearchCriteria(
                "productId", "Product ID", SearchCriteriaFieldType.SELECT2_SEARCH, ":", prevSearchedValues.get("productId")
        );

        searchCriteriaList.add(searchCriteria1);

        SearchCriteria searchCriteria2 = new SearchCriteria(
                "productName", "Product Name", SearchCriteriaFieldType.SELECT2_SEARCH, ":", prevSearchedValues.get("productName")
        );
        searchCriteriaList.add(searchCriteria2);

        SearchCriteria searchCriteria3 = new SearchCriteria("priceMin", "Minimum Price", SearchCriteriaFieldType.TEXT, ":", prevSearchedValues.get("priceMin"));
        searchCriteriaList.add(searchCriteria3);

        SearchCriteria searchCriteria4 = new SearchCriteria("priceMax", "Maximum Price", SearchCriteriaFieldType.TEXT, ":", prevSearchedValues.get("priceMax"));
        searchCriteriaList.add(searchCriteria4);

        return searchCriteriaList;
    }

    private List<SearchCriteria> getActiveOrderSearchCriteria(HashMap<String, String> prevSearchedValues) {

        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        SearchCriteria searchCriteria0 = new SearchCriteria(
                "orderId", "Order No", SearchCriteriaFieldType.TEXT, ":", prevSearchedValues.get("orderId")
        );
        searchCriteriaList.add(searchCriteria0);

//        SearchCriteria searchCriteriaX = new SearchCriteria(
//                "productId", "Product ID", SearchCriteriaFieldType.SELECT2_SEARCH, ":", prevSearchedValues.get("productId")
//        );

        SearchCriteria searchCriteria1 = SearchCriteria.builder()
                .key("productId")
                .viewKey("Product ID")
                .fieldType(SearchCriteriaFieldType.SELECT2_SEARCH)
                .value(prevSearchedValues.get("productId"))
                .build();

        searchCriteriaList.add(searchCriteria1);

        SearchCriteria searchCriteria2 = SearchCriteria.builder()
                .key("fromDate")
                .viewKey("From Date")
                .fieldType(SearchCriteriaFieldType.DATE)
                .value(prevSearchedValues.get("fromDate"))
                .build();
        searchCriteriaList.add(searchCriteria2);

        SearchCriteria searchCriteria3 = new SearchCriteria("toDate", "To Date", SearchCriteriaFieldType.DATE, ":", prevSearchedValues.get("toDate"));
        searchCriteriaList.add(searchCriteria3);


        return searchCriteriaList;
    }
}
