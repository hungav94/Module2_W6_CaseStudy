package com.codegym.service;

import com.codegym.model.Material;
import com.codegym.model.MaterialForm;
import com.codegym.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialService extends Service<Material> {

    void edit(MaterialForm materialForm, String image);

    Page<Material> findAllByCode(String code,Pageable pageable);

    Page<Material> findAllBySupplier(Supplier supplier, Pageable pageable);

    Page<Material> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Material> findAllByOrderByPriceDesc(Pageable pageable);
}
