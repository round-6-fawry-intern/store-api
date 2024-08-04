package com.fawryEx.storyEx.Exception.handle;

import com.fawryEx.storyEx.Exception.base.BaseResponse;
import com.fawryEx.storyEx.Exception.exceptiones.InsufficientStockException;
import com.fawryEx.storyEx.Exception.exceptiones.ProductNotFoundException;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody BaseResponse<String> handleStoreNotFoundException(StoreNotFoundException ex) {
        logger.error("StoreNotFoundException: {}", ex.getMessage());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        BaseResponse<String> error = new BaseResponse<String>();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return error;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody BaseResponse<String> handleProductNotFoundException(ProductNotFoundException ex) {
        logger.error("ProductNotFoundException: {}", ex.getMessage());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

        BaseResponse<String> error = new BaseResponse<String>();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return error;

    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody BaseResponse<String> handleInsufficientStockException(InsufficientStockException ex) {
        logger.error("InsufficientStockException: {}", ex.getMessage());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        BaseResponse<String> error = new BaseResponse<String>();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST);
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody BaseResponse<String> handleGenericException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage(), ex);
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        BaseResponse<String> error = new BaseResponse<String>();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return error;
    }
}
