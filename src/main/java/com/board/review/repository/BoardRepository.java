package com.board.review.repository;

import com.board.review.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query(value="update Board set views = views + 1 where id = :id")
    void updateViews(@Param("id") Long id);

    @Modifying //(clearAutomatically = true)
    @Query("update Board set likes = likes + 1 where id = :id")
    void updateLikes(@Param("id") Long id);
}
