package me.minseok.catalog_service.dto;

import java.util.List;

public class ProductTagsDto {

    private Long productId;

    private List<String> tags;

    public ProductTagsDto() {
    }

    public ProductTagsDto(Long productId, List<String> tags) {
        this.productId = productId;
        this.tags = tags;
    }

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
