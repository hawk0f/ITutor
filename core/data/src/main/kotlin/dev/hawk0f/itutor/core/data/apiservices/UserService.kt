package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService
{
    @GET("Users/getUserById")
    suspend fun getUserById(@Query("userId") userId: Int): Response<UserDTO>

    @FormUrlEncoded
    @POST("Users/authUser")
    suspend fun authUser(@Field("email") email: String, @Field("password") password: String): Response<UserDTO>

    @POST("Users/registerUser")
    suspend fun registerUser(@Body userDTO: UserDTO): Response<UserDTO>

    @PUT("Users/updateUser")
    suspend fun updateUser(@Body userDTO: UserDTO): Response<Unit>

    @DELETE("Users/deleteUser")
    suspend fun deleteUser(@Query("userId") userId: Int): Response<Unit>
}