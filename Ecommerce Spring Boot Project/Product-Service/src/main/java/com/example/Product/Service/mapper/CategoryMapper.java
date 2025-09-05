package com.example.Product.Service.mapper;

import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}
