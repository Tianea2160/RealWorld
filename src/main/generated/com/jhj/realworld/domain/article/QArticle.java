package com.jhj.realworld.domain.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -1533851587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final com.jhj.realworld.domain.QTimeExtend _super = new com.jhj.realworld.domain.QTimeExtend(this);

    public final StringPath body = createString("body");

    public final ListPath<com.jhj.realworld.domain.comment.Comment, com.jhj.realworld.domain.comment.QComment> comments = this.<com.jhj.realworld.domain.comment.Comment, com.jhj.realworld.domain.comment.QComment>createList("comments", com.jhj.realworld.domain.comment.Comment.class, com.jhj.realworld.domain.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.jhj.realworld.domain.like.Like, com.jhj.realworld.domain.like.QLike> likes = this.<com.jhj.realworld.domain.like.Like, com.jhj.realworld.domain.like.QLike>createList("likes", com.jhj.realworld.domain.like.Like.class, com.jhj.realworld.domain.like.QLike.class, PathInits.DIRECT2);

    public final com.jhj.realworld.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public final StringPath slug = createString("slug");

    public final ListPath<com.jhj.realworld.domain.tag.Tag, com.jhj.realworld.domain.tag.QTag> tags = this.<com.jhj.realworld.domain.tag.Tag, com.jhj.realworld.domain.tag.QTag>createList("tags", com.jhj.realworld.domain.tag.Tag.class, com.jhj.realworld.domain.tag.QTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.jhj.realworld.domain.member.QMember(forProperty("member")) : null;
    }

}

