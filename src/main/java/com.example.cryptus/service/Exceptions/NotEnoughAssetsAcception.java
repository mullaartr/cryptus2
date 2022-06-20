package com.example.cryptus.service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "De bank heeft op dit moment niet genoeg van deze currency in zijn bezit")
public class NotEnoughAssetsAcception extends RuntimeException{}

