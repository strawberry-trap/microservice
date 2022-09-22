package dto.common;

public record ResponseDTO<T> (
        T data, Integer status, String message
) {
    public static <T>ResponseDTO ok (T data) {
        return new ResponseDTO<T>(data, 200, "200 ok");
    }

    public static <T>ResponseDTO notFound () {
        return new ResponseDTO<T>(null, 404, "404 not found");
    }
}
