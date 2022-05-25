package com.example.cryptus.service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Vul een geldige BSN in")
public class WrongBsnException extends RuntimeException {}

