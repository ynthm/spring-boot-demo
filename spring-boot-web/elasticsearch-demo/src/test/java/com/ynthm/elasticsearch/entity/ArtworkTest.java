package com.ynthm.elasticsearch.entity;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@DataElasticsearchTest
class ArtworkTest {

  @Resource ElasticsearchTemplate elasticsearchTemplate;

  @Test
  void saveOrUpdateTest() {
    Artwork artwork = new Artwork().setCode("abc");

    IndexQuery indexQuery = new IndexQueryBuilder().withObject(artwork).build();
    String index = elasticsearchTemplate.index(indexQuery, IndexCoordinates.of(""));
    // 返回的 index 是数据 id,如果指定了,返回指定的 id 值,未指定,返回一个 es 自动生成的
    System.out.println(index);
  }

  /**
   * 批量保存
   *
   * @author liuqiuyi
   * @date 2021/4/29 19:53
   */
  @Test
  void batchSaveTest() {
    Artwork artwork1 = new Artwork().setCode("abc");
    Artwork artwork2 = new Artwork().setCode("abc");
    ArrayList<Artwork> employeeArrayList = Lists.newArrayList(artwork1, artwork2);

    List<IndexQuery> indexQueryList = Lists.newArrayList();
    for (Artwork artwork : employeeArrayList) {
      IndexQuery indexQuery = new IndexQueryBuilder().withObject(artwork).build();

      indexQueryList.add(indexQuery);
    }

    elasticsearchTemplate.bulkIndex(indexQueryList, Artwork.class);
  }

  /**
   * 根据 id 单个删除
   *
   * @author liuqiuyi
   * @date 2021/4/29 20:30
   */
  @Test
  void deleteByIdTest() {
    String delete = elasticsearchTemplate.delete("79R2HXkBm5pjjA5okt3o", Artwork.class);
    System.out.println(delete);
  }

  /**
   * 批量删除
   *
   * @author liuqiuyi
   * @date 2021/5/6 15:37
   */
  @Test
  void batchDeleteByIdsTest() {
    CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());
    criteriaQuery.setIds(Lists.newArrayList("18", "19"));

    elasticsearchTemplate.delete(criteriaQuery, Artwork.class);
  }
}
