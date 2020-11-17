package com.kakao.pay.moneyspread;

import com.kakao.pay.moneyspread.utils.TokenGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("유틸 테스트")
public class UtilTest {
    @Test
    @DisplayName("토큰 생성 테스트")
    void testCreate() {
        final String token = TokenGenerator.create(3);
        assertEquals(3, token.length());
    }
}
