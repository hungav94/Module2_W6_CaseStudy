package com.codegym.service.impl;

import com.codegym.model.Material;
import com.codegym.model.Supplier;
import com.codegym.repository.SupplierRepository;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SupplierServiceImpl implements SupplierService {
    @Autowired
    public SupplierRepository supplierRepository;


    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findOne(id);
    }

    @Override
    public void save(Supplier model) {
        supplierRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        supplierRepository.delete(id);
    }

    @Override
    public Iterable<Supplier> findAll() {
        return supplierRepository.findAll();
    }
}
