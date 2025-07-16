package shop.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import shop.server.model.dto.ProductDto;
import shop.server.model.entity.ProductEntity;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toProductEntity(ProductDto productDto);
    ProductDto toProductDto(ProductEntity productEntity);
}
