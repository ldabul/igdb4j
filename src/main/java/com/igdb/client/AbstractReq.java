package com.igdb.client;

import static com.igdb.client.IgdbClient.QUERY_SEPARATOR;
import static com.igdb.client.IgdbClient.VALUE_SEPARATOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public abstract class AbstractReq {
    private List<Long> ids = new ArrayList<>();
    private List<String> fields = new ArrayList<>();

    public void addId(Long id) {
        addIds(Arrays.asList(id));
    }

    public void addIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ids.stream()
                        .filter(id -> !getIds().contains(id))
                        .forEach(id -> getIds().add(id));
    }

    public void addField(String field) {
        addFields(Arrays.asList(field));
    }

    public void addFields(List<String> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        fields.stream()
                        .filter(fld -> !getFields().contains(fld))
                        .forEach(fld -> getFields().add(fld));
    }

    public String toPath() {
        List<String> parts = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(getIds())) {
            parts.add(StringUtils.join(getIds(),
                                       VALUE_SEPARATOR));
        }
        parts.add(QUERY_SEPARATOR);
        parts.add(String.format("fields=%s",
                                CollectionUtils.isEmpty(getFields()) ? "*" : StringUtils.join(getFields(),
                                                                                              VALUE_SEPARATOR)));

        return StringUtils.join(parts.toArray());
    }
}
