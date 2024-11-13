package me.minseok.search_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void addTagCache(Long productId, List<String> tags) {
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        tags.forEach(tag -> {
            ops.add(tag, productId.toString());
        });
    }

    public void removeTagCache(Long productId, List<String> tags) {
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        tags.forEach(tag -> {
            ops.remove(tag, productId.toString());
        });
    }

    public List<Long> getProductIdsByTag(String tag) {
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        Set<String> members = ops.members(tag);
        if (members != null) {
            return members.stream().map(Long::parseLong).toList();
        }

        return Collections.emptyList();
    }
}
