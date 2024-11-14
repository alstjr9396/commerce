package me.minseok.catalog_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.minseok.catalog_service.cassandra.entity.Product;
import me.minseok.catalog_service.cassandra.repository.ProductRepository;
import me.minseok.catalog_service.dto.ProductTagsDto;
import me.minseok.catalog_service.feign.SearchClient;
import me.minseok.catalog_service.mysql.entity.SellerProduct;
import me.minseok.catalog_service.mysql.repository.SellerProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    @Autowired
    SellerProductRepository sellerProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SearchClient searchClient;

    public Product registerProduct(Long sellerId, String name, String description, Long price, Long stockCount, List<String> tags) {
        SellerProduct sellerProduct = new SellerProduct(sellerId);
        sellerProductRepository.save(sellerProduct);

        Product product = new Product(sellerProduct.getId(), sellerProduct.getSellerId(), name, description, price, stockCount, tags);

        searchClient.addTagCache(new ProductTagsDto(product.getId(), product.getTags()));

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            searchClient.removeTagCache(new ProductTagsDto(product.get().getId(), product.get().getTags()));
        }

        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);
    }

    public List<Product> getProductsBySellerId(Long sellerId) {
        List<SellerProduct> sellerProducts = sellerProductRepository.findBySellerId(sellerId);
        List<Product> products = new ArrayList<>();
        for (SellerProduct product : sellerProducts) {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            optionalProduct.ifPresent(products::add);
        }

        return products;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Product decreaseStockCount(Long productId, Long decreaseCount) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStockCont(product.getStockCont() - decreaseCount);
        return productRepository.save(product);
    }
}
