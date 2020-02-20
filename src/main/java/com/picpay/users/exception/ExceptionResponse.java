package com.picpay.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionResponse {

    public static ResponseEntity<GeneralException> ValidationError(){
       return new ResponseEntity<GeneralException>(GeneralException
                .builder()
                .code("422")
                .message("Erro de validação nos campos")
                .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static ResponseEntity<GeneralException> UserNotFoundError(){
        return new ResponseEntity<GeneralException>(GeneralException
                .builder()
                .code("404")
                .message("Usuário não encontrado")
                .build(), HttpStatus.NOT_FOUND);
    }

    public static  ResponseEntity<GeneralException> InternalError(){
        return new ResponseEntity<GeneralException>(new GeneralException("500", "Erro interno do servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static  ResponseEntity<GeneralException> UnauthorizedError(){
        return new ResponseEntity<GeneralException>(new GeneralException("401", "Transação não autorizada"), HttpStatus.UNAUTHORIZED);
    }
    public static  ResponseEntity<GeneralException> TransactionNotFoundError(){
        return new ResponseEntity<GeneralException>(new GeneralException("401","Transação não encontrada"), HttpStatus.NOT_FOUND);
    }
}
