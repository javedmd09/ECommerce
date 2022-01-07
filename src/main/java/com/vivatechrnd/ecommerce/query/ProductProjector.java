package com.vivatechrnd.ecommerce.query;

import com.vivatechrnd.ecommerce.Repository.ProductSummaryRepository;
import com.vivatechrnd.ecommerce.api.AddProductEvent;
import com.vivatechrnd.ecommerce.api.UpdateProductEvent;
import com.vivatechrnd.ecommerce.api.UpdateProductStockEvent;
import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjector {

    private final ProductSummaryRepository repository;


    public ProductProjector(ProductSummaryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AddProductEvent event){
        ProductSummary summary = new ProductSummary(
                event.getProductId(),
                event.getPrice(),
                event.getStock(),
                event.getDescription()
        );
        repository.save(summary);
    }

    @EventHandler
    public void on(UpdateProductEvent event){
        ProductSummary summary = repository.findById(event.getProductId()).orElse(null);
        if(summary !=null){
            summary.setPrice(event.getPrice());
            summary.setStock(event.getStock());
        }else {
            throw new RuntimeException("Product not updated, unable to find product");
        }
    }

    @EventHandler
    public void on(UpdateProductStockEvent event){
        ProductSummary summary = repository.findById(event.getProductId()).orElse(null);
        if(summary!=null){
            summary.setStock(summary.getStock() - event.getStock());
            repository.save(summary);
        }
    }

    @QueryHandler
    public List<ProductSummary> handle(GetProductQuery query){
        return repository.findAll();
    }
}
