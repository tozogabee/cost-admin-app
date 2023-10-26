package cost.admin.app.util;

import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.input.SpendDto;
import org.mapstruct.Mapper;

@Mapper
public interface SpendEntityMapper {

    SpendEntity convertToEntityFromDto(SpendDto spendDto);

}
