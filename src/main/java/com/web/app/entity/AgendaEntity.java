package com.web.app.entity;

import lombok.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;

/**
 * An entity class, representing user's agenda (the "agenda" table).
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
@Table(name = "agenda")
/* Additional info:
 * @NoArgsConstructor Generates an empty constructor.
 * It is used to for creating a new instance of a class using reflection (Class<T>.newInstance())
 * by persistence provider(Hibernate). Also used for deserialization of a JSON.
 * Few more details here:
 * https://stackoverflow.com/questions/2935826/why-does-hibernate-require-no-argument-constructor */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AgendaEntity extends BaseEntity {

    /**
     * A field, representing the primary key column in the "agenda" table.
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
     * Type of this field is {@link Long}, because it is equivalent to {@code BIGSERIAL} in PostgreSQL.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This field represents the "usersid" column in the table.
     * <p>
     * {@link ManyToOne} annotation indicates <strong>Many-to-one</strong> relationship between entities
     * Each user can have <strong>many</strong> agendas,
     * but each agenda corresponds exactly to <strong>one</strong> user.
     * <p>
     * {@link JoinColumn} annotation specifies a column's name for joining an entity.
     * <p>
     * Type of this field is {@link UsersEntity} - thus we specify relationship between <strong>entities</strong>.
     */
    @ManyToOne
    @JoinColumn(name = "usersid")
    private UsersEntity usersid;

    /**
     * This field represents the "day" column in the "agenda" table.
     * <p>
     * {@link Enumerated} annotation means the all this field's values can be enumerated,
     * the type of this values is {@link String} as indicated by {@link EnumType#STRING}
     * <p>
     * {@link Column} ...
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private DayOfWeek day;

    /**
     * This field represents the "time" column in the "agenda" table.
     * <p>
     * As far as time if formatted as HH:MM, it's type is {@link String} of a length 5.
     * <p>
     * {@link Basic} annotation means that this field doesn't possess any special properties.
     * <p>
     * {@link Column} ..., {@code nullable = false} means this field can never be {@code null} in database.
     * Also specify it's <strong>maximum</strong> length with {@code length = 5}.
     */
    @Basic
    @Column(name = "time", nullable = false, length = 5)
    private String time;

    /**
     * This field represents the "accessible" column in the "agenda" table.
     * Defines if an agenda accessible to other users.
     * <p>
     * {@link Basic} ...
     * <p>
     * {@link Column} ...
     */
    @Basic
    @Column(name = "accessible", nullable = false)
    private boolean accessible;

    /**
     * This field represents the "note" column in the "agenda" table (the agenda itself).
     * <p>
     * {@link Basic} ...
     * <p>
     * {@link Column} ...
     */
    @Basic
    @Column(name = "note", nullable = false)
    private String note;
}