package lihan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result {

    private boolean success;

    private String message;

    private String stackTrace;

    private  Object data;
}
