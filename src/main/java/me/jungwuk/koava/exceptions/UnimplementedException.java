package me.jungwuk.koava.exceptions;

public class UnimplementedException extends RuntimeException {
    public UnimplementedException() {
        super("아직 구현되지 않은 메소드입니다!");
    }
}
