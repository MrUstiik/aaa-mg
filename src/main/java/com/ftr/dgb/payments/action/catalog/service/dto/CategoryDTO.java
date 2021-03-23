package com.ftr.dgb.payments.action.catalog.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link com.ftr.dgb.payments.action.catalog.domain.Category} entity.
 */
@ApiModel(description = "Payment transaction category.")
public class CategoryDTO implements Serializable {
    
    private String id;

    @NotNull
    private UUID uuid;

    @NotNull
    private String name;

    @NotNull
    private Boolean enabled;

    private UUID parentCategoryId;

    @Size(min = 4, max = 4)
    private String mcc;

    private String iconUrl;

    private Integer defaultOrderId;

    private String regions;

    private String tags;

    @NotNull
    private ZonedDateTime addedDate;

    @NotNull
    private ZonedDateTime updatedDate;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
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
