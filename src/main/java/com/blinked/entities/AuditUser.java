package com.blinked.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.utils.DateUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AuditUser implements Serializable {

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

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@PrePersist
	public void prePersist() {
		Date now = DateUtils.now();
		if (createTime == null) {
			createTime = now;
		}

		if (updateTime == null) {
			updateTime = now;
		}
		createdBy = AuthorizedUser.current().map(authorized -> authorized.getId()).orElse(null);
	}

	@PreUpdate
	private void update() {
		updateTime = new Date();
		updatedBy = AuthorizedUser.current().map(authorized -> authorized.getId()).orElse(null);
	}

	@PreRemove
	protected void preRemove() {
		updateTime = new Date();
	}
}