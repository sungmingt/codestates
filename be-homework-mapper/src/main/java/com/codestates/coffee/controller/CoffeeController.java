package com.codestates.coffee.controller;

import com.codestates.coffee.dto.CoffeeDto;
import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.mapper.CoffeeMapper;
import com.codestates.coffee.service.CoffeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v5/coffees")
@Validated
public class CoffeeController {

    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;
    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }


    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeeDto.CoffeePostDto coffeePostDto) {
        Coffee coffee = coffeeMapper.coffeePostDtoToCoffee(coffeePostDto);
        coffeeService.createCoffee(coffee);

        return new ResponseEntity<>(
                coffeeMapper.coffeeToCoffeeResponseDto(coffee), HttpStatus.CREATED);
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id") @Positive long coffeeId,
                                      @Valid @RequestBody CoffeeDto.CoffeePatchDto coffeePatchDto) {

        Coffee coffee = coffeeMapper.coffeePatchDtoToCoffee(coffeePatchDto);
        coffeeService.updateCoffee(coffee);

        return new ResponseEntity<>(
                coffeeMapper.coffeeToCoffeeResponseDto(coffee), HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {

        Coffee coffee = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(
                coffeeMapper.coffeeToCoffeeResponseDto(coffee), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees() {
        List<Coffee> coffees = coffeeService.findCoffees();

        List<CoffeeDto.CoffeeResponseDto> response = coffees.stream()
                .map(coffee -> coffeeMapper.coffeeToCoffeeResponseDto(coffee))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id") long coffeeId) {
        coffeeService.deleteCoffee(coffeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
