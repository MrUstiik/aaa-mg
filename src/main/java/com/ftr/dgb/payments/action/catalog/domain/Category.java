package com.ftr.dgb.payments.action.catalog.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Payment transaction category.
 */
@Document(collection = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("uuid")
    private UUID uuid;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("enabled")
    private Boolean enabled;

    @Field("parent_category_id")
    private UUID parentCategoryId;

    @Field("mcc")
    private String mcc;

    @Field("icon_url")
    private String iconUrl;

    @Field("default_order_id")
    private Integer defaultOrderId;

    @Field("regions")
    private String regions;

    @Field("tags")
    private String tags;

    @NotNull
    @Field("added_date")
    private ZonedDateTime addedDate;

    @NotNull
    @Field("updated_date")
    private ZonedDateTime updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Category uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Category enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getParentCategoryId() {
        return parentCategoryId;
    }

    public Category parentCategoryId(UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    public void setParentCategoryId(UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getMcc() {
        return mcc;
    }

    public Category mcc(String mcc) {
        this.mcc = mcc;
        return this;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Category iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public Category defaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
        return this;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public String getRegions() {
        return regions;
    }

    public Category regions(String regions) {
        this.regions = regions;
        return this;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTags() {
        return tags;
    }

    public Category tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public Category addedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public void setAddedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public Category updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", parentCategoryId='" + getParentCategoryId() + "'" +
            ", mcc='" + getMcc() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            ", defaultOrderId=" + getDefaultOrderId() +
            ", regions='" + getRegions() + "'" +
            ", tags='" + getTags() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
