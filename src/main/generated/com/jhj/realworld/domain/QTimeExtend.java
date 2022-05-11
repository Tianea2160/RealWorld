package com.jhj.realworld.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimeExtend is a Querydsl query type for TimeExtend
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QTimeExtend extends EntityPathBase<TimeExtend> {

    private static final long serialVersionUID = -1292570456L;

    public static final QTimeExtend timeExtend = new QTimeExtend("timeExtend");

    public final DateTimePath<java.time.LocalDateTime> created = createDateTime("created", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modified = createDateTime("modified", java.time.LocalDateTime.class);

    public QTimeExtend(String variable) {
        super(TimeExtend.class, forVariable(variable));
    }

    public QTimeExtend(Path<? extends TimeExtend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeExtend(PathMetadata metadata) {
        super(TimeExtend.class, metadata);
    }

}

