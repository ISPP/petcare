package com.ispp.petcare.repository;

import com.ispp.petcare.domain.MessageFolder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MessageFolder entity.
 */
public interface MessageFolderRepository extends JpaRepository<MessageFolder,Long> {

    @Query("select messageFolder from MessageFolder messageFolder where messageFolder.user.login = ?#{principal.username}")
    List<MessageFolder> findByUserIsCurrentUser();

}
