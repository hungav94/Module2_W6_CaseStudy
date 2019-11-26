package com.codegym.service;

import com.codegym.model.Supplier;

public interface SupplierService extends Service<Supplier> {
    Iterable<Supplier> findAll();
}
