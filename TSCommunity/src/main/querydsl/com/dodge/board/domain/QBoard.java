package com.dodge.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1284876904L;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardCheck = createString("boardCheck");

    public final NumberPath<Long> cCnt = createNumber("cCnt", Long.class);

    public final NumberPath<Long> cnt = createNumber("cnt", Long.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath fileSize = createString("fileSize");

    public final NumberPath<Long> groupLayer = createNumber("groupLayer", Long.class);

    public final NumberPath<Long> groupOrd = createNumber("groupOrd", Long.class);

    public final NumberPath<Long> likeCnt = createNumber("likeCnt", Long.class);

    public final StringPath newDate = createString("newDate");

    public final StringPath originalFileName = createString("originalFileName");

    public final NumberPath<Long> originNo = createNumber("originNo", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> type = createNumber("type", Long.class);

    public final StringPath writer = createString("writer");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

