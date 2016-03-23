package com.langpath.service.api;

import com.langpath.sql.model.entity.word.WordGroup;
import com.langpath.sql.model.helps.AggregationWordGroup;

import java.util.Collection;

/**
 * Created by Sebastian on 2016-03-18.
 */
public interface WordGroupServiceApi extends CommonApi<WordGroup, Long>{

    Collection<AggregationWordGroup> getAggregationWordGroup();

}
