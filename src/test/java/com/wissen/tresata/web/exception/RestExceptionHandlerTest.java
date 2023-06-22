package com.wissen.tresata.web.exception;

import com.wissen.tresata.web.model.exceptionResponse.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUNIT Test case for Rest Exception Handler.
 *
 * @author Anushka Saxena
 */

public class RestExceptionHandlerTest {

    @InjectMocks
    RestExceptionHandler restExceptionHandler;

    @Mock
    Logger log;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void globalExceptionHandlerTest(){

        Exception testException = new Exception("Test");
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.globalExceptionHandler(testException);

        assertEquals(HttpStatus.BAD_GATEWAY, responseEntity.getStatusCode());

        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(testException.getMessage(), errorResponse.getMessage());
    }
}
