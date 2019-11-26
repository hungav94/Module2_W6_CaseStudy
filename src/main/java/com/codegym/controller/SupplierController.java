package com.codegym.controller;

import com.codegym.model.Supplier;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {
    @Autowired
    public SupplierService supplierService;

    @GetMapping("/supplier")
    public ModelAndView showListSupplier(@PageableDefault(size = 10) Pageable pageable){
        Page<Supplier> suppliers = supplierService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("supplier/list");
        modelAndView.addObject("suppliers", suppliers);
        return modelAndView;
    }

    @GetMapping("/create-supplier")
    public ModelAndView showCreateSupplier(){
        ModelAndView modelAndView = new ModelAndView("supplier/create");
        modelAndView.addObject("supplier", new Supplier());
        return modelAndView;
    }

    @PostMapping("/create-supplier")
    public ModelAndView saveSupplier(@ModelAttribute Supplier supplier){
        supplierService.save(supplier);
        ModelAndView modelAndView = new ModelAndView("supplier/create");
        modelAndView.addObject("supplier", new Supplier());
        modelAndView.addObject("message","Create supplier successfully");
        return modelAndView;
    }

    @GetMapping("/edit-supplier/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id){
        Supplier supplier = supplierService.findById(id);
        ModelAndView modelAndView = null;
        if (supplier != null){
            modelAndView = new ModelAndView("supplier/edit");
            modelAndView.addObject("supplier", supplier);
        } else {
            modelAndView = new ModelAndView("error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit-supplier")
    public ModelAndView updateSupplier(@ModelAttribute Supplier supplier){
        supplierService.save(supplier);
        ModelAndView modelAndView = new ModelAndView("supplier/edit");
        modelAndView.addObject("supplier", supplier);
        modelAndView.addObject("message", "Edit supplier successfully");
        return modelAndView;
    }

    @GetMapping("/delete-supplier/{id}")
    public ModelAndView deleteSupplier(@PathVariable Long id){
        supplierService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/supplier");
        return modelAndView;
    }
}
