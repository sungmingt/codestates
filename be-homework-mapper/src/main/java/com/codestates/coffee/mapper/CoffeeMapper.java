package com.codestates.coffee.mapper;

import com.codestates.coffee.dto.CoffeeDto;
import com.codestates.coffee.entity.Coffee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    Coffee coffeePostDtoToCoffee(CoffeeDto.CoffeePostDto coffeePostDto);
    Coffee coffeePatchDtoToCoffee(CoffeeDto.CoffeePatchDto coffeePatchDto);
    CoffeeDto.CoffeeResponseDto coffeeToCoffeeResponseDto(Coffee coffee);
}
