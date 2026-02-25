package application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,Object>> handleNoSuchElementException(NoSuchElementException ex){
        Map<String,Object> map = new HashMap<>();
        map.put("error","NotFound");
        map.put("message",ex.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        Map<String,Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        Map<String,Object> map = new HashMap<>();
        map.put("errors","Validation Failed");
        map.put("message",errors);
        return ResponseEntity.badRequest().body(map);

    }
}
