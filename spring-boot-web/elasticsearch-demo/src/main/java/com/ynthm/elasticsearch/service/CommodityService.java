package com.ynthm.elasticsearch.service;

import com.ynthm.elasticsearch.entity.Commodity;
import com.ynthm.elasticsearch.repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {

  @Autowired private CommodityRepository commodityRepository;

  public long count() {
    return commodityRepository.count();
  }

  public Commodity save(Commodity commodity) {
    return commodityRepository.save(commodity);
  }

  public void delete(Commodity commodity) {
    commodityRepository.delete(commodity);
    //   commodityRepository.deleteById(commodity.getSkuId());
  }

  public List<Commodity> getAll() {
    return (List<Commodity>) commodityRepository.findAll();
  }

  public List<Commodity> getByName(String name) {
    return commodityRepository.findByNameContainsOrderByName(name);
  }

  public Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw) {
    return commodityRepository.findAll(PageRequest.of(pageNo, pageSize));
  }
}
