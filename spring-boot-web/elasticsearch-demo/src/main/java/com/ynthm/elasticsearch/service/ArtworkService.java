package com.ynthm.elasticsearch.service;

import com.ynthm.elasticsearch.entity.Artwork;
import com.ynthm.elasticsearch.repository.ArtworkRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ethan Wang
 * @version 1.0
 * @date 2020/11/2 3:31 下午
 */
@Service
public class ArtworkService {
  private final ArtworkRepository artworkRepository;

  private ElasticsearchTemplate template;

  @Autowired
  public void template(ElasticsearchTemplate template) {
    this.template = template;
  }

  public ArtworkService(ArtworkRepository artworkRepository) {
    this.artworkRepository = artworkRepository;
  }

  public SearchHits<Artwork> artworkList(String artworkTitle) {
    NativeSearchQuery searchQuery =
        new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.matchQuery("title", artworkTitle))
            .build();
    return template.search(searchQuery, Artwork.class);
  }

  public long count() {
    return artworkRepository.count();
  }

  public Artwork save(Artwork artwork) {
    return artworkRepository.save(artwork);
  }

  public void delete(Artwork artwork) {
    artworkRepository.delete(artwork);
    //   commodityRepository.deleteById(commodity.getSkuId());
  }

  public Iterable<Artwork> getAll() {
    return artworkRepository.findAll();
  }

  public List<Artwork> getByName(String name) {
    return artworkRepository.findAllByActorNamesContaining(name);
  }

  public Page<Artwork> pageQuery(Integer pageNo, Integer pageSize, String kw) {

    return artworkRepository.findAllByActorNamesEquals(PageRequest.of(pageNo, pageSize), kw);
  }
}
