package com.example.cryptus.service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "U moet achttien jaar of ouder zijn om u te registreren als klant.")
public class BelowEighteenException extends RuntimeException {}
