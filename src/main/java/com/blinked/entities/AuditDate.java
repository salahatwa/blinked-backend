package com.blinked.entities;

import java.io.Serializable;
import java.util.Date;

import com.api.common.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AuditDate implements Serializable {

	/**
	 * Create time.
	 */
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/**
	 * Update time.
	 */
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@PrePersist
	public void prePersist() {
		Date now = DateUtils.now();
		if (createTime == null) {
			createTime = now;
		}

		if (updateTime == null) {
			updateTime = now;
		}
	}

	@PreUpdate
	private void update() {
		updateTime = new Date();
	}

	@PreRemove
	protected void preRemove() {
		updateTime = new Date();
	}
}