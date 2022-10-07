package com.ynthm.elasticsearch.utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.ynthm.elasticsearch.entity.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticSearchUtil {

  private ElasticsearchClient elasticsearchClient;

  @Autowired
  public void setElasticsearchClient(ElasticsearchClient elasticsearchClient) {
    this.elasticsearchClient = elasticsearchClient;
  }

  public void bulk() throws IOException {
    BulkResponse bulkResponse =
        elasticsearchClient.bulk(
            i ->
                i.index("artwork")
                    .operations(
                        operationBuilder ->
                            operationBuilder.create(
                                createBuilder ->
                                    createBuilder.index("artwork").document(new Artwork()))));

    if (!bulkResponse.errors()) {
      System.out.println(bulkResponse.items());
    }
  }
}
