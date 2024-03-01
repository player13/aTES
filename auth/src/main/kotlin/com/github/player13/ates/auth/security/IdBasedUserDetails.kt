package com.github.player13.ates.auth.security

import com.github.player13.ates.auth.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class IdBasedUserDetails(
    val user: User,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority("ROLE_${user.role}"))

    override fun getPassword(): String =
        "{noop}${user.password}"

    override fun getUsername(): String =
        user.id.toString()

    override fun isAccountNonExpired(): Boolean =
        true

    override fun isAccountNonLocked(): Boolean =
        true

    override fun isCredentialsNonExpired(): Boolean =
        true

    override fun isEnabled(): Boolean =
        true
}