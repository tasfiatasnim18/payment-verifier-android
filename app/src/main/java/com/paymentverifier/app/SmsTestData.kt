package com.paymentverifier.app

data class TestSms(
    val name: String,
    val sender: String,
    val message: String
)

object SmsTestData {

    val bKash = TestSms(
        name = "bKash",
        sender = "BKASH",
        message =
            "You have received Tk 100.00 from 01717044734. " +
                    "Ref PV-TEST001. Fee Tk 0.00. " +
                    "Balance Tk 3,612.40. " +
                    "TrxID TESTBKASH001 at 27/03/2026 18:26"
    )

    val nagad = TestSms(
        name = "Nagad",
        sender = "NAGAD",
        message =
            "Money Received. " +
                    "Amount: Tk 250.00 " +
                    "Ref PV-TEST002 " +
                    "TxnID TESTNAGAD001"
    )

    val unknownSender = TestSms(
        name = "Unknown Sender",
        sender = "UNKNOWN_TEST",
        message =
            "You have received Tk 100.00. " +
                    "Ref PV-TEST003. " +
                    "TrxID UNKNOWN001"
    )

    val noTransactionId = TestSms(
        name = "No Transaction ID",
        sender = "BKASH",
        message =
            "You have received Tk 100.00 from 01717044734. " +
                    "Ref PV-TEST004."
    )
}