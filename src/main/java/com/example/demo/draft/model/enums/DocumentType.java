package com.example.demo.draft.model.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    UNDEFINED(-1),
    PAYMENT_ORDER(10),
    INTERNAL_TRANSFER(11),
    SALARY_PAYMENT(13),
    STATEMENT(60),
    /** Валютный перевод */
    CURRENCY_PAYMENT(100),
    /** Депозитное заявление */
    DEPOSIT_APPLICATION(270),
    /** Заявление на отзыв депозита */
    DEPOSIT_WITHDRAWAL(275),
    BANK_MESSAGE(50),
    BANK_MESSAGE_WITH_ATTACHMENT(52),
    /** Запрос на аннуляцию */
    CANCELLATION_REQUEST(53),
    /** СВО */
    CURRENCY_OPERATION_INFORMATION(995),
    /** СВО табличная часть */
    CURRENCY_OPERATION(595),
    /** СПД */
    SUPPORTING_DOCUMENT_INFORMATION(997),
    /** СПД табличная часть */
    SUPPORTING_DOCUMENT(597),
    /** РОП */
    OBLIGATORY_SALE(90),
    /** Заявка на транш */
    TRANCHE_APPLICATION(271),
    /** Заявление о постановке на учет контракта */
    INTERNATIONAL_CONTRACT_REGISTRATION(985),
    /** Заявление о постановке на учет кредитного договора */
    INTERNATIONAL_CREDIT_REGISTRATION(987),
    /** Заявление о внесении изменений в контракт или кредитный договор */
    INTERNATIONAL_CONTRACT_AMENDMENT(989),
    /** Строка заявления о внесении изменений в контракт или кредитный договор */
    INTERNATIONAL_CONTRACT_AMENDMENT_LINE(589),
    /** Заявление о снятии контракта или кредитного договора с учета */
    INTERNATIONAL_CONTRACT_CLOSURE(988),
    /** Покупка валюты */
    PURCHASE_CURRENCY(70),
    /** Продажа валюты */
    SALE_CURRENCY(80),
    /** Зарплатный реестр */
    SALARY_REGISTRY(55),
    /** Заявление о присоединении к Соглашению об общих условиях "E-Trading */
    ETRADING_AGREEMENT(670);


    private final int value;

    DocumentType(int value) {
        this.value = value;
    }

    public static DocumentType valueOf(int value) {
        for (var v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return UNDEFINED;
    }
}
