package com.ftr.dgb.payments.action.catalog.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import com.ftr.dgb.payments.action.catalog.domain.enumeration.ActionType;

/**
 * A DTO for the {@link com.ftr.dgb.payments.action.catalog.domain.CategoryAction} entity.
 */
@ApiModel(description = "Payment transaction action.")
public class CategoryActionDTO implements Serializable {
    
    private String id;

    @NotNull
    private UUID uuid;

    private ActionType actionType;

    @NotNull
    private String name;

    @NotNull
    private Boolean enabled;

    @NotNull
    private UUID categoryId;

    @Size(min = 4, max = 4)
    private String mcc;

    private Integer defaultOrderId;

    private String iconUrl;

    private String regions;

    private String tags;

    private String source;

    private String processId;

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

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
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
        if (!(o instanceof CategoryActionDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryActionDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", actionType='" + getActionType() + "'" +
            ", name='" + getName() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", categoryId='" + getCategoryId() + "'" +
            ", mcc='" + getMcc() + "'" +
            ", defaultOrderId=" + getDefaultOrderId() +
            ", iconUrl='" + getIconUrl() + "'" +
            ", regions='" + getRegions() + "'" +
            ", tags='" + getTags() + "'" +
            ", source='" + getSource() + "'" +
            ", processId='" + getProcessId() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
