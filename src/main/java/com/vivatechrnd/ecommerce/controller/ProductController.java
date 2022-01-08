package com.vivatechrnd.ecommerce.controller;

import com.vivatechrnd.ecommerce.Dto.ProductDto;
import com.vivatechrnd.ecommerce.Repository.ProductSummaryRepository;
import com.vivatechrnd.ecommerce.Utility.Select2AjaxSuggestionResponse;
import com.vivatechrnd.ecommerce.api.AddProductCommand;
import com.vivatechrnd.ecommerce.api.UpdateProductCommand;
import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.ObjectUtils;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class ProductController {

    @Autowired
    private ProductSummaryRepository repository;

    private CommandGateway commandGateway;
    private QueryGateway queryGateway;

    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/add-product")
    public void handle(@RequestBody ProductSummary summary){
        try {
            if (summary != null) {
                AddProductCommand command = new AddProductCommand(
                        summary.getProductId(),
                        summary.getPrice(),
                        summary.getStock(),
                        summary.getDescription()
                );
                commandGateway.send(command);
            }
        } finally {
            throw new RuntimeException("Product fields are empty");
        }

    }
    @PutMapping("/update-product/{productId}")
    public void handle(@RequestBody ProductSummary summary, @PathVariable ("productId") String productId){
        UpdateProductCommand command = new UpdateProductCommand(
                summary.getProductId(),
                summary.getStock(),
                summary.getPrice()
        );
        commandGateway.send(command);
    }
//    @GetMapping("/products")
//    public CompletableFuture<List<ProductSummary>> getAllProducts(){
//        return queryGateway.query(new GetProductQuery(),
//                ResponseTypes.multipleInstancesOf(ProductSummary.class));
//    }
    @PostMapping("/products")
    public List<ProductSummary> getProducts(@RequestBody ProductDto summary){

        return getFilteredData(summary);

    }

    public List<ProductSummary> getFilteredData(ProductDto dto) {
        List<ProductSummary> summaries = repository.findAll();
        List<ProductSummary> result = summaries;
        if (!StringUtils.isEmpty(dto.getProductId())) {
            result = summaries.stream().filter(data -> data.getProductId().equals(dto.getProductId()))
                    .collect(Collectors.toList());
        } else if (!StringUtils.isEmpty(dto.getDescription())) {
            result = summaries.stream().filter(data -> data.getDescription().equalsIgnoreCase(dto.getDescription()))
                    .collect(Collectors.toList());
        } else if (dto.getPriceMin() != null && dto.getPriceMax() != null) {
            result = summaries.stream().filter(x -> x.getPrice() >= dto.getPriceMin() && x.getPrice() <= dto.getPriceMax())
                    .collect(Collectors.toList());
        }
        return result;
    }

    @GetMapping("/product-id/{productId}")
    public Optional<ProductSummary> getProduct(@PathVariable(value = "productId") String productId){
        return repository.findById(productId);
    }

    @GetMapping("/product-name/{productName}")
    public List<ProductSummary> getProductByName(@PathVariable(value = "productName") String productName){
        return repository.findAllByDescriptionContaining(productName);
    }

    @RequestMapping(value = "/get-products-by-ids", method = RequestMethod.POST)
    public Object getAllProductById(@RequestBody ProductDto productDto){
        List<ProductSummary> summaries = new ArrayList<>();
        summaries = repository.findAll();

        List<Select2AjaxSuggestionResponse> responses = new ArrayList<>();

        for (int i = 0; i < summaries.size(); i++) {
            String val = "";
            val = summaries.get(i).getProductId();

            Select2AjaxSuggestionResponse dto = Select2AjaxSuggestionResponse.builder()
                    .id(val)
                    .text(val)
                    .build();
            responses.add(dto);

        }

        return responses;
    }

    @RequestMapping(value = "/get-products-by-names", method = RequestMethod.POST)
    public Object getAllProductByNames(@RequestBody ProductDto productDto){

        List<ProductSummary> summaries = new ArrayList<>();

        List<Select2AjaxSuggestionResponse> responses = new ArrayList<>();
        try {
            if (productDto != null){
                summaries = repository.findAllByDescriptionContaining(productDto.getDescription());
            }else {
                summaries = repository.findAll();
            }

            for (int i = 0; i < summaries.size(); i++) {
                String val = "";
                val = summaries.get(i).getDescription();

                Select2AjaxSuggestionResponse dto = Select2AjaxSuggestionResponse.builder()
                        .id(val)
                        .text(val)
                        .build();
                responses.add(dto);

            }
        } catch (Exception ex){
            return responses;
        }

        return responses;
    }




}
