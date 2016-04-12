package com.langpath.service.api;

import com.common.service.api.CrudApi;
import com.langpath.data.model.entity.word.WordGroup;
import com.langpath.data.model.helps.AggregationWordGroup;

import java.util.Collection;

/**
 * Created by Sebastian on 2016-03-18.
 */
public interface WordGroupServiceApi extends CrudApi<WordGroup, Long> {

    Collection<AggregationWordGroup> getAggregationWordGroup();

}
