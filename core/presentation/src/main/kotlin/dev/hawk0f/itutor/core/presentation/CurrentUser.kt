package dev.hawk0f.itutor.core.presentation

object CurrentUser
{
    private var userId: Int = 0

    fun getUserId(): Int = userId

    fun setUserId(id: Int)
    {
        userId = id
    }
}