package com.igdb.client;

import static com.igdb.client.IgdbClient.PARAM_SEPARATOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GamesReq extends AbstractListReq {
    private String search;
    private List<FilterItem> filters = new ArrayList<>();

    public GamesReq id(Long id) {
        addId(id);
        return this;
    }

    public GamesReq ids(List<Long> ids) {
        addIds(ids);
        return this;
    }

    public GamesReq field(String field) {
        addField(field);
        return this;
    }

    public GamesReq fields(List<String> fields) {
        addFields(fields);
        return this;
    }

    public GamesReq limit(Integer limit) {
        setLimit(limit);
        return this;
    }

    public GamesReq offset(Integer offset) {
        setOffset(offset);
        return this;
    }

    public GamesReq search(String search) {
        setSearch(search);
        return this;
    }

    public GamesReq filter(FilterItem filter) {
        return filters(Arrays.asList(filter));
    }

    public GamesReq filters(List<FilterItem> filters) {
        addFilters(filters);
        return this;
    }

    public void addFilters(List<FilterItem> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            return;
        }
        filters.stream()
                        .filter(flt -> !getFilters().contains(flt))
                        .forEach(flt -> getFilters().add(flt));
    }

    @Override
    public String toPath() {
        List<String> parts = new ArrayList<>();
        parts.add(super.toPath());

        if (StringUtils.isNotBlank(getSearch())) {
            parts.add(getSearch());
        }

        if (CollectionUtils.isNotEmpty(getFilters())) {
            parts.add(StringUtils.join(getFilters(),
                                       PARAM_SEPARATOR));
        }
        return StringUtils.join(parts,
                                PARAM_SEPARATOR);
    }

    public static GamesReq of() {
        return new GamesReq();
    }
}
