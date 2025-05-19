package com.v1.apiDebt.Io.domain.enums;

public enum ErrorCodeEnum {
    // Erros remetentes a usuários
    USR001("USR001", "CPF inserido é inválido."),
    USR002("USR002", "Telefone inserido é inválido."),
    USR003("USR003", "Email inserido é inválido."),
    USR004("USR004", "Senha inserida é inválida. Por favor, ajuste os caracteres."),
    USR005("USR005", "Ocorreu um erro na rotina de criação de usuário."),
    USR006("USR006", "Usuário não foi encontrado."),
    USR007("USR007", "Senha incorreta."),
    USR008("USR008", "As novas senhas devem ser iguais."),
    USR009("USR009", "Esse CPF já está cadastrado."),
    USR010("USR010", "Esse email já está cadastrado."),
    USR011("USR011", "Por favor, confirme o cadastro do usuário."),

    // Erros de Mappers
    MAP001("MAP001", "Ocorreu um erro ao aplicar um Mapper de usuário, método: deDominioParaEntidade."),
    MAP002("MAP002", "Ocorreu um erro ao aplicar um Mapper de usuário, método: deEntidadeParaDominio."),
    MAP003("MAP003", "Ocorreu um erro ao aplicar um Mapper de contas, método: deDominioParaEntidade."),
    MAP004("MAP004", "Ocorreu um erro ao aplicar um Mapper de contas, método: deEntidadeParaDominio."),

    //Erros de Contas
    CON001("CON001", "Ocorreu um erro na rotina de criação de contas."),
    CON002("CON002", "Conta não encontrada."),
    CON003("CON003", "Ocorreu um erro na rotina de atualização de conta."),
    CON004("CON004", "Ocorreu um erro na rotina de obter total gasto mês."),

    TESTE_PRA_FINALIZAR("Teste nesse caralho", "Teste nesse caralho");

     // Atributos
    private String code;
    private String message;

    // Construtor
    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Métodos
    public static String concatError(String exceptionMessage, ErrorCodeEnum errorCodeEnum) {
        return String.format("%s: {%s}", errorCodeEnum.getMessage(), exceptionMessage);
    }

    // Getters e Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
