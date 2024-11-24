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
@Table(name = "BS_ORDER")
@EntityListeners(AuditingEntityListener.class)
@ConstructorBinding
public class BsOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "user_id")
    private Long userId;

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;


    @Column(name = "status")
    private Integer status;

}

