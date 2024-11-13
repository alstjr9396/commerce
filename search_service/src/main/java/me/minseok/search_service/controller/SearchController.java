package me.minseok.search_service.controller;

import java.util.List;
import me.minseok.search_service.dto.ProductTagDto;
import me.minseok.search_service.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping("/search/addTagCache")
    public void addTagCache(@RequestBody ProductTagDto productTagDto) {
        searchService.addTagCache(productTagDto.getProductId(), productTagDto.getTags());
    }

    @PostMapping("/search/removeTagCache")
    public void removeTagCache(@RequestBody ProductTagDto productTagDto) {
        searchService.removeTagCache(productTagDto.getProductId(), productTagDto.getTags());
    }

    @GetMapping("/search/tags/{tag}/productIds")
    public List<Long> getTagProductIds(@PathVariable String tag) {
        return searchService.getProductIdsByTag(tag);
    }
}
