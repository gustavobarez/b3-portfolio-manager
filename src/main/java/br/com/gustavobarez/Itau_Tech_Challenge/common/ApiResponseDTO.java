package br.com.gustavobarez.Itau_Tech_Challenge.common;

public class ApiResponseDTO<T> {
    private T data;
    private String message;

    public ApiResponseDTO(T data, String operation) {
        this.data = data;
        this.message = buildMessage(operation);
    }

    private String buildMessage(String operation) {
        return "Operation from handler: " + operation + " successfully";
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
