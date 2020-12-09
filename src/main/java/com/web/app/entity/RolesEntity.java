package com.web.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * An entity class, representing roles (the "roles" table).
 * <p>
 * The following annotations just makes this class an entity - see entity-class requirements here:
 * https://docs.oracle.com/cd/E19798-01/821-1841/bnbqb/index.html
 * <p>
 * {@link Getter} and {@link Setter} are used for creating getters and setters for all class's fields
 * and the {@link AllArgsConstructor} generates constructor for all class's fields.
 *
 * @see com.web.app.entity.BaseEntity
 */
@Entity
@Table(name = "roles")
/* Additional info:
 * @NoArgsConstructor generates an empty constructor.
 * It is used to for creating a new instance of a class using reflection (Class<T>.newInstance())
 * by persistence provider(Hibernate). Also used for deserialization of a JSON.
 * Few more details here:
 * https://stackoverflow.com/questions/2935826/why-does-hibernate-require-no-argument-constructor */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RolesEntity extends BaseEntity {

    /**
     * A field, representing the primary key column in the "roles" table.
     * <p>
     * Indicate that it is a primary key with the {@link Id} annotation.
     * <p>
     * {@link Column} indicates that this field is a column in the table, {@code name = "id}
     * indicates that it's name is "id".
     * <p>
     * {@link GeneratedValue} annotation specifies, how the primary key will be generated to this entity.
     * {@code strategy = GenerationType.IDENTITY} indicates that <strong>primary key
     * will be automatically generated by persistence provider</strong>, basing on the fact,
     * than "id" column is declared as a primary key, i.e. basing on the database's primary key column.
     * <p>
     * Type of this field is {@link Integer}, because it is equivalent to {@code SERIAL} in PostgreSQL.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * This field represents the "role" field in the "roles" table (the role name).
     * <p>
     * {@link Basic} annotation means that this field doesn't possess any special properties.
     * <p>
     * {@link Column} indicates that this field is a column in the table.
     * <ul>
     *     <li>
     *         {@code name = "role"} means that this field is called "role" in the table.
     *     </li>
     *     <li>
     *         {@code unique = true} means that this column has <strong>only</strong> unique values.
     *     </li>
     *     <li>
     *         {@code nullable = false} means this field can never be {@code null} in database.
     *     </li>
     * </ul>
     */
    @Basic
    @Column(name = "role", unique = true, nullable = false)
    private String role;

    /**
     * There is no field, corresponding to this field in the "roles" table, but instead, we use an additional
     * table called "users_to_roles", as far as the relationship between the {@link RolesEntity} and the
     * {@link UsersEntity} is <strong>Many-to-many:</strong>
     * <ul>
     *     <li>
     *         Each user may have many roles. For example, an admin can also be moderator and a basic user.
     *     </li>
     *     <li>
     *         Many users may correspond to a single role. For example, there can be several admins.
     *     </li>
     * </ul>
     * {@link ManyToMany} annotation indicates this relationship, and the {@code mappedBy = "roles"} shows that
     * "roles" defines the "owner" of a relationship.
     */
    @ManyToMany(mappedBy = "roles")
    private Set<UsersEntity> users;
}
