package cn.com.ut.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.entity.DsImportHistory;

public interface DsImportHistoryRepository extends JpaRepository<DsImportHistory, String> {

}
