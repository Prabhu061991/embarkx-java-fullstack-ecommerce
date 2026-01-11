package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private ModelMapper modelMapper;
    //private List<Category> categories = new ArrayList<>();
    //private long nextId=1L;
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
       // public List<Category> getAllCategories() {
        //return categories;
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryrepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        //List<Category> categories = categoryrepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No Category Created Till Now");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
        //return categories;
        //return categoryrepository.findAll();
    }

    @Override
    //public void createCategory(Category category) {
        public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryrepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDB != null)
            throw new APIException("Category With the name "+ category.getCategoryName() + " Already Exists!!!");
        //category.setCategoryId(nextId++);
        //categories.add(category);
        //categoryrepository.save(category);
        Category savedCategory = categoryrepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

    @Override
    //public String deleteCategory(Long categoryId) {
        public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryrepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not found"));
       // List<Category> categories = categoryrepository.findAll();
        //Category category = categories.stream()
          //      .filter(c -> c.getCategoryId().equals(categoryId))
          //      .findFirst()
          //      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));


        //categories.remove(category);
        categoryrepository.delete(category);
        //return "Category with categoryId: " + categoryId + " deleted successfully !!";
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    //public Category updateCategory(Category category, Long categoryId) {
        public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category savedCategory = categoryrepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryrepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
        //return savedcategory;
        //List<Category> categories = categoryrepository.findAll();
       // Optional<Category> optionalCategory = categories.stream()
        //.filter(c -> c.getCategoryId().equals(categoryId))
         //       .findFirst();

        //if(optionalCategory.isPresent()){
         //   Category existingCategory = optionalCategory.get();
         //   existingCategory.setCategoryName(category.getCategoryName());
         //   Category savedCategory = categoryrepository.save(existingCategory);
            //return existingCategory;
         //   return savedCategory;
        //}else{
        //    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        //}
    }

}
