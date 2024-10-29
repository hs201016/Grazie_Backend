package Grazie.com.Grazie_Backend.global.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Error {
    private final int code;
    private final Object message;

    public static Error of(int code, Object message) {
        return new Error(code, message);
    }
}
