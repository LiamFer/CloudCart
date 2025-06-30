package com.liamfer.CloudCart.dto;

public record APIMessage <T>(int code,T message) {
}
