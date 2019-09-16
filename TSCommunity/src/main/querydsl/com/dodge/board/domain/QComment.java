package com.dodge.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -1285435039L;

    public static final QComment comment = new QComment("comment");

    public final NumberPath<Long> b_seq = createNumber("b_seq", Long.class);

    public final StringPath c_content = createString("c_content");

    public final StringPath c_createDate = createString("c_createDate");

    public final NumberPath<Long> c_seq = createNumber("c_seq", Long.class);

    public final StringPath c_writer = createString("c_writer");

    public final NumberPath<Long> groupLayer = createNumber("groupLayer", Long.class);

    public final NumberPath<Long> groupOrd = createNumber("groupOrd", Long.class);

    public final NumberPath<Long> originNo = createNumber("originNo", Long.class);

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

