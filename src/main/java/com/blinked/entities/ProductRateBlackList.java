package com.blinked.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssatwa
 * @date 2020/1/3
 */
@Data
@javax.persistence.Entity
@Table(name = "rate_black_list")
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRateBlackList extends AuditUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.blinked.utils.CustomIdGenerator")
    private Long id;

    @Column(name = "ip_address", length = 127, nullable = false)
    private String ipAddress;


    @Column(name = "ban_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date banTime;
}
