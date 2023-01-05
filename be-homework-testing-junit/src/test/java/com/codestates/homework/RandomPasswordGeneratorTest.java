package com.codestates.homework;

import com.codestates.helper.RandomPasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class RandomPasswordGeneratorTest {

    @DisplayName("실습 3: 랜덤 패스워드 생성 테스트")
    @Test
    public void generateTest() {
        // TODO 여기에 테스트 케이스를 작성해주세요.
        //given
        String generate = RandomPasswordGenerator.generate(5, 10, 3, 2);

        //when
        int totalLength = generate.length();
        long upperCases = countUpperCase(generate);
        long lowerCases = countLowerCase(generate);
        long numericValues = countNumeric(generate);
        long specialChars = countSpecial(generate);

        //then
        assertEquals(20, totalLength);
        assertEquals(5, upperCases);
        assertEquals(10, lowerCases);
        assertEquals(3, numericValues);
        assertEquals(2, specialChars);
    }

    //==============================
    public static long countUpperCase(String str) {
        return str.chars()
                .filter(Character::isUpperCase)
                .count();
    }
    public static long countLowerCase(String str) {
        return str.chars()
                .filter(Character::isLowerCase)
                .count();
    }
    public static long countNumeric(String str) {
        return str.chars()
                .filter(Character::isDigit)
                .count();
    }
    public static long countSpecial(String str) {
        return str.chars()
                .filter(s -> !Character.isLetterOrDigit(s) && !Character.isSpaceChar(s))
                .count();
    }
}
