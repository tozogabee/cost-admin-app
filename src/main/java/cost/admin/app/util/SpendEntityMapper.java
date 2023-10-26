package cost.admin.app.util;

import cost.admin.app.model.currency.Currency;
import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.category.ProductCategory;
import cost.admin.app.model.input.SpendDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpendEntityMapper {

    SpendEntityMapper MAPPER = Mappers.getMapper(SpendEntityMapper.class);

    @Mapping(target = "category", expression = "java(getCategory(spendDto.getCategory()))")
    @Mapping(target = "currency", expression = "java(getCurrency(spendDto.getCurrency()))")
    SpendEntity convertToEntityFromDto(SpendDto spendDto);

    default ProductCategory getCategory(String value) {
        return ProductCategory.valueOf(value.toUpperCase());
    }

    default Currency getCurrency(String value) {
        return Currency.valueOf(value.toUpperCase());
    }



}
