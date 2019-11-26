package com.codegym.repository;

import com.codegym.model.Material;
import com.codegym.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MaterialRepository extends PagingAndSortingRepository<Material, Long> {

    Page<Material> findAllByCode(String code,Pageable pageable);

    Page<Material> findAllBySupplier(Supplier supplier, Pageable pageable);

    Page<Material> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Material> findAllByOrderByPriceDesc(Pageable pageable);
}
