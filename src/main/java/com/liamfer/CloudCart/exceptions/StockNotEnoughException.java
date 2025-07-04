package com.liamfer.CloudCart.exceptions;

import java.util.List;

public class StockNotEnoughException extends RuntimeException {
  private final List<Long> productIds;

  public StockNotEnoughException(String message, List<Long> productIds) {
    super(message);
    this.productIds = productIds;
  }

  public List<Long> getProductIds() {
    return productIds;
  }
}
