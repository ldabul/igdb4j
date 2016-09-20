package com.igdb.client;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformsReq extends AbstractListReq {
    public PlatformsReq id(Long id) {
        addId(id);
        return this;
    }

    public PlatformsReq ids(List<Long> ids) {
        addIds(ids);
        return this;
    }

    public PlatformsReq field(String field) {
        addField(field);
        return this;
    }

    public PlatformsReq fields(List<String> fields) {
        addFields(fields);
        return this;
    }

    public PlatformsReq limit(Integer limit) {
        setLimit(limit);
        return this;
    }

    public PlatformsReq offset(Integer offset) {
        setOffset(offset);
        return this;
    }

    public static PlatformsReq of() {
        return new PlatformsReq();
    }
}
