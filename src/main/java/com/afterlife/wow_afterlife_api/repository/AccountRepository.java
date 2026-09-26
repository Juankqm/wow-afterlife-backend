package com.afterlife.wow_afterlife_api.repository;
import java.util.Optional;
import com.afterlife.wow_afterlife_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, Integer> {
            Optional<Account> findByUsername(String username);
}