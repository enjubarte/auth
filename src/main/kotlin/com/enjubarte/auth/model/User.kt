package com.enjubarte.auth.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.util.UUID

@Entity(name = "tb_users")
@JsonIgnoreProperties(
    "enabled",
    "authorities",
    "password",
    "username",
    "accountNonExpired",
    "accountNonLocked",
    "credentialsNonExpired"
)
data class User(
    @Id
    @GeneratedValue
    val id: UUID,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val senha: String,

    val createAt: Instant = Instant.now(),
    val updateAt: Instant = Instant.now()

): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>  = mutableListOf()
    override fun getPassword(): String = senha
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }
    override fun hashCode(): Int = javaClass.hashCode()
    @Override
    override fun toString(): String =
        this::class.simpleName + "(id = $id , email = $email , senha = $senha , createAt = $createAt , updateAt = $updateAt )"
}
