package com.itciwen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//完成date access层，jpa部分
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
