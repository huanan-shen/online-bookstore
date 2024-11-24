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
 * user table for online bookstore(BsBookRepo)实体类
 *
 * @author makejava
 * @since 2024-11-24 11:42:27
 */
@Data
@Entity
@Table(name = "BS_BOOK_REPO")
@EntityListeners(AuditingEntityListener.class)
@ConstructorBinding
public class BsBookRepo implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "book_count")
    private Integer bookCount;

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;

}

