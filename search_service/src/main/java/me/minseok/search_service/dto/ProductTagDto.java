package me.minseok.search_service.dto;

import java.util.List;

public class ProductTagDto {

    private Long productId;

    private List<String> tags;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
