package com.codestates.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/coffees")
public class CoffeeController {

    Map<String, Object> map = new HashMap<>();

    @PostMapping
    public ResponseEntity postCoffee(@RequestParam("korName") String korName,
                                     @RequestParam("engName") String engName,
                                     @RequestParam("price") int price) {
        map.put("korName", korName);
        map.put("engName", engName);
        map.put("price", price);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        System.out.println("# coffeeId: " + coffeeId);

        // not implementation

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees() {
        System.out.println("# get Coffees");

        // not implementation

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //---------------- 여기서 부터 아래에 코드를 구현하세요! -------------------//
    // 1. 커피 정보 수정을 위한 핸들러 메서드 구현
    @PutMapping("/{coffee-id}")
    public ResponseEntity updateCoffee(@PathVariable("coffee-id") long coffeeId,
                                       @RequestParam("korName") String korName,
                                       @RequestParam("engName") String engName,
                                       @RequestParam("price") String price) {

        String korName1 = "바닐라 빈 라떼";
        int price1 = 5000;

        map.put("coffee-id", coffeeId);
        if(coffeeId == 1){
            map.put("korName", korName1);
            map.put("engName", engName);
            map.put("price", price1);
        }
        else{
            map.put("korName", korName);
            map.put("engName", engName);
            map.put("price", price);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 2. 커피 정보 삭제를 위한 핸들러 메서드 구현
    @DeleteMapping("/{coffeeId}")
    public ResponseEntity deleteMember(@PathVariable("coffeeId") long coffeeId) {

        map.remove(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
