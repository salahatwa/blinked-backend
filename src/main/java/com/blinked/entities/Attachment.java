package com.blinked.entities;

import org.hibernate.annotations.ColumnDefault;

import com.api.common.attachment.AttachmentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Attachment entity
 *
 * @author ssatwa
 * @date 2019-03-12
 */
@Data
@Entity
@Table(name = "attachments", indexes = { @Index(name = "attachments_media_type", columnList = "media_type"),
		@Index(name = "attachments_create_time", columnList = "create_time") })
public class Attachment extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Attachment name.
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Attachment access path.
	 */
	@Column(name = "path", length = 1023, nullable = false)
	private String path;

	/**
	 * File key: oss file key or local file key (Just for deleting)
	 */
	@Column(name = "file_key", length = 2047)
	private String fileKey;

	/**
	 * Thumbnail access path.
	 */
	@Column(name = "thumb_path", length = 1023)
	private String thumbPath;

	/**
	 * Attachment media type.
	 */
	@Column(name = "media_type", length = 127, nullable = false)
	private String mediaType;

	/**
	 * Attachment suffix,such as png, zip, mp4, jpge.
	 */
	@Column(name = "suffix", length = 50)
	private String suffix;

	/**
	 * Attachment width.
	 */
	@Column(name = "width")
	@ColumnDefault("0")
	private Integer width;

	/**
	 * Attachment height.
	 */
	@Column(name = "height")
	@ColumnDefault("0")
	private Integer height;

	/**
	 * Attachment size.
	 */
	@Column(name = "size", nullable = false)
	private Long size;

	/**
	 * Attachment upload type,LOCAL,UPYUN or QNYUN.
	 */
	@Column(name = "type")
	@ColumnDefault("0")
	private AttachmentType type;

	@Column(name = "active", nullable = false)
	@ColumnDefault("true")
	private Boolean active = true;

}
