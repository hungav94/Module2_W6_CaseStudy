package com.codegym.controller;

import com.codegym.model.Material;
import com.codegym.model.MaterialForm;
import com.codegym.model.Supplier;
import com.codegym.service.MaterialService;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@PropertySource("classpath:upload_file.properties")
public class MaterialController {
    @Autowired
    Environment environment;

    @Autowired
    public MaterialService materialService;

    @Autowired
    public SupplierService supplierService;

    @ModelAttribute("suppliers")
    public Iterable<Supplier> suppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/material")
    public ModelAndView showListMaterial(@RequestParam("s") Optional<String> s,@RequestParam("supplier") Optional<Long> supplier,@PageableDefault(size = 10) Pageable pageable) {
        Page<Material> materials;
        if (s.isPresent()){
            materials = materialService.findAllByCode(s.get(), pageable);
        } else if (supplier.isPresent()){
            Supplier supplier1 = supplierService.findById(supplier.get());
            materials = materialService.findAllBySupplier(supplier1, pageable);
        } else {
            materials = materialService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }

//    @GetMapping("/search")

    @GetMapping("/create-material")
    public ModelAndView showCreateMaterial() {
        ModelAndView modelAndView = new ModelAndView("material/create");
        modelAndView.addObject("material", new Material());
        return modelAndView;
    }

    @PostMapping("/create-material")
    public ModelAndView saveMaterial(@ModelAttribute MaterialForm materialForm) throws IOException {
        MultipartFile multipartFile = materialForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();

        FileCopyUtils.copy(materialForm.getImage().getBytes(), new File(fileUpload + fileName));

        Material material = new Material();
        material.setCode(materialForm.getCode());
        material.setName(materialForm.getName());
        material.setImportDate(materialForm.getImportDate());
        material.setPrice(materialForm.getPrice());
        material.setQuantity(materialForm.getQuantity());
        material.setDescription(materialForm.getDescription());
        material.setImage(fileName);
        material.setSupplier(materialForm.getSupplier());

        materialService.save(material);
        ModelAndView modelAndView = new ModelAndView("material/create");
        modelAndView.addObject("material", new Material());
        modelAndView.addObject("message", "Create new material successfully");
        return modelAndView;
    }

    @GetMapping("/edit-material/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Material material = materialService.findById(id);
        ModelAndView modelAndView = null;
        if (material != null) {
            MaterialForm materialForm = new MaterialForm();
            modelAndView = new ModelAndView("material/edit");
            modelAndView.addObject("material", material);
        } else {
            modelAndView = new ModelAndView("error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit-material")
    public ModelAndView updateMaterial(@ModelAttribute("material") MaterialForm materialForm) throws IOException {
        MultipartFile multipartFile = materialForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();

        FileCopyUtils.copy(materialForm.getImage().getBytes(), new File(fileUpload + fileName));

        materialService.edit(materialForm, fileName);

        ModelAndView modelAndView = new ModelAndView("material/edit");
        modelAndView.addObject("material", materialForm);
        modelAndView.addObject("message", "Edit material successfully");
        return modelAndView;
    }

    @GetMapping("/delete-material/{id}")
    public ModelAndView deleteMaterial(@PathVariable Long id) {
        materialService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/material");
        return modelAndView;
    }

    @GetMapping("/sort-price-asc")
    public ModelAndView showSortPriceAsc(@PageableDefault(size = 10) Pageable pageable){
        Page<Material> materials = materialService.findAllByOrderByPriceAsc(pageable);
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }

    @GetMapping("/sort-price-desc")
    public ModelAndView showSortPriceDesc(@PageableDefault(size = 10) Pageable pageable){
        Page<Material> materials = materialService.findAllByOrderByPriceDesc(pageable);
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }
}
