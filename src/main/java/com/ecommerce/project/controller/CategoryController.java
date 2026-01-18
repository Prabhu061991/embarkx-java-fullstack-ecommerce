package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

  private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message){
        //public ResponseEntity<String> echoMessage(@RequestParam(name = "message", defaultValue = "Hello World!") String message){
        return new ResponseEntity<>("Echoed message: " + message, HttpStatus.OK);
    }
    //@GetMapping("api/public/categories")
    @RequestMapping(value="/public/categories",method = RequestMethod.GET)
    //public ResponseEntity<List<Category>> getCategories() {
        public ResponseEntity<CategoryResponse> getCategories(@RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
                                                              @RequestParam(name ="pageSize",defaultValue = AppConstants.PAGE_SIZE)Integer pageSize,
                                                              @RequestParam(name ="sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY)String sortBy,
                                                              @RequestParam(name ="sortOrder",defaultValue = AppConstants.SORT_DIR)String sortOrder){
      CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
      return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
      //  List<Category> categories = categoryService.getAllCategories();
      //  return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //@PostMapping("api/public/categories")
    @RequestMapping(value="/public/categories",method = RequestMethod.POST)
    //public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        //categoryService.createCategory(category);
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
       // return new ResponseEntity<>("Category Added Successfully",HttpStatus.CREATED);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }

    //@DeleteMapping("api/admin/categories/{categoryId}")
    @RequestMapping(value="/admin/categories/{categoryId}",method = RequestMethod.DELETE)
    //public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
      //try {
        //String status = categoryService.deleteCategory(categoryId);
        CategoryDTO  deletedCategoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
        //return new ResponseEntity<>(status, HttpStatus.OK);
      //} catch (ResponseStatusException e) {
       // return new ResponseEntity<>(e.getReason(), e.getStatusCode());
      //}
    }

  //@PutMapping("api/public/categories/{categoryId}")
  @RequestMapping(value="/public/categories/{categoryId}",method = RequestMethod.PUT)
   // public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category,
     //                                            @PathVariable Long categoryId){
        public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                               @PathVariable Long categoryId){
      //try{
        //Category savedCategory = categoryService.updateCategory(category, categoryId);
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        //return new ResponseEntity<>("Updated Category with Category Id :" + categoryId, HttpStatus.OK);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
      //} catch (ResponseStatusException e) {
       // return new ResponseEntity<>(e.getReason(), e.getStatusCode());
      //}
    }

    }

