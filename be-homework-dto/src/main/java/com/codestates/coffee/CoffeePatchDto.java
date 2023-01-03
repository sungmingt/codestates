package com.codestates.coffee;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CoffeePatchDto {

    private Long coffeeId;

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    String korName;

    @NotBlank(message = "영문 이름은 공백이 아니어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$",
            message = "영문만 입력 가능합니다.")
    String engName;

    @Range(min = 100, max = 50000)
    int price;



    public Long getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(Long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public String getKorName() {
        return korName;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
