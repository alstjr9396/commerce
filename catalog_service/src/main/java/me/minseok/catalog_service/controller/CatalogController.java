package me.minseok.catalog_service.controller;

import java.util.List;
import me.minseok.catalog_service.cassandra.entity.Product;
import me.minseok.catalog_service.dto.DecreaseStockCountDto;
import me.minseok.catalog_service.dto.RegisterProductDto;
import me.minseok.catalog_service.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @PostMapping("/catalog/products")
    public Product registerProduct(@RequestBody RegisterProductDto registerProductDto) {
        return catalogService.registerProduct(registerProductDto.getSellerId(), registerProductDto.getName(), registerProductDto.getDescription(),
                registerProductDto.getPrice(), registerProductDto.getStockCount(), registerProductDto.getTags());
    }

    @DeleteMapping("/catalog/products/{productId}")
    public void registerProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/catalog/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return catalogService.getProductById(productId);
    }

    @GetMapping("/catalog/sellers/{sellerId}/products")
    public List<Product> getProductBySellerId(@PathVariable Long sellerId) {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @GetMapping("/catalog/products/{productId}/decreaseStockCount")
    public Product decreaseStockCount(@PathVariable Long productId, @RequestBody DecreaseStockCountDto decreaseStockCountDto) {
        return catalogService.decreaseStockCount(productId, decreaseStockCountDto.getDecreaseCount());
    }
}
