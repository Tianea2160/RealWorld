package com.jhj.realworld.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 771700909L;

    public static final QMember member = new QMember("member1");

    public final com.jhj.realworld.domain.QTimeExtend _super = new com.jhj.realworld.domain.QTimeExtend(this);

    public final ListPath<com.jhj.realworld.domain.article.Article, com.jhj.realworld.domain.article.QArticle> articles = this.<com.jhj.realworld.domain.article.Article, com.jhj.realworld.domain.article.QArticle>createList("articles", com.jhj.realworld.domain.article.Article.class, com.jhj.realworld.domain.article.QArticle.class, PathInits.DIRECT2);

    public final StringPath bio = createString("bio");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final StringPath email = createString("email");

    public final ListPath<Member, QMember> followers = this.<Member, QMember>createList("followers", Member.class, QMember.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

