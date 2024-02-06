package com.ki.models

import com.ki.Config
import com.ki.enums.PaymentSource
import java.math.BigDecimal
import java.time.LocalDate

class Payment {
    var customerId = 0
    var date: LocalDate? = null
    var amount = 0
    var fee = 0

    var card: Card? = null
    var bankAccount: BankAccount? = null

    constructor() {}
    constructor(data: Array<String>, source: String) {
        customerId = data[0].toInt()
        val paymentFeeRate = Config.paymentFeeRate
        val totalAmount = data[2].toInt()
        fee = paymentFeeRate.multiply(BigDecimal(totalAmount)).toInt()
        amount = totalAmount - fee
        date = LocalDate.parse(data[1])

        if(source == PaymentSource.card.name){
            val card = Card()
            card.cardId = data[3].toInt()
            card.status = data[4]
            this.card = card
        } else if(source == PaymentSource.bank.name){
            val bankAccount = BankAccount()
            bankAccount.bankAccountId = data[3].toInt()
            this.bankAccount = bankAccount
        } else {
            // todo: error
        }
    }

    val isSuccessful: Boolean
        get() = card?.status == "processed" || bankAccount?.bankAccountId != null
        //todo - async??
}