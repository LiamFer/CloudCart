package com.liamfer.CloudCart.dto;

import java.util.List;

public record APICheckoutErrorMessage(int code, String message, List<Long> products) {
}
