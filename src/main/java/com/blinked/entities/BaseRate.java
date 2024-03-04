package com.blinked.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.api.common.utils.CustomIdGenerator;
import com.api.common.utils.ServiceUtils;
import com.blinked.entities.enums.RateStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base comment entity.
 *
 * @author ssatwa
 * @date 2019-03-20
 */
@Data
@Entity(name = "BaseRate")
@Table(name = "rates", indexes = { @Index(name = "rates_product_id", columnList = "product_id"),
		@Index(name = "rates_type_status", columnList = "type, status"),
		@Index(name = "rates_parent_id", columnList = "parent_id") })
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int default 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseRate extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id",type = CustomIdGenerator.class)
	private Long id;

	/**
	 * Commentator's name.
	 */
	@Column(name = "author", length = 50, nullable = false)
	private String author;

	/**
	 * Commentator's email.
	 */
	@Column(name = "email", nullable = false)
	private String email;

	/**
	 * Commentator's ip address.
	 */
	@Column(name = "ip_address", length = 127)
	private String ipAddress;

	/**
	 * Commentator's website.
	 */
	@Column(name = "author_url", length = 511)
	private String authorUrl;

	/**
	 * Gravatar md5
	 */
	@Column(name = "gravatar_md5", length = 127)
	private String gravatarMd5;

	/**
	 * Comment content.
	 */
	@Column(name = "content", length = 1023, nullable = false)
	private String content;

	/**
	 * Comment status.
	 */
	@Column(name = "status")
	@ColumnDefault("1")
	private RateStatus status;

	/**
	 * Commentator's userAgent.
	 */
	@Column(name = "user_agent", length = 511)
	private String userAgent;

	/**
	 * Is admin's comment.
	 */
	@Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
	@Column(name = "is_admin")
	@ColumnDefault("0")
	private Boolean isAdmin;

	/**
	 * Allow notification.
	 */
	@Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
	@Column(name = "allow_notification")
	@ColumnDefault("1")
	private Boolean allowNotification;

	/**
	 * Post id.
	 */
	@Column(name = "product_id", nullable = false)
	private Integer productId;

	/**
	 * Whether to top the comment.
	 */
	@Column(name = "top_priority")
	@ColumnDefault("0")
	private Integer topPriority;

	/**
	 * Parent comment.
	 */
	@Column(name = "parent_id")
	@ColumnDefault("0")
	private Long parentId;

	@Override
	public void prePersist() {
		super.prePersist();

		if (ServiceUtils.isEmptyId(parentId)) {
			parentId = 0L;
		}

		if (ipAddress == null) {
			ipAddress = "";
		}

		if (authorUrl == null) {
			authorUrl = "";
		}

		if (gravatarMd5 == null) {
			gravatarMd5 = "";
		}

		if (status == null) {
			status = RateStatus.AUDITING;
		}

		if (userAgent == null) {
			userAgent = "";
		}

		if (isAdmin == null) {
			isAdmin = false;
		}

		if (allowNotification == null) {
			allowNotification = true;
		}
	}
}
