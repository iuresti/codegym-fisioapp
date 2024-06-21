package codegym.tequila.fisioapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class FisioAppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FisioAppExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException exception) {

        logger.error("No such element exception", exception);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
