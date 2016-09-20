package com.igdb.client;

import static com.igdb.client.IgdbClient.PARAM_SEPARATOR;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractListReq extends AbstractReq {
    private Integer limit = 50;
    private Integer offset = 0;

    @Override
    public String toPath() {
        List<String> parts = new ArrayList<>();
        parts.add(super.toPath());

        if (getLimit() != null) {
            parts.add(String.format("limit=%d",
                                    getLimit()));
        }
        if (getOffset() != null) {
            parts.add(String.format("offset=%d",
                                    getOffset()));
        }
        return StringUtils.join(parts,
                                PARAM_SEPARATOR);
    }
}
