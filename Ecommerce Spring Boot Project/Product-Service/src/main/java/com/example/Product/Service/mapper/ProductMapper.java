package com.example.Product.Service.mapper;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "productId", target = "productId")
    ProductDTO toDTO(Product product);
    @Mapping(source = "productId", target = "productId")
    Product toProduct(ProductDTO productDTO);

    Product fromCreateRequest(CreateProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromRequest(UpdateProductRequest request, @MappingTarget Product product);
}
