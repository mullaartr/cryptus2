package com.example.cryptus.service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "je hebt op dit moment niet genoeg saldo op je bankrekening")
public class NotEnoughSaldoException extends RuntimeException {}
