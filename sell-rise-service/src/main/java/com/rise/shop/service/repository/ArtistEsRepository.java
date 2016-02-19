package com.rise.shop.service.repository;

import com.rise.shop.domain.art.mongo.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by wangdi on 16-2-3.
 */
public interface ArtistEsRepository extends ElasticsearchRepository<Artist, Long> {

    Page<Artist> findByNameAndSex(String name, Integer sex, Pageable pageable);

    Page<Artist> findByNameOrSex(String name, Integer sex, Pageable pageable);

    Page<Artist> findByName(String name, Pageable pageable);

}
