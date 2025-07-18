package modules.payments.domain.repository

import modules.payments.domain.model.Payment
import modules.payments.domain.model.PaymentStatus

interface IPaymentRepository {
    suspend fun createPayment(payment: Payment): Boolean
    suspend fun getPaymentsByUser(userId: String): List<Payment>
    suspend fun getPaymentById(paymentId: String): Payment?
    suspend fun updateStatus(paymentId: String, status: PaymentStatus): Boolean
}
