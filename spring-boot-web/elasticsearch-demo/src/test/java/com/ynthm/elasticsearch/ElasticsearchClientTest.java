package com.ynthm.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.ShardFailure;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch.cluster.GetClusterSettingsResponse;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndicesSettingsRequest;
import co.elastic.clients.elasticsearch.indices.GetIndicesSettingsResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ynthm.elasticsearch.entity.Artwork;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@DataElasticsearchTest
class ElasticsearchClientTest {

  @Autowired private ElasticsearchClient elasticsearchClient;

  @Test
  void bulkIndex() throws IOException {
    List<Artwork> list = new ArrayList();

    BulkRequest.Builder bk = new BulkRequest.Builder();
    int indexId = 4;
    for (Artwork artwork : list) {
      bk.operations(
          op ->
              op.index(
                  i -> i.index("newindex").id(UUID.randomUUID().toString()).document(artwork)));
    }
    BulkResponse response = elasticsearchClient.bulk(bk.build());
    if (response.errors()) {
      System.out.println("Bulk had errors");
      for (BulkResponseItem item : response.items()) {
        if (item.error() != null) {
          System.out.println(item.error().reason());
        }
      }
    }
  }

  /** Cluster Get Settings API */
  @Test
  void testCluster() throws IOException {
    GetClusterSettingsResponse response =
        elasticsearchClient
            .cluster()
            .getSettings(builder -> builder.flatSettings(false).includeDefaults(true));
    Map<String, JsonData> defaultSettings = response.defaults();
    Map<String, JsonData> persistentSettings = response.persistent();
    Map<String, JsonData> transientSettings = response.transient_();

    GetIndicesSettingsResponse settings =
        elasticsearchClient
            .indices()
            .getSettings(GetIndicesSettingsRequest.of(builder -> builder.includeDefaults(true)));

    for (Map.Entry<String, IndexState> indexToSetting : settings.result().entrySet()) {
      System.out.println(indexToSetting.getKey());
    }
  }

  /**
   * Document APIs
   *
   * @throws IOException
   */
  @Test
  void testDocument() throws IOException {
    Artwork artwork = new Artwork();

    IndexResponse indexResponse =
        elasticsearchClient.index(i -> i.index("artwork").id(artwork.getId()).document(artwork));

    String index = indexResponse.index();
    String id = indexResponse.id();
    if (indexResponse.result() == Result.Deleted) {
      System.out.println("created");

    } else if (indexResponse.result() == Result.Updated) {
      System.out.println("updated");
    }

    ShardStatistics shards = indexResponse.shards();

    if (shards.total() != shards.successful()) {
      System.out.println("ShardStatistics failed");
    }

    if (shards.failed().intValue() > 0) {
      for (ShardFailure failure : shards.failures()) {
        System.out.println(failure);
      }
    }
  }

  @Test
  void readById() throws IOException {
    GetResponse<Artwork> response =
        elasticsearchClient.get(g -> g.index("artwork").id("bk-1"), Artwork.class);

    if (response.found()) {
      Artwork artwork = response.source();
      log.info(" name " + artwork);
    } else {
      log.info(" not found");
    }
  }

  @Test
  void testCreateIndex() throws IOException {
    CreateIndexResponse createIndexResponse =
        elasticsearchClient
            .indices()
            .create(
                createIndexBuilder ->
                    createIndexBuilder
                        .index("twitter_two")
                        // 创建的每个索引都可以有与之关联的特定设置。
                        .settings(
                            settingsBuilder ->
                                settingsBuilder.numberOfShards("3").numberOfReplicas("2"))
                        // 在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示。
                        .waitForActiveShards(activeShardsBuilder -> activeShardsBuilder.count(2))
                        .aliases("twitter_alias", aliasBuilder -> aliasBuilder.isWriteIndex(true))
                        .timeout(timeoutBuilder -> timeoutBuilder.time("2m")));

    System.out.println(createIndexResponse);
  }

  @Test
  void readJson() throws IOException {
    GetResponse<ObjectNode> response =
        elasticsearchClient.get(g -> g.index("artwork").id("bk-1"), ObjectNode.class);

    if (response.found()) {
      ObjectNode json = response.source();
      String name = json.get("title").asText();
      log.info("title " + name);
    } else {
      log.info("not found");
    }
  }

  @Test
  public void testQuery1() throws IOException {

    String searchText = "bike";

    SearchResponse<Artwork> response =
        elasticsearchClient.search(
            s -> s.index("artwork").query(q -> q.match(t -> t.field("title").query(searchText))),
            Artwork.class);

    TotalHits total = response.hits().total();
    boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

    if (isExactResult) {
      log.info("There are " + total.value() + " results");
    } else {
      log.info("There are more than " + total.value() + " results");
    }

    List<Hit<Artwork>> hits = response.hits().hits();
    for (Hit<Artwork> hit : hits) {
      Artwork product = hit.source();
      String id = hit.id();
      String type = hit.type();
      String index = hit.index();
      log.info("Found product " + product.getTitle() + ", score " + hit.score());
    }
  }
}
