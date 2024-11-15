package me.minseok.search_service.service;

import com.google.protobuf.InvalidProtocolBufferException;
import kafka.protobuf.EdaMessage.ProductTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SearchService searchService;

    @KafkaListener(topics = "product_tags_added")
    public void cosumeTagAdded(byte[] message) throws InvalidProtocolBufferException {
        ProductTags productTags = ProductTags.parseFrom(message);

        logger.info("[product_tags_added] consumed: {}", productTags);

        searchService.addTagCache(productTags.getProductId(), productTags.getTagsList());
    }

    @KafkaListener(topics = "product_tags_removed")
    public void cosumeTagRemoved(byte[] message) throws InvalidProtocolBufferException {
        ProductTags productTags = ProductTags.parseFrom(message);

        logger.info("[product_tags_removed] consumed: {}", productTags);

        searchService.removeTagCache(productTags.getProductId(), productTags.getTagsList());
    }
}
