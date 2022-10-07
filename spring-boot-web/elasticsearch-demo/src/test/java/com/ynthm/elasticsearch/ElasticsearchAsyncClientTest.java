package com.ynthm.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.ynthm.elasticsearch.entity.Artwork;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
@DataElasticsearchTest
public class ElasticsearchAsyncClientTest {

  @Autowired private ElasticsearchAsyncClient esAsyncClient;

  @Test
  void testIndexRequest() {
    Artwork artwork = new Artwork();

    esAsyncClient
        .index(i -> i.index("products").id(artwork.getId()).document(artwork))
        .whenComplete(
            (response, exception) -> {
              if (exception != null) {
                log.error("Failed to index", exception);
              } else {
                log.info("Indexed with version " + response.version());
              }
            });
  }
}
