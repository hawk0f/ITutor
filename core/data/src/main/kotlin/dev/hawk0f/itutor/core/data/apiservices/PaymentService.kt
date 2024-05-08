package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.PaymentDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PaymentService
{
    @GET("Payments/getAllPayments")
    suspend fun fetchPayments(@Query("userId") userId: Int): Response<List<PaymentDTO>>

    @PUT("Payments/updatePaymentStatus")
    suspend fun updatePayment(@Query("studentId") studentId: Int, @Query("lessonId") lessonId: Int, @Query("hasPaid") hasPaid: Boolean): Response<Unit>
}