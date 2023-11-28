package com.crmapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.crmapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

        @Query("SELECT u FROM User u WHERE "
                        + "(:first_name is null or u.first_name like %:first_name%) AND "
                        + "(:last_name is null or u.last_name like %:last_name%) AND "
                        + "(:email is null or u.email like %:email%) AND "
                        + "(:phone is null or u.phone like %:phone%) AND "
                        + "(:address is null or u.address like %:address%)")
        Page<User> findByContaining(@Param("first_name") String first_name,
                        @Param("last_name") String last_name,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("address") String address, Pageable pageable);

        @Query("SELECT u FROM User u WHERE "
                        + "(:query is null or u.first_name like %:query%) OR "
                        + "(:query is null or u.last_name like %:query%) OR "
                        + "(:query is null or u.email like %:query%) OR "
                        + "(:query is null or u.phone like %:query%) OR "
                        + "(:query is null or u.address like %:query%)")
        Page<User> findByQuery(@Param("query") String query, Pageable pageable);
}
