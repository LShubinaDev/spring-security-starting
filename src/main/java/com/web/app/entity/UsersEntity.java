package com.web.app.entity;

import com.web.app.config.SpringSecurityConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

//todo лучше бы перечитать документации ко всем энтити-классасм - переписывал по многу раз, так и не пришел к единому выводу

/**
 * An entity class, representing user (the "users" table).
 * <p>
 * The following annotations just makes this class an {@code entity} - see entity-class requirements here:
 * https://docs.oracle.com/cd/E19798-01/821-1841/bnbqb/index.html
 * <p>
 * {@link Getter} and {@link Setter} are used for creating getters and setters for all class's fields
 * and the {@link AllArgsConstructor} generates constructor for all class's fields.
 *
 * @see com.web.app.entity.BaseEntity
 */
@Entity
@Table(name = "users")
/* Additional info:
 * <pre> @NoArgsConstructor </pre> generates an empty constructor.
 * It is used to for creating a new instance of a class using reflection (<pre> Class<T>.newInstance() </pre>)
 * by persistence provider(<pre> Hibernate </pre>). Also used for deserialization of a <pre> JSON </pre>.
 * Few more details here:
 * https://stackoverflow.com/questions/2935826/why-does-hibernate-require-no-argument-constructor */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersEntity extends BaseEntity {

    /**
     * A field, representing the {@code primary key} column in the "users" table.
     * <p>
     * Indicate that it is a {@code primary key} with the {@link Id} annotation.
     * <p>
     * {@link Column} indicates that this field is a column in the table, {@code name = "id}
     * indicates that it's name is "id".
     * <p>
     * {@link GeneratedValue} annotation specifies, how the {@code primary key} will be generated to this entity.
     * {@code strategy = GenerationType.IDENTITY} indicates that <strong>{@code primary key}
     * will be automatically generated by persistence provider</strong>, basing on the fact,
     * than "id" column is declared as a {@code primary key}, i.e. basing on the database's {@code primary key} column.
     * <p>
     * Type of this field is {@link Integer}, because it is equivalent to {@code SERIAL} in {@code PostgreSQL}.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * A field, representing the "enabled" column in the "users" table.
     * <p>
     * Usually, it is set to {@code true},
     * but if user was banned by admin, it is set to {@code false}.
     * User also can be unbanned by admin.
     * <p>
     * {@link Basic} indicates that there are no special properties to this field.
     * <p>
     * {@link Column} indicates that this field is a column in the table.
     * <ul>
     *     <li>
     *         {@code name = "enabled"} means that this field is called "enabled" in the table.
     *     </li>
     *     <li>
     *         @code nullable = false} means this field can never be {@code null} in the table.
     *     </li>
     * </ul>
     * <p>
     *
     * @see com.web.app.service.AdminService#banUserByUsername(String),
     * @see com.web.app.service.AdminService#unBanUserByUsername(String).
     */
    @Basic
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * A field, representing the "email" column in the table. At the moment, there is no additional functionality,
     * connected with user's email, but may be added in further.
     * <p>
     * {@link Basic} ...
     * <p>
     * {@link Column} ..., the {@code unique = true} means that this column has <strong>only</strong> unique values.
     */
    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * A field, representing the "username" column in the table.
     * <p>
     * <strong>NOTE:</strong> this field is <strong>very important</strong> for {@code Spring Security},
     * as far as all users logs in by <strong>username</strong> and <strong>password</strong>.
     * It is <strong>unique</strong>, because
     * {@link org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(String)}
     * loads user by username and each username should correspond to a <strong>single</strong> user.
     * Also it is <strong>never</strong> {@code null}.
     * <p>
     * {@link Basic} ...
     * <p>
     * {@link Column} ...
     */
    @Basic
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * A field, representing the "password" column in the table.
     * <p>
     * <strong>NOTE:</strong> this field is <strong>very important</strong> for {@code Spring Security},
     * as far as all users logs in by <strong>username</strong> and <strong>password</strong>.
     * <p>
     * {@link Basic} ...
     * <p>
     * {@link Column} ...
     *
     * @see SpringSecurityConfig#passwordEncoder()
     */
    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * There no column in the table, corresponding to this field, but, there is <strong>Many-to-may</strong>
     * relationship between {@link UsersEntity} and {@link RolesEntity} and the {@link ManyToMany} annotation
     * specifies that.
     * <p>
     * <ul>
     *     <li>
     *         Each user may have many roles.
     *         For example, an admin can also be a moderator and a basic user.
     *     </li>
     *     <li>
     *         Many users may correspond to a single role.
     *         For example, there can be several admins.
     *     </li>
     * </ul>
     * <p>
     * For a nice explanation of what is {@code fetch}, particularly, what {@code fetch = FetchType.EAGER} means, see:
     * https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
     * <p>
     * In the {@link JoinTable} annotation we specify:
     * <ul>
     *     <li>
     *         {@code name  = "users_to_roles"} - name of the mapping(joining) table.
     *     </li>
     *     <li>
     *         {@code joinColumns}:
     *         In the {@link JoinColumn} we specify the first column's name of the mapping table by setting
     *         {@code name = "usersid"}, then, which column it refers in the <strong>"users"</strong> table:
     *         {@code referencedColumnName = "id"}.
     *     </li>
     *     <li>
     *         {@code inverseJoinColumns}:
     *         In the {@link JoinColumn} we specify the second column's name of the mapping table by setting
     *         {@code name = "rolesid"}, then, which column it refers in the <strong>"roles"</strong> table:
     *         {@code referencedColumnName = "id"}.
     *     </li>
     * </ul>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_roles",
            joinColumns = {@JoinColumn(name = "usersid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rolesid", referencedColumnName = "id")})
    private Set<RolesEntity> roles;

    /**
     * This field has no mapping in the "users" table, but shows <strong>One-to-many</strong> relationship
     * between {@link UsersEntity} and {@link AgendaEntity}.
     * <p>
     * {@link OneToMany} annotation indicates <strong>One-to-many</strong> relationship
     * between <strong>entities</strong>, meaning, that a user may have
     * <strong>many</strong> agendas,
     * but each agenda corresponds <strong>exactly to one</strong> user.
     * <p>
     * For a nice explanation of what is {@code fetch}, particularly, what {@code fetch = FetchType.EAGER} means, see:
     * https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
     * <p>
     * Type of this field is {@link Set<AgendaEntity>} -
     * thus we specify relationship between <strong>entities</strong>.
     */
    @OneToMany(mappedBy = "usersid", fetch = FetchType.EAGER)
    private Set<AgendaEntity> agendas;
}
