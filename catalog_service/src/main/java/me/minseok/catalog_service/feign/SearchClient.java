package me.minseok.catalog_service.feign;

import me.minseok.catalog_service.dto.ProductTagsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "searchClient", url = "http://search-service:8080")
public interface SearchClient {

    @PostMapping(value = "/search/addTagCache")
    void addTagCache(@RequestBody ProductTagsDto productTagsDto);

    @PostMapping(value = "/search/removeTagCache")
    void removeTagCache(@RequestBody ProductTagsDto productTagsDto);
}
