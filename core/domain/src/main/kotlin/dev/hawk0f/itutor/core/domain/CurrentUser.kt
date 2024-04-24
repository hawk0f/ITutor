package dev.hawk0f.itutor.core.domain

object CurrentUser
{
    private var userId: Int = 0

    fun setUserId(userId: Int)
    {
        this.userId = userId
    }

    fun getUserId(): Int
    {
        return userId
    }
}