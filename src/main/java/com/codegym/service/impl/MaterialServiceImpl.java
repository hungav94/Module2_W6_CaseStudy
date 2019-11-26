package com.codegym.service.impl;

import com.codegym.model.Material;
import com.codegym.model.MaterialForm;
import com.codegym.model.Supplier;
import com.codegym.repository.MaterialRepository;
import com.codegym.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class MaterialServiceImpl implements MaterialService {
    @Autowired
    public MaterialRepository materialRepository;

    @Override
    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @Override
    public Material findById(Long id) {
        return materialRepository.findOne(id);
    }

    @Override
    public void save(Material model) {
        materialRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        materialRepository.delete(id);
    }

    @Override
    public void edit(MaterialForm materialForm, String image) {
        Material material = materialRepository.findOne(materialForm.getId());
        material.setName(materialForm.getName());
        material.setCode(materialForm.getCode());
        material.setDescription(materialForm.getDescription());
        material.setImportDate(materialForm.getImportDate());
        material.setPrice(materialForm.getPrice());
        material.setQuantity(materialForm.getQuantity());
        material.setImage(image);
        material.setSupplier(materialForm.getSupplier());
        materialRepository.save(material);
    }

    @Override
    public Page<Material> findAllByCode(String code, Pageable pageable) {
        return materialRepository.findAllByCode(code, pageable);
    }

    @Override
    public Page<Material> findAllBySupplier(Supplier supplier, Pageable pageable) {
        return materialRepository.findAllBySupplier(supplier, pageable);
    }

    @Override
    public Page<Material> findAllByOrderByPriceAsc(Pageable pageable) {
        return materialRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Material> findAllByOrderByPriceDesc(Pageable pageable) {
        return materialRepository.findAllByOrderByPriceDesc(pageable);
    }
}
