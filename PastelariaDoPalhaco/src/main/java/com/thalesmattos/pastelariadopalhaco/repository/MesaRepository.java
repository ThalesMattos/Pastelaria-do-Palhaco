package com.thalesmattos.pastelariadopalhaco.repository;

import com.thalesmattos.pastelariadopalhaco.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {}
