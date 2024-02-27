package com.blinked.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.blinked.entities.enums.ProductStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Product base entity.
 *
 * @author ssatwa
 */
@Data
@Entity(name = "BaseProduct")
@Table(name = "products", indexes = { @Index(name = "products_type_status", columnList = "type, status"),
		@Index(name = "products_create_time", columnList = "create_time") })
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int default 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseProduct extends AuditUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "com.blinked.utils.CustomIdGenerator")
	private Integer id;

	/**
	 * Post title.
	 */
	@Column(name = "title", nullable = false)
	private String title;

	/**
	 * Post status.
	 */
	@Column(name = "status")
	@ColumnDefault("1")
	private ProductStatus status;

	/**
	 * Product url.
	 */
//    @Deprecated
	@Column(name = "url")
	private String url;

	/**
	 * Product slug.
	 */
	@Column(name = "slug", unique = true)
	private String slug;

	/**
	 * Original content,not format.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@Column(name = "template", nullable = false)
	private String template;

	/**
	 * Product summary.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@Column(name = "summary")
	private String summary;

	/**
	 * Cover thumbnail of the product.
	 */
	@Column(name = "thumbnail", length = 1023)
	private String thumbnail;

	/**
	 * Product visits.
	 */
	@Column(name = "visits")
	@ColumnDefault("0")
	private Long visits;

	/**
	 * Whether to allow rating.
	 */
	@Type(type = "numeric_boolean")
	@Column(name = "disallow_rate")
	@ColumnDefault("0")
	private Boolean disallowRate;

	/**
	 * Whether to allow sale.
	 */
	@Type(type = "numeric_boolean")
	@Column(name = "active_sale")
	@ColumnDefault("0")
	private Boolean activeSale;

	/**
	 * Whether to allow offer.
	 */
	@Type(type = "numeric_boolean")
	@Column(name = "active_offer")
	@ColumnDefault("0")
	private Boolean activeOffer;

	/**
	 * Product price.
	 */
	@Column(name = "price")
	private double price;

	/**
	 * Product offer price.
	 */
	@Column(name = "offer_price")
	private double offerPrice;

	/**
	 * Product offer price.
	 */
	@Column(name = "tax_precentage")
	private double taxPrecentage;

	/**
	 * Whether to top the post.
	 */
	@Column(name = "top_priority")
	@ColumnDefault("0")
	private Integer topPriority;

	/**
	 * Likes
	 */
	@Column(name = "likes")
	@ColumnDefault("0")
	private Long likes;

	/**
	 * Edit time.
	 */
	@Column(name = "edit_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date editTime;

	/**
	 * Meta keywords.
	 */
	@Column(name = "meta_keywords", length = 511)
	private String metaKeywords;

	/**
	 * Meta description.
	 */
	@Column(name = "meta_description", length = 1023)
	private String metaDescription;

	/**
	 * Content word count
	 */
	@Column(name = "word_count")
	@ColumnDefault("0")
	private Long wordCount;

	@Override
	public void prePersist() {
		super.prePersist();

		if (editTime == null) {
			editTime = getCreateTime();
		}

		if (status == null) {
			status = ProductStatus.DRAFT;
		}

		if (summary == null) {
			summary = "";
		}

		if (thumbnail == null) {
			thumbnail = "";
		}

		if (disallowRate == null) {
			disallowRate = false;
		}

		if (price == 0) {
			price = 0;
		}

		if (template == null) {
			template = "";
		}

		if (topPriority == null) {
			topPriority = 0;
		}

		if (visits == null || visits < 0) {
			visits = 0L;
		}

		if (likes == null || likes < 0) {
			likes = 0L;
		}

		
		if (wordCount == null || wordCount < 0) {
			wordCount = 0L;
		}
	}

}
