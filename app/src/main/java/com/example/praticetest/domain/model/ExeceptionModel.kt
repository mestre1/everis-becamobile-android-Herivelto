package com.example.praticetest.domain.model

class ExceptionModel(message: String = "Falha ao conectar na Internet") : Exception(message) {

}