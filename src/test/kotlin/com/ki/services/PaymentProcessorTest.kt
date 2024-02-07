package com.ki.services

import com.ki.Fixture
import com.ki.models.Card
import com.ki.models.BankAccount
import com.ki.models.Payment
import org.junit.Assert
import org.junit.Test

class PaymentProcessorTest {
    // card
    @Test
    fun testGetPaymentsCard() {
        val fixturePath = Fixture.getPath("card_payments_mixed.csv")
        val processor = PaymentProcessor()
        val payments = processor.getPayments(fixturePath, "card")
        Assert.assertEquals(3, payments.size.toLong())
        Assert.assertEquals(30, payments[0].card!!.cardId)
        Assert.assertEquals(45, payments[1].card!!.cardId)
        Assert.assertEquals(10, payments[2].card!!.cardId)
    }

    @Test
    fun testGetPaymentsEmptyCard() {
        val fixturePath = Fixture.getPath("card_payments_empty.csv")
        val processor = PaymentProcessor()
        val payments = processor.getPayments(fixturePath, "card")
        Assert.assertEquals(0, payments.size.toLong())
    }

    @Test
    fun testVerifyPaymentsCard() {
        val payment1 = createPaymentCard("processed")
        val payment2 = createPaymentCard("declined")
        val payment3 = createPaymentCard("processed")
        val payments = arrayOf<Payment>(payment1, payment2, payment3)
        val processor = PaymentProcessor()
        val result = processor.verifyPayments(payments)
        val expected = arrayOf(payment1, payment3)
        Assert.assertArrayEquals(expected, result)
    }

    private fun createPaymentCard(cardStatus: String): Payment {
        val card = Card()
        card.status = cardStatus
        val payment = Payment()
        payment.card = card
        return payment
    }

    // bank
    @Test
    fun testGetPaymentsBank() {
        val fixturePath = Fixture.getPath("bank_payments.csv")
        val processor = PaymentProcessor()
        val payments = processor.getPayments(fixturePath, "bank")
        Assert.assertEquals(2, payments.size.toLong())
        Assert.assertEquals(20, payments[0].bankAccount!!.bankAccountId)
        Assert.assertEquals(60, payments[1].bankAccount!!.bankAccountId)
    }

    @Test
    fun testGetPaymentsEmptyBank() {
        val fixturePath = Fixture.getPath("bank_payments_empty.csv")
        val processor = PaymentProcessor()
        val payments = processor.getPayments(fixturePath, "bank")
        Assert.assertEquals(0, payments.size.toLong())
    }

    @Test
    fun testVerifyPaymentsBank() {
        val payment1 = createPaymentBankAccount()
        val payment2 = Payment()
        val payments = arrayOf<Payment>(payment1, payment2)
        val processor = PaymentProcessor()
        val result = processor.verifyPayments(payments)
        val expected = arrayOf(payment1)
        Assert.assertArrayEquals(expected, result)
    }

    fun createPaymentBankAccount (): Payment {
        val bankAccount = BankAccount()
        bankAccount.bankAccountId = 123
        val payment = Payment()
        payment.bankAccount = bankAccount

        return payment
    }


   
}