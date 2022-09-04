package com.tutorial.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2698850205870683247L;

    @CreatedBy
    @Column(name = "CREATE_BY")
    private String createBy;

    @CreationTimestamp
    @Column(name = "CREATE_DATE")
    private Instant createDate;

    @LastModifiedBy
    @Column(name = "UPDATE_BY")
    private String updateBy;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE")
    private Instant updateDate;

}
