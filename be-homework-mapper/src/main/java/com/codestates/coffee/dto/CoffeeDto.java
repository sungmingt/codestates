package com.codestates.coffee.dto;

import com.codestates.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Optional;

public class CoffeeDto {

    @Getter
    @AllArgsConstructor
    public static class CoffeePostDto{
        public CoffeePostDto(){}

        @NotBlank
        private String korName;

        @NotBlank
        @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
                message = "커피명(영문)은 영문이어야 합니다(단어 사이 공백 한 칸 포함). 예) Cafe Latte")
        private String engName;

        @Range(min= 100, max= 50000)
        private int price;
    }

    @Getter
    @AllArgsConstructor
    public static class CoffeePatchDto {
        private long coffeeId;

        @NotSpace(message = "커피명(한글)은 공백이 아니어야 합니다.")
        private String korName;

        @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$", message = "커피명(영문)은 영문이어야 합니다. 예) Cafe Latte")
        private String engName;

        @Range(min = 5000, max = 10000)
        private int price;

    }

    @Getter
    @AllArgsConstructor
    public static class CoffeeResponseDto {
        private long coffeeId;
        private String korName;
        private String engName;
        private int price;
    }
}
