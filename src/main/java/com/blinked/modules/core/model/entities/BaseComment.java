package com.blinked.modules.core.model.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.blinked.modules.core.model.enums.CommentStatus;
import com.blinked.modules.core.utils.ServiceUtils;

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
@Entity(name = "BaseComment")
@Table(name = "comments", indexes = { @Index(name = "comments_post_id", columnList = "post_id"),
		@Index(name = "comments_type_status", columnList = "type, status"),
		@Index(name = "comments_parent_id", columnList = "parent_id") })
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int default 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseComment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "com.blinked.modules.core.model.entities.CustomIdGenerator")
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
	private CommentStatus status;

	/**
	 * Commentator's userAgent.
	 */
	@Column(name = "user_agent", length = 511)
	private String userAgent;

	/**
	 * Is admin's comment.
	 */
	@Type(type = "numeric_boolean")
	@Column(name = "is_admin")
	@ColumnDefault("0")
	private Boolean isAdmin;

	/**
	 * Allow notification.
	 */
	@Type(type = "numeric_boolean")
	@Column(name = "allow_notification")
	@ColumnDefault("1")
	private Boolean allowNotification;

	/**
	 * Post id.
	 */
	@Column(name = "post_id", nullable = false)
	private Integer postId;

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
			status = CommentStatus.AUDITING;
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
