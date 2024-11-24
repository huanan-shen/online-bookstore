package org.anthony.bs.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * user table for online bookstore(BsBook)实体类
 *
 * @author makejava
 * @since 2024-11-24 11:42:27
 */
@Data
@Entity
@Table(name = "BS_BOOK")
@EntityListeners(AuditingEntityListener.class)
@ConstructorBinding
public class BsBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * user name
     */
    @Column(name = "name")
    private String name;
    /**
     * author
     */
    @Column(name = "author")
    private String author;
    /**
     * publisher
     */
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "pub_version")
    private String pubVersion;
    /**
     * price
     */
    @Column(name = "price")
    private Double price;

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;
    /**
     * is active, 1: active, 0:deleted
     */
    @Column(name = "is_active")
    private Integer isActive;

}

