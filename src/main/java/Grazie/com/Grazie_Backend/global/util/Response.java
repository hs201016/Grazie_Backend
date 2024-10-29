package Grazie.com.Grazie_Backend.global.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Response<T> {
    private String msg;
    private T result;

    // 공통 응답에 사용하기!
    public static <T> Response<T> success(T result){
        return new Response<>("Success", result);
    }
}
