package com.ki.models

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class PaymentTest {
    // card 
    @Test
    fun testPaymentFromCsvRowCard() {
        val CUSTOMER_ID = 123
        val AMOUNT = "2000"
        val CARD_STATUS = "processed"
        val CARD_ID = 45
        val DATE = "2019-02-01"
        val data = arrayOf(
            CUSTOMER_ID.toString(),
            DATE,
            AMOUNT, CARD_ID.toString(),
            CARD_STATUS
        )

        val source = "card"
        
        val payment = Payment(data, source)
        Assert.assertEquals(CUSTOMER_ID, payment.customerId)
        Assert.assertEquals(1960, payment.amount)
        Assert.assertEquals(40, payment.fee)
        Assert.assertEquals(LocalDate.of(2019, 2, 1), payment.date)
        Assert.assertTrue(payment.card is Card)
        val card = payment.card
        Assert.assertEquals(CARD_ID, card!!.cardId)
        Assert.assertEquals(CARD_STATUS, card.status)
    }

    @Test
    fun testIsSuccessfulCard() {
        val payment = Payment()
        payment.card = Card()
        payment.card!!.status = "processed"
        Assert.assertTrue(payment.isSuccessful)
    }

    @Test
    fun testIsSuccessfulDeclinedCard() {
        val payment = Payment()
        payment.card = Card()
        payment.card!!.status = "declined"
        Assert.assertFalse(payment.isSuccessful)
    }

    @Test
    fun testIsSuccessfulErroredCard() {
        val payment = Payment()
        payment.card = Card()
        payment.card!!.status = "error"
        Assert.assertFalse(payment.isSuccessful)
    }

    // bank
    @Test
    fun testPaymentFromCsvRowBank() {
        val CUSTOMER_ID = 123
        val AMOUNT = "2000"
        val BANK_ACCOUNT_ID = 45
        val DATE = "2019-02-01"
        val data = arrayOf(
            CUSTOMER_ID.toString(),
            DATE,
            AMOUNT, 
            BANK_ACCOUNT_ID.toString()
        )

        val source = "bank"
        
        val payment = Payment(data, source)
        Assert.assertEquals(CUSTOMER_ID, payment.customerId)
        Assert.assertEquals(1960, payment.amount)
        Assert.assertEquals(40, payment.fee)
        Assert.assertEquals(LocalDate.of(2019, 2, 1), payment.date)
        Assert.assertTrue(payment.bankAccount is BankAccount)
        val bankAccount = payment.bankAccount
        Assert.assertEquals(BANK_ACCOUNT_ID, bankAccount!!.bankAccountId)
    }

    @Test
    fun testIsSuccessfulBank() {
        val payment = Payment()
        payment.bankAccount = BankAccount()
        payment.bankAccount!!.bankAccountId = 123
        Assert.assertTrue(payment.isSuccessful)
    }

   
}